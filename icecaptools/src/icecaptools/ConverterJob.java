package icecaptools;

import icecaptools.conversion.ConversionConfiguration;
import util.ICompilationRegistry;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLClassLoader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;

public class ConverterJob extends Job {

	public static CompilationSequence mostRecentSequence;

	private PrintStream out;
	private ICompilationRegistry cregistry;
	private ConversionConfiguration config;
	private RestartableMethodObserver methodObserver;
	private URLClassLoader urlClassLoader;

	public ConverterJob(String jobName, RestartableMethodObserver methodObserver, ConversionConfiguration config,
			PrintStream out, ICompilationRegistry cregistry, URLClassLoader urlClassLoader) {
		super(jobName);
		this.methodObserver = methodObserver;
		this.out = out;
		this.cregistry = cregistry;
		this.config = config;
		this.urlClassLoader = urlClassLoader;
	}

	@Override
	protected IStatus run(IProgressMonitor progressMonitor) {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		String outputFolder = preferenceStore.getString(SetOutputFolderAction.ICECAP_OUTPUTFOLDER);

		File oFile;

		if ((outputFolder != null) && (outputFolder.length() > 0)) {
			oFile = new File(outputFolder);
			if (!oFile.isDirectory()) {
				close();
				return new Status(IStatus.ERROR, "Icecaptools", IStatus.ERROR, "Illegal output folder",
						new Exception());
			}
		}
		config.setOutputFolder(outputFolder);
		CompilationSequence sequencer = new CompilationSequence();

		try {
			sequencer.startCompilation(out, methodObserver, config, new IcecapEclipseProgressMonitor(progressMonitor),
					cregistry, true);
			mostRecentSequence = sequencer;
		} catch (Throwable e) {
			mostRecentSequence = null;
			close();
			return new Status(IStatus.ERROR, "Icecaptools", IStatus.ERROR, "compilation failed", e);
		}
		close();
		return Status.OK_STATUS;
	}

	private void close() {
		if (urlClassLoader != null) {
			try {
				urlClassLoader.close();
			} catch (IOException e) {
			}
		}
	}

}
