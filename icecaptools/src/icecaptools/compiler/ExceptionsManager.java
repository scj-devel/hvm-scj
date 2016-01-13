package icecaptools.compiler;

import icecaptools.AnalysisObserver;
import icecaptools.IcecapIterator;
import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.utils.CheckSubClassRelationShip;
import icecaptools.compiler.utils.SubClassChecker;

import java.util.Iterator;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

public class ExceptionsManager {

    private AnalysisObserver observer;
    private JavaClass runtimException;
    private CheckSubClassRelationShip subClassChecker;
    private MemorySegment header;
    private ByteCodePatcher patcher;
    private JavaClass error;
    private JavaClass noSuchMethodException;

    public ExceptionsManager(AnalysisObserver observer, MemorySegment header, ByteCodePatcher patcher) throws ClassNotFoundException {
        this.observer = observer;
        this.runtimException = Repository.lookupClass("java.lang.RuntimeException");
        this.error = Repository.lookupClass("java.lang.Error");
        this.noSuchMethodException = Repository.lookupClass("java.lang.NoSuchMethodException");
        subClassChecker = new SubClassChecker();
        this.header = header;
        this.patcher = patcher;
    }

    public StringBuffer getDeclarations(IDGenerator idGen) throws Exception {
        StringBuffer decl = new StringBuffer();
        int numberOfRuntimeExceptions = 0;
        Iterator<String> usedClasses = observer.getUsedClasses();

        decl.append(" = {\n");
        while (usedClasses.hasNext()) {
            String nextClass = usedClasses.next();
            JavaClass currentClass = Repository.lookupClass(nextClass);
            if (subClassChecker.isSubclassOf(currentClass, runtimException) || subClassChecker.isSubclassOf(currentClass, error) || subClassChecker.isSubclassOf(currentClass, noSuchMethodException)) {
                IcecapIterator<MethodOrFieldDesc> methods = observer.getUsedMethods(nextClass);
                while (methods.hasNext()) {
                    MethodOrFieldDesc nextMethod = methods.next();
                    if (nextMethod.getName().equals("<init>")) {
                        if (nextMethod.getSignature().equals("()V")) {
                            numberOfRuntimeExceptions++;
                            decl.append("   { " + patcher.getClassNumber(nextClass) + ", " + patcher.getMethodNumber(nextMethod, idGen) + ", 0 }, \n");
                        }
                    }
                }
            }
        }
        header.print("#define NUMRUNTIMEEXCEPTIONS " + numberOfRuntimeExceptions + "\n");

        if (numberOfRuntimeExceptions == 0) {
            decl.insert(0, "#if defined(PRE_INITIALIZE_EXCEPTIONS)\nstatic ExceptionObject _exceptionObjects[1]");
            decl.append("  { 0, 0, 0 }\n");
        } else {
            decl.insert(0, "#if defined(PRE_INITIALIZE_EXCEPTIONS)\nstatic ExceptionObject _exceptionObjects[" + numberOfRuntimeExceptions + "]");
        }
        decl.append("};\n");
        decl.append("#endif\n");        
        return decl;
    }
}
