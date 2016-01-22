package icecaptools.launching;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;

import icecaptools.actions.ConvertJavaFileAction;

public class HVMLaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof TreeSelection) {
			TreeSelection s = (TreeSelection) selection;
			Object receiver = s.getFirstElement();
			if (receiver != null) {
				IType type = null;
				if (receiver instanceof ICompilationUnit) {
					type = getCompilationUnitType((ICompilationUnit) receiver);
				} else if (receiver instanceof IType) {
					type = getSourceType((IType) receiver);
				} else if (receiver instanceof IMethod) {
					type = getMethodType((IMethod) receiver);
				}
				if (type != null) {
					launch(type);
				}
			}
		}
	}

	private void launch(IType type) {
		String mainClass = type.getFullyQualifiedName();

		try {
			StringBuffer classPath = ConvertJavaFileAction.getClasspathFromProject(type.getJavaProject());

			

			try {
				URL[] urls = getURLs(classPath);

				URLClassLoader loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());

				try {
					Class<?> mainClazz = loader.loadClass(mainClass);
					try {
						Object instance = mainClazz.newInstance();

						Method m;
						try {
							m = mainClazz.getMethod("getOutputFolder", new Class[0]);
							String outputFolder = (String) m.invoke(instance, new Object[0]);

							m = mainClazz.getMethod("getBuildCommands", new Class[0]);
							String[] buildCommands = (String[]) m.invoke(instance, new Object[0]);

							m = mainClazz.getMethod("getJavaHeapSize", new Class[0]);
							int heapSize = (Integer) m.invoke(instance, new Object[0]);

							m = mainClazz.getMethod("getDeployCommand", new Class[0]);
							String deployCommand = (String) m.invoke(instance, new Object[0]);

							launch(type, outputFolder, buildCommands, heapSize, deployCommand);

							try {
								loader.close();
							} catch (IOException e) {
							}
						} catch (NoSuchMethodException | SecurityException e) {
						} catch (IllegalArgumentException | InvocationTargetException e1) {
						}
					} catch (InstantiationException | IllegalAccessException e) {
					}
				} catch (ClassNotFoundException e) {
				}
			} catch (MalformedURLException e) {
			}
		} catch (JavaModelException e1) {
		}
	}

	public static URL[] getURLs(StringBuffer classPath) throws MalformedURLException {
		String[] elements = classPath.toString().split(System.getProperty("path.separator"));

		URL[] urls = new URL[elements.length];
		
		for (int i = 0; i < elements.length; i++) {
			urls[i] = new File(elements[i]).toURI().toURL();
		}
		return urls;
	}

	private void launch(IType type, String outputFolder, String[] buildCommands, int heapSize, String deployCommand) {
		ILaunchManager launchManager;

		launchManager = DebugPlugin.getDefault().getLaunchManager();

		if (launchManager != null) {
			try {
				ILaunchConfiguration[] launchConfigurations = launchManager.getLaunchConfigurations();

				for (ILaunchConfiguration launchConfiguration : launchConfigurations) {
					ILaunchConfigurationType configType = launchConfiguration.getType();
					if ("HVM Generic Launcher".equals(configType.getName())) {
						IResource[] resources = launchConfiguration.getMappedResources();
						if (resources != null) {
							if (resources.length == 1) {
								IResource typeResource = type.getUnderlyingResource();
								IResource targetResource = resources[0];
								if (typeResource.equals(targetResource)) {
									launch(launchConfiguration.getWorkingCopy(), outputFolder, buildCommands, heapSize,
											deployCommand);
									return;
								}
							}
						}
					}
				}

				ILaunchConfigurationType[] launchConfigurationTypes = launchManager.getLaunchConfigurationTypes();

				for (ILaunchConfigurationType lct : launchConfigurationTypes) {
					String name = lct.getName();
					if ("HVM Generic Launcher".equals(name)) {

						ILaunchConfigurationWorkingCopy wc = null;
						try {
							ILaunchConfigurationType configType = lct;
							wc = configType.newInstance(null,
									launchManager.generateLaunchConfigurationName(type.getTypeQualifiedName('.')));
							wc.setMappedResources(new IResource[] { type.getUnderlyingResource() });
							launch(wc, outputFolder, buildCommands, heapSize, deployCommand);
						} catch (CoreException exception) {
							AbstractHVMPOSIXLaunchConfigurationDelegate.notify(exception.getStatus().getMessage());
						}
					}
					name = null;
				}
			} catch (CoreException e) {
			}
		}
	}

	private void launch(ILaunchConfigurationWorkingCopy wc, String outputFolder, String[] buildCommands, int heapSize,
			String deployCommand) {
		ILaunchConfiguration config;
		try {
			wc.setAttribute(TargetSpecificLauncherTab.SOURCE_FOLDER, outputFolder);
			wc.setAttribute(CommonLauncherTab.COMPILER_COMMAND, compilerCommandToString(buildCommands));
			wc.setAttribute(TargetSpecificLauncherTab.HEAPSIZE, heapSize);
			wc.setAttribute(GenericLauncherTab.DEPLOYCOMMAND, deployCommand);
			config = wc.doSave();
			DebugUITools.launch(config, ILaunchManager.RUN_MODE);
		} catch (CoreException e) {
		}
	}

	public static String compilerCommandToString(String[] buildCommands) {
		StringBuffer strBuffer = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator");

		for (int i = 0; i < buildCommands.length; i++) {
			strBuffer.append(buildCommands[i]);
			if (i < buildCommands.length - 1) {
				strBuffer.append(lineSeparator);
			}
		}
		return strBuffer.toString();
	}

	public static String[] compilerCommandFromString(String buildCommands) {
		return buildCommands.split(System.getProperty("line.separator"));
	}

	private IType getMethodType(IMethod receiver) {
		IType type = receiver.getDeclaringType();
		while (type.getDeclaringType() != null) {
			type = type.getDeclaringType();
		}
		return type;
	}

	private IType getSourceType(IType receiver) {
		IType type = receiver;
		while (type.getDeclaringType() != null) {
			type = type.getDeclaringType();
		}
		return type;
	}

	private IType getCompilationUnitType(ICompilationUnit receiver) {
		IType type = receiver.findPrimaryType();
		return type;
	}

	@Override
	public void launch(IEditorPart delection, String mode) {
		System.out.println("launch editorpart");
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection arg0) {
		System.out.println("getLaunchConfigurations");
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart arg0) {
		System.out.println("getLaunchConfigurations");
		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection arg0) {
		System.out.println("getLaunchableResource");
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart arg0) {
		System.out.println("getLaunchableResource");
		return null;
	}

}
