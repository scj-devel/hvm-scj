package main;

import java.io.FileOutputStream;

import icecaptools.CompilationSequence;
import icecaptools.HVMProperties;
import icecaptools.NewList;
import icecaptools.TestResourceManager;
import icecaptools.compiler.AOTRegistry;
import icecaptools.compiler.CodeDetector;
import icecaptools.compiler.DefaultIcecapCodeFormatter;
import icecaptools.compiler.DefaultIcecapProgressMonitor;
import icecaptools.compiler.DefaultIcecapSourceCodeLinker;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.compiler.NativeMethodDetector;
import icecaptools.conversion.ConversionConfiguration;
import test.icecaptools.DefaultCompilationRegistry;
import test.icecaptools.compiler.TestConversionConfiguration;
import util.ICompilationRegistry;

public class CompilationManager {
	static boolean aotCompile = false;
	static boolean includeJMLMethods = false;
	static String sourceFileName = null;
	static String inputClass = "Main";
	static String inputPackage = "jembench";
	static String outputFolder = "";
	static String inputFolder = "/home/skr/workspace/jembench/bin";
	static String vmSourceFolder = "/home/skr/git/hvm-scj/icecapvm/src";

	public static class JMLCompilationRegistry extends DefaultCompilationRegistry {
		private boolean doIcareHuh;

		@Override
		public boolean isMethodCompiled(String clazz, String targetMethodName, String targetMethodSignature) {
			doIcareHuh = true;
			if (clazz.contains("jml")) {
				return true;
			}
			if (clazz.startsWith("sun.security.action.GetPropertyAction")) {
				return true;
			}
			if (clazz.startsWith("java.io.BufferedWriter")) {
				return true;
			}

			if (clazz.startsWith("java.io.PrintStream")) {
				return true;
			}
			doIcareHuh = false;
			return super.isMethodCompiled(clazz, targetMethodName, targetMethodSignature);
		}

		@Override
		public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
			doIcareHuh = true;
			if (clazz.startsWith("sun.")) {
				if (clazz.startsWith("sun.security.action.GetPropertyAction")) {
					return false;
				}
				return true;
			}
			if (clazz.startsWith("java.util.concurrent")) {
				return true;
			}
			if (clazz.startsWith("java.io")) {
				if (clazz.startsWith("java.io.PrintStream")) {
					return false;
				}
				if (clazz.startsWith("java.io.FilterOutputStream")) {
					return false;
				}
				if (clazz.startsWith("java.io.Writer")) {
					return false;
				}
				if (clazz.startsWith("java.io.BufferedWriter")) {
					return false;
				}
				if (clazz.startsWith("java.io.OutputStreamWriter")) {
					return false;
				}
				if (clazz.startsWith("java.io.OutputStream") && targetMethodName.contains("init")) {
					return false;
				}
				return true;
			}
			if (clazz.startsWith("java.nio")) {
				return true;
			}
			if (clazz.startsWith("java.lang.Thread")) {
				return true;
			}
			if (clazz.startsWith("java.lang.ClassLoader")) {
				return true;
			}
			if (clazz.startsWith("java.lang.System")) {
				if (targetMethodName.startsWith("initProperties")) {
					return true;
				}
				if (targetMethodName.startsWith("setErr0")) {
					return true;
				}
				if (targetMethodName.startsWith("setIn0")) {
					return true;
				}
				if (targetMethodName.startsWith("setOut")) {
					return true;
				}
				if (targetMethodName.startsWith("getProperty")) {
					return true;
				}
			}
			doIcareHuh = false;
			return super.isMethodExcluded(clazz, targetMethodName, targetMethodSignature);
		}

		@Override
		public boolean alwaysClearOutputFolder() {
			return true;
		}

		@Override
		public boolean didICareHuh() {
			return doIcareHuh;
		}
	}
	
	private static void setDefaults(HVMProperties props) {
		if (props.getProperty("inputClass") != null) {
			inputClass = props.getProperty("inputClass");
		}

		if (props.getProperty("inputPackage") != null) {
			inputPackage = props.getProperty("inputPackage");
		}

		if (props.getProperty("outputFolder") != null) {
			outputFolder = props.getProperty("outputFolder");
		}

		if (props.getProperty("inputFolder") != null) {
			inputFolder = props.getProperty("inputFolder");
		}

		if (props.getProperty("vmSourceFolder") != null) {
			vmSourceFolder = props.getProperty("vmSourceFolder");
		}

		if (props.getProperty("aotCompile") != null) {
			if (props.getProperty("aotCompile").equals("true")) {
				aotCompile = true;
			}
		}

		if (props.getProperty("includeJMLMethods") != null) {
			if (props.getProperty("includeJMLMethods").equals("true")) {
				includeJMLMethods = true;
			}
		}
	}

	public static void main(String args[]) throws Throwable {
		ConversionConfiguration config = new TestConversionConfiguration();

		config.setInputSourceFileName(sourceFileName);

		HVMProperties props = config.getProperties();

		setDefaults(props);

		String propertiesFileName = config.getPropertiesFileName();

		if (propertiesFileName != null) {
			System.out.println("Loaded properties from [" + propertiesFileName + "]");
		} else {
			System.out.println("Using default properties");
		}

		if (args.length < 1) {
			System.out.println("Using default values\nUsage: CompilationManager " + inputFolder + " " + inputPackage
					+ " " + inputClass);
		} else {
			if (args.length >= 4) {
				inputFolder = args[0];
				inputPackage = args[1];
				inputClass = args[2];
				outputFolder = args[3];
				if (args.length == 5) {
					if ("-aot".compareTo(args[4]) == 0) {
						aotCompile = true;
					}
				}
			} else {
				if (args.length == 1) {
					if ("-aot".compareTo(args[0]) == 0) {
						aotCompile = true;
					}
				} else {
					System.out.println("Parameter count mismatch");
				}
			}
			System.out.print("CompilationManager");
			for (int i = 0; i < args.length; i++) {
				System.out.print(" " + args[i]);
			}
			System.out.println();
		}
		ICompilationRegistry cregistry;

		if (aotCompile) {
			cregistry = new AOTRegistry(new DefaultCompilationRegistry());
		} else {
			cregistry = new DefaultCompilationRegistry();
		}

		props.setIncludeJMLMethods(includeJMLMethods);
		config.setClassPath(inputFolder);
		config.setInputPackage(inputPackage);
		config.setInputClass(inputClass);
		config.setCodeFormatter(new DefaultIcecapCodeFormatter());
		config.setSourceCodeLinker(new DefaultIcecapSourceCodeLinker());
		config.setCodeDetector(new CodeDetector() {

			@Override
			public void newRead(char next) {

			}

			@Override
			public void fileStart(String resourceName, FileOutputStream writer) {

			}
		});
		config.setNativeMethodDetector(new NativeMethodDetector() {

			@Override
			public void startAnalysis() {
			}

			@Override
			public void endAnalysis() {
			}
		});

		config.setResourceManager(new TestResourceManager(vmSourceFolder));

		CompilationSequence sequencer = new CompilationSequence();

		System.out.println("outputFolder = " + outputFolder);
		config.setOutputFolder(outputFolder);
		sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(),
				cregistry, true);

		sequencer = null;
		config = null;
		cregistry = null;
		System.out.println("Instancecount = " + NewList.getInstanceCount());

		System.out.println("Done. Press any key to exit");
	}

}
