package icecaptools.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.MessageConsole;

import icecaptools.ConverterJob;
import icecaptools.RestartableMethodObserver;
import icecaptools.compiler.ArchitectureDependentCodeDetector;
import icecaptools.compiler.CompilationRegistryManager;
import icecaptools.compiler.EclipseCCodeFormatter;
import icecaptools.compiler.EclipseNativeMethodDetector;
import icecaptools.compiler.EclipseSourceCodeLinker;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.launching.HVMLaunchShortcut;
import icecaptools.views.DELabelProvider;
import icecaptools.views.DependencyView;
import test.icecaptools.DefaultCompilationRegistry;
import util.ICompilationRegistry;

public class ConvertJavaFileAction implements IObjectActionDelegate {

	private static final String CONSOLE_NAME = "Icecap tools messages";

	private IMethod entryPoint;

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public static PrintStream getConsolePrintStream() {
		return getConsolePrintStream(CONSOLE_NAME);
	}

	public static PrintStream getConsolePrintStream(String console_name) {
		MessageConsole myConsole = null;

		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (console_name.equals(existing[i].getName())) {
				myConsole = (MessageConsole) existing[i];
				break;
			}
		}
		if (myConsole == null) {
			myConsole = new MessageConsole(console_name, null);
			conMan.addConsoles(new IConsole[] { myConsole });
		}

		IOConsoleOutputStream stream = myConsole.newOutputStream();
		PrintStream print = new PrintStream(stream);
		return print;
	}

	public static void bringConsoleToFront(String console_name, boolean clear) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (console_name.equals(existing[i].getName())) {
				conMan.showConsoleView(existing[i]);
				if (clear) {
					if (existing[i] instanceof IOConsole) {
						((IOConsole) existing[i]).clearConsole();
					}
				}
				return;
			}
		}
	}

	public static void bringConsoleToFront(boolean clear) {
		bringConsoleToFront(CONSOLE_NAME, clear);
	}

	private static PrintStream out = null;

	@Override
	public void run(IAction action) {
		if (entryPoint != null) {
			try {
				if (entryPoint.isMainMethod()) {
					if (out == null) {
						out = getConsolePrintStream();
					}
					IJavaProject javaProject = entryPoint.getJavaProject();

					RestartableMethodObserver methodObserver = getProgressView();

					DELabelProvider deLabelProvider = (DELabelProvider) methodObserver;

					deLabelProvider.setLabelSource(entryPoint);

					ConversionConfiguration config = new ConversionConfiguration();

					try {
						ICompilationUnit selectedJavaFile = entryPoint.getCompilationUnit();
						String fileName = selectedJavaFile.getElementName();
						IResource resource = selectedJavaFile.getCorrespondingResource();
						if (resource != null) {
							IPath fileLocation = resource.getLocation();

							if (fileLocation != null) {
								config.setInputSourceFileName(fileLocation.toOSString());
							}
						}

						config.setProjectResource(javaProject);

						StringBuffer classPath = getClasspathFromProject(javaProject);

						config.setClassPath(classPath.toString());

						IPackageDeclaration[] packageDeclarations = selectedJavaFile.getPackageDeclarations();

						String packageName;

						if (packageDeclarations.length == 1) {
							packageName = packageDeclarations[0].getElementName();
						} else {
							packageName = ConversionConfiguration.DEFAULT_PACKAGE_NAME;
						}

						config.setInputPackage(packageName);
						config.setInputClass(fileName);
						config.setEntryPointMethodName(entryPoint.getElementName());

						config.setCodeFormatter(new EclipseCCodeFormatter());
						config.setSourceCodeLinker(new EclipseSourceCodeLinker(javaProject));
						config.setReportConversion(true);
						config.setCodeDetector(new ArchitectureDependentCodeDetector());
						config.setNativeMethodDetector(new EclipseNativeMethodDetector());
						Job converterJob;

						CompilationRegistryManager cm = new CompilationRegistryManager();

						cm.addRegistry(new DefaultCompilationRegistry());

						BaseCompilationRegistryProxy sourceRegistry = getSourceDefinedRegistry(selectedJavaFile, classPath);
						cm.addRegistry(sourceRegistry);

						cm.addRegistry(deLabelProvider.getCompilationRegistry());

						converterJob = new ConverterJob("Converting from " + selectedJavaFile.getElementName(),
								methodObserver, config, out, cm, sourceRegistry.getLoader());

						converterJob.schedule();

					} catch (JavaModelException e) {
						out.println(e.getMessage());
					} catch (Exception e) {
						out.println(e.getMessage());
					} finally {
						out.flush();
					}
				}
			} catch (JavaModelException e) {
				out.println(e.getMessage());
				out.flush();
			}
		}
	}

	private BaseCompilationRegistryProxy getSourceDefinedRegistry(ICompilationUnit selectedJavaFile, StringBuffer classPath) {
		IType type = selectedJavaFile.findPrimaryType();
		String mainClass = type.getFullyQualifiedName();
		URL[] urls;
		URLClassLoader loader = null;

		try {
			urls = HVMLaunchShortcut.getURLs(classPath);
			loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());

			Class<?> mainClazz = loader.loadClass(mainClass);

			Object instance = mainClazz.newInstance();

			return new CompilationRegistryProxy(mainClazz, instance, loader);

		} catch (Throwable t) {
			return new BaseCompilationRegistryProxy(loader);
		} 
	}

	public static StringBuffer getClasspathFromProject(IJavaProject javaProject) throws JavaModelException {
		IPath outputLocation;
		IResource projectRessource = javaProject.getCorrespondingResource();

		IPath projectLocation = projectRessource.getLocation().removeLastSegments(1);

		outputLocation = javaProject.getOutputLocation();

		StringBuffer classPath = new StringBuffer(projectLocation.toOSString() + outputLocation.toOSString());

		String[] requiredProjects = javaProject.getRequiredProjectNames();

		for (String projectName : requiredProjects) {
			IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

			if (p != null) {
				classPath.append(File.pathSeparator);
				classPath.append(p.getRawLocation());
				classPath.append(File.separatorChar);
				classPath.append("bin");
			} else {
			}

		}

		IClasspathEntry[] requiredLibraries = javaProject.getRawClasspath();
		for (IClasspathEntry entry : requiredLibraries) {
			switch (entry.getContentKind()) {
			case IPackageFragmentRoot.K_BINARY:
				IPath cpentry = entry.getPath();
				String cpitem = cpentry.toOSString();
				classPath.append(System.getProperty("path.separator"));
				classPath.append(projectLocation);
				classPath.append(cpitem);
				break;
			case IPackageFragmentRoot.K_SOURCE:
				if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {

				}
				entry = null;
				break;
			default:
				break;
			}
		}
		return classPath;
	}

	private DependencyView getProgressView() {
		IWorkbench wb = icecaptools.Activator.getDefault().getWorkbench();
		IWorkbenchWindow activeWindow = wb.getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWindow.getActivePage();
		IViewPart dependencyExtentViewPart = activePage.findView(DependencyView.VIEWID);
		DependencyView dependencyExtentView = null;

		if (dependencyExtentViewPart != null) {
			if (dependencyExtentViewPart instanceof DependencyView) {
				dependencyExtentView = (DependencyView) dependencyExtentViewPart;
			}
		} else {
			try {
				dependencyExtentViewPart = activePage.showView(DependencyView.VIEWID, null,
						org.eclipse.ui.IWorkbenchPage.VIEW_CREATE);
				if (dependencyExtentViewPart instanceof DependencyView) {
					dependencyExtentView = (DependencyView) dependencyExtentViewPart;
				}
			} catch (PartInitException e) {
			}
		}
		if (dependencyExtentView != null) {
			activePage.bringToTop(dependencyExtentView);
		}
		return dependencyExtentView;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection != null) {
			if (selection instanceof TreeSelection) {
				TreeSelection treeSelection = (TreeSelection) selection;
				Object firstElement = treeSelection.getFirstElement();
				if (firstElement instanceof IMethod) {
					IMethod iMethod = (IMethod) firstElement;
					this.entryPoint = iMethod;
				}
			}
		}
	}
	
	private static class BaseCompilationRegistryProxy implements ICompilationRegistry 
	{
		private URLClassLoader loader;

		public BaseCompilationRegistryProxy(URLClassLoader loader) {
			this.loader = loader;
		}

		public URLClassLoader getLoader() {
			return loader;
		}
		
		@Override
		public boolean didICareHuh() {
			return false;
		}

		@Override
		public boolean isMethodCompiled(String clazz, String targetMethodName, String targetMethodSignature) {
			return false;
		}

		@Override
		public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
			return false;
		}

		@Override
		public boolean alwaysClearOutputFolder() {
			return false;
		}		
	}

	private static class CompilationRegistryProxy extends BaseCompilationRegistryProxy  {

		private Object delegate;

		private Method didICareHuhMethod;
		private Method isMethodCompiledMethod;
		private Method isMethodExcludedMethod;
		private Method alwaysClearOutputFolderMethod;

		public CompilationRegistryProxy(Class<?> mainClazz, Object delegate, URLClassLoader loader)
				throws NoSuchMethodException, SecurityException {
			super(loader);
			this.delegate = delegate;

			didICareHuhMethod = mainClazz.getMethod("didICareHuh", new Class[0]);

			isMethodCompiledMethod = mainClazz.getMethod("isMethodCompiled",
					new Class[] { String.class, String.class, String.class });

			isMethodExcludedMethod = mainClazz.getMethod("isMethodExcluded",
					new Class[] { String.class, String.class, String.class });

			alwaysClearOutputFolderMethod = mainClazz.getMethod("alwaysClearOutputFolder", new Class[0]);
		}

		@Override
		public boolean didICareHuh() {
			try {
				boolean result = (boolean) didICareHuhMethod.invoke(delegate, new Object[0]);
				return result;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println("problem!");
			}
			return false;
		}

		@Override
		public boolean isMethodCompiled(String clazz, String targetMethodName, String targetMethodSignature) {
			try {
				boolean result = (boolean) isMethodCompiledMethod.invoke(delegate, clazz, targetMethodName, targetMethodSignature );
				return result;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println("problem!");
			}
			return false;
		}

		@Override
		public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
			try {
				boolean result = (boolean) isMethodExcludedMethod.invoke(delegate, clazz, targetMethodName, targetMethodSignature );
				return result;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println("problem!");
			}
			return false;
		}

		@Override
		public boolean alwaysClearOutputFolder() {
			try {
				boolean result = (boolean) alwaysClearOutputFolderMethod.invoke(delegate, new Object[0]);
				return result;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
			return false;
		}
	}
}
