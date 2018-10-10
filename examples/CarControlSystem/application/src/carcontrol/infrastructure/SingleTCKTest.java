package carcontrol.infrastructure;

import java.io.File;
import java.io.FileOutputStream;

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

/**
 * * HSO: 01-12-2017:
 *   If using the Linux Bash Shell on Windows 10, then 
 *     runningBash = true;
 *   else
 *     runningBash = false;
 * @author HSO
 *
 */
public class SingleTCKTest {
	
	private static boolean runningBash = true;  // Using the Linux Bash Shell on Windows 10
	
	@SuppressWarnings("rawtypes")
	private Class app;

	@SuppressWarnings("rawtypes")
	public SingleTCKTest(Class app) {
		this.app = app;
	}

	private File prepareOutputFolder() throws Exception {
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
   
	private String getInputPackage() {
		return app.getPackage().getName();
	}
		
	
	public final void performTest() throws Exception, Throwable
	{				
		File outputFolder = prepareOutputFolder();
		String inputPackage = getInputPackage();
		
		testIt(app.getSimpleName() + ".java", VMConfiguration.INPUTFOLDER, inputPackage, outputFolder);

		System.out.println("------------------------------ Done ------------------------------------");
	}
				
	private void testIt(String testClass, String inputFolder, String inputPackage, File outputFolder)
			throws Throwable {
		
		ConversionConfiguration config = new TestConversionConfiguration();
		config.setInputSourceFileName(null);
		config.setClassPath(inputFolder);
		config.setInputPackage(inputPackage);
		config.setInputClass(testClass);
		
		// HSO begin.
		try{
			System.out.println ("carcontrol.SingleTCKTest.testIt.testClass: " + testClass);
			System.out.println ("carcontrol.SingleTCKTest.testIt.inputFolder: " + inputFolder);		
			System.out.println ("carcontrol.SingleTCKTest.testIt.inputPackage: " + inputPackage);
			System.out.println ("carcontrol.SingleTCKTest.testIt.outputFolder: " + outputFolder.toString());
		
			Thread.sleep(3000);
		} catch (InterruptedException e) {	}		
		// HSO end		
		
		config.setCodeFormatter(new DefaultIcecapCodeFormatter());			
		config.setSourceCodeLinker(new DefaultIcecapSourceCodeLinker());			
		config.setResourceManager(new TestResourceManager(VMConfiguration.ICECAPVM_SRC));
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

		CompilationSequence sequencer = new CompilationSequence();
		config.setOutputFolder(outputFolder.toString());
		sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(),
				cregistry, true);
		
		compileAndExecute(outputFolder, testClass);
	}
		
	private ICompilationRegistry getCompilationRegistry() {
		return new TestCompilationRegistry();
	}
	
	private void compileAndExecute(File outputFolder, String testClass) throws Exception {
		
		System.out.println ("*** carcontrol.infrastructure.SingleTckTest.compileAndExecute: testClass = " + testClass);
		
		String exe = "a.exe";
		String prefix = "sudo ";  // " ";
		
		String gccCommand; // = VMConfiguration.GCC_COMMAND + exe;
		
		if (runningBash)
			gccCommand = "bash -c" + " \"" + VMConfiguration.GCC_COMMAND + exe + "\"";
		else
			gccCommand= VMConfiguration.GCC_COMMAND + exe;;		
		
		System.out.println("CMD: " + gccCommand);
		
		String executablePath = outputFolder.getAbsolutePath() + File.separatorChar + exe;
		File executable = new File(executablePath);

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

			throw new Exception("carcontrol.infrastructure.SingleTCKTest: Compilation failed " + executable.getAbsolutePath());
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
			throw new Exception("carcontrol.infrastructure.SingleTCKTest: Test failed [" + testClass + "]" + "; returnValue = " + returnValue);
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


