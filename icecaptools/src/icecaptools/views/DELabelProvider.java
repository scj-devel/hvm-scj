package icecaptools.views;

import icecaptools.compiler.ICompilationRegistry;

import org.eclipse.jdt.core.IMethod;

public interface DELabelProvider {

    void setLabelSource(IMethod entryPoint);

    IMethod getLabelSource();

    ICompilationRegistry getCompilationRegistry();
}
