package icecaptools;

import icecaptools.compiler.ByteCodePatcher;
import icecaptools.compiler.Compiler;
import icecaptools.compiler.FieldInfo;
import icecaptools.compiler.IDGenerator;
import icecaptools.compiler.IcecapByteCodePatcher;
import icecaptools.compiler.MemorySegment;
import icecaptools.compiler.RequiredMethodsManager;
import icecaptools.compiler.VirtualTable;
import icecaptools.compiler.utils.CompilerUtils;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.conversion.Converter;
import icecaptools.conversion.DependencyExtent;
import icecaptools.stackanalyser.ProducerConsumerAnalyser;
import icecaptools.stackanalyser.StackArrayReferencesAnalyser;
import icecaptools.stackanalyser.StackReferencesAnalyser;
import icecaptools.stackanalyser.Util;
import util.ICompilationRegistry;
import util.MethodIdentifier;
import util.MethodOrFieldDesc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.bcel.Repository;

public class CompilationSequence {

	public static byte[] failingCode;

	private AdditionalResourceManager additionalResourceManager;

	private static class Observer implements RestartableMethodObserver, AnalysisObserver {
		private RestartableMethodObserver rdelegate;
		private AnalysisObserver adelegate;

		public Observer(RestartableMethodObserver rdelegate, AnalysisObserver adelegate) {
			this.rdelegate = rdelegate;
			this.adelegate = adelegate;
		}

		@Override
		public void methodCodeUsed(String className, String targetMethodName, String targetMethodSignature, boolean report) throws CanceledByUserException {
			rdelegate.methodCodeUsed(className, targetMethodName, targetMethodSignature, report);
			adelegate.methodCodeUsed(className, targetMethodName, targetMethodSignature, report);
		}

		@Override
		public void restart() {
			rdelegate.restart();
		}

		@Override
		public void refresh() {
			rdelegate.refresh();
		}

		@Override
		public void classUsed(String newType) {
			adelegate.classUsed(newType);

		}

		@Override
		public void classFieldUsed(String className, String fieldName) {
			adelegate.classFieldUsed(className, fieldName);

		}

		@Override
		public void interfaceUsed(String className) {
			adelegate.interfaceUsed(className);

		}

		@Override
		public boolean isMethodUsed(String className, String targetMethodName, String targetMethodSignature) {
			return adelegate.isMethodUsed(className, targetMethodName, targetMethodSignature);
		}

		@Override
		public boolean isClassFieldUsed(String className, String fieldName) {
			return adelegate.isClassFieldUsed(className, fieldName);
		}

		@Override
		public boolean isInterfaceUsed(String className) {
			return adelegate.isInterfaceUsed(className);
		}

		@Override
		public IcecapIterator<MethodOrFieldDesc> getUsedMethods() {
			return adelegate.getUsedMethods();
		}

		@Override
		public Iterator<String> getUsedClasses() {
			return adelegate.getUsedClasses();
		}

		@Override
		public void setProgressMonitor(IcecapProgressMonitor progressMonitor) {
			adelegate.setProgressMonitor(progressMonitor);

		}

		@Override
		public boolean isClassUsed(String className) {
			return adelegate.isClassUsed(className);
		}

		@Override
		public void registerLockingTypes(ArrayList<String> types) {
			adelegate.registerLockingTypes(types);
		}

		@Override
		public boolean isLockingType(String className) {
			return adelegate.isLockingType(className);
		}

		@Override
		public void registerLockingType(String type) {
			adelegate.registerLockingType(type);
		}

		@Override
		public void byteCodeUsed(byte opCode) {
			adelegate.byteCodeUsed(opCode);
		}

		@Override
		public boolean isBytecodeUsed(int i) {
			return adelegate.isBytecodeUsed(i);
		}

		@Override
		public void registerNativeField(String containingClass, FieldInfo field, IcecapCVar cvar) {
			adelegate.registerNativeField(containingClass, field, cvar);
		}

		@Override
		public NativeFieldInfo isNativeField(String containingClass, FieldInfo field) {
			return adelegate.isNativeField(containingClass, field);
		}

		@Override
		public void classInitializerUsed(String className) {
			adelegate.classInitializerUsed(className);
		}

		@Override
		public Iterator<String> getUsedClassInitializers() {
			return adelegate.getUsedClassInitializers();
		}

		@Override
		public IcecapIterator<MethodOrFieldDesc> getUsedMethods(String nextClass) {
			return adelegate.getUsedMethods(nextClass);
		}

		@Override
		public void reportVtableSize(int s) {
			adelegate.reportVtableSize(s);
		}

		@Override
		public int getMaxVtableSize() {
			return adelegate.getMaxVtableSize();
		}

		@Override
		public void registerCFunc(String className, String name, String signature, IcecapCFunc cfunc) {
			adelegate.registerCFunc(className, name, signature, cfunc);
		}

		@Override
		public CFuncInfo isCFunc(String className, String name, String signature) {
			return adelegate.isCFunc(className, name, signature);
		}

		@Override
		public IcecapIterator<CFuncInfo> getCFunctions() throws Exception {
			return adelegate.getCFunctions();
		}
	}

	private DependencyExtent extent;

	private Observer observer;

	private IDGenerator idGen;

	private ByteCodePatcher patcher;

	private ConversionConfiguration config;

	private static class CompilerRegistry implements ICompilationRegistry {
		private ICompilationRegistry delegate;
		private RequiredMethodsManager rmManager;

		public CompilerRegistry(ICompilationRegistry cregistry, RequiredMethodsManager rmManager) {
			this.delegate = cregistry;
			this.rmManager = rmManager;
		}

		@Override
		public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
			if (rmManager.isRequieredMethod(mdesc)) {
				return false;
			} else {
				return delegate.isMethodCompiled(mdesc);
			}
		}

		@Override
		public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
			return delegate.isMethodExcluded(clazz, targetMethodName, targetMethodSignature);
		}

		@Override
		public boolean alwaysClearOutputFolder() {
			return delegate.alwaysClearOutputFolder();
		}
	}

	public void startCompilation(PrintStream out, RestartableMethodObserver methodObserver, ConversionConfiguration config, IcecapProgressMonitor progressMonitor,
			ICompilationRegistry cregistry, boolean compile) throws Throwable {
		boolean supportLoading = false;
		this.config = config;

		UsedElementsObserver usedElementsObserver = new UsedElementsObserver();

		usedElementsObserver.setProgressMonitor(progressMonitor);

		observer = new Observer(methodObserver, usedElementsObserver);

		Converter converter = new Converter(out, observer, cregistry, supportLoading);

		converter.setObserver(observer);

		VirtualTable.init();

		if (converter.startConversion(config) != null) {

			extent = converter.getDependencyExtent();

			idGen = new IDGenerator();

			RequiredMethodsManager rmManager = new RequiredMethodsManager(idGen, supportLoading);

			cregistry = new CompilerRegistry(cregistry, rmManager);

			converter.getCallGraph().analyse(extent, cregistry);

			Iterator<MethodEntryPoints> methods = extent.getMethods();

			while (methods.hasNext()) {
				MethodEntryPoints next = methods.next();
				if (!next.getMethod().isStatic() && next.getMethod().isSynchronized()) {
					observer.registerLockingType(next.getClazz().getClassName());
				}
			}

			FieldOffsetCalculator foCalc = new FieldOffsetCalculator();

			foCalc.calculate(usedElementsObserver.getUsedClasses(), usedElementsObserver);

			patcher = new IcecapByteCodePatcher(usedElementsObserver, idGen, foCalc, supportLoading);
			methods = extent.getMethods();
			int methodCount = 0;
			int maxSwitchSize = 0;

			while (methods.hasNext()) {
				MethodEntryPoints next = methods.next();
				methodCount++;

				ProducerConsumerAnalyser.annotate(next);

				StackReferencesAnalyser stackReferences = new StackReferencesAnalyser(next, next.getClazz());

				try {
					stackReferences.analyseStackUsage();
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("Stack analysis failed! [" + next.getClazz().getClassName() + ", " + next.getMethod().getName() + "]");
				}

				StackArrayReferencesAnalyser stackArrayReferences = new StackArrayReferencesAnalyser(next, next.getClazz());

				try {
					stackArrayReferences.analyseStackUsage();
				} catch (Exception e) {
					throw new Exception("Array usage analysis failed!");
				}

				LocalVariableUsageAnalyser lvAnalyser = new LocalVariableUsageAnalyser(next);

				lvAnalyser.analyse();

				ArrayList<BNode> bnodes = next.getBNodes();

				String className = next.getClazz().getClassName();
				String targetMethodName = next.getMethod().getName();
				String targetMethodSignature = next.getMethod().getSignature();

				for (int i = 0; i < bnodes.size(); i++) {
					BNode current = bnodes.get(i);
					switch ((byte) current.getOpCode()) {
					case RawByteCodes.new_opcode:
					case RawByteCodes.checkcast_opcode:
					case RawByteCodes.instanceof_opcode:
					case RawByteCodes.invokevirtual_opcode:
					case RawByteCodes.invokespecial_opcode:
					case RawByteCodes.invokestatic_opcode:
					case RawByteCodes.invokedynamic_opcode:
					case RawByteCodes.invokeinterface_opcode:
					case RawByteCodes.getstatic_opcode:
					case RawByteCodes.putstatic_opcode:
					case RawByteCodes.getfield_opcode:
					case RawByteCodes.putfield_opcode:
					case RawByteCodes.ldc_opcode:
					case RawByteCodes.ldc_w_opcode:
					case RawByteCodes.ldc2_w_opcode:
						patcher.registerByteCode(className, targetMethodName, targetMethodSignature, current);
						break;
					case RawByteCodes.anewarray_opcode:
					case RawByteCodes.newarray_opcode:
						patcher.registerByteCode(className, targetMethodName, targetMethodSignature, current);
						if (stackArrayReferences.isFlashArray(className, targetMethodName, targetMethodSignature, current.getOriginalAddress())) {
							((NewArrayBNode) current).setFlashArray();
						}
						break;
					case RawByteCodes.multianewarray_opcode:
						patcher.registerByteCode(className, targetMethodName, targetMethodSignature, current);
					}

					switch ((byte) current.getOpCode()) {
					case RawByteCodes.arraylength_opcode:
					case RawByteCodes.newarray_opcode:
					case RawByteCodes.faload_opcode:
					case RawByteCodes.aastore_opcode:
					case RawByteCodes.fastore_opcode:
					case RawByteCodes.bastore_opcode:
					case RawByteCodes.castore_opcode:
					case RawByteCodes.sastore_opcode:
					case RawByteCodes.lastore_opcode:
					case RawByteCodes.dastore_opcode:
					case RawByteCodes.iastore_opcode:
					case RawByteCodes.aaload_opcode:
					case RawByteCodes.baload_opcode:
					case RawByteCodes.caload_opcode:
					case RawByteCodes.saload_opcode:
					case RawByteCodes.laload_opcode:
					case RawByteCodes.iaload_opcode:
					case RawByteCodes.putfield_opcode:
					case RawByteCodes.getfield_opcode:
					case RawByteCodes.invokevirtual_opcode:
					case RawByteCodes.invokespecial_opcode:
					case RawByteCodes.invokeinterface_opcode:
					case RawByteCodes.ireturn_opcode:
					case RawByteCodes.lreturn_opcode:
					case RawByteCodes.freturn_opcode:
					case RawByteCodes.dreturn_opcode:
					case RawByteCodes.areturn_opcode:
					case RawByteCodes.return_opcode:
						((BNode) current).setStackLayout(stackReferences.getStackLayout(current.getAddress()));
					}

					switch ((byte) current.getOpCode()) {
					case RawByteCodes.monitorenter_opcode: {
						ArrayList<String> types;
						types = Util.getCellType(next, current.getAinfo().entryStack.peek());
						observer.registerLockingTypes(types);
						break;
					}
					}

					if (((byte) current.getOpCode() == RawByteCodes.lookupswitch_opcode) || ((byte) current.getOpCode() == RawByteCodes.tableswitch_opcode)) {
						SwitchBNode switchNode = (SwitchBNode) current;
						if (switchNode.getNumberOfTargets() > maxSwitchSize) {
							maxSwitchSize = switchNode.getNumberOfTargets();
						}
					}
				}

				MethodOrFieldDesc mdesc = Util.getMethodOrFieldDesc(next.getClazz(), next.getMethod());

				if (!Compiler.compileMethod(cregistry, next.getMethod(), mdesc)) {
					for (int i = 0; i < bnodes.size(); i++) {
						observer.byteCodeUsed(bnodes.get(i).getOpCode());
					}
				}

				BNodeUtils.collectExceptions(next);

				Converter.extendBNodes(bnodes, next.getMethod().getCode().getExceptionTable());

				ArrayList<Byte> newCode = new ArrayList<Byte>();
				for (int index = 0; index < bnodes.size(); index++) {
					BNode node = bnodes.get(index);
					byte[] rawBytes = node.getRawBytes();
					for (int j = 0; j < rawBytes.length; j++) {
						newCode.add(rawBytes[j]);
					}
				}
				byte[] newByteCodes = new byte[newCode.size()];
				for (int index = 0; index < newByteCodes.length; index++) {
					newByteCodes[index] = newCode.get(index);
				}
				next.getMethod().getCode().setCode(newByteCodes);
				failingCode = newByteCodes;
			}

			StackDepthAnalyser sda = new StackDepthAnalyser(converter.getCallGraph());

			sda.analyse(config);

			if (compile) {
				Compiler compiler = new Compiler(idGen, rmManager, config, supportLoading);

				ResourceManager rManager = config.getResourceManager();
				if (rManager == null) {
					if (additionalResourceManager == null) {
						additionalResourceManager = new AdditionalResourceManager();
					}
					rManager = additionalResourceManager.createResorceManager();
				}
				compiler.writeClassesToFile("classes", patcher, config, foCalc, usedElementsObserver,cregistry, rManager, out);

				compiler.writeMethodsToFile("methods", usedElementsObserver, patcher, converter, config,  cregistry, converter.getDependencyExtent(), progressMonitor);

				writeTimingInformation(config.getOutputFolder(), out, sda, foCalc, patcher, maxSwitchSize, usedElementsObserver.getMaxVtableSize());

				if (config.reportConversion()) {
					CompilerUtils.reportConversion(methodCount);
				}
				out.println("Code memory: " + MemorySegment.codeBytes + " bytes");
				out.println("Data memory: " + MemorySegment.dataBytes + " bytes");
			}
		} else {
			File methodsFile = new File(Compiler.checkOutputFolder(config.getOutputFolder()) + "methods" + ".c");
			File classesFile = new File(Compiler.checkOutputFolder(config.getOutputFolder()) + "classes" + ".c");
			if (methodsFile.exists()) {
				methodsFile.delete();
			}
			if (classesFile.exists()) {
				classesFile.delete();
			}
			throw new Exception("Compilation failed - check logs in console");
		}
		out.println("Dependency extent: classes[" + usedElementsObserver.numberOfUsedClasses() + "], methods[" + usedElementsObserver.numberOfUsedMethods() + "]");
		Repository.clearCache();
	}

	private void writeTimingInformation(String outputFolder, PrintStream out, StackDepthAnalyser sda, FieldOffsetCalculator foCalc, ByteCodePatcher patcher, int maxSwitchSize,
			int maxVTableSize) {
		FileOutputStream tinfo = null;
		try {
			StringBuffer tinfoPath = new StringBuffer();
			tinfoPath.append(outputFolder);
			if (!outputFolder.endsWith(File.separatorChar + "")) {
				tinfoPath.append(File.separatorChar);
			}
			tinfoPath.append("tinfo.h");

			tinfo = new FileOutputStream(tinfoPath.toString());
			StringBuffer content = new StringBuffer();

			content.append("/* Maximum stack depth: \n");
			LinkedList<MethodIdentifier> maxStack = sda.getMaxStack();
			for (MethodIdentifier current : maxStack) {
				content.append("   " + current.getClassName() + "." + current.getName() + "(" + current.getSignature() + ")\n");
			}
			content.append("*/\n");
			content.append("#define MAX_APP_STACK " + sda.calculateMaxDepth() + "\n");

			content.append("/* Maximum class heirarchy: \n");
			LinkedList<String> heirarchy = foCalc.getMaxClassHeirarchy();
			for (String current : heirarchy) {
				content.append("   " + current + "\n");

			}
			content.append("*/\n");
			content.append("#define MAX_CLASS_HEIRARCHY " + heirarchy.size() + "\n");
			content.append("/* Maximum lookup/tableswitch jump table size */\n");
			content.append("#define MAX_LOOKUPTABLE_SWITCH_SIZE " + maxSwitchSize + "\n");
			content.append("/* Maximum invokevirtual/interface jump table size */\n");
			content.append("#define MAX_INVOKE_TABLE_SIZE " + maxVTableSize + "\n");

			tinfo.write(content.toString().getBytes());
			tinfo.flush();
			tinfo.close();
		} catch (FileNotFoundException e) {
			out.println(e.getMessage());
		} catch (IOException e) {
			out.println(e.getMessage());
		}
	}

	public DependencyExtent getDependencyExtent() {
		return extent;
	}

	public AnalysisObserver getObserver() {
		return observer;
	}

	public ByteCodePatcher getPatcher() {
		return patcher;
	}

	public ConversionConfiguration getConfig() {
		return config;
	}

	public IDGenerator getIDGen() {
		return idGen;
	}
}
