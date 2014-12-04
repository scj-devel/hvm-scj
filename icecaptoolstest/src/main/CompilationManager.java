package main;

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
import icecaptools.compiler.ICompilationRegistry;
import icecaptools.compiler.NativeMethodDetector;
import icecaptools.compiler.TestCompilationRegistry;
import icecaptools.conversion.ConversionConfiguration;

import java.io.FileOutputStream;

import test.icecaptools.compiler.TestConversionConfiguration;

public class CompilationManager {
	public static void main(String args[]) throws Throwable {
		boolean aotCompile = false;
		boolean includeJMLMethods = true;
		String pathSeparator = System.getProperty("path.separator");

		/*System.out.println("Press any key to start");
		Scanner in = new Scanner(System.in);
		in.next();*/
		// String outputFolder = "";
		// String inputFolder = "/home/workspace/Modbus/bin";
		// String inputPackage = "main";
		// String inputClass = "Main";
		// String sourceFileName = null;

		// String sourceFileName =
		// "/home/sek/workspace/icecaptools/src/test/icecapvm/minitests/TestSCJminiCDj.java";
		// String inputFolder = "/home/sek/workspace/SCJ_HVM/classes";

		/* HSO begin ********************************************************* */

		String sourceFileName = null;

		// String inputFolder = "/home/hso/java/SCJ_Workspace/SCJ_HVM/classes";
		// String inputFolder = "/home/hso/java/SCJ_Workspace/icecapSDK/bin";
		// String inputFolder = "/home/hso/java/SCJ_Workspace/icecaptools/bin";
		// String inputFolder = "/home/hso/java/SCJ_Workspace/EmbeddedSDJ/bin";

		String inputFolder = "/home/skr/workspace/SCJJMLTest/bin/" + pathSeparator
				+ "/home/skr/workspace/jml4c/exe/jml4c.jar";

		// String outputFolder = "/home/hso/java/SCJ_Workspace/icecaptools/";

		// String inputPackage = "test.safetycritical.cyclicschedule1";
		// String inputPackage = "test.safetycritical.priorityschedule0";
		// String inputPackage = "test.safetycritical.oneshot1";
		// String inputPackage = "test.safetycritical.sharedResource1";
		// String inputPackage = "test.safetycritical.boundedbuffer";
		// String inputPackage = "test.safetycritical.sleepingQueue1";
		// String inputPackage = "test.safetycritical.twoMissions";
		// String inputPackage = "test.safetycritical.executeInAreaOf";
		// String inputPackage = "test.safetycritical.enterPrivateMemory";
		// String inputPackage =
		// "test.safetycritical.priorityscheduleMemAreaNesting";
		// String inputPackage = "test.safetycritical.time.test1";

		// String inputPackage = "esdj.scj.test.example1";
		// String inputPackage = "esdj.scj.priorityschedule0";
		// String inputPackage = "esdj.scj.cyclicschedule1";

		// String inputClass = "MyApp";
		// String inputClass = "TestSCJSleepingQueue1";
		// String inputClass = "TestSCJBoundedBuffer";
		// String inputClass = "TestSCJSharedResource1";
		// String inputClass = "MyAppTestCase";
		// String inputClass = "Test_ESDJ_Example1";

		/* JML tests */

		// String inputPackage = "jml.account.test";

		// String inputPackage = "javax.realtime.test";
		// String inputPackage = "javax.safetycritical.test";

		String inputClass = "AllTests";

		// String inputClass = "Main2Clock";
		// String inputClass = "Main2RealtimeClock";
		// String inputClass = "Issue";

		/* JML tests end */

		// String inputClass = "TestSCJBoundedBuffer";
		// String inputClass = "TestSCJSleepingQueue1";

		// String inputClass = "TestSCJTwoMissions";

		// String inputClass = "TestSCJCyclicSchedule3";

		/* HSO end ********************************************************** */

		// String inputPackage = "jml.test.apr";

		String inputPackage = "jml.account.test";
		// String inputClass = "TestSCJBoundedBuffer";

		// String inputPackage = "test.icecapvm.minitests";

		// String inputClass = "TestAllocationArea2";

		// String inputPackage = "test.icecapvm.minitests";
		// String inputClass = "TestLMul";

		// String inputPackage = "test.safetycritical.priorityschedule2";

		// String inputPackage = "test.safetycritical.cyclicschedule2";

		// String inputPackage = "test.safetycritical.twoMissions";
		// String inputPackage = "minicdj";

		// String inputClass = "TestExecuteWithStack2";

		// String inputClass = "AllTests";

		// String inputClass = "TestSCJBoundedBuffer";
		// String inputClass = "TestSCJSleepingQueue1";

		// String inputClass = "TestSCJTwoMissions";
		// String inputClass = "TestSCJCyclicSchedule3";

		// String inputPackage = "test.icecaptools";
		// String inputClass = "TestAll";

		/* HSO end ************************************************** */

		// String sourceFileName = null;

		// String pathSeparator = System.getProperty("path.separator");

		// String inputFolder = "/home/sek/workspace/JML/src/" + pathSeparator +
		// "/home/sek/workspace/JML/jml4c.jar" + pathSeparator +
		// "/home/sek/workspace/JML/jml4rt.jar";

		// String inputFolder =
		// "/home/skr/workspace/JMLTest/bin:/home/skr/workspace/JMLTest/lib/jml4c.jar";

		String outputFolder = "";
		//String inputFolder = "/home/skr/workspace/icecaptoolstest/bin/";

		// String sourceFileName = null;
		// String inputFolder = "/home/sek/workspace/CDj/bin";
		// String inputPackage = "heap";

		//String inputClass = "TestSCJThreadMemory";

		// String sourceFileName =
		// "C:\\home\\workspace\\Modbus\\src\\main\\Main.java";
		// String inputFolder = "C:\\home\\workspace\\Modbus\\bin";
		// String inputPackage = "main";
		// String inputClass = "Main";

		// Properties properties = System.getProperties();
		// String inputFolder = "/home/sek/workspace/PeriodicBlink/bin";
		// String inputPackage = "test.CR16C";
		// String inputClass = "TestUARTWriter";
		// String sourceFileName = null;

		// String inputFolder = "";
		// String inputPackage = "test.V850ES";
		// String inputClass = "HelloV850";
		// String sourceFileName = null;

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
			cregistry = new AOTRegistry();
		} else {
			cregistry = new TestCompilationRegistry();
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
		config.setResourceManager(new TestResourceManager("/home/skr/git/hvm-scj/icecapvm/src"));

		CompilationSequence sequencer = new CompilationSequence();

		System.out.println("outputFolder = " + outputFolder);

		sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(),
				cregistry, outputFolder, true);

		sequencer = null;
		config = null;
		cregistry = null;
		System.out.println("Instancecount = " + NewList.getInstanceCount());

		System.out.println("Done. Press any key to exit");

		// in.next(); in.close();
	}
}
