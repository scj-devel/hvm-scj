package icecaptools;

import icecaptools.compiler.FieldInfo;
import icecaptools.compiler.LDCConstant;
import icecaptools.compiler.utils.CheckSubClassRelationShip;
import icecaptools.compiler.utils.MethodMap;
import icecaptools.compiler.utils.SubClassChecker;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.conversion.Converter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

public class DependencyWalker {

	private static class WorkItem {
		public BNode bnode;
		public NewList newList;

		public WorkItem(BNode bnode, NewList newList) {
			this.bnode = bnode;
			this.newList = newList;
		}
	}

	private static final String[] jmlMethodNames = { "checkPre", "checkPost", "evalOldExprInHC", "checkInv", "checkHC" };

	private SubClassChecker subClassChecker;
	private ImplementorChecker implementorChecker;
	private Converter converter;
	private AnalysisObserver observer;
	private int stackDepth;
	private DependencyLeakException dleak;
	private PrintStream out;
	private ConversionConfiguration config;

	private HashSet<String> leafMethods;

	private MethodMap<MethodAndClass> methodCache;

	private static class DependencyWalkerObserver implements AnalysisObserver {
		private AnalysisObserver delegate;

		DependencyWalkerObserver(AnalysisObserver delegate) {
			this.delegate = delegate;
		}

		@Override
		public void methodCodeUsed(String className, String targetMethodName, String targetMethodSignature,
				boolean report) throws CanceledByUserException {
			delegate.methodCodeUsed(className, targetMethodName, targetMethodSignature, report);
		}

		@Override
		public void classUsed(String newType) {
			delegate.classUsed(newType);
		}

		@Override
		public void classFieldUsed(String className, String fieldName) {
			delegate.classFieldUsed(className, fieldName);
		}

		@Override
		public void interfaceUsed(String className) {
			delegate.interfaceUsed(className);
		}

		@Override
		public void byteCodeUsed(byte opCode) {
			delegate.byteCodeUsed(opCode);
		}

		@Override
		public boolean isMethodUsed(String className, String targetMethodName, String targetMethodSignature) {
			return delegate.isMethodUsed(className, targetMethodName, targetMethodSignature);
		}

		@Override
		public boolean isClassFieldUsed(String className, String fieldName) {
			return delegate.isClassFieldUsed(className, fieldName);
		}

		@Override
		public boolean isInterfaceUsed(String className) {
			return delegate.isInterfaceUsed(className);
		}

		@Override
		public IcecapIterator<MethodOrFieldDesc> getUsedMethods() {
			return delegate.getUsedMethods();
		}

		@Override
		public Iterator<String> getUsedClasses() {
			return delegate.getUsedClasses();
		}

		@Override
		public void setProgressMonitor(IcecapProgressMonitor progressMonitor) {
			delegate.setProgressMonitor(progressMonitor);
		}

		@Override
		public boolean isClassUsed(String className) {
			return delegate.isClassUsed(className);
		}

		@Override
		public void registerLockingTypes(ArrayList<String> types) {
			delegate.registerLockingTypes(types);
		}

		@Override
		public void registerLockingType(String type) {
			delegate.registerLockingType(type);
		}

		@Override
		public boolean isLockingType(String className) {
			return delegate.isLockingType(className);
		}

		@Override
		public boolean isBytecodeUsed(int i) {
			return delegate.isBytecodeUsed(i);
		}

		@Override
		public void registerNativeField(String containingClass, FieldInfo field, IcecapCVar cvar) {
			delegate.registerNativeField(containingClass, field, cvar);
		}

		@Override
		public NativeFieldInfo isNativeField(String containingClass, FieldInfo field) {
			return delegate.isNativeField(containingClass, field);
		}

		@Override
		public void classInitializerUsed(String className) {
			delegate.classInitializerUsed(className);
		}

		@Override
		public Iterator<String> getUsedClassInitializers() {
			return delegate.getUsedClassInitializers();
		}

		@Override
		public IcecapIterator<MethodOrFieldDesc> getUsedMethods(String nextClass) {
			return delegate.getUsedMethods(nextClass);
		}

		@Override
		public void reportVtableSize(int s) {
			delegate.reportVtableSize(s);
		}

		@Override
		public int getMaxVtableSize() {
			return delegate.getMaxVtableSize();
		}

		@Override
		public void registerCFunc(String className, String name, String signature, IcecapCFunc cfunc) {
			delegate.registerCFunc(className, name, signature, cfunc);
		}

		@Override
		public CFuncInfo isCFunc(String className, String name, String signature) {
			return delegate.isCFunc(className, name, signature);
		}

		@Override
		public IcecapIterator<CFuncInfo> getCFunctions() throws Exception {
			return delegate.getCFunctions();
		}
	}

	public DependencyWalker(Converter converter, AnalysisObserver observer, PrintStream out,
			ConversionConfiguration config) {
		subClassChecker = new SubClassChecker();
		implementorChecker = new ImplementorChecker();
		this.converter = converter;
		this.observer = new DependencyWalkerObserver(observer);
		this.dleak = new DependencyLeakException();
		this.out = out;
		this.config = config;
		this.leafMethods = new HashSet<String>();
		this.methodCache = new MethodMap<MethodAndClass>();
	}

	private void analyseBNode(BNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack) throws Throwable {
		NewList bnodeNewList = bnode.getNewList();

		if (newList.lessThanOrEquals(bnodeNewList)) {
			return;
		} else {

			if (bnodeNewList == null) {
				bnodeNewList = new NewList(newList);
				bnode.setNewList(bnodeNewList);
			} else {
				bnodeNewList.merge(newList);
			}

			if (bnode.throwsExceptions() && !bnode.exceptionsHandled()) {
				Iterator<String> exceptions = bnode.getExceptionsThrown();
				while (exceptions.hasNext()) {
					String nextException = exceptions.next();
					classInstantiated(newList, mr, nextException);
					observer.methodCodeUsed(nextException, "<init>", "()V", true);
					JavaClass clazz = lookupClass(nextException);
					MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "<init>", "()V", false);

					if (entryPoints != null) {
						NewList calledMethodResult = analyseMethod(entryPoints, newList);
						mergeResult(newList, mr, calledMethodResult);
						observer.classUsed(nextException);
					}
				}
				bnode.setExceptionsHandled();
			}

			try {
				if (bnode instanceof BasicBNode) {
					analyseBasicBNode((BasicBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof BranchBNode) {
					analyseBranchBNode((BranchBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof GotoBNode) {
					analyseGotoBNode((GotoBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof NewArrayBNode) {
					analyseNewArrayBNode((NewArrayBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof CheckcastBNode) {
					analyseCheckcastBNode((CheckcastBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof NewBNode) {
					analyseNewBNode((NewBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof ReturnBNode) {
					analyseReturnBNode((BNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof VirtualMethodCallBNode) {
					analyseVirtualMethodCallBNode((MethodCallBNode) bnode, newList, mr, workItemStack, subClassChecker);
				} else if (bnode instanceof SpecialMethodCallBNode) {
					analyseSpecialMethodCallBNode((MethodCallBNode) bnode, newList, false, mr, workItemStack);
				} else if (bnode instanceof StaticMethodCallBNode) {
					analyseSpecialMethodCallBNode((MethodCallBNode) bnode, newList, true, mr, workItemStack);
				} else if (bnode instanceof ObjectFieldAccessBNode) {
					analyseObjectFieldAccessBNode((ObjectFieldAccessBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof StaticFieldAccessBNode) {
					analyseStaticFieldAccessBNode((StaticFieldAccessBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof SwitchBNode) {
					analyseSwitchBNodeBNode((SwitchBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof InterfaceMethodCallBNode) {
					analyseInterfaceMethodCallBNode((MethodCallBNode) bnode, newList, mr, workItemStack,
							implementorChecker);
				} else if (bnode instanceof LDCBNode) {
					analyseLDCBNode((LDCBNode) bnode, newList, mr, workItemStack);
				} else if (bnode instanceof DynamicMethodCallBNode) {
					analyseDynamicMethodCallBNode((DynamicMethodCallBNode) bnode, newList, mr, workItemStack);
				} else {
					throw new Exception("Unknown BNode instance");
				}
			} catch (StackOverflowError soe) {
				stackDepth = 0;
				throw dleak;
			} catch (DependencyLeakException dleak) {
				stackDepth++;
				if (stackDepth < 50) {
					throw dleak;
				} else if (stackDepth == 50) {
					out.println("icecaptools.DependencyWalker: DependencyLeakException");
					throw dleak;
				} else {
					StringBuffer buffer = new StringBuffer();
					MethodAndClass methodAndClass = ClassfileUtils.findMethod(bnode.locationClass,
							bnode.locationMethod, bnode.locationMethodSignature);
					buffer.append("\tat ");
					buffer.append(methodAndClass.getClazz().getClassName());
					buffer.append(".");
					buffer.append(methodAndClass.getMethod().getName());
					buffer.append("(");
					buffer.append(methodAndClass.getClazz().getSourceFileName());
					buffer.append(":");
					buffer.append(ClassfileUtils.getLineNumber(methodAndClass.getMethod(), bnode.getOriginalAddress()));
					buffer.append(")");
					out.println(buffer.toString());
					throw dleak;
				}
			}
		}
	}

	public JavaClass lookupClass(String className) throws ClassNotFoundException {
		JavaClass clss = Repository.lookupClass(className);
		return clss;
	}

	private void analyseDynamicMethodCallBNode(DynamicMethodCallBNode bnode, NewList newList, NewList mr,
			Stack<WorkItem> workItemStack) throws Throwable {
		if (bnode instanceof DynamicMethodCallVirtualMethodBNode) {
			DynamicMethodCallVirtualMethodBNode virtualMethodcallType = (DynamicMethodCallVirtualMethodBNode) bnode;

			JavaClass clazz = lookupClass(virtualMethodcallType.getClassNameHandle());

			observer.methodCodeUsed(virtualMethodcallType.getClassNameHandle(),
					virtualMethodcallType.getMethodNameHandle(), virtualMethodcallType.getMethodSigHandle(), true);
			observer.classUsed(virtualMethodcallType.getClassNameHandle());

			MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz,
					virtualMethodcallType.getMethodNameHandle(), virtualMethodcallType.getMethodSigHandle(), false);

			if (entryPoints != null) {
				NewList calledMethodResult = analyseMethod(entryPoints, newList);
				mergeResult(newList, mr, calledMethodResult);
			}

			String lambdaClassName = virtualMethodcallType.getLambdaClassName();

			classInstantiated(newList, mr, lambdaClassName);
			observer.classUsed(lambdaClassName);

		} else {
			throw new Exception("Unimplemented dynamic type");
		}
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private void classInstantiated(NewList newList, NewList mr, String className) {
		newList.addElement(className);
		mr.addElement(className);
	}

	private void analyseObjectFieldAccessBNode(ObjectFieldAccessBNode bnode, NewList newList, NewList mr,
			Stack<WorkItem> workItemStack) throws Exception {
		JavaClass clazz = lookupClass(bnode.getClassName());
		observer.classUsed(clazz.getClassName());
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private void analyseNewArrayBNode(NewArrayBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Exception {
		String type = bnode.getType();
		if (JavaArrayClass.isArrayClass(type)) {
			String referredType = JavaArrayClass.getReferredType(type);
			if ((referredType.length() > 0) && JavaArrayClass.isReferenceClass(referredType)) {
				String elementType = JavaArrayClass.getElementType(referredType);
				JavaClass elementClazz = lookupClass(elementType);
				if (elementClazz.isInterface()) {
					observer.interfaceUsed(elementType);
				} else {
					observer.classUsed(elementType);
				}

			}
		}
		observer.classUsed(type);

		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private void analyseLDCBNode(LDCBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Throwable {
		LDCConstant constant = bnode.getLDCConstant();
		if (constant.getType() == LDCConstant.STRING) {
			ensureStringInitializer(bnode, newList, mr);
		} else if (constant.getType() == LDCConstant.CLASS) {

			classInstantiated(newList, mr, "java.lang.Class");
			observer.classUsed("java.lang.Class");

			JavaClass clazz = lookupClass(constant.getClassName());
			if (!(clazz instanceof JavaArrayClass)) {
				if (ClassfileUtils.hasDefaultConstructor(clazz)) {
					MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "<init>", "()V", false);

					if (entryPoints != null) {
						NewList calledMethodResult = analyseMethod(entryPoints, newList);
						mergeResult(newList, mr, calledMethodResult);
					}
				}
			}
			classInstantiated(newList, mr, constant.getClassName());
			observer.classUsed(constant.getClassName());

		}
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	protected void ensureStringInitializer(BNode bnode, NewList newList, NewList mr) throws ClassNotFoundException,
			Throwable {
		JavaClass clazz = lookupClass("java/lang/String");
		MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "<init>", "([C)V", true);

		if (entryPoints != null) {
			NewList calledMethodResult = analyseMethod(entryPoints, newList);
			mergeResult(newList, mr, calledMethodResult);
		}

		classInstantiated(newList, mr, "java.lang.String");
		observer.classUsed("java.lang.String");
		observer.classUsed("[C");
	}

	public NewList analyseMethod(MethodEntryPoints entryPoints, NewList newList) throws Throwable {
		Stack<WorkItem> workItemStack = new Stack<WorkItem>();

		NewList mr = new NewList();

		workItemStack.push(new WorkItem(entryPoints.getMainEntryPoint(), new NewList(newList)));

		while (workItemStack.size() > 0) {
			WorkItem nextWorkItem = workItemStack.pop();
			analyseBNode(nextWorkItem.bnode, nextWorkItem.newList, mr, workItemStack);
		}

		newList.merge(mr);

		IcecapIterator<BNode> handlerEntryPoints = entryPoints.getHandlerEntryPoints();

		while (handlerEntryPoints.hasNext()) {
			BNode nextEntrypoint = handlerEntryPoints.next();
			newList.merge(mr);
			workItemStack.push(new WorkItem(nextEntrypoint, new NewList(newList)));
			while (workItemStack.size() > 0) {
				WorkItem nextWorkItem = workItemStack.pop();
				analyseBNode(nextWorkItem.bnode, nextWorkItem.newList, mr, workItemStack);
			}
		}
		return mr;
	}

	private void dispatchRest(BNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack) throws Exception {
		Iterator<BNode> children = bnode.getChildren();
		while (children.hasNext()) {
			workItemStack.push(new WorkItem(children.next(), new NewList(newList)));
		}
	}

	private void analyseStaticFieldAccessBNode(StaticFieldAccessBNode bnode, NewList newList, NewList mr,
			Stack<WorkItem> workItemStack) throws Throwable {
		MethodEntryPoints entryPoints;

		JavaClass clazz = lookupClass(bnode.getClassName());

		observer.classUsed(clazz.getClassName());
		observer.classFieldUsed(clazz.getClassName(), bnode.getFieldName());

		if (ClassfileUtils.hasClassInitializer(clazz)) {
			entryPoints = converter.convertByteCode(bnode, clazz, "<clinit>", "()V", true);

			if (entryPoints != null) {
				NewList calledMethodResult = analyseMethod(entryPoints, newList);
				mergeResult(newList, mr, calledMethodResult);
			}
		}

		if (bnode.isGet()) {
			if (clazz.getClassName().replace("/", ".").equals("java.lang.System")) {
				clazz = lookupClass("devices.System");
				if (clazz != null) {
					if (ClassfileUtils.findMethodInClass(clazz, "initializeSystemClass", "()V") != null) {
						entryPoints = converter.convertByteCode(bnode, clazz, "initializeSystemClass", "()V", true);

						if (entryPoints != null) {
							NewList calledMethodResult = analyseMethod(entryPoints, newList);
							mergeResult(newList, mr, calledMethodResult);
						}
					}
				}
				/* if (ClassfileUtils.findMethodInClass(clazz, "initializeSystemClass", "()V") != null) {
				     entryPoints = converter.convertByteCode(bnode, clazz, "initializeSystemClass", "()V", true);

				     if (entryPoints != null) {
				         NewList calledMethodResult = analyseMethod(entryPoints, newList);
				         mergeResult(newList, mr, calledMethodResult);
				     }
				 }*/
			} else {
				observer.classInitializerUsed(clazz.getClassName());
			}
		}

		dispatchRest(bnode, newList, mr, workItemStack);
	}

	protected void mergeResult(NewList newList, NewList mr, NewList calledMethodResult) throws Exception {
		newList.merge(calledMethodResult);
		mr.merge(calledMethodResult);
	}

	private static class ImplementorChecker implements CheckSubClassRelationShip {

		private HashMap<String, Boolean> relations;

		public ImplementorChecker() {
			relations = new HashMap<String, Boolean>();
		}

		@Override
		public boolean isSubclassOf(JavaClass subClazz, JavaClass clazz) throws ClassNotFoundException {
			StringBuffer key = new StringBuffer();
			key.append(subClazz.getClassName());
			key.append(clazz.getClassName());
			String keyString = key.toString();

			if (relations.containsKey(keyString)) {
				return relations.get(keyString);
			}

			while (subClazz != null) {
				if (subClazz.implementationOf(clazz)) {
					relations.put(keyString, true);
					return true;
				}
				subClazz = subClazz.getSuperClass();
			}
			relations.put(keyString, false);
			return false;
		}

	}

	private void analyseVirtualMethodCallBNode(MethodCallBNode bnode, NewList newList, NewList mr,
			Stack<WorkItem> workItemStack, CheckSubClassRelationShip checker) throws Throwable {
		String clazzName = bnode.getClassName();

		if (JavaArrayClass.isArrayClass(clazzName)) {
			while (JavaArrayClass.isArrayClass(clazzName)) {
				clazzName = JavaArrayClass.getElementType(clazzName);
			}
			if (JavaArrayClass.isReferenceClass(clazzName)) {
				clazzName = JavaArrayClass.getReferredType(clazzName);
			} else {
				dispatchRest(bnode, newList, mr, workItemStack);
				return;
			}
		}

		JavaClass clazz = lookupClass(clazzName);

		MethodAndClass methodAndClass = findMethodInClassHierarchy(clazz, bnode.getMethodName(), bnode.getMethodSig());

		clazzName = methodAndClass.getClazz().getClassName();
		String methodName = bnode.getMethodName();

		observer.methodCodeUsed(clazzName, methodName, bnode.getMethodSig(), true);

		if (clazzName.equals("java.lang.Float")) {
			if (methodName.equals("toString")) {
				ensureStringInitializer(bnode, newList, mr);
			}
		}

		if (clazzName.equals("java.lang.reflect.Method")) {
			if (methodName.equals("invoke")) {
				ensureUnboxing(bnode, newList, mr);
				ensureInvocationTargetException(bnode, newList, mr);
			}
		}

		if (clazzName.equals("java.lang.reflect.Constructor")) {
			if (methodName.equals("newInstance")) {
				ensureUnboxing(bnode, newList, mr);
				ensureInvocationTargetException(bnode, newList, mr);
			}
		}

		if (clazzName.equals("java.lang.Class")) {
			if (methodName.equals("newInstance")) {
				handleNewInstance(bnode, newList, mr);
			} else if (methodName.equals("getConstructor")) {
				handleGetConstructor(newList, mr);
			} else if (methodName.equals("getMethod")) {
				handleGetMethod(bnode, newList, mr);
			}
		}

		if (clazzName.equals("java.lang.Thread")) {
			if (methodName.equals("start")) {
				handleThreadStart(bnode, newList, mr);
			}
		}

		analyseMethodCallBNode(clazz, bnode, newList, mr, workItemStack, checker);
	}

	private void handleThreadStart(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		JavaClass clazz = lookupClass("thread/ThreadUtils");
		if (clazz != null) {
			MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "dispatchRunnable",
					"(Ljava/lang/Runnable;)V", true, true);

			if (entryPoints != null) {
				NewList calledMethodResult = analyseMethod(entryPoints, newList);
				mergeResult(newList, mr, calledMethodResult);
			}
			observer.classUsed("thread.ThreadUtils");
			observer.methodCodeUsed(clazz.getClassName(), "dispatchRunnable", "(Ljava/lang/Runnable;)V", true);
		}
	}

	private void handleGetConstructor(NewList newList, NewList mr) {
		String constructor = "java.lang.reflect.Constructor";
		classInstantiated(newList, mr, constructor);
		observer.classUsed(constructor);
	}

	private void ensureInvocationTargetException(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		JavaClass clazz = lookupClass("java/lang/reflect/InvocationTargetException");

		if (clazz != null) {
			MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "<init>",
					"(Ljava/lang/Throwable;)V", false, true);

			if (entryPoints != null) {
				NewList calledMethodResult = analyseMethod(entryPoints, newList);
				mergeResult(newList, mr, calledMethodResult);
			}

			classInstantiated(newList, mr, "java.lang.reflect.InvocationTargetException");
			observer.classUsed("java.lang.reflect.InvocationTargetException");
			observer.methodCodeUsed(clazz.getClassName(), "<init>", "(Ljava/lang/Throwable;)V", true);
		}
	}

	private void handleCurrentTimeMillis(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		JavaClass clazz = lookupClass("devices.System");

		MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "currentTimeMillis", "()J", true);

		if (entryPoints != null) {
			NewList calledMethodResult = analyseMethod(entryPoints, newList);
			mergeResult(newList, mr, calledMethodResult);
		}
		
		observer.classUsed("devices.System");
		
	}

	private void handleGetProperty(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		JavaClass clazz = lookupClass("devices.System");

		MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "getProperty", "(Ljava/lang/String;)Ljava/lang/String;", true);

		if (entryPoints != null) {
			NewList calledMethodResult = analyseMethod(entryPoints, newList);
			mergeResult(newList, mr, calledMethodResult);
		}
		
		observer.classUsed("devices.System");
	}

	
	private void handleDoPriviledge(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		String nextClass = "java.lang.Boolean";
		classInstantiated(newList, mr, nextClass);
		observer.classUsed(nextClass);
		observer.methodCodeUsed(nextClass, "<init>", "()V", true);
		JavaClass clazz = lookupClass(nextClass);
		MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "<init>", "(Z)V", false);

		
		if (entryPoints != null) {
			NewList calledMethodResult = analyseMethod(entryPoints, newList);
			mergeResult(newList, mr, calledMethodResult);
		}

		clazz = lookupClass("devices.AccessController");

		entryPoints = converter.convertByteCode(bnode, clazz, "doPrivileged", "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;", true);

		if (entryPoints != null) {
			NewList calledMethodResult = analyseMethod(entryPoints, newList);
			mergeResult(newList, mr, calledMethodResult);
		}
		
		observer.classUsed("devices.AccessController");
		
	}

	private void handleGetMethod(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		String nextException = "java.lang.NoSuchMethodException";
		classInstantiated(newList, mr, nextException);
		observer.classUsed(nextException);
		observer.methodCodeUsed(nextException, "<init>", "()V", true);
		JavaClass clazz = lookupClass(nextException);
		MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "<init>", "()V", false);

		if (entryPoints != null) {
			NewList calledMethodResult = analyseMethod(entryPoints, newList);
			mergeResult(newList, mr, calledMethodResult);
		}
		observer.classUsed("java.lang.reflect.Method");
	}

	private void handleNewInstance(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		IcecapIterator<String> forcedincludes = config.getForcedIncludes();
		while (forcedincludes.hasNext()) {
			String nextClass = forcedincludes.next();
			classInstantiated(newList, mr, nextClass);

			observer.methodCodeUsed(nextClass, "<init>", "()V", true);
			JavaClass clazz = lookupClass(nextClass);
			MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, "<init>", "()V", false);

			if (entryPoints != null) {
				NewList calledMethodResult = analyseMethod(entryPoints, newList);
				mergeResult(newList, mr, calledMethodResult);
			}

		}
	}

	private void ensureUnboxing(MethodCallBNode bnode, NewList newList, NewList mr) throws Throwable {
		String[] methods = { "unbox", "(Ljava/lang/Object;)V", "boxBoolean", "(Z)Ljava/lang/Boolean;", "boxByte",
				"(B)Ljava/lang/Byte;", "boxShort", "(S)Ljava/lang/Short;", "boxCharacter", "(C)Ljava/lang/Character;",
				"boxInteger", "(I)Ljava/lang/Integer;", "boxLong", "(J)Ljava/lang/Long;", };

		JavaClass clazz = lookupClass("reflect/Unboxing");

		for (int i = 0; i < methods.length; i = i + 2) {
			MethodEntryPoints entryPoints = converter.convertByteCode(bnode, clazz, methods[i], methods[i + 1], true);

			if (entryPoints != null) {
				NewList calledMethodResult = analyseMethod(entryPoints, newList);
				mergeResult(newList, mr, calledMethodResult);
			}
		}

		classInstantiated(newList, mr, "reflect.Unboxing");
		observer.classUsed("reflect.Unboxing");
	}

	private void analyseInterfaceMethodCallBNode(MethodCallBNode bnode, NewList newList, NewList mr,
			Stack<WorkItem> workItemStack, ImplementorChecker implementorChecker) throws Throwable {
		String clazzName = bnode.getClassName();

		JavaClass clazz = lookupClass(clazzName);

		JavaClass declaringClass = ClassfileUtils.findDeclaringInterface(clazz, bnode.getMethodName(),
				bnode.getMethodSig()).getClazz();

		observer.interfaceUsed(declaringClass.getClassName());

		analyseMethodCallBNode(clazz, (MethodCallBNode) bnode, newList, mr, workItemStack, implementorChecker);
	}

	private void analyseMethodCallBNode(JavaClass clazz, MethodCallBNode bnode, NewList newList, NewList mr,
			Stack<WorkItem> workItemStack, CheckSubClassRelationShip checker) throws Throwable {
		String[] elements = newList.getElementsAsArray();
		int count = 0;
		boolean used = false;

		NewList exitList = new NewList();

		while (count < elements.length) {
			String className = elements[count++];
			if (!JavaArrayClass.isArrayClass(className)) {
				JavaClass subClazz = lookupClass(className);

				if (checker.isSubclassOf(subClazz, clazz)) {
					subClazz = findMethodInClassHierarchy(subClazz, bnode.getMethodName(), bnode.getMethodSig())
							.getClazz();

					bnode.addTarget(subClazz);

					addMethodToExtent(bnode, subClazz, bnode.getMethodName(), bnode.getMethodSig(), false, newList,
							exitList);
					used = true;
				}
			}
		}

		String key = clazz.getClassName() + ": " + bnode.getMethodName();

		if (!used) {
			if (!leafMethods.contains(key)) {
				leafMethods.add(key);
			}
		} else {
			if (leafMethods.contains(key)) {
				leafMethods.remove(key);
			}
		}

		mergeResult(newList, mr, exitList);

		checkNatives(bnode, newList, clazz, bnode.getMethodName(), bnode.getMethodSig(), mr);

		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private MethodAndClass findMethodInClassHierarchy(JavaClass subClazz, String methodName, String methodSig)
			throws Exception {
		MethodAndClass mac = methodCache.get(subClazz.getClassName(), methodName, methodSig);
		if (mac == null) {
			mac = ClassfileUtils.findMethodInClassHierarchy(subClazz, methodName, methodSig);
			methodCache.put(subClazz.getClassName(), methodName, methodSig, mac);
		}
		return mac;
	}

	private void checkNatives(MethodCallBNode cause, NewList newList, JavaClass clazz, String methodName,
			String methodSignature, NewList mr) throws Throwable {
		if (!cause.isNativesChecked(newList)) {
			cause.setNativesChecked(newList);
			Method method = cause.findMethodInClass(clazz);
			if (method != null) {
				if (method.isNative() || converter.skipMethodHack(clazz.getClassName(), methodName, methodSignature)
						|| hackHack(clazz, methodName, methodSignature)) {
					Type returnType = method.getReturnType();
					if (returnType instanceof ReferenceType) {
						String typeName = returnType.toString();
						classInstantiated(newList, mr, typeName);
					}
					Type[] types = method.getArgumentTypes();
					for (Type type : types) {
						if (type instanceof ObjectType) {
							ObjectType rtype = (ObjectType) type;
							String typeName = rtype.toString();
							JavaClass runnable = lookupClass("java.lang.Runnable");
							JavaClass argClass = lookupClass(typeName);
							if (implementorChecker.isSubclassOf(argClass, runnable)) {
								// Check for runnables being passed to natives -
								// their run
								// method may get called
								registerRunables(cause, newList, mr);
								return;
							}
						}
					}
				}
			}
		}
	}

	private boolean hackHack(JavaClass clazz, String methodName, String methodSignature) {
		if (clazz.getClassName().equals("vm.Memory")) {
			if (methodName.equals("getHeapArea")) {
				if (methodSignature.equals("()Lvm/Memory;")) {
					return true;
				}
			}
		}
		return false;
	}

	private void registerRunables(BNode cause, NewList newList, NewList mr) throws Throwable {
		String[] elements = newList.getElementsAsArray();
		int count = 0;
		boolean foundIt = false;
		JavaClass runnable = lookupClass("java.lang.Runnable");
		while (count < elements.length) {
			String className = elements[count++];
			if (!JavaArrayClass.isArrayClass(className)) {
				JavaClass subClazz = lookupClass(className);

				if (implementorChecker.isSubclassOf(subClazz, runnable)) {
					observer.methodCodeUsed(subClazz.getClassName(), "run", "()V", true);

					MethodEntryPoints entryPoints = converter.convertByteCode(cause, subClazz, "run", "()V", false);

					if (entryPoints != null) {
						entryPoints.neverCallWithArgs();
						NewList calledMethodResult = analyseMethod(entryPoints, newList);
						mergeResult(newList, mr, calledMethodResult);
					}
					foundIt = true;
				}
			}
		}
		if (foundIt) {
			observer.interfaceUsed(runnable.getClassName());
		}
	}

	private void analyseSpecialMethodCallBNode(MethodCallBNode bnode, NewList newList, boolean isStatic, NewList mr,
			Stack<WorkItem> workItemStack) throws Throwable {

		JavaClass clazz = lookupClass(bnode.getClassName());

		JavaClass declaringClass;

		declaringClass = findMethodInClassHierarchy(clazz, bnode.getMethodName(), bnode.getMethodSig()).getClazz();

		MethodEntryPoints entryPoints;

		if (isStatic) {
			if (ClassfileUtils.hasClassInitializer(declaringClass)) {
				entryPoints = converter.convertByteCode(bnode, declaringClass, "<clinit>", "()V", true);

				if (entryPoints != null) {
					NewList calledMethodResult = analyseMethod(entryPoints, newList);
					mergeResult(newList, mr, calledMethodResult);
				}
			}
		}

		NewList exitList = new NewList();

		addMethodToExtent(bnode, declaringClass, bnode.getMethodName(), bnode.getMethodSig(), isStatic, newList,
				exitList);

		mergeResult(newList, mr, exitList);

		observer.classUsed(declaringClass.getClassName());
		observer.classUsed(clazz.getClassName());

		checkNatives(bnode, newList, declaringClass, bnode.getMethodName(), bnode.getMethodSig(), mr);

		if (declaringClass.getClassName().equals("java.lang.Class")) {
			if (bnode.getMethodName().equals("forName")) {
				handleForname(bnode, newList, mr);
			}
		}

		if (declaringClass.getClassName().equals("java.security.AccessController")) {
			if (bnode.getMethodName().equals("doPrivileged")) {
				handleDoPriviledge(bnode, newList, mr);
			}
		}
		
		if (declaringClass.getClassName().equals("java.lang.System")) {
			if (bnode.getMethodName().equals("getProperty")) {
				handleGetProperty(bnode, newList, mr);
			}
			if (bnode.getMethodName().equals("currentTimeMillis")) {
				handleCurrentTimeMillis(bnode, newList, mr);
			}
		}
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	
	private void addMethodToExtent(MethodCallBNode bnode, JavaClass declaringClass, String methodName,
			String methodSig, boolean isStatic, NewList newList, NewList exitList) throws Throwable {
		MethodEntryPoints entryPoints;

		if (config.getProperties().isIncludeJMLMethods()) {
			if (!methodName.equals("<init>")) {
				if (!isStatic) {
					JavaClass superClass = declaringClass.getSuperClass();

					while (superClass != null) {
						if (superClass.isAbstract()) {
							for (int i = 0; i < jmlMethodNames.length; i++) {
								ensureJMLMethod(bnode, newList, exitList, superClass, jmlMethodNames[i]);
							}
						}
						superClass = superClass.getSuperClass();
					}

					JavaClass[] interfaces = declaringClass.getAllInterfaces();
					if (interfaces != null) {
						for (JavaClass iface : interfaces) {
							String surrogate = iface.getClassName() + "$JmlSurrogate";
							try {
								JavaClass jmlSurrogateClass = lookupClass(surrogate);

								while (jmlSurrogateClass != null) {
									for (int i = 0; i < jmlMethodNames.length; i++) {
										ensureJMLMethod(bnode, newList, exitList, jmlSurrogateClass, jmlMethodNames[i]);
									}
									ensureJMLMethod(bnode, newList, exitList, jmlSurrogateClass, "<init>");
									jmlSurrogateClass = jmlSurrogateClass.getSuperClass();
								}
							} catch (ClassNotFoundException cnfe) {
								;
							}
						}
					}
				}
			}
		}

		entryPoints = converter.convertByteCode(bnode, declaringClass, methodName, methodSig, isStatic);

		if (entryPoints != null) {
			NewList calledMethodResult = analyseMethod(entryPoints, new NewList(newList));
			exitList.merge(calledMethodResult);
		}
	}

	private void ensureJMLMethod(MethodCallBNode bnode, NewList newList, NewList exitList, JavaClass superClass,
			String jmlMethodName) throws Exception, Throwable {
		MethodEntryPoints entryPoints;
		Method[] methods = superClass.getMethods();
		for (Method method : methods) {
			if (method.getName().contains(jmlMethodName)) {
				entryPoints = converter.convertByteCode(bnode, superClass, method.getName(), method.getSignature(),
						false);
				if (entryPoints != null) {
					NewList calledMethodResult = analyseMethod(entryPoints, new NewList(newList));
					exitList.merge(calledMethodResult);
				}
			}
		}
	}

	private void handleForname(MethodCallBNode bnode, NewList newList, NewList mr) {
		IcecapIterator<String> forcedincludes = config.getForcedIncludes();
		while (forcedincludes.hasNext()) {
			String nextClass = forcedincludes.next();
			observer.classUsed(nextClass);
		}
	}

	private void analyseReturnBNode(BNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack) {
	}

	private void analyseCheckcastBNode(CheckcastBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Exception {
		dispatchRest(bnode, newList, mr, workItemStack);
		if (!bnode.typeIsInterface()) {
			observer.classUsed(bnode.getType());
		} else {
			observer.interfaceUsed(bnode.getType());
		}
	}

	private void analyseNewBNode(NewBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Exception {
		classInstantiated(newList, mr, bnode.getType());
		observer.classUsed(bnode.getType());
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private void analyseGotoBNode(GotoBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Exception {
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private void analyseBranchBNode(BranchBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Exception {
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private void analyseBasicBNode(BasicBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Exception {
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private void analyseSwitchBNodeBNode(SwitchBNode bnode, NewList newList, NewList mr, Stack<WorkItem> workItemStack)
			throws Exception {
		dispatchRest(bnode, newList, mr, workItemStack);
	}

	private static class LeafMethodsIterator implements IcecapIterator<String> {
		private Iterator<String> iterator;

		public LeafMethodsIterator(Iterator<String> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public String next() {
			return iterator.next();
		}
	}

	public IcecapIterator<String> getLeafs() {
		return new LeafMethodsIterator(leafMethods.iterator());

	}
}
