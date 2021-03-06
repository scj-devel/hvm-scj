package main;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

import icecaptools.CompilationSequence;
import icecaptools.IcecapProgressMonitor;
import icecaptools.TestResourceManager;
import icecaptools.compiler.CodeDetector;
import icecaptools.compiler.DefaultIcecapCodeFormatter;
import icecaptools.compiler.DefaultIcecapProgressMonitor;
import icecaptools.compiler.DefaultIcecapSourceCodeLinker;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.compiler.NativeMethodDetector;
import icecaptools.compiler.TestCompilationRegistry;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.launching.ShellCommand;
import test.icecaptools.compiler.TestConversionConfiguration;
import util.ICompilationRegistry;

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
 * HSO: 01-12-2017:
 *   If using the Linux Bash Shell on Windows 10, then 
 *     runningBash = true;
 *   else
 *     runningBash = false;
 *   
 */
public class TestAll {

	private static String icecapvmSrcPath;
	
	private static boolean runningBash = true;  // Using the Linux Bash Shell on Windows 10

	public static void main(String[] args) throws Throwable {
		new TestAll().performTest();
	}

	protected static class TestInformation
	{
		public String test;
		public String inputFolder;
		public File outputFolder;
		public File testsDirectory;

		public TestInformation(String test, String inputFolder, File outputFolder, File testsDirectory) {
			this.test = test;
			this.inputFolder = inputFolder;
			this.outputFolder = outputFolder;
			this.testsDirectory = testsDirectory;
		}
	}

	protected ArrayList<TestInformation> testinformation;
	
	protected void collectTests() throws Exception, Throwable {
		File testsDirectory;
		StringBuffer path = new StringBuffer();
		
		String inputFolder = setup(path);

		File outputFolder = prepareOutputFolder();

		Iterator<File> testsDirectories = getTestDirectories(path);

		testinformation = new ArrayList<TestInformation>();
		
		while (testsDirectories.hasNext()) {
			testsDirectory = testsDirectories.next();
			if (testsDirectory.isDirectory()) {
				String[] tests = testsDirectory.list();
				Arrays.sort(tests);

				ArrayList<String> testlist = new ArrayList<String>();
				for (int i = 0; i < tests.length; i++) {
					File candidate = new File(
							testsDirectory.getAbsolutePath() + File.separatorChar + (String) tests[i]);
					if (candidate.isFile()) {
						testlist.add((String) tests[i]);
					}
				}
				
				for (String test : testlist) {
					if (includeFileInTest(test)) {
						if (!skipIt(test)) {
							TestInformation ti = new TestInformation(test, inputFolder, outputFolder, testsDirectory);
							testinformation.add(ti);
						}
					}
				}
			}
		}
	}

	protected String setup(StringBuffer path) {
		String cwd = new File(".").getAbsolutePath();
		StringTokenizer strt = new StringTokenizer(cwd, File.separatorChar + "");
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
		return inputFolder;
	}

	protected File prepareOutputFolder() throws Exception {
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
		return outputFolder;
	}
	
	
	// HSO added:
	public final void performPreCompile() throws Exception, Throwable
	{
		collectTests();	
	
		int testNo = 0;
		for (TestInformation ti: testinformation)
		{
			makePreCompile(ti.test, ti.inputFolder, ti.outputFolder, testNo++, ti.testsDirectory);
		}
		//System.out.println("------------------------------ PreCompile Done ------------------------------------");
	}
	
	private void makePreCompile(String testClass, String inputFolder, File outputFolder, int testNo, File testsDirectory)
			throws Throwable {

		String inputPackage = getInputPackage(testsDirectory);

		preCompile(inputPackage, testClass);
	}
	
	// HSO end

	
	public final void performTest() throws Exception, Throwable
	{
		collectTests();
		int testNo = 0;
		for (TestInformation ti: testinformation)
		{
			testIt(ti.test, ti.inputFolder, ti.outputFolder, testNo++, ti.testsDirectory);
		}
		System.out.println("------------------------------ Done ------------------------------------");
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
		return test.endsWith(".java"); // && (!test.contains("TestTCPConnection"));
	}

	private static String[] skippedClasses = { "TestSCJWaitAndNotify2.java", "TestSCJLevel2Thread0.java",
			"TestSCJStep0.java", "TestGC1.java", "TestGCSimple.java", /* "TestCalculator.java",
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
		public boolean isMethodCompiled(String clazz, String targetMethodName, String targetMethodSignature) {
			boolean b = super.isMethodCompiled(clazz, targetMethodName, targetMethodSignature);
			boolean result = false;

			if (didICareHuh()) {
				result = b;
			} else if (targetMethodName.contains("main")) {
				result = b;
			} else if (clazz.contains("InvokeDynamic")) {
				result = b;
			} else if (clazz.contains("Thread")) {
				result = b;
			} else if (clazz.contains("Float")) {
				result = b;
			} else {
				result = true;
			}
			return result;
		}
	}

	private void testIt(String testClass, String inputFolder, File outputFolder, int testNo, File testsDirectory)
			throws Throwable {
		ConversionConfiguration config = new TestConversionConfiguration();

		String inputPackage = getInputPackage(testsDirectory);

		//preCompile(inputPackage, testClass);

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

		cregistry = getCompilationRegistry();

		// cregistry = new CompileAllRegistry();

		CompilationSequence sequencer = new CompilationSequence();
		config.setOutputFolder(outputFolder.toString());
		sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(),
				cregistry, true);

		compileAndExecute(outputFolder, testClass, testNo);
	}

	protected ICompilationRegistry getCompilationRegistry() {
		return new TestCompilationRegistry();
	}

	protected void preCompile(String inputPackage, String testClass) throws Exception {
	}

	protected String getInputPackage(File testsDirectory) {
		return "test";
	}

	private void compileAndExecute(File outputFolder, String testClass, int testNo) throws Exception {
		String exe = "a" + testNo + ".exe";
		String prefix = "sudo ";
		String gccCommand;
		
		if (runningBash)
			gccCommand = "bash -c" + " \"" + getGCCCommand(testClass, testNo) + exe + "\"";
		else
			gccCommand= getGCCCommand(testClass, testNo) + exe;		
		
		System.out.println("CMD: " + gccCommand);

		String executablePath = outputFolder.getAbsolutePath() + File.separatorChar + exe;
		File executable = new File(executablePath);
		
		System.out.println("testClass: " + testClass);
		System.out.println("outputFolder: " + outputFolder.getAbsolutePath());
		System.out.println("executablePath: " + executablePath);
		System.out.println("executable file: " + executable);

		deleteIfExists(outputFolder, exe);

		ShellCommand.executeCommand(gccCommand, System.out, true, outputFolder.getAbsolutePath(), null, 1000,
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

		System.out.println("before running ...");
		
		String runCommand;
		
		if (runningBash)
			runCommand = "bash -c" + " \" ./" + exe + "\"";
		else
			runCommand = prefix + executablePath;
		
		int returnValue = ShellCommand.executeCommand(runCommand, System.out, true,
				outputFolder.getAbsolutePath(), null, 1000, new IcecapProgressMonitor() {
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

	protected String getGCCCommand(String testClass, int testNo) {
		/* for 64 bit Linux */
		String gccCommand = "gcc -Wall -pedantic -Os -DPC64" + getPackedAttribute(testClass) + " -DPRINTFSUPPORT"
				+ getRefOffset(testNo)
				+ " -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 -L/usr/lib64 "
				+ "classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c print.c x86_64_interrupt.s "
				+ "-lpthread -lrt -lm -o ";

		/* for 32 bit Linux */
		//String gccCommand =
		//"gcc -Wall -pedantic -Os -DPC32 -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c  print.c x86_32_interrupt.s -lpthread -lrt -lm -o ";

		/* for 64 bit Windows using cygwin */
		// String gccCommand =
		// "gcc -Wall -pedantic -Werror -Os -DPC64 -DREF_OFFSET -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 -L/usr/lib64 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c  print.c x86_64_cygwin_interrupt.s -lpthread -lrt -lm -o "

		/* for 32 bit Windows using cygwin */
		// String gccCommand =
		// "gcc -Wall -pedantic -Os -DPC32 -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=10240000 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c  print.c x86_32_cygwin_interrupt.s -lpthread -lrt -lm -o "

		/* Patmos, fails at TestCVar1.java */
		// String gccCommand =
		// "patmos-clang -DPACKED= -Wall -pedantic -Os -DPC32 -DPRINTFSUPPORT -DSUPPORTGC -DJAVA_HEAP_SIZE=1024000 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c print.c -o "

		// prefix = "pasim ";
		return gccCommand;
	}

	private String getRefOffset(int testNo) {
		if (testNo % 2 == 0) {
			return " -DREF_OFFSET";
		} else {
			return " ";
		}
	}

	private String getPackedAttribute(String testClass) {
		if (testClass.contains("TestGC1")) {
			return " -DPACKED=";
		} else {
			return "";
		}

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
