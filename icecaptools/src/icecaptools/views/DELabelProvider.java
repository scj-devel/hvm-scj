package icecaptools.views;

import org.eclipse.jdt.core.IMethod;

import util.ICompilationRegistry;

public interface DELabelProvider {

    void setLabelSource(IMethod entryPoint);

    IMethod getLabelSource();

    ICompilationRegistry getCompilationRegistry();
}
