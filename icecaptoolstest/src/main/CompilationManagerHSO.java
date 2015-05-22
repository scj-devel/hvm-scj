package main;

import icecaptools.CompilationSequence;
import icecaptools.HVMProperties;
import icecaptools.MethodOrFieldDesc;
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
import icecaptools.conversion.ConversionConfiguration;

import java.io.FileOutputStream;

import test.icecaptools.compiler.TestConversionConfiguration;

public class CompilationManagerHSO {
	
	private static class JMLCompilationRegistry implements ICompilationRegistry
	{

		@Override
		public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
			if (mdesc.getClassName().contains("jml")) {
				return true;
			}
			if (mdesc.getClassName().startsWith("sun.security.action.GetPropertyAction")) {
				return true;
			}
			if (mdesc.getClassName().startsWith("java.io.BufferedWriter")) {
				return true;
			}

			if (mdesc.getClassName().startsWith("java.io.PrintStream")) {
				return true;
			}
			return false;
		}

		@Override
		public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
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
			return false;
		}

		@Override
		public boolean alwaysClearOutputFolder() {
			return true;
		}

	}
    public static void main(String args[]) throws Throwable {
        boolean aotCompile = false;
        boolean includeJMLMethods = true;
        String pathSeparator = System.getProperty("path.separator");
        
        /* HSO begin ********************************************************* */

        String sourceFileName = null;

        //String inputFolder = "/home/hso/java/SCJ_Workspace/SCJ_HVM/bin";
        
        // String inputFolder = "/home/hso/java/SCJ_Workspace/icecapSDK/bin";
        // String inputFolder = "/home/hso/java/SCJ_Workspace/icecaptools/bin";
        //String inputFolder = "/home/hso/java/SCJ_Workspace/icecaptoolstest/bin";
        // String inputFolder = "/home/hso/java/SCJ_Workspace/EmbeddedSDJ/bin";

        // For JML test  with jml4c:
        //String inputFolder = "/home/hso/java/SCJ_Workspace/SCJJMLTest/bin/" +
        // pathSeparator + "/home/hso/java/SCJ_Workspace/JML/jml4c.jar";

        // For OpenJML test:
        String inputFolder = "/home/hso/java/SCJ_Workspace/OpenJMLTest/bin/" +
         pathSeparator + "/home/hso/java/SCJ_Workspace/OpenJMLTest/lib/jmlruntime.jar" +
         pathSeparator + "/home/hso/git/hvm-scj/icecapSDK/bin/";
        
        
        String outputFolder = "";

        // String inputPackage = "test.safetycritical.cyclicschedule3";
        //String inputPackage = "test.safetycritical.priorityschedule0";
        // String inputPackage = "test.safetycritical.oneshot1";
        // String inputPackage = "test.safetycritical.sharedResource1";
        // String inputPackage = "test.safetycritical.boundedbuffer";
        // String inputPackage = "test.safetycritical.sleepingQueue1";
        // String inputPackage = "test.safetycritical.twoMissions";
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
         
         
         /* Level 1-2 tests  end */
        
        /* OpenJML tests */
        
        //String inputPackage = "test";
        //String inputClass = "JMLTest1";
        
        //String inputPackage = "account";       
       
        
        //String inputPackage = "javax.realtime.test.timeClasses";
        //String inputPackage = "javax.realtime.test.clock";
        //String inputPackage = "javax.realtime.test.memoryArea";
        //String inputPackage = "javax.safetycritical.test.cyclic";
        //String inputPackage = "javax.safetycritical.test.safelet";        
        //String inputPackage = "javax.safetycritical.test.priorityScheduling";
        
        //String inputPackage = "javax.realtime.test";
        String inputPackage = "javax.safetycritical.test";
        
        //String inputClass = "AllTests";
        //String inputClass = "TckTestCyclicSchedule3";
        String inputClass = "TckTestSafelet1";
        
        //String inputClass = "TckTestMemoryParameters";
        //String inputClass = "TckTestPriorityScheduler";
        //String inputClass = "TckTestAperiodicParameters";
        //String inputClass = "TckTestPriorityParameters";        
        //String inputClass = "TckTestAbsoluteTime";        
        //String inputClass = "TckTestRelativeTime";
        
        //String inputClass = "TckTestStorageParameters";
        //String inputClass = "TckTestFrame2";
        //String inputClass = "TckTestCyclicSchedule0";
        
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
            System.out.println("Using default values\nUsage: CompilationManager " + inputFolder + " " + inputPackage + " " + inputClass);
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

        sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(), cregistry, outputFolder, true);

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
