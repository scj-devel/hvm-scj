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

public class CompilationManagerHSO {

	private static class JMLCompilationRegistry implements ICompilationRegistry {
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
			return false;
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
			return false;
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

	public static void main(String args[]) throws Throwable {
		boolean aotCompile = false;
		boolean includeJMLMethods = true;
		String pathSeparator = System.getProperty("path.separator");

		/* HSO begin ********************************************************* */

		String sourceFileName = null;

		//String inputFolder = "/home/hso/workspace/SCJ_HVM/bin";

		//String inputFolder = "/home/hso/workspace/icecapSDK/bin";
		// String inputFolder = "/home/hso/workspace/icecaptools/bin";
		//String inputFolder = "/home/hso/workspace/icecaptoolstest/bin";
		// String inputFolder = "/home/hso/workspace/EmbeddedSDJ/bin";

		// For JML test  with jml4c:
		//String inputFolder = "/home/hso/java/SCJ_Workspace/SCJJMLTest/bin/" +
		// pathSeparator + "/home/hso/java/SCJ_Workspace/JML/jml4c.jar";

		// For OpenJML test:
		String inputFolder = "/home/hso/workspace/OpenJMLTest/bin/" + pathSeparator
				+ "/home/hso/workspace/OpenJMLTest/lib/jmlruntime.jar" + pathSeparator
				+ "/home/hso/git/hvm-scj/icecapSDK/bin/";

		String outputFolder = "";

		// String inputPackage = "test.safetycritical.cyclicschedule3";
		//String inputPackage = "test.safetycritical.priorityschedule0";
		// String inputPackage = "test.safetycritical.oneshot1";
		// String inputPackage = "test.safetycritical.sharedResource1";
		// String inputPackage = "test.safetycritical.boundedbuffer";
		// String inputPackage = "test.safetycritical.sleepingQueue1";
		//String inputPackage = "test.safetycritical.twoMissions";
		// String inputPackage = "test.safetycritical.executeInAreaOf";
		//String inputPackage = "test.safetycritical.enterPrivateMemory";
		// String inputPackage =
		// "test.safetycritical.priorityscheduleMemAreaNesting";

		//String inputPackage = "test";

		// String inputPackage = "esdj.scj.test.example1";
		// String inputPackage = "esdj.scj.priorityschedule0";
		// String inputPackage = "esdj.scj.cyclicschedule1";

		// String inputClass = "MyApp";
		// String inputClass = "TestSCJSleepingQueue1";
		// String inputClass = "TestSCJBoundedBuffer";
		// String inputClass = "TestSCJSharedResource1";
		// String inputClass = "MyAppTestCase";
		// String inputClass = "Test_ESDJ_Example1";

		/* Level 1-2 tests */

		//String inputPackage = "test.safetycritical.managedMemoryTest1";
		//String inputClass = "TestManagedMemory";

		//String inputPackage = "test.safetycritical.memoryModelTest3";
		//String inputClass = "MySCJ";

		//String inputPackage = "test.safetycritical.level2ThreadTest1";
		//String inputClass = "MyApp";
		//String inputClass = "TestSCJLevel2Thread0";

		//String inputPackage = "test.safetycritical.level2NestedSequencerTest1";
		//String inputClass = "MySCJ";

		//String inputPackage = "test.safetycritical.waitAndNotifyTest2";
		//String inputClass = "MySCJ";

		//        String inputPackage = "test.safetycritical.cyclicschedule1";
		//        String inputClass = "MyApp";

		//String inputPackage = "test.safetycritical.SPlevel2";
		//String inputClass = "TestSPLevel2";

		/* Level 1-2 tests  end */

		/* OpenJML tests */

		//String inputPackage = "test";
		//String inputClass = "JMLTest1";

		//String inputPackage = "account";       

		//String inputPackage = "javax.realtime.test.timeClasses";
		//String inputPackage = "javax.realtime.test.clock";
		//String inputPackage = "javax.realtime.test.memoryArea";
		//String inputPackage = "javax.realtime.test.priorityScheduler";

		//String inputPackage = "javax.safetycritical.test.safelet1"; 
		//String inputPackage = "javax.safetycritical.test.safelet22"; 
		//String inputPackage = "javax.safetycritical.test.priorityScheduling";
		//String inputPackage = "javax.safetycritical.test.cyclicschedule1";
		//String inputPackage = "javax.safetycritical.test.cyclicexecutive1";

		String inputPackage = "javax.realtime.test";
		//String inputPackage = "javax.safetycritical.test";

		//String inputClass = "AllTests";

		/* --- TCKs of javax.realtime.test --- : */
		//String inputClass = "TckTestMemoryParameters";
		String inputClass = "TckTestConfigurationParameters";
		//String inputClass = "TckTestReleaseParameters";
		//String inputClass = "TckTestPeriodicParameters";
		//String inputClass = "TckTestAperiodicParameters";
		//String inputClass = "TckTestPriorityScheduler";        
		//String inputClass = "TckTestPriorityParameters";   
		//String inputClass = "TckTestHighResolutionTime";
		//String inputClass = "TckTestAbsoluteTime";        
		//String inputClass = "TckTestRelativeTime";

		/* --- TCKs of javax.safetycritical.test --- : */
		//String inputClass = "TckTestStorageParameters";
		//String inputClass = "SafeletStub1"; //   TckTestCyclicExecutive1
		//String inputClass = "TckTestFrame1";
		//String inputClass = "TckTestCyclicSchedule1";  //            SafeletStub
		//String inputClass = "TckTestCyclicSchedule3";
		//String inputClass = "TckTestSafelet1";
		//String inputClass = "TckTestSafelet22";   //           SafeletStub22

		//String inputClass = "TckTestPreemptiveScheduling1";

		/* OpenJML tests end */

		/* JML tests */

		//String inputPackage = "jml.account.test";

		//String inputPackage = "javax.realtime.test.clock";
		//String inputPackage = "javax.realtime.test.priorityParameters";
		//String inputPackage = "javax.realtime.test.releaseParameters";
		//String inputPackage = "javax.realtime.test.timeClasses";

		//String inputPackage = "javax.safetycritical.test.priorityScheduling";
		//String inputPackage = "javax.safetycritical.test.cyclicExecutive"; 
		//String inputPackage = "javax.safetycritical.test.storageParameters";
		//String inputClass = "AllTests";

		//String inputClass = "Main2Clock";
		// String inputClass = "Main2RealtimeClock";
		// String inputClass = "Issue";

		/* JML tests end */

		/* HSO end ************************************************** */

		if (args.length < 1) {
			System.out.println("Using default values\nUsage: CompilationManager " + inputFolder + " " + inputPackage
					+ " " + inputClass);
		} else {
			if (args.length >= 3) {
				inputFolder = args[0];
				inputPackage = args[1];
				inputClass = args[2];
				if (args.length == 4) {
					if ("-aot".compareTo(args[3]) == 0) {
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
			cregistry = new JMLCompilationRegistry();
		}

		ConversionConfiguration config = new TestConversionConfiguration();

		config.setInputSourceFileName(sourceFileName);

		HVMProperties props = config.getProperties();
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
		config.setResourceManager(new TestResourceManager("/home/hso/java/SCJ_Workspace/icecapvm/src"));

		CompilationSequence sequencer = new CompilationSequence();

		System.out.println("outputFolder = " + outputFolder);
		config.setOutputFolder(outputFolder);
		sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(),
				cregistry, true);

		sequencer = null;
		config = null;
		cregistry = null;
		System.out.println("Instancecount = " + NewList.getInstanceCount());
		/*
		 * System.out.println("Done. Press any key to exit");
		 * 
		 * in.next(); in.close();
		 */
	}
}
