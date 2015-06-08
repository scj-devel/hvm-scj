package main;

import icecaptools.CompilationSequence;
import icecaptools.IcecapProgressMonitor;
import icecaptools.MethodOrFieldDesc;
import icecaptools.TestResourceManager;
import icecaptools.compiler.CodeDetector;
import icecaptools.compiler.DefaultIcecapCodeFormatter;
import icecaptools.compiler.DefaultIcecapProgressMonitor;
import icecaptools.compiler.DefaultIcecapSourceCodeLinker;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.compiler.ICompilationRegistry;
import icecaptools.compiler.NativeMethodDetector;
import icecaptools.compiler.TestCompilationRegistry;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.launching.ShellCommand;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

import test.icecaptools.compiler.TestConversionConfiguration;

/*
 * To run the automated tests make sure that gcc is installed and can be 
 * started from a normal command prompt.
 * 
 * If you run cygwin, include the path to gcc in your path environment 
 * variable (usually C:\cygwin\bin). Test that it works by starting gcc from
 * a CMD prompt (not a cygwin prompt). After changing environment variables, it may
 * be necessary to restart eclipse.
 * 
 * Then select your gcc compile command from one of the predefined commands below 
 * (look at the beginning of the 'compileAndExecute' method
 * 
 */
public class TestAll {

	private static String icecapvmSrcPath;

	public static void main(String[] args) throws Throwable {
		new TestAll().performTest();
	}

	protected void performTest() throws Exception, Throwable {
		File testsDirectory;
		String cwd = new File(".").getAbsolutePath();
		StringTokenizer strt = new StringTokenizer(cwd, File.separatorChar + "");
		StringBuffer path = new StringBuffer();
		path.append(File.separatorChar);
		String nextToken = strt.nextToken();

		while (strt.hasMoreTokens()) {
			path.append(nextToken);
			path.append(File.separatorChar);
			nextToken = strt.nextToken();
		}

		icecapvmSrcPath = getVMSource(path);
		icecapvmSrcPath = icecapvmSrcPath + "src";

		String inputFolder = getInputFolder(path);

		StringBuffer outputFolderPath = new StringBuffer(System.getProperty("java.io.tmpdir"));
		outputFolderPath.append(File.separatorChar);
		outputFolderPath.append("hvm");

		File outputFolder = new File(outputFolderPath.toString());
		outputFolder.mkdir();

		File[] files = outputFolder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				File[] loaderFiles = file.listFiles();
				for (File lfile : loaderFiles) {
					lfile.delete();
					if (lfile.exists()) {
						throw new Exception("Cannot delete: " + lfile.getAbsolutePath());
					}
				}
			}
			file.delete();
			if (file.exists()) {
				throw new Exception("Cannot delete: " + file.getAbsolutePath());
			}
		}

		Iterator<File> testsDirectories = getTestDirectories(path);

		while (testsDirectories.hasNext()) {
			testsDirectory = testsDirectories.next();
			if (testsDirectory.isDirectory()) {
				String[] tests = testsDirectory.list();
				Arrays.sort(tests);

				ArrayList<String> testlist = new ArrayList<String>();
				for (int i = 0; i < tests.length; i++) {
					File candidate = new File(testsDirectory.getAbsolutePath() + File.separatorChar + (String) tests[i]);
					if (candidate.isFile()) {
						testlist.add((String) tests[i]);
					}
				}

				/*
				 * testlist = new ArrayList<String>();
				 * testlist.add("TestFloat.java"); testlist.add("TestReturn.java");
				 * testlist.add("ANTTestMethodCall.java");
				 * testlist.add("TestBug3.java");
				 * testlist.add("ANTTestInvokeVirtual.java");
				 */

				int testNo = 0;
				for (String test : testlist) {
					if (includeFileInTest(test)) {
						if (!skipIt(test)) {
							System.out.println("------------------ " + test + " ------------------");
							testIt(test, inputFolder, outputFolder, testNo++, testsDirectory);
						}
					}
				}

			}
		}
		System.out.println("------------------ done ------------------");
	}

	protected Iterator<File> getTestDirectories(StringBuffer path) {
		File testsDirectory;
		path.append("src");
		path.append(File.separatorChar);
		path.append("test");
		path.append(File.separatorChar);
		testsDirectory = new File(path.toString());
		return Arrays.asList(new File[] { testsDirectory }).iterator();
	}

	protected String getVMSource(StringBuffer path) {
		return path.toString().replace("icecaptoolstest", "icecapvm");
	}

	protected String getInputFolder(StringBuffer path) {
		return path.toString() + "bin" + File.separatorChar + "test" + File.separatorChar;
	}

	protected boolean includeFileInTest(String test) {
		return test.endsWith(".java");
	}

	private static String[] skippedClasses = { "TestSCJWaitAndNotify2.java", "TestSCJLevel2Thread0.java",
			"TestSCJStep0.java",
			/* "TestCalculator.java",
			 "TestNewFloat.java",
			 */"TestLong.java", "TestMiniTests.java", "TestCAS.java" };

	// private static String[] skippedClasses = { /*"TestStackScan1.java",
	// "TestReflectClasses2.java", "TestObjectTraversal.java", */
	// "TestReflectMethod.java", "TestLong.java", "TestMiniTests.java",
	// "TestCAS.java", "TestCalculator.java", "TestNewFloat.java"
	// /*"TestArrayList.java", "TestHashMap.java", "TestHashSet.java",
	// "TestIntegerCache.java"*/ };

	private static boolean skipIt(String test) {
		for (int i = 0; i < skippedClasses.length; i++) {
			if (test.contains(skippedClasses[i])) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	private static class CompileAllRegistry extends TestCompilationRegistry {

		@Override
		public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
			if (mdesc.getName().contains("main")) {
				return false;
			}
			return true;
		}
	}

	private void testIt(String testClass, String inputFolder, File outputFolder, int testNo, File testsDirectory)
			throws Throwable {
		ConversionConfiguration config = new TestConversionConfiguration();

		String inputPackage = getInputPackage(testsDirectory);

		preCompile(inputPackage, testClass);

		config.setInputSourceFileName(null);
		config.setClassPath(inputFolder);
		config.setInputPackage(inputPackage);
		config.setInputClass(testClass);
		config.setCodeFormatter(new DefaultIcecapCodeFormatter());
		config.setSourceCodeLinker(new DefaultIcecapSourceCodeLinker());

		config.setResourceManager(new TestResourceManager(icecapvmSrcPath));
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
		ICompilationRegistry cregistry;

		cregistry = new TestCompilationRegistry();

		// cregistry = new CompileAllRegistry();

		CompilationSequence sequencer = new CompilationSequence();

		sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(),
				cregistry, outputFolder.toString(), true);

		compileAndExecute(outputFolder, testClass, testNo);
	}

	protected void preCompile(String inputPackage, String testClass) throws Exception {
		// TODO Auto-generated method stub

	}

	protected String getInputPackage(File testsDirectory) {
		return "test";
	}

	private void compileAndExecute(File outputFolder, String testClass, int testNo) throws Exception {
		String exe = "a" + testNo + ".exe";
		String prefix = "sudo ";
		String gccCommand = getGCCCommand() + exe;

		/* Patmos, fails at TestCVar1.java */
		// prefix = "pasim ";

		String executablePath = outputFolder.getAbsolutePath() + File.separatorChar + exe;
		File executable = new File(executablePath);

		deleteIfExists(outputFolder, exe);

		ShellCommand.executeCommand(gccCommand, System.out, true, outputFolder.getAbsolutePath(), null, 0,
				new IcecapProgressMonitor() {

					@Override
					public void worked(String string) {
					}

					@Override
					public boolean isCanceled() {
						return false;
					}

					@Override
					public void worked(int i) {
					}

					@Override
					public void subTask(String string) {
					}

				});
		if (!executable.exists()) {

			throw new Exception("Compilation failed " + executable.getAbsolutePath());
		}

		int returnValue = ShellCommand.executeCommand(prefix + executablePath, System.out, true,
				outputFolder.getAbsolutePath(), null, 0, new IcecapProgressMonitor() {

					@Override
					public void worked(String string) {
					}

					@Override
					public boolean isCanceled() {
						return false;
					}

					@Override
					public void worked(int i) {
					}

					@Override
					public void subTask(String string) {
					}
				});

		if (returnValue != 0) {
			throw new Exception("Test failed [" + testClass + "]");
		}
	}

	protected String getGCCCommand() {
		/* for 64 bit Linux */
		String gccCommand = "gcc -Wall -pedantic -Os -DPC64 -DREF_OFFSET -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 -L/usr/lib64 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c native_scj.c print.c x86_64_interrupt.s -lpthread -lrt -lm -o ";

		/* for 32 bit Linux */
		//String gccCommand =
		//"gcc -Wall -pedantic -Os -DPC32 -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c native_scj.c print.c x86_32_interrupt.s -lpthread -lrt -lm -o ";

		/* for 64 bit Windows using cygwin */
		// String gccCommand =
		// "gcc -Wall -pedantic -Werror -Os -DPC64 -DREF_OFFSET -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 -L/usr/lib64 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c native_scj.c print.c x86_64_cygwin_interrupt.s -lpthread -lrt -lm -o "

		/* for 32 bit Windows using cygwin */
		// String gccCommand =
		// "gcc -Wall -pedantic -Os -DPC32 -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c native_scj.c print.c x86_32_cygwin_interrupt.s -lpthread -lrt -lm -o "

		/* Patmos, fails at TestCVar1.java */
		// String gccCommand =
		// "patmos-clang -DPACKED= -Wall -pedantic -Os -DPC32 -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=1024000 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c print.c -o "

		// prefix = "pasim ";
		return gccCommand;
	}

	private static void deleteIfExists(File outputFolder, String name) throws Exception {
		File file = new File(outputFolder.getAbsolutePath() + File.separatorChar + name);
		if (file.exists()) {
			if (!file.delete()) {
				throw new Exception("Unable to delete " + file.getAbsolutePath());
			}
		}

	}
}
