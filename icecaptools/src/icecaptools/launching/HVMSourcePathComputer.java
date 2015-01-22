package icecaptools.launching;

import icecaptools.CompilationSequence;
import icecaptools.ConverterJob;
import icecaptools.conversion.ConversionConfiguration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.WorkspaceSourceContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaProjectSourceContainer;

public class HVMSourcePathComputer implements ISourcePathComputerDelegate {

    @Override
    public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException {
        ISourceContainer sourceContainer = null;
        CompilationSequence compilationSequence = ConverterJob.mostRecentSequence;
        if (compilationSequence != null) {
            ConversionConfiguration config = compilationSequence.getConfig();
            IJavaProject resource = config.getProjectResource();
            sourceContainer = new JavaProjectSourceContainer(resource);
        }
        if (sourceContainer == null) {
            sourceContainer = new WorkspaceSourceContainer();
        }
        ISourceContainer[] container = new ISourceContainer[1];
        container[0] = sourceContainer;
        return container;
    }
}
