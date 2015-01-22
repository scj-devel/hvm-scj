package icecaptools.compiler.aot;

import icecaptools.AnalysisObserver;
import icecaptools.ClassManager;
import icecaptools.IcecapTool;
import icecaptools.compiler.ByteCodePatcher;
import icecaptools.compiler.ICompilationRegistry;
import icecaptools.compiler.IDGenerator;
import icecaptools.compiler.StaticInitializersManager;
import icecaptools.compiler.utils.ClassInheritanceMatrix;
import icecaptools.conversion.DependencyExtent;

public class AOTToolBox {
    private ClassManager manager;
    private IcecapTool tool;
    private ByteCodePatcher patcher;
    private String currentClassName;
    private ICompilationRegistry cregistry;
    private DependencyExtent extent;
    private IDGenerator idGen;
    private AnalysisObserver observer;
    private ClassInheritanceMatrix classMatrix;
    
    public AOTToolBox(ClassManager manager, DependencyExtent extent, IcecapTool tool, ByteCodePatcher patcher, String currentClassName, ICompilationRegistry cregistry, StaticInitializersManager siManager, IDGenerator idGen, AnalysisObserver observer, ClassInheritanceMatrix classMatrix) {
        this.manager = manager;
        this.extent = extent;
        this.tool = tool;
        this.patcher = patcher;
        this.currentClassName = currentClassName;
        this.cregistry = cregistry;
        this.idGen = idGen;
        this.observer = observer;
        this.classMatrix = classMatrix;
    }

    public DependencyExtent getDependencyExtent()
    {
        return extent;
    }
    
    public IcecapTool getTool() {
        return tool;
    }

    public void setCurrentClassName(String currentClassName) {
        this.currentClassName = currentClassName;
    }

    public ICompilationRegistry getCregistry() {
        return cregistry;
    }

    public String getCurrentClassName() {
        return currentClassName;
    }

    public ClassManager getManager() {
        return manager;
    }

    public ByteCodePatcher getPatcher() {
        return patcher;
    }

    public IDGenerator getIdGenerator() {
        return idGen;
    }

    public AnalysisObserver getObserver() {
        return observer;
    }
    
    public ClassInheritanceMatrix getClassMatrix()
    {
        return classMatrix;
    }
}
