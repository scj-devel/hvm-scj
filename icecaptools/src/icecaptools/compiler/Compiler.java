package icecaptools.compiler;

import icecaptools.AnalysisObserver;
import icecaptools.AnnotationsAttribute;
import icecaptools.ClassManager;
import icecaptools.ClassfileUtils;
import icecaptools.FieldOffsetCalculator;
import icecaptools.HVMProperties;
import icecaptools.IcecapCFunc;
import icecaptools.IcecapCompileMe;
import icecaptools.IcecapIterator;
import icecaptools.IcecapProgressMonitor;
import icecaptools.IcecapTool;
import icecaptools.JavaArrayClass;
import icecaptools.LambdaClass;
import icecaptools.MethodAndClass;
import icecaptools.MethodOrFieldDesc;
import icecaptools.ResourceManager;
import icecaptools.StreamResource;
import icecaptools.compiler.aot.AOTCompiler;
import icecaptools.compiler.aot.AOTToolBox;
import icecaptools.compiler.utils.ClassInheritanceMatrix;
import icecaptools.compiler.utils.CustomNewlineConverter;
import icecaptools.compiler.utils.DefaultNewlineConverter;
import icecaptools.compiler.utils.NewlineConverter;
import icecaptools.compiler.utils.OutputLocation;
import icecaptools.compiler.utils.StringBufferContainer;
import icecaptools.compiler.utils.StringContainer;
import icecaptools.compiler.utils.StringStringContainer;
import icecaptools.compiler.utils.SubClassChecker;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.conversion.DependencyExtent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.RuntimeInvisibleAnnotations;
import org.apache.bcel.generic.Type;

public class Compiler {

	private static final String GCSUPPORTTAG = "SUPPORTGC";

	private IDGenerator idGen;
	private RequiredMethodsManager rmManager;
	private NewlineConverter newLineConverter;

	private ClassInheritanceMatrix classMatrix;

	private CodeDetector codeDetector;
	private NativeMethodDetector nativeMethodDetector;

	private boolean supportLoading;
	private ClassFieldsManager classFieldsManager;

	private ConversionConfiguration config;

	public Compiler(IDGenerator idGen, RequiredMethodsManager rmManager, ConversionConfiguration manager,
			boolean supportLoading) {
		this.supportLoading = supportLoading;
		this.idGen = idGen;
		this.rmManager = rmManager;
		this.codeDetector = manager.getCodeDetector();
		this.nativeMethodDetector = manager.getNativeMethodDetector();
		this.config = manager;

		try {
			String newlineSeq = manager.getProperties().getNewlineSequence();
			if ((newlineSeq.length() > 0) && (!("\n".equals(newlineSeq)))) {
				newLineConverter = new CustomNewlineConverter(newlineSeq);
			} else {
				newLineConverter = new DefaultNewlineConverter();
			}
		} catch (Exception e) {
			newLineConverter = new DefaultNewlineConverter();
		}
	}

	private static class ClassInfo {
		@SuppressWarnings("unused")
		int classSize;
		@SuppressWarnings("unused")
		int vtableSize;
	}

	public void writeClassesToFile(String filename, ByteCodePatcher patcher, IcecapTool manager,
			FieldOffsetCalculator foCalc, AnalysisObserver observer, String outputFolder,
			ICompilationRegistry cregistry, ResourceManager resourceManager, PrintStream out) throws Throwable {
		RequiredEntryManager rcManager = new RequiredClassesManager(supportLoading);
		RequiredFieldsManager fieldsManager = new RequiredFieldsManager(supportLoading);
		RequiredEntryManager interfacesManager = new RequiredInterfacesManager(supportLoading);
		ReferencesManager referencesManager = new ReferencesManager(foCalc);
		classFieldsManager = new ClassFieldsManager();
		StructsManager structsManager = new StructsManager(foCalc, idGen);

		HashMap<JavaClass, OffsetPair> objectSize = foCalc.getObjectSizes();
		FileOutputStream classesSource = null;
		FileOutputStream classesHeader = null;

		StringBuffer fileSb = new StringBuffer(); // StringBuffer connected with
													// file output
		HVMProperties props = manager.getProperties();

		MemorySegment classesSb = new MemorySegment(props);
		MemorySegment classNames = new MemorySegment(props);

		try {
			outputFolder = checkOutputFolder(outputFolder);
			classesSource = new FileOutputStream(outputFolder + filename + ".c");
			classesHeader = new FileOutputStream(outputFolder + filename + ".h");
			dumpVMSource(outputFolder, resourceManager, cregistry, out);
		} catch (FileNotFoundException e) {
			out.println("writeClassStructure[1]:" + e.getMessage());
		} catch (Throwable e) {
			out.println("writeClassStructure[2]:" + e.getMessage());
			throw e;
		}

		StringBuffer referenceFieldsOffssets = referencesManager.generateReferenceFieldOffsets(GCSUPPORTTAG);
		StringBuffer objectFieldStructs = structsManager.generateObjectFieldStructs();
		StringBuffer classFieldStructs = structsManager.generateClassFieldStructs(observer);

		int classNumber = 0;
		Iterator<String> classes = observer.getUsedClasses();

		classNames.startProgmem();
		while (classes.hasNext()) {
			String currentClassName = classes.next();
			JavaClass currentClass = Repository.lookupClass(currentClassName);
			patcher.registerClassNumber(currentClassName, classNumber);
			if (!(currentClass instanceof JavaArrayClass)) {
				if ((currentClass instanceof LambdaClass) || (!currentClass.isInterface())) {
					handleInterfaces(patcher, currentClass);
				}
			}
			classNumber++;
		}

		InterfaceManager iManager = patcher.getInterfaceManager();
		iManager.createInterfaceTableDecl(manager, idGen);

		Iterator<String> interfaces = iManager.getInterfaces();
		MemorySegment interfaceMatrixDecls = new MemorySegment(manager.getProperties());

		StringBuffer systemClassesBuffer = new StringBuffer();

		interfaceMatrixDecls.print(iManager.generateImplementors(observer, patcher, manager));

		interfaces = iManager.getInterfaces();
		systemClassesBuffer.append("#define NUMBEROFINTERFACES " + iManager.getNumberOfInterfaces() + "\n");
		while (interfaces.hasNext()) {
			String nextInterface = interfaces.next();
			if (observer.isInterfaceUsed(nextInterface)) {
				int interfaceNumber = iManager.getInterfaceIndex(nextInterface);
				interfacesManager.registerEntry(nextInterface, interfaceNumber);
			}
		}
		fileSb.append("#include \"types.h\"\n#include \"methods.h\"\n#include \"ostypes.h\"\n#include \"classes.h\"\n\n");
		classesSb.startProgmem();
		classesSb.appendCode("static RANGE const ClassInfo _classes[" + classNumber + "] PROGMEM = {\n",
				35 * classNumber);

		classes = observer.getUsedClasses();
		classNumber = 0;

		while (classes.hasNext()) {
			String currentClassName = classes.next();
			JavaClass currentClass = Repository.lookupClass(currentClassName);

			ArrayList<FieldInfo> objectFieldSet = foCalc.getObjectFields().get(currentClass);

			registerFields(patcher, objectFieldSet, currentClassName, false, fieldsManager);

			ArrayList<FieldInfo> classFieldSet = foCalc.getClassFields().get(currentClass);

			registerFields(patcher, classFieldSet, currentClassName, true, fieldsManager);

			classFieldsManager.addClassFields(currentClassName, classFieldSet, observer);

			int superClassIndex = -1;
			JavaClass superClass = currentClass.getSuperClass();

			if (superClass != null) {
				superClassIndex = patcher.getClassNumber(currentClass.getSuperclassName());
				if (superClassIndex == -1) {
					throw new Exception("Could not find super class for [" + currentClassName + "]");
				}
			}
			OffsetPair oSize = objectSize.get(currentClass);

			if (oSize == null) {
				throw new Exception("Could not find object size");
			}

			int doSize = oSize.dheapoffset;
			int poSize = oSize.pheapoffset;
			String doSizeString;

			int dimension = 0;

			if (currentClass instanceof JavaArrayClass) {
				JavaArrayClass arrayClass = (JavaArrayClass) currentClass;
				dimension = arrayClass.getDimension();
				String className = arrayClass.getClassName();
				String elementType = JavaArrayClass.getElementType(className);
				if (JavaArrayClass.isReferenceClass(elementType)) {
					elementType = JavaArrayClass.getReferredType(elementType);
					JavaClass elementClass = Repository.lookupClass(currentClassName);
					if (!elementClass.isInterface()) {
						int elementTypeIndex = patcher.getClassNumber(elementType);
						doSize = elementTypeIndex;
					} else {
						doSize = -4;
					}
				} else {
					int basicSize = arrayClass.getBasicTypeSize();
					doSize = -basicSize;
				}
				doSizeString = doSize + "";
			} else {
				String structName = structsManager.getStructName(currentClass.getClassName());
				if (structName != null) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("(sizeof(struct ");
					buffer.append(structsManager.getStructName(currentClass.getClassName()));
					buffer.append(") - sizeof(Object))");
					buffer.append(" << 3 /* approx ");
					buffer.append(doSize >> 3);
					buffer.append(" bytes */");

					doSizeString = buffer.toString();
				} else {
					doSizeString = doSize + "";
				}
			}

			if (props.includeMethodAndClassNames()) {
				classNames.appendCode(
						"RANGE static const char className" + classNumber + "[" + (currentClassName.length() + 1) + "]"
								+ " PROGMEM = \"" + currentClassName + "\";\n", currentClassName.length());
			}

			String hasLock = (oSize.hasRoomForLock() ? "1" : "0");

			classesSb.print("\t\t{ " + superClassIndex + ", " + dimension + ", " + hasLock + ", " + doSizeString + ", "
					+ poSize);

			String classInterfaceName = iManager.getClassInterfaceName(classNumber);

			if (classInterfaceName != null) {
				classesSb.print(", " + classInterfaceName);
			} else {
				classesSb.print(", 0");
			}

			classesSb.print(", ");
			if (props.includeMethodAndClassNames()) {
				classesSb.print("className" + classNumber);
			} else {
				classesSb.print("className_");
			}
			classesSb.print("\n");
			classesSb.print("#if defined(" + GCSUPPORTTAG + ")\n");
			if (referencesManager.hasReferences(currentClass)) {
				classesSb.print("\t\t, " + ClassfileUtils.getClassNameIdentifier(currentClass.getClassName()));
				classesSb.print("_references\n");
			} else {
				classesSb.print("\t\t, 0\n");
			}
			classesSb.print("#endif\n");
			classesSb.print("\t\t}" + (classes.hasNext() ? "," : "") + "\n");

			rcManager.registerEntry(currentClassName, classNumber);

			classNumber++;
		}

		MemorySegment fieldDecl = new MemorySegment(props);

		fieldDecl.startProgmem();

		boolean staticReferenceOffsetsVariableUsed = classFieldsManager.finalizeClassfieldDeclarations(fieldDecl);

		systemClassesBuffer.append("#define NUMBEROFCLASSES " + classNumber + "\n");
		classNames.stopProgmem();

		classesSb.print("};\n");
		classesSb.stopProgmem();

		systemClassesBuffer.append(rcManager.getDeclarations().toString());

		rcManager.getVariableDeclarations(systemClassesBuffer, fileSb);

		StringBuffer requieredFields = fieldsManager.getDeclarations();

		fieldDecl.stopProgmem();

		fileSb.append(fieldDecl + "\n");
		fileSb.append(interfaceMatrixDecls.toString());

		if (classFieldsManager.NUMBEROFCLASSES_varUsed) {
			fileSb.append("RANGE uint16 NUMBEROFCLASSES_var = NUMBEROFCLASSES;\n\n");
		}

		if (props.includeMethodAndClassNames()) {
			fileSb.append(classNames);
		} else {
			fileSb.append("RANGE static const char className_[8] PROGMEM = \"\";\n");
		}

		fileSb.append(referenceFieldsOffssets.toString());
		fileSb.append("\n");

		fileSb.append(classesSb);
		fileSb.append("\n");

		fileSb.append("#if defined(INSTANCEOF_OPCODE_USED) || defined(CHECKCAST_OPCODE_USED) || defined(JAVA_LANG_THROWABLE_INIT_) || defined(PUTHWFIELD_OPCODE_USED) || defined(GETHWFIELD_OPCODE_USED)\n");
		this.classMatrix = getClassMatrix(patcher);
		fileSb.append(this.classMatrix.getMatrix());
		fileSb.append("\n");
		fileSb.append("const unsigned char *inheritanceMatrix = &_inheritanceMatrix[0];\n");

		if (supportLoading) {
			fileSb.append("#else\n");
			fileSb.append("RANGE const uint8 tupac = 0;\n");
			fileSb.append("RANGE static const unsigned char _inheritanceMatrix[1] PROGMEM = { 0 };\n");
			fileSb.append("const unsigned char *inheritanceMatrix = &_inheritanceMatrix[0];\n");
		}
		fileSb.append("#endif\n");
		fileSb.append("const ClassInfo *classes = &_classes[0];\n");

		if (classFieldsManager.hasClassFields()) {
			fileSb.append("const unsigned char* classData;\n\n");
			if (staticReferenceOffsetsVariableUsed) {
				fileSb.append("const uint32* staticReferenceOffsets;\n\n");
			}
		}

		requieredFields.append("#define CLASSDATASIZE " + classFieldsManager.getClassDataSize() + "\n");
		requieredFields.append("#define INHERITANCEMATRIXSIZE " + classMatrix.getMatrixSize() + "\n");
		fileSb.append("unsigned char initClasses(void) {\n");
		if (classFieldsManager.hasClassFields()) {
			fileSb.append("   classData = &_classData[0];\n");
			if (staticReferenceOffsetsVariableUsed) {
				fileSb.append("   staticReferenceOffsets = &_staticReferenceOffsets[0];\n");
			}
		}
		fileSb.append("   return 1;\n");
		fileSb.append("}\n\n");

		fileSb = normalizeNewLines(new StringBufferContainer(fileSb), manager);

		try {
			classesSource.write(fileSb.toString().getBytes());
			classesSource.flush();
			classesSource.close();

			systemClassesBuffer = normalizeNewLines(new StringBufferContainer(systemClassesBuffer), manager);
			requieredFields = normalizeNewLines(new StringBufferContainer(requieredFields), manager);

			StringBuffer interfaceBuffer = interfacesManager.getDeclarations();
			interfaceBuffer.append("\n");
			interfaceBuffer = normalizeNewLines(new StringBufferContainer(interfaceBuffer), manager);

			classesHeader.write(systemClassesBuffer.toString().getBytes());
			classesHeader.write(requieredFields.toString().getBytes());
			classesHeader.write(interfaceBuffer.toString().getBytes());
			classesHeader.write(objectFieldStructs.toString().getBytes());
			classesHeader.write(classFieldStructs.toString().getBytes());
			classesHeader.flush();
			classesHeader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ClassInheritanceMatrix getClassMatrix(ByteCodePatcher patcher) throws ClassNotFoundException {
		int numberOfClasses = patcher.getNumberOfClasses();
		ClassInheritanceMatrix matrix = new ClassInheritanceMatrix(numberOfClasses);
		SubClassChecker checker = new SubClassChecker();

		for (int i = 0; i < numberOfClasses; i++) {
			for (int j = 0; j < numberOfClasses; j++) {
				String subClassName = patcher.getClassName(i);
				String superClassName = patcher.getClassName(j);

				JavaClass subClass = Repository.lookupClass(subClassName);
				JavaClass superClass = Repository.lookupClass(superClassName);

				if (checker.isSubclassOf(subClass, superClass)) {
					matrix.setInherits(i, j);
				}
			}
		}
		return matrix;
	}

	protected ClassInfo ensureClassInfo(HashMap<JavaClass, ClassInfo> classInfo, JavaClass currentClass) {
		ClassInfo currentInfo;
		if (!classInfo.containsKey(currentClass)) {
			currentInfo = new ClassInfo();
			classInfo.put(currentClass, currentInfo);
		} else {
			currentInfo = classInfo.get(currentClass);
		}
		return currentInfo;
	}

	private StringBuffer normalizeNewLines(StringContainer buffer, IcecapTool manager) throws Exception {
		StringBuffer normalized = new StringBuffer();

		for (int i = 0; i < buffer.length(); i++) {
			newLineConverter.convert(normalized, buffer.charAt(i));
		}
		return normalized;
	}

	private void dumpVMSource(String outputFolder, ResourceManager resourceManager, ICompilationRegistry cregistry,
			PrintStream out) throws IOException {

		if (outputFolder.length() > 0) {
			IcecapIterator<StreamResource> resources = resourceManager.getResources(out);
			while (resources.hasNext()) {
				String resourcePath = outputFolder;
				StreamResource nextInput = (StreamResource) resources.next();
				String resourceName = nextInput.getResourceName();

				StringTokenizer tokenizer = new StringTokenizer(resourceName, "/");
				while (tokenizer.hasMoreTokens()) {
					resourcePath += tokenizer.nextToken();
					if (tokenizer.hasMoreTokens()) {
						File folder = new File(resourcePath);
						if (!folder.exists()) {
							folder.mkdir();
						}
						resourcePath += File.separatorChar;
					}
				}
				File nextOutput = new File(resourcePath);

				if (cregistry.alwaysClearOutputFolder()) {
					if (nextOutput.exists()) {
						try {
							nextOutput.delete();
						} catch (Throwable t) {
							out.println("Unable to delete [" + nextOutput.getAbsolutePath() + "]");
						}
					}
				}

				if (!nextOutput.exists()) {
					FileOutputStream writer = new FileOutputStream(nextOutput);
					InputStream reader = nextInput.getStream();

					this.codeDetector.fileStart(resourceName, writer);
					;
					int next;
					while ((next = reader.read()) != -1) {
						newLineConverter.convert(writer, next);
						this.codeDetector.newRead((char) next);
					}
					writer.flush();
					writer.close();
				} else {
					Date current = new Date();
					nextOutput.setLastModified(current.getTime());
				}
			}
		}
	}

	public static String checkOutputFolder(String outputFolder) {
		if ((outputFolder == null) || (outputFolder.length() == 0)) {
			outputFolder = "";
		} else {
			if (!outputFolder.endsWith("" + File.separatorChar)) {
				outputFolder = outputFolder + File.separatorChar;
			}
		}
		return outputFolder;
	}

	private void registerFields(ByteCodePatcher patcher, ArrayList<FieldInfo> fieldSet, String currentClassName,
			boolean isStatic, RequiredFieldsManager fieldsManager) throws ClassNotFoundException {
		if (fieldSet.size() > 0) {
			int fieldNumber = 0;
			Iterator<FieldInfo> itField = fieldSet.iterator();
			while (itField.hasNext()) {
				FieldInfo currentField = itField.next();
				patcher.registerFieldNumber(currentClassName, currentField, fieldNumber, isStatic);
				fieldsManager.registerEntry(currentClassName + "." + currentField.getName(), fieldNumber, currentField);
				fieldNumber++;
			}
		}
	}

	private void handleInterfaces(ByteCodePatcher patcher, JavaClass clazz) throws ClassNotFoundException {
		JavaClass[] interfaces = clazz.getAllInterfaces();
		for (JavaClass _interface : interfaces) {
			patcher.registerInterface(clazz.getClassName(), _interface.getClassName());
		}
	}

	public void writeMethodsToFile(String filename, AnalysisObserver observer, ByteCodePatcher patcher,
			ClassManager manager, IcecapTool tool, String outputFolder, ICompilationRegistry cregistry,
			DependencyExtent dependencyExtent, IcecapProgressMonitor progressMonitor) throws Exception {
		FileOutputStream sourceFile = null;
		FileOutputStream headerFile = null;
		OutputLocation nativeHeaderFile = null;
		OutputLocation nativeTargetSource = null;
		OutputLocation nativeHostSource = null;

		MethodsFile methodsFile = new MethodsFile(true);
		StaticInitializersManager siManager = new StaticInitializersManager(patcher);

		HVMProperties props = tool.getProperties();

		MemorySegment sb = new MemorySegment(props);
		this.nativeMethodDetector.startAnalysis();
		try {
			outputFolder = checkOutputFolder(outputFolder);
			sourceFile = new FileOutputStream(outputFolder + filename + ".c");
			headerFile = new FileOutputStream(outputFolder + filename + ".h");

			File nhFile = new File(outputFolder + "natives.h");
			if (!nhFile.exists()) {
				nativeHeaderFile = new OutputLocation(nhFile);
			} else {
				nativeHeaderFile = new OutputLocation();
			}

			File ntsFile = new File(outputFolder + "natives_target.c");
			if (!ntsFile.exists()) {
				nativeTargetSource = new OutputLocation(ntsFile);
			} else {
				nativeTargetSource = new OutputLocation();
			}

			File nhsFile = new File(outputFolder + "natives_host.c");
			if (!nhsFile.exists()) {
				nativeHostSource = new OutputLocation(nhsFile);
			} else {
				nativeHostSource = new OutputLocation();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		NativeFileManager nfileManager = new NativeFileManager(this.classFieldsManager.NUMBEROFCLASSES_varUsed,
				patcher.getNumberOfClasses());

		byte[] currentMethodCode = null;

		int methodNumber = 0;
		int mainMethodIndex = 0;

		MemorySegment methodInfoArray = new MemorySegment(tool.getProperties());
		MemorySegment header = new MemorySegment(tool.getProperties());
		MemorySegment methodNames = new MemorySegment(tool.getProperties());
		LabeledMemorySegment requiredIncludes = new LabeledMemorySegment(tool.getProperties());
		NoDuplicatesMemorySegment userIncludes = new NoDuplicatesMemorySegment(tool.getProperties());

		methodsFile.setHeader(header);

		requiredIncludes.print("#include \"classes.h\"\n");

		// for code all classes
		sb.print("\n");
		sb.startProgmem();
		methodNames.startProgmem();

		IcecapIterator<MethodOrFieldDesc> methods = observer.getUsedMethods();

		while (methods.hasNext()) {
			MethodOrFieldDesc currentMethod = methods.next();
			patcher.registerMethodNumber(currentMethod, methodNumber, idGen);
			if (isStaticInitializer(currentMethod)) {
				siManager.registerStaticInitializer(currentMethod, methodNumber);
			}
			methodNumber++;
		}

		methodNumber = 0;
		methods = observer.getUsedMethods();

		while (methods.hasNext()) {
			MethodOrFieldDesc currentMethod = methods.next();

			MethodAndClass methodDesc = ClassfileUtils.findMethod(currentMethod.getClassName(),
					currentMethod.getName(), currentMethod.getSignature());

			if (methodDesc != null) {

				Method javaMethod = methodDesc.getMethod();
				if (javaMethod != null) {
					sb.print("/* Class: " + currentMethod.getClassName() + " */\n");

					Code codeAttr = javaMethod.getCode();
					int numExceptionHandlers = 0;
					sb.print("/* Method: " + currentMethod.getName() + " */\n");

					String uniqueMethodId = idGen.getUniqueId(currentMethod.getClassName(), currentMethod.getName(),
							currentMethod.getSignature());
					String methodNameConst;
					methodInfoArray
							.print("  { "
									+ ((patcher.getClassNumber(currentMethod.getClassName()) << 1) | getLambdaAdjustment(javaMethod))
									+ ", ");
					if ((codeAttr != null)
							&& (!manager.skipMethodHack(currentMethod.getClassName(), currentMethod.getName(),
									currentMethod.getSignature()))) {
						String methodDeclaration = null;

						currentMethodCode = codeAttr.getCode();

						patcher.patch(currentMethod.getClassName(), currentMethod.getName(),
								currentMethod.getSignature(), currentMethodCode, observer, idGen);

						if (compileMethod(cregistry, javaMethod, currentMethod)) {
							AOTToolBox toolBox = new AOTToolBox(manager, dependencyExtent, tool, patcher,
									currentMethod.getClassName(), cregistry, siManager, idGen, observer,
									this.classMatrix);
							AOTCompiler aotCompiler;
							aotCompiler = new UserIncudesAwareFlowBasedCompiler(javaMethod, currentMethodCode,
									uniqueMethodId, methodNumber, requiredIncludes, userIncludes, toolBox);

							methodDeclaration = aotCompiler.compile();
							sb.appendCode(methodDeclaration, 0);
							nfileManager.addCompiledMethod(methodNumber, uniqueMethodId, javaMethod, manager
									.skipMethodHack(currentMethod.getClassName(), currentMethod.getName(),
											currentMethod.getSignature()));
						} else {
							sb.appendCode("RANGE const unsigned char " + uniqueMethodId + "["
									+ currentMethodCode.length + "] PROGMEM = {\n", currentMethodCode.length);
							sb.print("  " + ConstantGenerator.getHex(currentMethodCode, 16, patcher.getFieldOffsets()));
							sb.print("\n};\n");
						}
						numExceptionHandlers = getNumExceptionhandlers(javaMethod);

						if (numExceptionHandlers > 0) {
							sb.print("\n");
							sb.appendCode("RANGE const ExceptionHandler ex_" + uniqueMethodId + "["
									+ numExceptionHandlers + "] PROGMEM = {\n", numExceptionHandlers * 14);
							CodeException[] exceptions = codeAttr.getExceptionTable();
							for (int i = 0; i < numExceptionHandlers; i++) {
								CodeException current = exceptions[i];
								sb.print("  { "
										+ current.getStartPC()
										+ ", "
										+ current.getEndPC()
										+ ", "
										+ current.getHandlerPC()
										+ ", "
										+ getClassNumber(currentMethod.getClassName(), manager, patcher,
												current.getCatchType()) + "}");
								if (i + 1 < numExceptionHandlers) {
									sb.print(",");
								}
								sb.print("\n");
							}
							sb.print("};\n");
						}

						if (methods.hasNext()) {
							sb.print("\n");
						}

						methodInfoArray.print("" + codeAttr.getMaxStack());
						{
							int maxLocals = getMaxLocals(codeAttr);
							methodInfoArray.print(", " + maxLocals);
						}
						if (methodDeclaration == null) {
							methodInfoArray.print(", " + currentMethodCode.length);
						} else {
							methodInfoArray.print(", 0");
						}

						methodInfoArray.print(", " + numExceptionHandlers);
						methodInfoArray.print(", " + ClassfileUtils.getNumArgs(currentMethod));
						methodInfoArray.print(", " + getNumReturnValues(javaMethod));

						if (numExceptionHandlers > 0) {
							methodInfoArray.print(", ex_" + uniqueMethodId);
						} else {
							methodInfoArray.print(", 0");
						}

						if (methodDeclaration == null) {
							methodInfoArray.print(", " + uniqueMethodId);
							methodInfoArray.print(", 0");
						} else {
							methodInfoArray.print(", 0");
							IcecapCFunc annotation = hasAnnotation(javaMethod, IcecapCFunc.class);
							if (annotation == null) {
								methodInfoArray.print(", (int16 (*)(int32 *))" + uniqueMethodId);
							} else {
								methodInfoArray.print(", (int16 (*)(int32 *))0");
							}
						}
						methodNameConst = currentMethod.getClassName() + "." + javaMethod.getName();
						if (props.includeMethodAndClassNames()) {
							methodInfoArray.print(", methodName" + methodNumber + " }");
						} else {
							methodInfoArray.print(", methodName_ }");
						}
					} else {
						nfileManager.addNativeMethod(methodNumber, "n_" + uniqueMethodId, javaMethod, manager
								.skipMethodHack(currentMethod.getClassName(), currentMethod.getName(),
										currentMethod.getSignature()));
						methodInfoArray.print("0");
						methodInfoArray.print(", 0");
						methodInfoArray.print(", 0");
						methodInfoArray.print(", 0");
						methodInfoArray.print(", " + ClassfileUtils.getNumArgs(javaMethod));
						methodInfoArray.print(", " + getNumReturnValues(javaMethod));
						methodInfoArray.print(", 0");
						methodInfoArray.print(", 0");
						if (javaMethod.isNative()
								|| manager.skipMethodHack(currentMethod.getClassName(), currentMethod.getName(),
										currentMethod.getSignature())) {
							methodInfoArray.print(", n_" + uniqueMethodId); // code
						} else {
							methodInfoArray.print(", 0"); // code
						}
						methodNameConst = javaMethod.getName();
						if (props.includeMethodAndClassNames()) {
							methodInfoArray.print(", methodName" + methodNumber + " }");
						} else {
							methodInfoArray.print(", methodName_ }");
						}
					}
					if (props.includeMethodAndClassNames()) {
						methodNames.appendCode("RANGE static const char methodName" + methodNumber + "["
								+ (methodNameConst.length() + 1) + "] PROGMEM = \"" + methodNameConst + "\";\n",
								methodNameConst.length());
					}
					if (methods.hasNext()) {
						methodInfoArray.print(",");
					}
					methodInfoArray.print("\n");

					header.print("#define ");
					header.print(uniqueMethodId.toUpperCase());
					header.print(" ");
					header.print("" + methodNumber);
					header.print(" /* " + currentMethod.getName() + " [" + currentMethod.getClassName() + "] */\n");

					if (isMainMethod(currentMethod, javaMethod)) {
						mainMethodIndex = methodNumber;
					}
					rmManager.registerEntry(uniqueMethodId, methodNumber);

					methodNumber++;
				}
			} else {
				methodInfoArray.print("  { 0, ");
				methodInfoArray.print("0");
				methodInfoArray.print(", 0");
				methodInfoArray.print(", 0");
				methodInfoArray.print(", 0");
				methodInfoArray.print(", 0");
				methodInfoArray.print(", 0");
				methodInfoArray.print(", 0");
				methodInfoArray.print(", 0");
				methodInfoArray.print(", 0"); // code
				methodInfoArray.print(", 0 }");
				if (methods.hasNext()) {
					methodInfoArray.print(",");
				}
				methodInfoArray.print("\n");
				methodNumber++;
			}
		}
		methodInfoArray.print("};\n");
		methodNames.stopProgmem();

		ExceptionsManager eManager = new ExceptionsManager(observer, header, patcher);
		StringBuffer usedExceptions = eManager.getDeclarations(idGen);

		StringBuffer temp = new StringBuffer("static RANGE const MethodInfo _methods[" + methodNumber
				+ "] PROGMEM = {\n" + methodInfoArray.toString());
		methodInfoArray = new MemorySegment(tool.getProperties());
		methodInfoArray.startProgmem();
		methodInfoArray.appendData(temp.toString(), methodNumber * 27);

		StringBuffer additionalHeaderFileContent = new StringBuffer();

		ConstantGenerator constantGenerator = new ConstantGenerator(patcher, tool, additionalHeaderFileContent,
				requiredIncludes, supportLoading);

		StringBuffer constantsSb = constantGenerator.generateConstants();

		StringBuffer staticInitializersDecl = siManager.getDecleration(observer);

		header.print("#define NUMBEROFCLASSINITIALIZERS " + siManager.getNumberOfClassInitializers() + "\n");
		if (siManager.getNumberOfClassInitializers() > 0) {
			header.print("#define INVOKECLASSINITIALIZERS\n");
		}
		header.print("#define NUMBEROFCONSTANTS " + patcher.getConstants().size() + "\n");
		header.print("#define MAINMETHODINDEX " + mainMethodIndex + "\n");

		methodsFile.generateMainMethodIndexDecl();
		methodsFile.generateNumberOfClassInitializersDecl();
		methodsFile.generateNumberOfConstantsDecl();
		header.print("#define NUMBEROFMETHODS " + methodNumber + "\n");

		sb.stopProgmem();

		methodInfoArray.stopProgmem();

		try {
			StringBuffer result = new StringBuffer();

			methodsFile.setImplementation(result);

			addToRequiredIncludes(userIncludes.toString(), requiredIncludes);
			addToRequiredIncludes("#include \"types.h\"\n#include \"ostypes.h\"\n#include \"methods.h\"\n\n",
					requiredIncludes);
			addToRequiredIncludes("extern void unimplemented_native_function(uint16 methodID);", requiredIncludes);
			String includes = oraganizeRequiredIncludes(requiredIncludes);

			result.append(includes);

			methodsFile.generateNumberOfClassInitializersImpl();
			methodsFile.generateNumberOfConstantsImpl();
			methodsFile.generateMainMethodIndexImpl();

			result.append(sb.toString());
			result.append(nfileManager.getDeclerations(additionalHeaderFileContent));
			if (props.includeMethodAndClassNames()) {
				result.append(methodNames.toString());
			} else {
				result.append("RANGE static const char methodName_[8] PROGMEM = \"\";\n");
			}
			result.append(methodInfoArray.toString());

			if ((constantGenerator.numberOfConstants() > 0) || supportLoading) {
				String constantDecls = constantsSb.toString();
				result.append(constantDecls + "\n");
			}
			result.append(staticInitializersDecl.toString() + "\n");

			rmManager.getVariableDeclarations(header.getBuffer(), result);

			result.append(usedExceptions.toString());
			result.append("const MethodInfo *methods = &_methods[0];\n");
			if ((constantGenerator.numberOfConstants() > 0) || supportLoading) {
				result.append("const ConstantInfo *constants;\n");
			}

			result.append("#ifdef INVOKECLASSINITIALIZERS\n");
			result.append("const short* classInitializerSequence;\n");
			result.append("#endif\n");

			result.append("#if defined(PRE_INITIALIZE_EXCEPTIONS)\n");
			result.append("ExceptionObject* exceptionObjects;\n");
			result.append("#endif\n\n");

			result.append("unsigned char initMethods(void) {\n");
			if (constantGenerator.numberOfConstants() > 0) {
				result.append("   constants = &_constants[0];\n");
			}
			result.append("#if defined(PRE_INITIALIZE_EXCEPTIONS)\n");
			result.append("   exceptionObjects = &_exceptionObjects[0];\n");
			result.append("#endif\n\n");
			result.append("#ifdef INVOKECLASSINITIALIZERS\n");
			result.append("   classInitializerSequence = &_classInitializerSequence[0];\n");
			result.append("#endif\n\n");
			result.append("   return 1;\n");
			result.append("}\n\n");

			String source = result.toString();

			IcecapCodeFormatter formatter = tool.getCodeFormatter();

			source = formatter.format(source);

			source = normalizeNewLines(new StringStringContainer(source), tool).toString();

			sourceFile.write(source.getBytes());

			source = nfileManager.getNativeHeader();
			source = formatter.format(source);
			source = normalizeNewLines(new StringStringContainer(source), tool).toString();

			nativeHeaderFile.write(source.getBytes());

			source = nfileManager.getNativeTargetSource();
			source = formatter.format(source);
			source = normalizeNewLines(new StringStringContainer(source), tool).toString();

			nativeTargetSource.write(source.getBytes());

			source = nfileManager.getNativeHostSource();
			source = formatter.format(source);
			source = normalizeNewLines(new StringStringContainer(source), tool).toString();

			nativeHostSource.write(source.getBytes());

			result = new StringBuffer();
			result.append(header.toString());
			result.append(rmManager.getUnregisteredDeclarations().toString() + "\n");
			//
			result.append(ByteCodeReporter.reportUsedByteCodes(observer));
			result.append(requiredIncludes.getDefines());
			result.append(additionalHeaderFileContent);

			RequiredEntryManager vtablesIndices = patcher.getRequiredVTableIndices();
			result.append(vtablesIndices.getDeclarations());

			result = normalizeNewLines(new StringBufferContainer(result), tool);

			headerFile.write(result.toString().getBytes());

			sourceFile.flush();
			sourceFile.close();

			headerFile.flush();
			headerFile.close();

			nativeHeaderFile.flush();
			nativeHeaderFile.close();

			nativeTargetSource.flush();
			nativeTargetSource.close();

			nativeHostSource.flush();
			nativeHostSource.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.nativeMethodDetector.endAnalysis();
	}

	private int getLambdaAdjustment(Method javaMethod) {
		if (LambdaClass.isLambdaMethod(javaMethod)) {
			return 1;
		} else {
			return 0;
		}
	}

	private String oraganizeRequiredIncludes(LabeledMemorySegment requiredIncludes) {
		StringBuffer includes = new StringBuffer();
		StringBuffer theRest = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(requiredIncludes.toString(), "\n");
		while (tokenizer.hasMoreElements()) {
			String next = tokenizer.nextToken().trim();
			if (next.startsWith("#include")) {
				if (next.contains("types.h")) {
					includes.insert(0, next + "\n");
				} else {
					includes.append(next);
					includes.append("\n");
				}
			} else {
				theRest.append(next);
				theRest.append("\n");
			}
		}
		includes.append(theRest);
		return includes.toString();
	}

	private void addToRequiredIncludes(String includes, LabeledMemorySegment requiredIncludes) {
		StringTokenizer tokenizer = new StringTokenizer(includes, "\n");
		while (tokenizer.hasMoreElements()) {
			String next = tokenizer.nextToken().trim();
			if (next.length() > 0) {
				requiredIncludes.print(next + "\n");
			}
		}
	}

	public static int getMaxLocals(Code codeAttr) {
		int maxLocals = codeAttr.getMaxLocals();
		if (maxLocals == 0) {
			maxLocals++;
		}
		return maxLocals;
	}

	private static <A extends Annotation> A hasAnnotation(Method javaMethod, Class<A> annotationClass) {
		Attribute[] attributes = javaMethod.getAttributes();
		for (Attribute attribute : attributes) {
			if (attribute instanceof AnnotationsAttribute) {
				AnnotationsAttribute icecapAttribute = (AnnotationsAttribute) attribute;
				A annotation = icecapAttribute.getAnnotation(annotationClass);
				if (annotation != null) {
					return annotation;
				}
			}
			if (attribute instanceof RuntimeInvisibleAnnotations) {
				AnnotationsAttribute icecapAttribute = AnnotationsAttribute
						.getAttribute((RuntimeInvisibleAnnotations) attribute);
				A annotation = icecapAttribute.getAnnotation(annotationClass);
				if (annotation != null) {
					return annotation;
				}
			}
		}
		return null;
	}

	public static boolean compileMethod(ICompilationRegistry cregistry, Method javaMethod,
			MethodOrFieldDesc currentMethod) {
		if (hasAnnotation(javaMethod, IcecapCompileMe.class) != null) {
			return true;
		}

		if (hasAnnotation(javaMethod, IcecapCFunc.class) != null) {
			return true;
		}

		if (cregistry.isMethodCompiled(currentMethod)) {
			return true;
		}

		return false;
	}

	public static boolean isStaticInitializer(MethodOrFieldDesc currentMethod) {
		return currentMethod.getName().equals("<clinit>");
	}

	private static int getClassNumber(String className, ClassManager manager, ByteCodePatcher patcher, int catchType)
			throws Exception {
		String exceptionClass;
		if (catchType > 0) {
			exceptionClass = ClassfileUtils.getClassName(className, catchType);
		} else {
			exceptionClass = "java/lang/Throwable";
		}
		return patcher.getClassNumber(exceptionClass);
	}

	private int getNumExceptionhandlers(Method javaMethod) {
		CodeException[] exceptions = javaMethod.getCode().getExceptionTable();
		if (exceptions != null) {
			return exceptions.length;
		}
		return 0;
	}

	public static int getNumReturnValues(Method m) throws Exception {
		int numReturnValues;
		Type returnType = m.getReturnType();
		if (returnType == Type.VOID) {
			numReturnValues = 0;
		} else if ((returnType == Type.LONG) || (returnType == Type.DOUBLE)) {
			numReturnValues = 2;
		} else {
			numReturnValues = 1;
		}
		int minfo;

		if (isConstructor(m)) {
			minfo = 16;
		} else {
			minfo = ClassfileUtils.getType(returnType);
		}

		if (!m.isStatic() && !m.isNative() && m.isSynchronized()) {
			minfo |= 32;
		}

		int returnInfo = numReturnValues | (minfo << 2);
		return returnInfo;
	}

	private static boolean isConstructor(Method m) {
		if ("<init>".equals(m.getName())) {
			return true;
		}
		return false;
	}

	private boolean isMainMethod(MethodOrFieldDesc currentMethod, Method javaMethod) {
		if ("main".equals(currentMethod.getName())) {
			String signature = currentMethod.getSignature();
			if ("([Ljava/lang/String;)V".equals(signature)) {
				String className = currentMethod.getClassName();
				String entrypointClassName = config.getEntryPointClassName();
				if (className.equals(entrypointClassName)) {
					return true;
				}
			}
		}
		return false;
	}
}
