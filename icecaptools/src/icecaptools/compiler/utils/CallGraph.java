package icecaptools.compiler.utils;

import icecaptools.BNode;
import icecaptools.ClassfileUtils;
import icecaptools.HackInterfaceMethodCallBNode;
import icecaptools.IcecapIterator;
import icecaptools.MethodEntryPoints;
import icecaptools.MethodIdentifier;
import icecaptools.MethodOrFieldDesc;
import icecaptools.VirtualOrInterfaceMethodCallBNode;
import icecaptools.compiler.ICompilationRegistry;
import icecaptools.conversion.DependencyExtent;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class CallGraph {

    public static class CGInfo implements MethodIdentifier {
        String locationClass;
        String locationMethod;
        String locationMethodSignature;

        String targetClassName;
        String targetMethodName;
        String targetMethodSignature;

        LinkedList<BNode> causes;
        private int gValue;

        public CGInfo(String locationClass, String locationMethod, String locationMethodSignature, String targetClassName, String targetMethodName, String targetMethodSignature) {
            this.causes = new LinkedList<BNode>();
            this.locationClass = locationClass;
            this.locationMethod = locationMethod;
            this.locationMethodSignature = locationMethodSignature;
            this.targetClassName = targetClassName;
            this.targetMethodName = targetMethodName;
            this.targetMethodSignature = targetMethodSignature;
        }

        public void addCause(BNode cause) {
            if (!causes.contains(cause)) {
                causes.add(cause);
            }
        }

        public Collection<? extends BNode> getCauses() {
            return causes;
        }

        @Override
        public String getClassName() {
            return targetClassName;
        }

        @Override
        public String getName() {
            return targetMethodName;
        }

        @Override
        public String toString() {
            return targetClassName + "." + targetMethodName + "(" + targetMethodSignature + ")";
        }

        @Override
        public String getSignature() {
            return targetMethodSignature;
        }

        @Override
        public int getGenericValue() {
            return gValue;
        }

        @Override
        public void setGenericValue(int m) {
            this.gValue = m;
        }

        public boolean isRecursive() {
            if (locationClass.equals(targetClassName)) {
                if (locationMethod.equals(targetMethodName)) {
                    if (locationMethodSignature.equals(targetMethodSignature)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (obj instanceof CGInfo) {
                CGInfo other = (CGInfo) obj;
                if (other.targetClassName.equals(targetClassName))
                    if (other.targetMethodName.equals(targetMethodName))
                        if (other.targetMethodSignature.equals(targetMethodSignature))
                            return true;

            }
            return false;
        }
    }

    private MethodMap<MethodMap<CGInfo>> cg;
    private MethodMap<LinkedList<BNode>> allCallSites;

    private boolean newInstanceInvoked;

    private JavaClass runtimException;
    private JavaClass error;
    private SubClassChecker subClassChecker;

    public CallGraph() throws ClassNotFoundException {
        cg = new MethodMap<MethodMap<CGInfo>>();
        this.newInstanceInvoked = false;
        allCallSites = new MethodMap<LinkedList<BNode>>();

        subClassChecker = new SubClassChecker();
    }

    public MethodMap<CGInfo> register(BNode cause, String locationClass, String locationMethod, String locationMethodSignature, String targetClassName, String targetMethodName, String targetMethodSignature) {
        MethodMap<CGInfo> callees = cg.get(locationClass, locationMethod, locationMethodSignature);

        if (callees == null) {
            callees = new MethodMap<CGInfo>();
            cg.put(locationClass, locationMethod, locationMethodSignature, callees);
        } 

        CGInfo cgInfo = callees.get(targetClassName, targetMethodName, targetMethodSignature);
        if (cgInfo == null) {
            cgInfo = new CGInfo(locationClass, locationMethod, locationMethodSignature, targetClassName, targetMethodName, targetMethodSignature);
            callees.put(targetClassName, targetMethodName, targetMethodSignature, cgInfo);
        } 
        cgInfo.addCause(cause);

        if (targetMethodName.equals("newInstance")) {
            this.newInstanceInvoked = true;
        }
        return callees;
    }

    public void analyse(DependencyExtent extent, ICompilationRegistry cregistry) throws ClassNotFoundException {
        runtimException = Repository.lookupClass("java.lang.RuntimeException");
        error = Repository.lookupClass("java.lang.Error");

        Iterator<MethodEntryPoints> methods = extent.getMethods();

        while (methods.hasNext()) {
            MethodEntryPoints nextMethod = methods.next();
            analyseMethod(nextMethod, cregistry);
            buildCallSites(nextMethod);
        }

        boolean carryOn = true;
        while (carryOn) {
            carryOn = false;
            Iterator<MethodEntryPoints> methodsIterator = extent.getMethods();
            while (methodsIterator.hasNext()) {
                MethodEntryPoints nextMethod = methodsIterator.next();
                if (nextMethod.canCallWithArgs()) {
                    LinkedList<BNode> callSites = allCallSites.get(nextMethod.getClazz().getClassName(), nextMethod.getMethod().getName(), nextMethod.getMethod().getSignature());
                    if (callSites != null) {
                        Iterator<BNode> callSitesIterator = callSites.iterator();
                        while (callSitesIterator.hasNext() && (carryOn == false)) {
                            VirtualOrInterfaceMethodCallBNode callsite = (VirtualOrInterfaceMethodCallBNode) callSitesIterator.next();
                            Iterator<JavaClass> targetClasses = callsite.getTargetClasses();
                            while (targetClasses.hasNext() && (carryOn == false)) {
                                String nextClass = targetClasses.next().getClassName();
                                MethodEntryPoints targetMethod = extent.getMethod(nextClass, nextMethod.getMethod().getName(), nextMethod.getMethod().getSignature());
                                if (targetMethod != null) {
                                    if (!targetMethod.canCallWithArgs()) {
                                        nextMethod.setCallWithArgs(false);
                                        carryOn = true;
                                    }
                                } else {
                                    nextMethod.setCallWithArgs(false);
                                    carryOn = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void buildCallSites(MethodEntryPoints method) {
        Iterator<MethodMap<CGInfo>> callers = cg.getValues();

        LinkedList<BNode> callSites = new LinkedList<BNode>();

        while (callers.hasNext()) {
            MethodMap<CGInfo> callees = callers.next();
            Iterator<CGInfo> entries = callees.getValues();
            while (entries.hasNext()) {
                CGInfo cgInfo = entries.next();
                if (method.isEqualTo(cgInfo.targetClassName, cgInfo.targetMethodName, cgInfo.targetMethodSignature)) {
                    Collection<? extends BNode> causes = cgInfo.getCauses();
                    for (BNode bNode : causes) {
                        if (bNode instanceof VirtualOrInterfaceMethodCallBNode) {
                            if (!(bNode instanceof HackInterfaceMethodCallBNode)) {
                                callSites.add(bNode);
                            }
                        }
                    }
                }
            }
        }
        if (callSites.size() > 0) {
            allCallSites.put(method.getClazz().getClassName(), method.getMethod().getName(), method.getMethod().getSignature(), callSites);
        }
    }

    private void analyseMethod(MethodEntryPoints method, ICompilationRegistry cregistry) throws ClassNotFoundException {
        Iterator<MethodMap<CGInfo>> callers = cg.getValues();

        LinkedList<BNode> callSites = new LinkedList<BNode>();

        while (callers.hasNext()) {
            MethodMap<CGInfo> callees = callers.next();
            Iterator<CGInfo> entries = callees.getValues();
            while (entries.hasNext()) {
                CGInfo cgInfo = entries.next();
                if (method.isEqualTo(cgInfo.targetClassName, cgInfo.targetMethodName, cgInfo.targetMethodSignature)) {
                    try {
                        Method caller = ClassfileUtils.findMethod(cgInfo.locationClass, cgInfo.locationMethod, cgInfo.locationMethodSignature).getMethod();
                        MethodOrFieldDesc desc = new MethodOrFieldDesc(cgInfo.locationClass, cgInfo.locationMethod, cgInfo.locationMethodSignature);
                        if (!icecaptools.compiler.Compiler.compileMethod(cregistry, caller, desc)) {
                            return;
                        }
                    } catch (Exception e) {
                        return;
                    }
                    callSites.addAll(cgInfo.getCauses());
                }
            }
        }

        Method m = method.getMethod();

        MethodOrFieldDesc desc = new MethodOrFieldDesc(method.getClazz().getClassName(), m.getName(), m.getSignature());

        if (desc.getClassName().equals("java.lang.String")) {
            if (desc.getName().equals("<init>")) {
                if (desc.getSignature().equals("([C)V")) {
                    return;
                }
            }
        }

        if (icecaptools.compiler.Compiler.isStaticInitializer(desc)) {
            return;
        }

        if (!icecaptools.compiler.Compiler.compileMethod(cregistry, m, desc)) {
            return;
        }

        if (m.isSynthetic()) {
            return;
        }

        if (newInstanceInvoked) {
            if (m.getName().equals("<init>")) {
                if (m.getSignature().equals("()V")) {
                    return;
                }
            }
        }

        if (m.getName().contains("dispatchRunnable")) {
            return;
        }

        if (m.getName().contains("lock")) {
            return;
        }

        if (m.getName().contains("unlock")) {
            return;
        }
        
        if (m.getName().contains("setNormalized")) {
            return;
        }

        String clazz = method.getClazz().getClassName();
        JavaClass currentClass = Repository.lookupClass(clazz);
        if (subClassChecker.isSubclassOf(currentClass, runtimException) || subClassChecker.isSubclassOf(currentClass, error)) {
            if (m.getName().equals("<init>")) {
                if (m.getSignature().equals("()V")) {
                    return;
                }
            }
        }

        method.setCallWithArgs(true);
    }

    private static class EmptyMethodIdentifierIterator implements IcecapIterator<MethodIdentifier> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public MethodIdentifier next() {
            return null;
        }
    }

    private static class MethodIdentifierIterator implements IcecapIterator<MethodIdentifier> {
        private Iterator<CGInfo> values;

        public MethodIdentifierIterator(Iterator<CGInfo> values) {
            this.values = values;
        }

        @Override
        public boolean hasNext() {
            return values.hasNext();
        }

        @Override
        public MethodIdentifier next() {
            return values.next();
        }
    }

    public IcecapIterator<MethodIdentifier> getCallees(String className, String methodName, String methodSignature) {
        MethodMap<CGInfo> calledMethods = cg.get(className, methodName, methodSignature);
        if (calledMethods == null) {
            return new EmptyMethodIdentifierIterator();
        } else {
            return new MethodIdentifierIterator(calledMethods.getValues());
        }
    }
}
