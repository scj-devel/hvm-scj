package icecaptools;

import icecaptools.compiler.FieldInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class DefaultObserver implements AnalysisObserver {

    @Override
    public void classUsed(String newType) {
    }

    @Override
    public void methodCodeUsed(String className, String targetMethodName, String targetMethodSignature, boolean report) {
    }

    @Override
    public boolean isMethodUsed(String className, String targetMethodName, String targetMethodSignature) {
        return false;
    }

    @Override
    public IcecapIterator<MethodOrFieldDesc> getUsedMethods() {
        return null;
    }

    @Override
    public Iterator<String> getUsedClasses() {
        return null;
    }

    @Override
    public void interfaceUsed(String className) {

    }

    @Override
    public boolean isInterfaceUsed(String className) {
        return false;
    }

    @Override
    public void setProgressMonitor(IcecapProgressMonitor progressMonitor) {
        
    }

    @Override
    public void classFieldUsed(String className, String fieldName) {
    }

    @Override
    public boolean isClassFieldUsed(String className, String fieldName) {
        return false;
    }

    @Override
    public boolean isClassUsed(String className) {
        return false;
    }

    @Override
    public void registerLockingTypes(ArrayList<String> types) {
    }

    @Override
    public boolean isLockingType(String className) {
        return false;
    }

    @Override
    public void registerLockingType(String type) {
        
    }

    @Override
    public void byteCodeUsed(byte opCode) {
    }

    @Override
    public boolean isBytecodeUsed(int i) {
        return false;
    }

    @Override
    public void registerNativeField(String containingClass, FieldInfo field, IcecapCVar cvar) {
    }

    @Override
    public NativeFieldInfo isNativeField(String containingClass, FieldInfo field) {
        return null;
    }

    @Override
    public void classInitializerUsed(String className) {
    }

    @Override
    public Iterator<String> getUsedClassInitializers() {
        return null;
    }

    @Override
    public IcecapIterator<MethodOrFieldDesc> getUsedMethods(String nextClass) {
        return null;
    }

    @Override
    public void reportVtableSize(int s) {
    }

    @Override
    public int getMaxVtableSize() {
        return 0;
    }

    @Override
    public void registerCFunc(String className, String name, String signature, IcecapCFunc cfunc) {
    }

    @Override
    public CFuncInfo isCFunc(String currentClassName, String name, String signature) {
        return null;
    }

    @Override
    public IcecapIterator<CFuncInfo> getCFunctions() {
        return null;
    }
}
