package icecaptools.compiler.aot;

import icecaptools.BNode;
import icecaptools.CFuncInfo;
import icecaptools.ClassfileUtils;
import icecaptools.FieldOffsetCalculator;
import icecaptools.IcecapCFunc;
import icecaptools.InterfaceMethodCallBNode;
import icecaptools.MethodCallBNode;
import icecaptools.MethodEntryPoints;
import icecaptools.MethodOrFieldDesc;
import icecaptools.RawByteCodes;
import icecaptools.RawByteCodes.Raw_lookupswitch;
import icecaptools.RawByteCodes.Raw_lookupswitch.Pair;
import icecaptools.RawByteCodes.Raw_tableswitch;
import icecaptools.SwitchBNode;
import icecaptools.VirtualMethodCallBNode;
import icecaptools.compiler.Compiler;
import icecaptools.compiler.FieldInfo;
import icecaptools.compiler.ICompilationRegistry;
import icecaptools.compiler.LDCConstant;
import icecaptools.compiler.NativeFileManager;
import icecaptools.compiler.NoDuplicatesMemorySegment;
import icecaptools.compiler.utils.ClassInheritanceMatrix;
import icecaptools.conversion.TargetAddressMap;
import icecaptools.stackanalyser.AbstractStack;
import icecaptools.stackanalyser.AbstractStack.StackCell;
import icecaptools.stackanalyser.ProducerConsumerCellInfo;
import icecaptools.stackanalyser.ProducerConsumerStack;
import icecaptools.stackanalyser.RefType;
import icecaptools.stackanalyser.RefType.RefState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;
import org.apache.bcel.generic.Type;

public abstract class AOTCompiler implements SPManipulator {

	protected Method javaMethod;
	protected byte[] currentMethodCode;
	protected String uniqueMethodId;
	protected NoDuplicatesMemorySegment requiredIncludes;
	protected AOTToolBox toolBox;
	protected int methodNumber;
	private boolean spUsed;
	private CFuncInfo cfunc;

	public AOTCompiler(Method javaMethod, byte[] methodCode, String uniqueMethodId, int methodNumber,
			NoDuplicatesMemorySegment requiredIncludes, AOTToolBox toolBox) {
		this.javaMethod = javaMethod;
		this.currentMethodCode = methodCode;
		this.uniqueMethodId = uniqueMethodId;
		this.requiredIncludes = requiredIncludes;
		this.toolBox = toolBox;
		this.methodNumber = methodNumber;
		spUsed = false;

		cfunc = toolBox.getObserver().isCFunc(toolBox.getCurrentClassName(), javaMethod.getName(),
				javaMethod.getSignature());
	}

	public String compile() throws Exception {
		String currentClassName = toolBox.getCurrentClassName();
		String methodName = javaMethod.getName();
		String methodSignature = javaMethod.getSignature();

		MethodEntryPoints entrypoints = toolBox.getDependencyExtent().getMethod(currentClassName, methodName,
				methodSignature);

		StringBuffer prelude = new StringBuffer();
		prelude.append("/* " + javaMethod.getName() + "\n");
		prelude.append(" * param : " + NativeFileManager.getParameters(javaMethod) + "\n");
		prelude.append(" * return: " + javaMethod.getReturnType().toString() + "\n");
		prelude.append(" */\n");

		StringBuffer body = new StringBuffer();
		NoDuplicatesMemorySegment localVariables = new NoDuplicatesMemorySegment(toolBox.getTool().getProperties());
		StackManager sm = new LevelNRegisterAllocator(body, localVariables, javaMethod.getCode().getMaxStack(),
				javaMethod.getCode().getMaxLocals(), javaMethod.isSynthetic(), this);

		sm.getLocalVariableManager().init(body, localVariables, javaMethod.getCode().getMaxLocals(), javaMethod);

		int noLocals = generateSignature(prelude, entrypoints, sm.getLocalVariableManager(), requiredIncludes);

		prelude.append("{  \n");

		StringBuffer fullFunction = new StringBuffer();

		if (entrypoints == null) {
			fullFunction.append(prelude);
			fullFunction.append(generateReturnStatement("   return -1;\n"));
		} else {
			TargetAddressMap tMap = entrypoints.getJumpTargets();

			StringBuffer exceptionSwitch = new StringBuffer();
			LabelsManager labelsManager = new LabelsManager(entrypoints, methodNumber, localVariables,
					requiredIncludes, this);

			compileByteCode(sm, labelsManager, tMap, body, localVariables, 0, false, entrypoints);

			boolean spUsed = this.spUsed;
			String spInitialization;

			if (spUsed) {
				int stackOffset = Compiler.getMaxLocals(javaMethod.getCode());

				prelude.append("   int32* sp;\n");

				spInitialization = "   sp = &fp[" + (stackOffset + 2)
						+ "]; /* make room for local VM state on the stack */\n";
			} else {
				spInitialization = "";
			}

			fullFunction.append(prelude);

			exceptionSwitch.append(labelsManager.getLabels());

			boolean skipLocalInitialization = false;
			if (entrypoints != null) {
				if (entrypoints.canCallWithArgs()) {
					skipLocalInitialization = true;
				}
			}

			String lvInitialization = "";

			if (!skipLocalInitialization) {
				lvInitialization = sm.getLocalVariableManager().generateLocalInitialization(localVariables, noLocals);
			} else {
				sm.getLocalVariableManager().generateLocalInitialization(localVariables, noLocals);
			}

			fullFunction.append(localVariables);

			fullFunction.append(lvInitialization);

			fullFunction.append(spInitialization);

			fullFunction.append(body);
			if (exceptionSwitch != null) {
				fullFunction.append(exceptionSwitch);
			}
		}
		fullFunction.append("}\n");
		return fullFunction.toString();
	}

	private Object generateReturnStatement(String stmt) {
		if (cfunc == null) {
			return stmt;
		} else {
			IcecapCFunc cFuncAttribute = cfunc.getCfunc();
			String hasReturnValue = cFuncAttribute.hasReturnValue();

			if ((hasReturnValue == null) || hasReturnValue.equals("false")) {
				return ";\n";
			} else {
				return stmt;
			}
		}
	}

	private int generateSignature(StringBuffer prelude, MethodEntryPoints entrypoints,
			LocalVariableManager localVariableManager, NoDuplicatesMemorySegment requiredIncludes) throws Exception {
		int noLocals = 0;
		StringBuffer decl = new StringBuffer();

		int returnTypeSize = Size.SHORT;

		if (entrypoints != null) {
			if (entrypoints.useCombinedReturnType()) {
				returnTypeSize = getReturntypeSize(entrypoints.getMethod()) + 1;
			}
		}

		if (cfunc != null) {
			IcecapCFunc cFuncAttribute = cfunc.getCfunc();
			String signature = cFuncAttribute.signature();
			if (signature.endsWith(")")) {
				signature = signature.substring(0, signature.length() - 1);
			}
			prelude.append(signature);
			String decls = cFuncAttribute.requiredIncludes();
			if ((decls != null) && (decls.trim().length() > 0)) {
				if (!decls.endsWith("\n")) {
					decls = decls + "\n";
				}
				requiredIncludes.print(decls);
			}
		} else {
			prelude.append(getTypeCast(returnTypeSize) + " " + uniqueMethodId + "(int32 *fp");
			decl.append(getTypeCast(returnTypeSize) + " " + uniqueMethodId + "(int32 *fp");
		}

		if (entrypoints != null) {
			if (entrypoints.canCallWithArgs()) {
				NoDuplicatesMemorySegment parameters = new NoDuplicatesMemorySegment(toolBox.getTool().getProperties());

				if (!javaMethod.isStatic()) {
					noLocals++;
				}

				noLocals += ClassfileUtils.getNumArgs(javaMethod);
				if (noLocals > 0) {
					prelude.append(", ");
					decl.append(", ");
					localVariableManager.generateLocalInitialization(parameters, 0, noLocals, ", ", true);
					String p = parameters.toString();
					prelude.append(p.substring(0, p.length() - 2));
					decl.append(p.substring(0, p.length() - 2));
				}
				localVariableManager.clear();
			}
		}
		prelude.append(")\n");
		if (cfunc == null) {
			decl.append(");\n");
		}
		requiredIncludes.print(decl.toString());
		return noLocals;
	}

	private BNode bnode;

	public void compileByteCode(StackManager sm, LabelsManager labelsManager, TargetAddressMap tMap,
			StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc, boolean compileOne,
			MethodEntryPoints entrypoints) throws Exception {
		while (pc < currentMethodCode.length) {
			bnode = toolBox.getDependencyExtent().getBNodeFromMethod(toolBox.getCurrentClassName(),
					javaMethod.getName(), javaMethod.getSignature(), pc);
			toolBox.getTool().getSourceCodeLinker()
					.getSourceCode(toolBox.getCurrentClassName(), javaMethod, bnode.getOriginalAddress(), output);
			if (tMap.isJumpTarget(bnode) || isExceptionHandler(javaMethod, pc)) {
				handleLabel(sm);
				output.append("   L" + pc + ":\n");
			}
			switch (currentMethodCode[pc]) {
			case RawByteCodes.aconst_null_opcode:
				sm.push("0");
				pc++;
				break;
			case RawByteCodes.return_opcode:
			case RawByteCodes.freturn_opcode:
			case RawByteCodes.ireturn_opcode:
			case RawByteCodes.areturn_opcode: {
				int size;

				if (currentMethodCode[pc] != RawByteCodes.return_opcode) {
					size = normalizeProducersSize(bnode, bnode.getAinfo().entryStack.size() - 1);
				} else {
					size = Size.DONTCARE;
				}

				if (synchronizedSupported(javaMethod)) {
					if (entrypoints.canCallWithArgs()) {
						NoDuplicatesMemorySegment thisName = new NoDuplicatesMemorySegment(toolBox.getTool()
								.getProperties());
						sm.getLocalVariableManager().generateLocalInitialization(thisName, 0, 1, "", true);

						StringTokenizer tokenizer = new StringTokenizer(thisName.toString());
						String lastToken = null;
						while (tokenizer.hasMoreTokens()) {
							lastToken = tokenizer.nextToken();
						}

						output.append("   handleMonitorEnterExit((Object*)(pointer)" + lastToken
								+ ", 0, fp + 1, \"\");\n");
					} else {
						output.append("   handleMonitorEnterExit((Object*)(pointer)fp[0], 0, fp + 1, \"\");\n");
					}
					requiredIncludes
							.print("extern unsigned char handleMonitorEnterExit(Object* lockObj, unsigned char isEnter, int32* sp, const char* fromMethod);\n");
				}

				boolean returnHandled = false;
				if (currentMethodCode[pc] != RawByteCodes.return_opcode) {
					if (currentMethodCode[pc] == RawByteCodes.freturn_opcode) {
						sm.pop("   *(float*)fp", size);
					} else {
						if (entrypoints.useCombinedReturnType()) {

							int rsize = getReturntypeSize(javaMethod);

							if (sm.isCached(0)) {
								String top;
								top = sm.peekTop(1, rsize);
								output.append("   return (u" + getTypeCast(rsize) + ")" + top + ";\n");
							} else {
								String rval = "rval_m_" + pc;
								localVariables.print("   " + getTypeCast(entrypoints.getReturnTypeSize()) + " " + rval
										+ ";\n");
								sm.pop("   " + rval, getReturntypeSize(javaMethod));

								output.append("   return (u" + getTypeCast(getReturntypeSize(javaMethod)) + ")" + rval
										+ ";\n");
							}
							returnHandled = true;
						} else {
							String dsttype = getPointerCast(getReturntypeSize(javaMethod));
							sm.pop("   *(" + dsttype + "fp)", size);
						}
					}
				}

				if (!returnHandled) {
					output.append(generateReturnStatement("   return -1;\n"));
				}
				pc++;
				int extension = currentMethodCode[pc++];
				pc += extension;
				break;
			}
			case RawByteCodes.dreturn_opcode:
			case RawByteCodes.lreturn_opcode: {
				output.append("   spm1 = " + sm.peekTop(1, Size.INT) + ";\n");
				output.append("   spm2 = " + sm.peekTop(2, Size.INT) + ";\n");
				if (synchronizedSupported(javaMethod)) {
					output.append("   handleMonitorEnterExit((Object*)(pointer)fp[0], 0, fp + 2, \"\");\n");
					requiredIncludes
							.print("extern unsigned char handleMonitorEnterExit(Object* lockObj, unsigned char isEnter, int32* sp, const char* fromMethod);\n");
				}
				output.append("   fp[0] = spm2;\n");
				output.append("   fp[1] = spm1;\n");
				output.append(generateReturnStatement("   return -1;\n"));
				localVariables.print("   int32 spm1;\n");
				localVariables.print("   int32 spm2;\n");
				pc++;
				int extension = currentMethodCode[pc++];
				pc += extension;
				break;
			}
			case RawByteCodes.bipush_opcode: {
				int valueSize = getConsumerSize(pc, 0);

				if (valueSize == Size.DONTCARE) {
					valueSize = getProducersSize(pc, 0);
				}

				sm.push(valueSize, "(signed char)" + currentMethodCode[++pc]);
				pc++;
				break;
			}
			case RawByteCodes.iconst_m1_opcode:
			case RawByteCodes.iconst_0_opcode:
			case RawByteCodes.iconst_1_opcode:
			case RawByteCodes.iconst_2_opcode:
			case RawByteCodes.iconst_3_opcode:
			case RawByteCodes.iconst_4_opcode:
			case RawByteCodes.iconst_5_opcode: {
				int valueSize = getConsumerSize(pc, 0);
				if (valueSize == Size.DONTCARE) {
					valueSize = getProducersSize(pc, 0);
				}
				sm.push(valueSize, "" + (currentMethodCode[pc] - RawByteCodes.iconst_0_opcode));
				pc++;
				break;
			}
			case RawByteCodes.goto_opcode: {
				short branchbyte1 = (short) (currentMethodCode[pc + 1] & 0xff);
				short branchbyte2 = (short) (currentMethodCode[pc + 2] & 0xff);
				short offset = (short) ((branchbyte1 << 8) | branchbyte2);
				yield(output, offset);
				handleBranch(sm, currentMethodCode[pc], pc, pc + offset, true);
				output.append("   goto L" + (pc + offset) + ";\n");
				pc += 3;
				break;
			}
			case RawByteCodes.iinc_opcode: {
				short localVarNo = currentMethodCode[++pc];
				short constVal = currentMethodCode[++pc];
				int size = getLocalVariableSize(localVarNo, bnode.getOriginalAddress());
				String cast = getTypeCast(size);
				output.append("   " + sm.getLocal(localVarNo, bnode.getOriginalAddress()) + " = (" + cast + ")"
						+ sm.getLocal(localVarNo, bnode.getOriginalAddress()) + " + " + constVal + ";\n");
				pc++;
				break;
			}
			case RawByteCodes.ifnonnull_opcode:
			case RawByteCodes.ifnull_opcode:
			case RawByteCodes.ifeq_opcode:
			case RawByteCodes.ifne_opcode:
			case RawByteCodes.iflt_opcode:
			case RawByteCodes.ifge_opcode:
			case RawByteCodes.ifgt_opcode:
			case RawByteCodes.ifle_opcode: {
				pc += handleOneArgBranch(sm, currentMethodCode, pc, output, bnode, localVariables);
				break;
			}
			case RawByteCodes.if_icmpeq_opcode:
			case RawByteCodes.if_icmpne_opcode:
			case RawByteCodes.if_icmplt_opcode:
			case RawByteCodes.if_icmpge_opcode:
			case RawByteCodes.if_icmpgt_opcode:
			case RawByteCodes.if_icmple_opcode: {
				pc += handleTwoArgBranch(sm, currentMethodCode, pc, output, bnode, localVariables);
				break;
			}
			case RawByteCodes.new_opcode: {
				int classIndex = (currentMethodCode[pc + 1] << 8) & 0xffff;
				classIndex |= (currentMethodCode[pc + 2] & 0xff);
				classIndex = classIndex >> 1;
				sm.flush(true);
				output.append("   if (handleNewClassIndex(sp, " + classIndex + ") == 0) {\n");
				setSPUsed(true);
				if (hasExceptionHandlers(javaMethod)) {
					output.append("      pc = " + pc + ";\n");
					labelsManager.jumpTo(sm);
					output.append("      goto " + LabelsManager.LThrowOutOfMemory + ";\n");
					labelsManager.generateOutOfMemory();
				} else {
					output.append("      fp[0] = " + sm.peekTop(0, Size.INT) + ";\n");
					output.append("      return getClassIndex((Object*) (pointer) *sp);\n");
				}
				output.append("   }\n");

				output.append("   sp++;\n");
				pc += 3;
				requiredIncludes
						.print("extern unsigned char handleNewClassIndex(int32* sp, unsigned short classIndex);\n");
				requiredIncludes.print("extern unsigned short getClassIndex(Object* obj);\n");
				break;
			}
			case RawByteCodes.dup_opcode: {
				int dstSize = getConsumerSize(pc, 0);

				if (dstSize == Size.DONTCARE) {
					dstSize = getProducersSize(pc, 0);
				}

				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;

				int srcSize = normalizeProducersSize(bnode, entryStack.size() - 1);

				sm.push(dstSize, sm.peekTop(1, srcSize));

				pc++;
				break;
			}
			case RawByteCodes.invokespecial_opcode:
			case RawByteCodes.invokestatic_opcode: {
				int methodNumber;

				methodNumber = currentMethodCode[pc + 1] << 8;
				methodNumber |= (currentMethodCode[pc + 2] & 0xff);

				MethodOrFieldDesc methodDesc = toolBox.getPatcher().getMethodDescriptor(methodNumber);

				AOTInvokeSpecialEmitter emitter = new AOTInvokeSpecialEmitter(sm, output, localVariables, pc,
						labelsManager, bnode, currentMethodCode, requiredIncludes, methodDesc, toolBox, entrypoints);

				InvokeEmitter worker = emitter.getEmitter();

				worker.setup();
				worker.callSetup();
				worker.callSetupException();
				worker.performCall();
				worker.handleReturnValue();

				requiredIncludes.print("RANGE extern const MethodInfo *methods;\n");
				requiredIncludes.print("#include \"methods.h\"\n");
				requiredIncludes
						.print("extern int16 enterMethodInterpreter(unsigned short methodNumber, int32* sp);\n");
				pc += bnode.getRawBytes().length;
				break;
			}
			case RawByteCodes.invokevirtual_opcode:
			case RawByteCodes.invokeinterface_opcode: {
				short numArgs;
				byte offset;
				if (currentMethodCode[pc] == RawByteCodes.invokeinterface_opcode) {
					offset = 5;
				} else {
					offset = 4;
				}

				int i = 0;
				InvokeEmitter[] emitters = new InvokeEmitter[currentMethodCode[pc + offset]];

				for (int count = 0; count < currentMethodCode[pc + offset];) {
					int methodNumber;

					i++;
					i++;

					methodNumber = ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff) << 8;
					methodNumber |= ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff);

					MethodOrFieldDesc methodDesc = toolBox.getPatcher().getMethodDescriptor(methodNumber);

					AOTInvokeSpecialEmitter emitter = new AOTInvokeSpecialEmitter(sm, output, localVariables, pc,
							labelsManager, bnode, currentMethodCode, requiredIncludes, methodDesc, toolBox, entrypoints);

					InvokeEmitter worker = emitter.getEmitter();

					emitters[count] = worker;
					count++;
				}

				boolean useDirectCall;
				if (emitters.length > 0) {
					useDirectCall = true;
				} else {
					useDirectCall = false;
				}
				for (int count = 0; count < emitters.length; count++) {
					if (!emitters[count].isWithArgsEmitter()) {
						useDirectCall = false;
						break;
					}
				}

				localVariables.print("   unsigned short classIndex;\n");
				localVariables.print("   unsigned short methodIndex;\n");

				requiredIncludes.print("RANGE extern const ClassInfo *classes;\n");
				requiredIncludes.print("#include \"classes.h\"\n");

				if (useDirectCall) {
					InvokeEmitter firstEmitter = emitters[0];

					firstEmitter.setup();
					String thisName = firstEmitter.callSetup();
					firstEmitter.callSetupException();

					output.append("      classIndex = getClassIndex((Object *)(pointer)" + thisName + ");\n");
					output.append("      methodIndex = (unsigned short)-1;\n");
					output.append("      while (methodIndex == (unsigned short)-1) {\n");
					output.append("        switch (classIndex) {\n");
					requiredIncludes.print("extern unsigned short getClassIndex(Object* obj);\n");
					i = 0;
					for (int count = 0; count < currentMethodCode[pc + offset];) {
						int classNumber, methodNumber;

						classNumber = ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff) << 8;
						classNumber |= ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff);

						methodNumber = ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff) << 8;
						methodNumber |= ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff);

						output.append("          case " + classNumber + ":\n");
						InvokeEmitter emitter = emitters[count];
						firstEmitter.setMethodDesc(emitter.getMethodDesc());
						firstEmitter.performCall();
						output.append("            methodIndex = " + methodNumber + ";\n");
						output.append("            continue;\n");
						count++;
					}
					output.append("        }\n");
					output.append("        classIndex = pgm_read_word(&classes[classIndex].superClass);\n");
					output.append("      }\n");
					firstEmitter.handleReturnValue();

				} else {
					numArgs = (short) (currentMethodCode[pc + 3] & 0xff);

					localVariables.print("   unsigned char methodVtableIndex;\n");

					String exceptionVariable = "rval_m_" + pc;
					localVariables.print("   " + getTypeCast(entrypoints.getReturnTypeSize()) + " " + exceptionVariable
							+ ";\n");

					sm.flush(true);
					adjustStackAndCheckObject(output, localVariables, pc, labelsManager, numArgs + 1,
							"      classIndex = getClassIndex(i_obj);\n", "   ", sm);
					requiredIncludes.print("extern unsigned short getClassIndex(Object* obj);\n");

					output.append("      methodIndex = (unsigned short)-1;\n");
					output.append("      while (methodIndex == (unsigned short)-1) {\n");
					output.append("        switch (classIndex) {\n");
					i = 0;
					for (int count = 0; count < currentMethodCode[pc + offset];) {
						int classNumber, methodNumber;

						classNumber = ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff) << 8;
						classNumber |= ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff);

						methodNumber = ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff) << 8;
						methodNumber |= ((int) currentMethodCode[pc + offset + 1 + i++] & 0xff);

						output.append("          case " + classNumber + ":\n");
						output.append("            methodIndex = " + methodNumber + ";\n");
						output.append("            continue;\n");
						count++;
					}
					output.append("        }\n");
					output.append("        classIndex = pgm_read_word(&classes[classIndex].superClass);\n");
					output.append("      }\n");

					output.append("      " + exceptionVariable
							+ " = dispatchInterface(methodIndex, &methodVtableIndex, sp);\n");
					setSPUsed(true);
					String returnValueString = "      sp += methodVtableIndex;\n";

					handleExceptionOccurred(output, localVariables, pc, labelsManager, returnValueString, "   ", sm,
							null, exceptionVariable, entrypoints, exceptionVariable + " == -1", false);

					requiredIncludes
							.print("extern signed short dispatchInterface(unsigned short methodIndex, unsigned char *vTableIndex, int32* sp);\n");
				}
				pc += bnode.getRawBytes().length;
				break;
			}
			case RawByteCodes.athrow_opcode:
				localVariables.print("   Object* ex_ception;\n");
				output.append("   ex_ception = (Object*) (pointer) " + sm.peekTop(1, Size.INT) + ";\n");
				output.append("   excep = getClassIndex(ex_ception);\n");
				output.append("   pc = " + pc + ";\n");
				labelsManager.jumpTo(sm, true);
				output.append("   goto " + LabelsManager.LThrowIt + ";\n");
				labelsManager.generateThrowIt();
				localVariables.print("   " + getTypeCast(entrypoints.getReturnTypeSize()) + " excep;\n");
				localVariables.print("   unsigned short pc;\n");
				requiredIncludes.print("extern unsigned short getClassIndex(Object* obj);\n");
				pc++;
				break;
			case RawByteCodes.aload_0_opcode:
			case RawByteCodes.aload_1_opcode:
			case RawByteCodes.aload_2_opcode:
			case RawByteCodes.aload_3_opcode:
				sm.load(Size.INT, currentMethodCode[pc] - RawByteCodes.aload_0_opcode, pc);
				sm.push(Size.INT,
						sm.getLocal(currentMethodCode[pc] - RawByteCodes.aload_0_opcode, getOriginalAddress(pc)));
				pc++;
				break;
			case RawByteCodes.aload_opcode: {
				short index = (short) (currentMethodCode[pc + 1] & 0xff);
				sm.load(Size.INT, index, pc);
				sm.push(Size.INT, sm.getLocal(index, getOriginalAddress(pc)));
				pc += 2;
				break;
			}
			case RawByteCodes.astore_0_opcode:
			case RawByteCodes.astore_1_opcode:
			case RawByteCodes.astore_2_opcode:
			case RawByteCodes.astore_3_opcode: {
				String dst;
				if (bnode.isRedundant()) {
					dst = null;
				} else {
					dst = "   "
							+ sm.getLocal(currentMethodCode[pc] - RawByteCodes.astore_0_opcode, getOriginalAddress(pc));
				}
				sm.setLocal(Size.INT, (currentMethodCode[pc] - RawByteCodes.astore_0_opcode), pc);
				sm.pop(dst, Size.INT);
				pc++;
				break;
			}
			case RawByteCodes.astore_opcode: {
				short byteVal = (short) (currentMethodCode[pc + 1] & 0xff);
				String dst;
				if (bnode.isRedundant()) {
					dst = null;
				} else {
					dst = "   " + "   " + sm.getLocal(byteVal, getOriginalAddress(pc));
				}
				sm.setLocal(Size.INT, byteVal, pc);
				sm.pop(dst, Size.INT);

				pc += 2;
				break;
			}
			case RawByteCodes.fload_opcode:
			case RawByteCodes.fload_0_opcode:
			case RawByteCodes.fload_1_opcode:
			case RawByteCodes.fload_2_opcode:
			case RawByteCodes.fload_3_opcode:
			case RawByteCodes.iload_opcode:
			case RawByteCodes.iload_0_opcode:
			case RawByteCodes.iload_1_opcode:
			case RawByteCodes.iload_2_opcode:
			case RawByteCodes.iload_3_opcode: {
				int valueSize;
				int index;

				valueSize = getConsumerSize(pc, 0);

				if (valueSize == Size.DONTCARE) {
					valueSize = getProducersSize(pc, 0);
				}

				switch (currentMethodCode[pc]) {
				case RawByteCodes.iload_opcode:
				case RawByteCodes.iload_0_opcode:
				case RawByteCodes.iload_1_opcode:
				case RawByteCodes.iload_2_opcode:
				case RawByteCodes.iload_3_opcode:
					if (currentMethodCode[pc] == RawByteCodes.iload_opcode) {
						index = currentMethodCode[pc + 1];
					} else {
						index = currentMethodCode[pc] - RawByteCodes.iload_0_opcode;
					}
					break;
				default:
					valueSize = Size.FLOAT;
					if (currentMethodCode[pc] == RawByteCodes.fload_opcode) {
						index = currentMethodCode[pc + 1];
					} else {
						index = currentMethodCode[pc] - RawByteCodes.fload_0_opcode;
					}
					break;
				}

				int srcSize = getLocalVariableSize(index, bnode.getOriginalAddress());
				String srcCast = getTypeCast(srcSize);

				sm.load(srcSize, index, pc);
				sm.push(valueSize, "(" + srcCast + ")" + sm.getLocal(index, bnode.getOriginalAddress()));

				if ((currentMethodCode[pc] == RawByteCodes.iload_opcode)
						|| (currentMethodCode[pc] == RawByteCodes.fload_opcode)) {
					pc++;
				}
				pc++;
				break;
			}
			case RawByteCodes.fstore_opcode:
			case RawByteCodes.istore_opcode:
			case RawByteCodes.istore_0_opcode:
			case RawByteCodes.istore_1_opcode:
			case RawByteCodes.istore_2_opcode:
			case RawByteCodes.istore_3_opcode:
			case RawByteCodes.fstore_0_opcode:
			case RawByteCodes.fstore_1_opcode:
			case RawByteCodes.fstore_2_opcode:
			case RawByteCodes.fstore_3_opcode: {
				int valueSize;
				int index;

				switch (currentMethodCode[pc]) {
				case RawByteCodes.istore_opcode:
				case RawByteCodes.istore_0_opcode:
				case RawByteCodes.istore_1_opcode:
				case RawByteCodes.istore_2_opcode:
				case RawByteCodes.istore_3_opcode:
					valueSize = getIStoreSize(bnode, currentMethodCode[pc]);
					if (currentMethodCode[pc] == RawByteCodes.istore_opcode) {
						index = (short) (currentMethodCode[pc + 1] & 0xff);
						pc++;
					} else {
						index = currentMethodCode[pc] - RawByteCodes.istore_0_opcode;
					}
					break;
				default:
					valueSize = Size.FLOAT;
					if (currentMethodCode[pc] == RawByteCodes.fstore_opcode) {
						index = (short) (currentMethodCode[pc + 1] & 0xff);
						pc++;
					} else {
						index = currentMethodCode[pc] - RawByteCodes.fstore_0_opcode;
					}
				}

				sm.setLocal(valueSize, index, pc);

				String dst;
				if (bnode.isRedundant()) {
					dst = null;
				} else {
					dst = "   " + sm.getLocal(index, bnode.getOriginalAddress());
				}
				sm.pop(dst, valueSize);
				pc++;
				break;
			}
			case RawByteCodes.if_acmpeq_opcode:
			case RawByteCodes.if_acmpne_opcode: {
				localVariables.print("   int32 op2;\n");
				localVariables.print("   int32 op1;\n");
				localVariables.print("   unsigned char c_result;\n");

				sm.pop("      op2", Size.INT);
				sm.pop("      op1", Size.INT);

				if (currentMethodCode[pc] == RawByteCodes.if_acmpeq_opcode) {
					output.append("      c_result = (op1 == op2);\n");
				} else {
					output.append("      c_result = (op1 != op2);\n");
				}
				output.append("      if (c_result) {\n");
				short branchbyte1 = (short) (currentMethodCode[pc + 1] & 0xff);
				short branchbyte2 = (short) (currentMethodCode[pc + 2] & 0xff);
				short offset = (short) ((branchbyte1 << 8) | branchbyte2);
				handleBranch(sm, currentMethodCode[pc], pc, pc + offset, false);
				output.append("         goto L" + (pc + offset) + ";\n");
				output.append("      }\n");
				pc += 3;
				break;
			}
			case RawByteCodes.instanceof_opcode:
			case RawByteCodes.checkcast_opcode: {
				byte isInterface;

				short byte1 = (short) (currentMethodCode[pc + 1] & 0xff);
				short byte2 = (short) (currentMethodCode[pc + 2] & 0xff);
				int superType = ((byte1 << 8) | byte2);

				isInterface = (byte) (superType & 0x1);

				superType = superType >> 1;

				localVariables.print("   unsigned char *object;\n");
				if (currentMethodCode[pc] == RawByteCodes.instanceof_opcode) {
					localVariables.print("   uint8 i_res;\n");
				}
				sm.popRef("object");

				if (currentMethodCode[pc] == RawByteCodes.instanceof_opcode) {
					output.append("      excep = 1;\n");
				} else {
					output.append("      excep = 0;\n");
					sm.push(Size.INT, "(int32)(pointer)object");
				}

				output.append("      if (object != 0) {\n");
				if (isInterface == 0) {
					int JAVA_LANG_OBJECT = toolBox.getPatcher().getClassNumber("java.lang.Object");
					if (superType != JAVA_LANG_OBJECT) {
						localVariables.print("   signed short subClassIndex;\n");
						output.append("         subClassIndex = getClassIndex((Object*)object);\n");
						output.append("         excep = !isSubClassOf(subClassIndex, " + superType + ");\n");
						requiredIncludes.print("extern unsigned short getClassIndex(Object* obj);\n");
					} else {
						output.append("         excep = 0;\n");
					}

				} else {
					output.append("         excep = checkImplements((Object*)object, " + superType + ");\n");
					requiredIncludes
							.print("unsigned char checkImplements(Object* object, unsigned short interfaceIndex);\n");
				}
				output.append("      }\n");

				int valueSize = Size.INT;
				if (currentMethodCode[pc] == RawByteCodes.instanceof_opcode) {
					valueSize = getConsumerSize(pc, 0);
					if (valueSize == Size.DONTCARE) {
						valueSize = getProducersSize(pc, 0);
					}
				}

				output.append("      if (excep) {\n");
				if (currentMethodCode[pc] == RawByteCodes.checkcast_opcode) {
					output.append("         pc = " + pc + ";\n");
					labelsManager.jumpTo(sm);
					output.append("         goto " + LabelsManager.LThrowClassCast + ";\n");
				} else {
					output.append("      i_res = 0;\n");
				}
				output.append("      }\n");
				if (currentMethodCode[pc] == RawByteCodes.instanceof_opcode) {
					output.append("      else\n");
					output.append("      {\n");
					output.append("      i_res = 1;\n");
					output.append("      }\n");
					sm.push(valueSize, "i_res");
				}

				if (currentMethodCode[pc] == RawByteCodes.checkcast_opcode) {
					labelsManager.generateThrowClassCast();
					localVariables.print("   unsigned short pc;\n");
				}
				localVariables.print("   " + getTypeCast(entrypoints.getReturnTypeSize()) + " excep;\n");
				requiredIncludes
						.print("extern unsigned char isSubClassOf(unsigned short subClass, unsigned short superClass);\n");
				pc += 3;
				break;
			}
			case RawByteCodes.sipush_opcode: {
				short byte1 = (short) (currentMethodCode[pc + 1] & 0xff);
				short byte2 = (short) (currentMethodCode[pc + 2] & 0xff);
				short value = (short) ((byte1 << 8) | byte2);
				int valueSize = getConsumerSize(pc, 0);

				if (valueSize == Size.DONTCARE) {
					valueSize = getProducersSize(pc, 0);
				}
				if (valueSize == Size.BYTE) {
					if (value > 255) {
						throw new Exception("Illegal consumer size");
					}
				}
				sm.push(valueSize, "" + value);
				pc += 3;
				break;
			}
			case RawByteCodes.puthwfield_opcode:
			case RawByteCodes.putfield_opcode: {
				int foffset;
				short fsize;
				FieldInfo[] fieldOffsets = toolBox.getPatcher().getFieldOffsets();
				FieldInfo finfo = fieldOffsets[pc + 4];

				fsize = (short) (currentMethodCode[pc + 3] & 0xff);
				short byte1 = (short) (currentMethodCode[pc + 4] & 0xff);
				short byte2 = (short) (currentMethodCode[pc + 5] & 0xff);

				foffset = ((byte1 << 8) | byte2);
				String type;
				int srcSize;

				switch (fsize & 0xfc) {
				case 16:
					type = "int16";
					srcSize = Size.SHORT;
					break;
				case 8:
					type = "int8";
					srcSize = Size.BYTE;
					break;
				case 32:
					if (sm.topSize() == Size.FLOAT) {
						type = "float";
						srcSize = Size.INT;
						break;
					}
				case 64:
				default:
					type = "int32";
					srcSize = Size.INT;
					break;
				}

				localVariables.print("   unsigned char* cobj;\n");
				localVariables.print("   " + type + " lsb_" + type + ";\n");
				if (currentMethodCode[pc] == RawByteCodes.puthwfield_opcode) {
					localVariables.print("   unsigned char* hw_address;\n");
					localVariables.print("   struct _vm_HardwareObject_c* hwObject;\n");
					localVariables.print("   struct _vm_Address32Bit_c* addressObj;\n");
				}

				int objIndex = 1;

				if ((fsize & 0xfc) > 32) {
					localVariables.print("   int32 msb_int32;\n");
					objIndex = 2;
				}

				sm.pop("      lsb_" + type + "", srcSize);

				if ((fsize & 0xfc) > 32) {
					sm.pop("      msb_int32", Size.INT);
				}

				sm.popRef("cobj");

				AbstractStack stack = bnode.getStackLayout();
				StackCell cell = stack.getAt(stack.getSize() - 1 - objIndex);

				if (((RefType) cell.content).getState() != RefState.NONNULL) {
					checkObject(output, localVariables, pc, labelsManager, null, "", sm, "cobj");
				}

				if (currentMethodCode[pc] == RawByteCodes.putfield_opcode) {
					if ((finfo != null) && (!finfo.isFloat)) {
						output.append("      ((struct ");
						output.append(finfo.getStructName());
						output.append(" *)HEAP_REF(cobj, unsigned char*)) -> ");
						output.append(finfo.getStructMemberName());
						output.append(" = lsb_" + type + ";\n");
						if ((fsize & 0xfc) > 32) {
							output.append("      ((struct ");
							output.append(finfo.getStructName());
							output.append(" *)HEAP_REF(cobj, unsigned char*)) -> ");
							output.append(finfo.getStructMemberLSBName());
							output.append(" = msb_int32;\n");
						}
					} else {
						output.append("      cobj = HEAP_REF(&cobj[sizeof(Object) + " + (foffset >> 3)
								+ "], unsigned char*);\n");

						switch (fsize & 0xfc) {
						case 64:
							output.append("      *(int32*) &cobj[4] = msb_int32;\n");
						case 32:
							output.append("      *((" + type + "*) cobj) = lsb_" + type + ";\n");
							break;
						case 16:
							output.append("      *((signed short*) cobj) = lsb_" + type + ";\n");
							break;
						case 8:
							output.append("      *((signed char*) cobj) = lsb_" + type + ";\n");

							break;
						}
					}
				} else if (currentMethodCode[pc] == RawByteCodes.puthwfield_opcode) {
					int classIndex = (currentMethodCode[pc + 1] << 8) & 0xffff;
					classIndex |= (currentMethodCode[pc + 2] & 0xff);
					output.append("      hwObject = (struct _vm_HardwareObject_c*)HEAP_REF(cobj, struct _vm_HardwareObject_c*);\n");
					output.append("      addressObj = (struct _vm_Address32Bit_c*)HEAP_REF((pointer)(hwObject->address_f), struct _vm_Address32Bit_c*);\n");
					output.append("      hw_address = (unsigned char*) (pointer) (addressObj->address_f);\n");

					int referenceAddressIndex = toolBox.getPatcher().getClassNumber(
							FieldOffsetCalculator.ReferenceAddressClassName);

					ClassInheritanceMatrix classMatrix = toolBox.getClassMatrix();

					if ((referenceAddressIndex != -1) && classMatrix.getInherits(classIndex, referenceAddressIndex)) {
						output.append("      hw_address = HEAP_REF(hw_address, unsigned char *);\n");
					}

					foffset -= 32;

					switch (fsize & 0xfc) {
					case 64:
						output.append("      writeLongToIO((pointer)hw_address, " + foffset + ", msb_int32, lsb_"
								+ type + ");\n");
						requiredIncludes
								.print("extern void writeLongToIO(pointer address, unsigned short offset, int32 msb, int32 lsb);\n");
						break;
					case 32:
						output.append("      writeIntToIO((pointer)hw_address, " + foffset + ", lsb_" + type + ");\n");
						requiredIncludes
								.print("extern void writeIntToIO(pointer address, unsigned short offset, int32 lsb);\n");
						break;
					case 16:
						output.append("      writeShortToIO((pointer)hw_address, " + foffset + ", lsb_" + type
								+ " & 0xffff);\n");
						requiredIncludes
								.print("extern void writeShortToIO(pointer address, unsigned short offset, unsigned short lsb);\n");
						break;
					case 8:
						output.append("      writeByteToIO((pointer)hw_address, " + foffset + ", lsb_" + type
								+ " & 0xff);\n");
						requiredIncludes
								.print("extern void writeByteToIO(pointer address, unsigned short offset, unsigned char lsb);\n");
						break;
					case 1:
						output.append("      writeBitToIO((pointer)hw_address, " + foffset + ", lsb_" + type
								+ " & 0x1);\n");
						requiredIncludes
								.print("extern void writeBitToIO(pointer address, unsigned short offset, unsigned char bit);\n");
						break;
					}
				}
				pc += 6;
				break;
			}
			case RawByteCodes.gethwfield_opcode:
			case RawByteCodes.getfield_opcode: {
				FieldInfo[] fieldOffsets = toolBox.getPatcher().getFieldOffsets();
				FieldInfo finfo = fieldOffsets[pc + 4];
				int foffset;
				short fsize;

				int valueSize = getConsumerSize(pc, 0);

				if (valueSize == Size.DONTCARE) {
					valueSize = getProducersSize(pc, 0);
				}

				fsize = (short) (currentMethodCode[pc + 3] & 0xff);
				short byte1 = (short) (currentMethodCode[pc + 4] & 0xff);
				short byte2 = (short) (currentMethodCode[pc + 5] & 0xff);

				foffset = ((byte1 << 8) | byte2);

				localVariables.print("   unsigned char* cobj;\n");

				if (currentMethodCode[pc] == RawByteCodes.gethwfield_opcode) {
					localVariables.print("   unsigned char* hw_address;\n");
					localVariables.print("   struct _vm_HardwareObject_c* hwObject;\n");

					localVariables.print("   #if defined(VM_ADDRESS64BIT_INIT_)\n   Object* _addressObj_;\n#endif\n");
					localVariables.print("   struct _vm_Address32Bit_c* addressObj32bit;\n");

					if ((fsize & 0xfc) == 64) {
						localVariables.print("   int32 msb_int32;\n");
						localVariables.print("   int32 lsb_int32;\n");
					}
				}
				sm.popRef("cobj");

				AbstractStack stack = bnode.getStackLayout();
				StackCell cell = stack.peek();

				if (((RefType) cell.content).getState() != RefState.NONNULL) {
					checkObject(output, localVariables, pc, labelsManager, null, "", sm, "cobj");
				}

				if (currentMethodCode[pc] == RawByteCodes.getfield_opcode) {
					if ((finfo != null) && (!finfo.isFloat)) {
						StringBuffer buffer = new StringBuffer();
						if ((fsize & 0xfc) > 32) {
							buffer.append("((struct ");
							buffer.append(finfo.getStructName());
							buffer.append(" *)HEAP_REF(cobj, unsigned char*)) -> ");
							buffer.append(finfo.getStructMemberLSBName());
							sm.push(valueSize, buffer.toString());
							buffer = new StringBuffer();
						}
						buffer.append("((struct ");
						buffer.append(finfo.getStructName());
						buffer.append(" *)HEAP_REF(cobj, unsigned char*)) -> ");
						buffer.append(finfo.getStructMemberName());

						sm.push(valueSize, buffer.toString());
					} else {
						output.append("      cobj = HEAP_REF(&cobj[sizeof(Object) + " + (foffset >> 3)
								+ "], unsigned char*);\n");

						switch (fsize & 0xfc) {
						case 64:
							sm.push(Size.INT, "*(int32*) &cobj[4]");
						case 32: {
							String type;
							ProducerConsumerCellInfo topAtExit = bnode.getAinfo().exitStack.peek();
							BNode consumer = topAtExit.getConsumer();
							int size = normalizeConsumerSize(consumer, bnode.getAinfo().exitStack.size() - 1);
							if (size == Size.FLOAT) {
								type = "float";
							} else {
								type = "int32";
							}
							sm.push(valueSize, "*(" + type + "*) cobj");
							break;
						}
						case 16:
							sm.push(valueSize, "*(signed short*) cobj");
							break;
						case 8:
							sm.push(valueSize, "*(signed char*) cobj");
							break;
						}
					}
				} else if (currentMethodCode[pc] == RawByteCodes.gethwfield_opcode) {
					int classIndex = (currentMethodCode[pc + 1] << 8) & 0xffff;
					classIndex |= (currentMethodCode[pc + 2] & 0xff);
					output.append("      hwObject = (struct _vm_HardwareObject_c*)HEAP_REF(cobj, struct _vm_HardwareObject_c*);\n");
					output.append("#if defined(VM_ADDRESS64BIT_INIT_)\n");
					output.append("      _addressObj_ = (Object*)HEAP_REF((pointer)(hwObject->address_f), Object*);\n");
					output.append("      if (*_addressObj_ == VM_ADDRESS64BIT) {\n");
					output.append("          struct _vm_Address64Bit_c* addressObj64;\n");
					output.append("          long address;\n");
					output.append("          addressObj64 = (struct _vm_Address64Bit_c*)_addressObj_;\n");
					output.append("          address = addressObj64->lsbaddress_f;\n");
					output.append("          address = (address << 16) << 16;\n");
					output.append("          address |= addressObj64->address_f;\n");
					output.append("          hw_address = (unsigned char*) (pointer) address;\n");
					output.append("      } else {\n");
					output.append("#endif\n");
					output.append("          addressObj32bit = (struct _vm_Address32Bit_c*) HEAP_REF((pointer )(hwObject->address_f), struct _vm_Address32Bit_c*);\n");
					output.append("          hw_address = (unsigned char*) (pointer) (addressObj32bit->address_f);\n");
					output.append("#if defined(VM_ADDRESS64BIT_INIT_)\n");
					output.append("      }\n");
					output.append("#endif\n");
					int referenceAddressIndex = toolBox.getPatcher().getClassNumber(
							FieldOffsetCalculator.ReferenceAddressClassName);

					ClassInheritanceMatrix classMatrix = toolBox.getClassMatrix();

					if (classMatrix.getInherits(classIndex, referenceAddressIndex)) {
						output.append("      hw_address = HEAP_REF(hw_address, unsigned char *);\n");
					}

					foffset -= 32;

					switch (fsize & 0xfc) {
					case 64:
						output.append("      readLongFromIO((pointer)hw_address, " + foffset
								+ ", &msb_int32, &lsb_int32);\n");
						sm.push(Size.INT, "msb_int32");
						sm.push(Size.INT, "lsb_int32");
						requiredIncludes
								.print("extern void readLongFromIO(pointer address, unsigned short offset, int32* msb, int32* lsb);\n");
						break;
					case 32:
						sm.push(valueSize, "readIntFromIO((pointer)hw_address, " + foffset + ")");
						requiredIncludes.print("extern int32 readIntFromIO(pointer address, unsigned short offset);\n");
						break;
					case 16:
						sm.push(valueSize, "readShortFromIO((pointer)hw_address, " + foffset + ")");
						requiredIncludes
								.print("extern int16 readShortFromIO(pointer address, unsigned short offset);\n");
						break;
					case 8:
						sm.push(valueSize, "readByteFromIO((pointer)hw_address, " + foffset + ")");
						requiredIncludes.print("extern int8 readByteFromIO(pointer address, unsigned short offset);\n");
						break;
					case 1:
						sm.push(valueSize, "readBitFromIO((pointer)hw_address, " + foffset + ")");
						requiredIncludes.print("extern int8 readBitFromIO(pointer address, unsigned short offset);\n");
						break;
					}
				}
				pc += 6;
				break;
			}
			case RawByteCodes.ldc2_w_opcode:
			case RawByteCodes.ldc_w_opcode:
			case RawByteCodes.ldc_opcode: {
				short byte1 = (short) (currentMethodCode[pc + 1] & 0xff);
				short byte2 = (short) (currentMethodCode[pc + 2] & 0xff);

				int index = ((byte1 << 8) | byte2);

				ArrayList<LDCConstant> constants = toolBox.getPatcher().getConstants();
				LDCConstant constant = constants.get(index);
				if (constant.getType() == LDCConstant.INTEGER) {
					sm.push(Size.INT, "" + constant.getInt());
				} else if (constant.getType() == LDCConstant.STRING) {
					localVariables.print("   const ConstantInfo* constant_;\n");
					output.append("   constant_ = &constants[" + index + "];\n");
					output.append("#ifndef PRE_INITIALIZE_CONSTANTS\n");
					output.append("   initializeStringConstant(constant_, sp);\n");
					setSPUsed(true);
					output.append("#endif\n");

					requiredIncludes.print("extern Object* stringConstants[];\n");
					requiredIncludes.print("extern const ConstantInfo *constants;\n");
					requiredIncludes
							.print("#ifndef PRE_INITIALIZE_CONSTANTS\nextern int16 initializeStringConstant(const ConstantInfo* constant, int32* sp);\n#endif\n");

					sm.push(Size.INT, "(int32) (pointer) stringConstants[pgm_read_dword(&constant_->value) >> 16]");
				} else if (constant.getType() == LDCConstant.CLASS) {
					localVariables.print("   const ConstantInfo* constant_;\n");
					output.append("   constant_ = &constants[" + index + "];\n");
					requiredIncludes.print("extern const ConstantInfo *constants;\n");
					requiredIncludes.print("extern Object* getClass(unsigned short classIndex);\n");
					sm.push(Size.INT, "(uint32)(pointer)getClass(pgm_read_dword(&constant_->value))");
				} else if (constant.getType() == LDCConstant.LONG) {
					localVariables.print("   int32 msi;\n");
					localVariables.print("   int32 lsi;\n");
					localVariables.print("   const unsigned char *data_;\n");
					localVariables.print("   const ConstantInfo* constant_;\n");
					output.append("   constant_ = &constants[" + index + "];\n");
					requiredIncludes.print("extern const ConstantInfo *constants;\n");

					output.append("   data_ = (const unsigned char *) pgm_read_pointer(&constant_->data, const void **);\n");
					output.append("   msi = ((int32) pgm_read_byte(data_)) << 24;\n");
					output.append("   msi |= ((int32) pgm_read_byte(data_ +1)) << 16;\n");
					output.append("   msi |= pgm_read_byte(data_ + 2) << 8;\n");
					output.append("   msi |= pgm_read_byte(data_ + 3);\n");

					output.append("   lsi = ((int32) pgm_read_byte(data_ + 4)) << 24;\n");
					output.append("   lsi |= ((int32) pgm_read_byte(data_ + 5)) << 16;\n");
					output.append("   lsi |= pgm_read_byte(data_ + 6) << 8;\n");
					output.append("   lsi |= pgm_read_byte(data_ + 7);\n");

					sm.push(Size.INT, "msi");
					sm.push(Size.INT, "lsi");
				}
				/*
				 * else if (constant.getType() == LDCConstant.FLOAT) { String
				 * fval = "" + constant.getFloat(); if
				 * (fval.contains("Infinity")) { fval = fval.toUpperCase(); }
				 * sm.push(Size.FLOAT, fval); }
				 */else {
					sm.flush(true);
					setSPUsed(true);
					localVariables.print("   unsigned char topInc;\n");
					output.append("      topInc = handleLDCWithIndex(sp, " + index + ");\n");
					output.append("      sp += topInc;\n");
					requiredIncludes.print("unsigned char handleLDCWithIndex(int32* sp, unsigned short index);\n");
				}
				pc += 3;
				break;
			}
			case RawByteCodes.pop_opcode:
				requiredIncludes.print("static uint32 dummy;\n");
				sm.pop("dummy", Size.INT);
				pc++;
				break;
			case RawByteCodes.arraylength_opcode: {
				AbstractStack stack = bnode.getStackLayout();
				StackCell cell = stack.peek();

				int valueSize = getConsumerSize(pc, 0);

				if (valueSize == Size.DONTCARE) {
					valueSize = getProducersSize(pc, 0);
				}
				localVariables.print("   unsigned char* cobj;\n");
				sm.popRef("      cobj");
				if (((RefType) cell.content).getState() != RefState.NONNULL) {
					checkObject(output, localVariables, pc, labelsManager, null, "", sm, "cobj");
				}
				sm.push(valueSize, "*(uint16*) ((HEAP_REF(cobj, unsigned char*)) + sizeof(Object))");
				pc++;
				break;
			}
			case RawByteCodes.faload_opcode:
			case RawByteCodes.aaload_opcode:
			case RawByteCodes.baload_opcode:
			case RawByteCodes.caload_opcode:
			case RawByteCodes.saload_opcode:
			case RawByteCodes.laload_opcode:
			case RawByteCodes.iaload_opcode: {
				int valueSize = getConsumerSize(pc, 0);

				if (valueSize == Size.DONTCARE) {
					valueSize = getProducersSize(pc, 0);
				}

				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;
				int size = normalizeProducersSize(bnode, entryStack.size() - 1);
				String type = getTypeCast(size);
				localVariables.print("   " + type + " index_" + type + ";\n");
				localVariables.print("   unsigned char* cobj;\n");

				sm.pop("      index_" + type + "", size);
				sm.popRef("      cobj");

				AbstractStack stack = bnode.getStackLayout();
				StackCell cell = stack.getAt(stack.getSize() - 2);

				if (((RefType) cell.content).getState() != RefState.NONNULL) {
					checkObject(output, localVariables, pc, labelsManager, null, "", sm, "cobj");
				} else {
				}

				int elementSize = 0;

				elementSize = getElementSize(pc);

				adjustArrayPointer(output, elementSize, "cobj", "index_" + type + "");
				output.append("      cobj = HEAP_REF(cobj, unsigned char*);\n");
				switch (elementSize) {
				case 1:
					sm.push(valueSize, "*(signed char *)cobj");
					break;
				case 2:
					sm.push(valueSize, "*((signed short *) cobj)");
					break;
				case 4:
					sm.push(valueSize, "*((uint32 *) cobj)");
					break;
				case 8:
					sm.push(Size.INT, "*((uint32 *) cobj)");
					sm.push(Size.INT, "*((uint32 *) (cobj + 4))");
					break;
				}
				pc++;
				break;
			}
			case RawByteCodes.anewarray_opcode:
			case RawByteCodes.newflasharray_opcode:
			case RawByteCodes.newarray_opcode: {
				int classIndex = (currentMethodCode[pc + 1] << 8) & 0xffff;
				classIndex |= (currentMethodCode[pc + 2] & 0xff);

				localVariables.print("   Object* narray;\n");
				localVariables.print("   uint16 count;\n");
				sm.pop("      count", Size.SHORT);
				output.append("      narray = (Object*) createArray(" + classIndex
						+ ", (uint16) count FLASHARG((0)));\n");
				output.append("      if (narray == 0) {\n");
				output.append("         pc = " + pc + ";\n");
				labelsManager.jumpTo(sm);
				output.append("         goto " + LabelsManager.LThrowOutOfMemory + ";\n");
				localVariables.print("   unsigned short pc;\n");
				labelsManager.generateOutOfMemory();
				output.append("      }\n");
				sm.push(Size.INT, "(int32) (pointer) narray");
				pc += 3;
				requiredIncludes
						.print("extern unsigned char* createArray(unsigned short classIndex, uint16 count FLASHARG(uint8 flash));\n");
				requiredIncludes.print("extern unsigned short getClassIndex(Object* obj);\n");
				break;
			}
			case RawByteCodes.multianewarray_opcode: {
				int classIndex = (currentMethodCode[pc + 1] << 8) & 0xffff;
				classIndex |= (currentMethodCode[pc + 2] & 0xff);
				int dimensions = currentMethodCode[pc + 3];
				sm.flush(false);
				localVariables.print("   Object* narray;\n");
				output.append("      narray = (Object*) createMultiDimensionalArrays(sp, " + dimensions + ", "
						+ classIndex + ");\n");
				while (dimensions > 0) {
					sm.pop(null, Size.INT);
					dimensions--;
				}
				sm.push(Size.INT, "(int32) (pointer) narray");
				pc += 4;
				requiredIncludes
						.print("extern Object* createMultiDimensionalArrays(int32* sp, unsigned char dimensions, unsigned short classIndex);\n");
				break;
			}
			case RawByteCodes.aastore_opcode:
			case RawByteCodes.fastore_opcode:
			case RawByteCodes.bastore_opcode:
			case RawByteCodes.castore_opcode:
			case RawByteCodes.sastore_opcode:
			case RawByteCodes.lastore_opcode:
			case RawByteCodes.dastore_opcode:
			case RawByteCodes.iastore_opcode: {
				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;
				int indexStackCellIndex;
				if ((currentMethodCode[pc] != RawByteCodes.lastore_opcode)
						&& (currentMethodCode[pc] != RawByteCodes.dastore_opcode)) {
					indexStackCellIndex = entryStack.size() - 2;
				} else {
					indexStackCellIndex = entryStack.size() - 3;
				}

				int indexSize = normalizeProducersSize(bnode, indexStackCellIndex);
				String indexCast = getTypeCast(indexSize);

				String valueCast;
				int valueSize = Size.INT;
				switch (currentMethodCode[pc]) {
				case RawByteCodes.lastore_opcode:
				case RawByteCodes.dastore_opcode:
				case RawByteCodes.iastore_opcode:
				case RawByteCodes.aastore_opcode:
				case RawByteCodes.fastore_opcode:
					valueSize = Size.INT;
					break;
				case RawByteCodes.bastore_opcode:
				case RawByteCodes.castore_opcode:
					valueSize = Size.BYTE;
					break;
				case RawByteCodes.sastore_opcode:
					valueSize = Size.SHORT;
					break;
				}
				valueCast = getTypeCast(valueSize);

				localVariables.print("   " + valueCast + " lsb_" + valueCast + ";\n");
				if ((currentMethodCode[pc] == RawByteCodes.lastore_opcode)
						|| (currentMethodCode[pc] == RawByteCodes.dastore_opcode)) {
					localVariables.print("   int32 msb_int32;\n");
				}
				localVariables.print("   " + indexCast + " index_" + indexCast + ";\n");
				localVariables.print("   unsigned char* cobj;\n");

				sm.pop("      lsb_" + valueCast + "", valueSize);

				if ((currentMethodCode[pc] == RawByteCodes.lastore_opcode)
						|| (currentMethodCode[pc] == RawByteCodes.dastore_opcode)) {
					sm.pop("      msb_int32", Size.INT);
				}

				sm.pop("      index_" + indexCast + "", indexSize);
				sm.popRef("      cobj");

				int indexIndex;
				if ((currentMethodCode[pc] == RawByteCodes.lastore_opcode)
						|| (currentMethodCode[pc] == RawByteCodes.dastore_opcode)) {
					indexIndex = 4;
				} else {
					indexIndex = 3;
				}
				AbstractStack stack = bnode.getStackLayout();
				StackCell cell = stack.getAt(stack.getSize() - indexIndex);

				if (((RefType) cell.content).getState() != RefState.NONNULL) {
					checkObject(output, localVariables, pc, labelsManager, null, "", sm, "cobj");
				}

				int elementSize = getElementSize(pc);

				adjustArrayPointer(output, elementSize, "cobj", "index_" + indexCast);

				output.append("      cobj = HEAP_REF(cobj, unsigned char*);\n");
				switch (elementSize) {
				case 1:
					output.append("      *cobj = lsb_" + valueCast + ";\n");
					break;
				case 2:
					output.append("      *((unsigned short *) cobj) = lsb_" + valueCast + ";\n");
					break;
				case 4:
					output.append("      *((uint32 *) cobj) = lsb_" + valueCast + ";\n");
					break;
				case 8:
					output.append("      *((uint32 *) cobj) = msb_int32;\n");
					output.append("      *((uint32 *) (cobj + 4)) = lsb_" + valueCast + ";\n");
					break;
				}
				pc++;
				break;
			}
			case RawByteCodes.dcmpg_opcode:
			case RawByteCodes.dcmpl_opcode:
				sm.flush(true);
				localVariables.print("   double d_value1;\n");
				localVariables.print("   double d_value2;\n");
				localVariables.print("   int32 d_res;\n");
				output.append("   d_res = 0;\n");
				output.append("   sp-=2;\n");
				output.append("   d_value1 = *(double*) sp;\n");
				output.append("   sp-=2;\n");
				output.append("   d_value2 = *(double*) sp;\n");
				output.append("   if (d_value2 > d_value1) {\n");
				output.append("      d_res = 1;\n");
				output.append("   } else if (d_value1 > d_value2) {\n");
				output.append("      d_res = -1;\n");
				output.append("   }\n");
				output.append("   *sp++ = d_res;\n");
				pc++;
				break;
			case RawByteCodes.lcmp_opcode:
				sm.flush(true);
				setSPUsed(true);
				localVariables.print("   int32 l_res;\n");
				output.append("      l_res = handleLCMP(sp);\n");
				output.append("      sp -= 4;\n");
				output.append("      *sp++ = l_res;\n");
				requiredIncludes.print("extern int32 handleLCMP(int32* sp);\n");
				pc++;
				break;
			case RawByteCodes.i2b_opcode:
			case RawByteCodes.i2c_opcode: {
				pc++;
				break;
			}
			case RawByteCodes.invoke_clone_onarray:
				sm.flush(true);
				setSPUsed(true);
				output.append("      excep = handleCloneOnArray(sp);\n");

				output.append("      if (excep < 0) {\n");
				output.append("         pc = " + pc + ";\n");
				labelsManager.jumpTo(sm);
				output.append("         goto " + LabelsManager.LThrowNullPointer + ";\n");
				output.append("      } else if (excep > 0) {\n");
				output.append("         pc = " + pc + ";\n");
				labelsManager.jumpTo(sm);
				output.append("         goto " + LabelsManager.LThrowOutOfMemory + ";\n");
				output.append("      }\n");
				labelsManager.generateOutOfMemory();
				labelsManager.generateThrowNullPointer();
				requiredIncludes.print("extern signed char handleCloneOnArray(int32* sp);\n");
				localVariables.print("   " + getTypeCast(entrypoints.getReturnTypeSize()) + " excep;\n");
				pc += 4;
				break;
			case RawByteCodes.ishr_opcode:
			case RawByteCodes.ishl_opcode: {
				String operation = null;
				if (currentMethodCode[pc] == RawByteCodes.ishl_opcode) {
					operation = "<<";
				} else {
					operation = ">>";
				}

				ProducerConsumerStack exitStack = bnode.getAinfo().exitStack;
				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;

				int src2Size = normalizeProducersSize(bnode, entryStack.size() - 2);

				int dstSize = normalizeConsumerSize(exitStack.peek().getConsumer(), exitStack.size() - 1);

				if (dstSize == Size.DONTCARE) {
					dstSize = Size.INT;
				}

				localVariables.print("   unsigned char shiftv;\n");
				localVariables.print("   uint32 v_alue;\n");
				sm.pop("      shiftv", Size.INT);
				sm.pop("      v_alue", src2Size);
				output.append("      v_alue = v_alue " + operation + " shiftv;\n");
				sm.push(dstSize, "v_alue");
				pc++;
				break;
			}
			case RawByteCodes.dneg_opcode: {
				sm.flush(true);
				localVariables.print("   double d_ouble1;\n");
				output.append("   sp -= 2;\n");
				output.append("   d_ouble1 = *(double*) sp;\n");
				output.append("   d_ouble1 = -d_ouble1;\n");
				output.append("   *(double*) sp = d_ouble1;\n");
				output.append("   sp += 2;\n");
				pc++;
				break;
			}
			case RawByteCodes.fneg_opcode: {
				String srcType = getTypeCast(Size.FLOAT);
				localVariables.print("   " + srcType + " res_" + srcType + ";\n");
				sm.pop("      res_" + srcType + "", Size.FLOAT);
				output.append("      res_" + srcType + " = -res_" + srcType + ";\n");
				sm.push(Size.FLOAT, "res_" + srcType + "");
				pc++;
				break;
			}
			case RawByteCodes.ineg_opcode: {
				ProducerConsumerStack exitStack = bnode.getAinfo().exitStack;
				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;

				int srcSize = normalizeProducersSize(bnode, entryStack.size() - 1);

				int dstSize = normalizeConsumerSize(exitStack.peek().getConsumer(), exitStack.size() - 1);

				if (dstSize == Size.DONTCARE) {
					dstSize = Size.INT;
				}

				String srcType = getTypeCast(srcSize);
				localVariables.print("   " + srcType + " res_" + srcType + ";\n");
				sm.pop("      res_" + srcType + "", srcSize);
				output.append("      res_" + srcType + " = -res_" + srcType + ";\n");
				sm.push(dstSize, "res_" + srcType + "");
				pc++;
				break;
			}
			case RawByteCodes.fdiv_opcode:
			case RawByteCodes.fsub_opcode:
			case RawByteCodes.fmul_opcode:
			case RawByteCodes.fadd_opcode: {
				int dstSize = Size.FLOAT;

				String operand = "+";

				switch (currentMethodCode[pc]) {
				case RawByteCodes.fdiv_opcode:
					operand = "/";
					break;
				case RawByteCodes.fmul_opcode:
					operand = "*";
					break;
				case RawByteCodes.fsub_opcode:
					operand = "-";
					break;
				}
				localVariables.print("   float msb_float;\n");
				localVariables.print("   float lsb_float;\n");
				sm.pop("      msb_float", Size.FLOAT);
				sm.pop("      lsb_float", Size.FLOAT);
				output.append("      lsb_float " + operand + "= msb_float;\n");
				sm.push(dstSize, "lsb_float");
				pc++;
				break;
			}
			case RawByteCodes.ior_opcode:
			case RawByteCodes.ixor_opcode:
			case RawByteCodes.iushr_opcode:
			case RawByteCodes.iand_opcode:
			case RawByteCodes.irem_opcode:
			case RawByteCodes.idiv_opcode:
			case RawByteCodes.imul_opcode:
			case RawByteCodes.isub_opcode:
			case RawByteCodes.iadd_opcode: {
				ProducerConsumerStack exitStack = bnode.getAinfo().exitStack;
				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;

				int src1Size = normalizeProducersSize(bnode, entryStack.size() - 1);
				int src2Size = normalizeProducersSize(bnode, entryStack.size() - 2);

				int dstSize = normalizeConsumerSize(exitStack.peek().getConsumer(), exitStack.size() - 1);

				if (dstSize == Size.DONTCARE) {
					dstSize = Size.INT;
				}

				String src1Type = getTypeCast(src1Size);
				String src2Type = getTypeCast(src2Size);

				String operand = "";
				switch (currentMethodCode[pc]) {
				case RawByteCodes.ior_opcode:
				case RawByteCodes.ixor_opcode:
				case RawByteCodes.iand_opcode:
				case RawByteCodes.isub_opcode:
				case RawByteCodes.iadd_opcode:
					switch (currentMethodCode[pc]) {
					case RawByteCodes.ior_opcode:
						operand = "|";
						break;
					case RawByteCodes.ixor_opcode:
						operand = "^";
						break;
					case RawByteCodes.iand_opcode:
						operand = "&";
						break;
					case RawByteCodes.isub_opcode:
						operand = "-";
						break;
					case RawByteCodes.iadd_opcode:
						operand = "+";
						break;
					}
					localVariables.print("   " + src1Type + " msb_" + src1Type + ";\n");
					localVariables.print("   " + src2Type + " lsb_" + src2Type + ";\n");
					sm.pop("      msb_" + src1Type + "", src1Size);
					sm.pop("      lsb_" + src2Type + "", src2Size);
					output.append("      lsb_" + src2Type + " " + operand + "= msb_" + src1Type + ";\n");
					sm.push(dstSize, "lsb_" + src2Type + "");
					pc++;
					break;
				case RawByteCodes.imul_opcode: {
					String dstType = getTypeCast(dstSize);
					localVariables.print("   " + dstType + " res_" + dstType + ";\n");
					localVariables.print("   " + src2Type + " lsb_" + src2Type + ";\n");
					sm.pop("      res_" + dstType + "", src1Size);
					sm.pop("      lsb_" + src2Type + "", src2Size);
					output.append("#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)\n");
					output.append("      res_" + dstType + " = imul(res_" + dstType + ", lsb_" + src2Type + ");\n");
					requiredIncludes.print("extern int32 imul(int32 x, int32 y);\n");
					output.append("#else\n");
					output.append("      res_" + dstType + " = res_" + dstType + " * lsb_" + src2Type + ";\n");
					output.append("#endif\n");
					sm.push(dstSize, "res_" + dstType + "");
					pc++;
					break;
				}
				case RawByteCodes.idiv_opcode: {
					String lsb_type = getTypeCast(src2Size);
					String dstType = getTypeCast(dstSize);
					localVariables.print("   " + dstType + " res_" + dstType + ";\n");
					localVariables.print("   " + lsb_type + " lsb_" + lsb_type + ";\n");
					sm.pop("      res_" + dstType + "", src1Size);
					sm.pop("      lsb_" + lsb_type + "", src2Size);
					output.append("      if (res_" + dstType + " == 0) {\n");
					output.append("         pc = " + pc + ";\n");
					labelsManager.jumpTo(sm);
					output.append("         goto " + LabelsManager.LThrowArithmeticException + ";\n");
					labelsManager.generateArithmeticException();
					output.append("      }\n");
					// output.append("#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)\n");
					output.append("      res_" + dstType + " = idiv(lsb_" + lsb_type + ", res_" + dstType + ");\n");
					// output.append("#else\n");
					// output.append("      res_" + dstType + " = lsb_" +
					// lsb_type + " / res_" + dstType + ";\n");
					// output.append("#endif\n");
					sm.push(dstSize, "res_" + dstType + "");
					pc++;
					localVariables.print("   unsigned short pc;\n");
					requiredIncludes.print("extern int32 idiv(int32 x, int32 y);\n");
					break;
				}
				case RawByteCodes.irem_opcode: {
					String lsb_type = getTypeCast(src2Size);
					String dst_type = getTypeCast(dstSize);
					localVariables.print("   " + dst_type + " res_" + dst_type + ";\n");
					localVariables.print("   " + lsb_type + " lsb_" + lsb_type + ";\n");
					sm.pop("      res_" + dst_type + "", src1Size);
					sm.pop("      lsb_" + lsb_type + "", src2Size);
					output.append("      if (res_" + dst_type + " == 0) {\n");
					output.append("         pc = " + pc + ";\n");
					labelsManager.jumpTo(sm);
					output.append("         goto " + LabelsManager.LThrowArithmeticException + ";\n");
					labelsManager.generateArithmeticException();
					output.append("      }\n");
					output.append("#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)\n");
					output.append("      res_" + dst_type + " = imod(res_" + dst_type + ", lsb_" + lsb_type + ");\n");
					output.append("#else\n");
					output.append("      res_" + dst_type + " = lsb_" + lsb_type + " % res_" + dst_type + ";\n");
					output.append("#endif\n");
					sm.push(dstSize, "res_" + dst_type + "");
					pc++;
					localVariables.print("   unsigned short pc;\n");
					requiredIncludes.print("extern int32 imod(int32 a, int32 b);\n");
					break;
				}
				case RawByteCodes.iushr_opcode: {
					String value1_type = getTypeCast(src2Size);
					String dst_type = getTypeCast(dstSize);
					localVariables.print("   unsigned char value2_uc;\n");
					localVariables.print("   u" + value1_type + " value1_u" + value1_type + ";\n");
					localVariables.print("   u" + dst_type + " result_u" + dst_type + ";\n");
					sm.pop("      value2_uc", Size.BYTE);
					sm.pop("      value1_u" + value1_type + "", src2Size);
					output.append("      result_u" + dst_type + " = value1_u" + value1_type + " >> value2_uc;\n");
					sm.push(dstSize, "result_u" + dst_type + "");
					pc++;
					break;
				}
				}
				break;
			}
			case RawByteCodes.getstatic_opcode:
			case RawByteCodes.putstatic_opcode: {
				StaticFieldEmitter emitter = new AOTStaticFieldEmitter(pc, toolBox, currentMethodCode, localVariables,
						output, sm, requiredIncludes, bnode);

				FieldAccessEmitter worker = emitter.getEmitter();

				worker.performFieldAccess();

				pc += 6;
				break;
			}
			case RawByteCodes.lstore_0_opcode:
			case RawByteCodes.lstore_1_opcode:
			case RawByteCodes.lstore_2_opcode:
			case RawByteCodes.lstore_3_opcode: {
				int index = currentMethodCode[pc] - RawByteCodes.lstore_0_opcode;
				sm.pop("   " + sm.getLocal(index + 1, getOriginalAddress(pc)), Size.INT);
				sm.pop("   " + sm.getLocal(index, getOriginalAddress(pc)), Size.INT);
				pc++;
				break;
			}
			case RawByteCodes.lload_0_opcode:
			case RawByteCodes.lload_1_opcode:
			case RawByteCodes.lload_2_opcode:
			case RawByteCodes.lload_3_opcode: {
				int index = currentMethodCode[pc] - RawByteCodes.lload_0_opcode;
				sm.push(Size.INT, sm.getLocal(index, getOriginalAddress(pc)));
				sm.push(Size.INT, sm.getLocal(index + 1, getOriginalAddress(pc)));
				pc++;
				break;
			}
			case RawByteCodes.ddiv_opcode:
			case RawByteCodes.dadd_opcode:
			case RawByteCodes.dsub_opcode:
			case RawByteCodes.dmul_opcode: {
				sm.flush(true);
				localVariables.print("   double d_ouble1;\n");
				localVariables.print("   double d_ouble2;\n");
				output.append("   sp -= 2;\n");
				output.append("   d_ouble1 = *(double*) sp;\n");
				output.append("   sp -= 2;\n");
				output.append("   d_ouble2 = *(double*) sp;\n");
				String operator = "";
				switch (currentMethodCode[pc]) {
				case RawByteCodes.dadd_opcode:
					operator = "+";
					break;
				case RawByteCodes.dsub_opcode:
					operator = "-";
					break;
				case RawByteCodes.dmul_opcode:
					operator = "*";
					break;
				case RawByteCodes.ddiv_opcode:
					operator = "/";
					break;
				}
				output.append("   d_ouble1 = d_ouble1 " + operator + " d_ouble2;\n");
				output.append("   *(double*) sp = d_ouble1;\n");
				output.append("   sp += 2;\n");
				pc++;
				break;
			}
			case RawByteCodes.ldiv_opcode:
			case RawByteCodes.lmul_opcode:
			case RawByteCodes.lrem_opcode:
				sm.flush(true);
				setSPUsed(true);
				localVariables.print("   unsigned char topInc;\n");
				output.append("      topInc = handleLMULLDIVLREM(sp, " + (currentMethodCode[pc] & 0xff) + ");\n");
				output.append("      sp -= 4;\n");
				output.append("      if (topInc == 0) {\n");
				output.append("         pc = " + pc + ";\n");
				labelsManager.jumpTo(sm);
				output.append("         goto " + LabelsManager.LThrowArithmeticException + ";\n");
				labelsManager.generateArithmeticException();
				output.append("      }\n");
				output.append("      sp += topInc;\n");
				pc++;
				requiredIncludes.print("extern unsigned char handleLMULLDIVLREM(int32* sp, unsigned char code);\n");
				localVariables.print("   unsigned short pc;\n");
				break;
			case RawByteCodes.dstore_0_opcode:
			case RawByteCodes.dstore_1_opcode:
			case RawByteCodes.dstore_2_opcode:
			case RawByteCodes.dstore_3_opcode:
			case RawByteCodes.dstore_opcode:
			case RawByteCodes.lstore_opcode: {
				short byteVal;
				int pcInc;
				if ((currentMethodCode[pc] == RawByteCodes.dstore_opcode)
						|| (currentMethodCode[pc] == RawByteCodes.lstore_opcode)) {
					byteVal = (short) (currentMethodCode[pc + 1] & 0xff);
					pcInc = 2;
				} else {
					byteVal = (short) (currentMethodCode[pc] - RawByteCodes.dstore_0_opcode);
					pcInc = 1;
				}
				sm.pop("   " + sm.getLocal(byteVal + 1, getOriginalAddress(pc)), Size.INT);
				sm.pop("   " + sm.getLocal(byteVal, getOriginalAddress(pc)), Size.INT);

				pc += pcInc;
				break;
			}
			case RawByteCodes.d2f_opcode:
				localVariables.print("   double d_ouble;\n");
				sm.flush(true);
				output.append("   d_ouble = *(double*) (sp - 2);\n");
				output.append("   sp -= 2;\n");
				output.append("   *(float*)sp = (float)d_ouble;\n");
				output.append("   sp++;\n");
				pc++;
				break;
			case RawByteCodes.d2l_opcode:
				localVariables.print("   double d_ouble;\n");
				sm.flush(true);
				output.append("   d_ouble = *(double*) (sp - 2);\n");
				output.append("   sp -= 2;\n");
				output.append("   *(int32*)sp = 0;\n");
				output.append("   sp++;\n");
				output.append("   *(int32*)sp = (int32)d_ouble;\n");
				output.append("   sp++;\n");
				pc++;
				break;
			case RawByteCodes.dload_opcode:
			case RawByteCodes.lload_opcode: {
				short index = (short) (currentMethodCode[pc + 1] & 0xff);
				sm.push(Size.INT, sm.getLocal(index, getOriginalAddress(pc)));
				sm.push(Size.INT, sm.getLocal(index + 1, getOriginalAddress(pc)));
				pc += 2;
				break;
			}
			case RawByteCodes.lsub_opcode:
			case RawByteCodes.ladd_opcode:
			case RawByteCodes.lor_opcode:
			case RawByteCodes.lxor_opcode:
			case RawByteCodes.land_opcode:
				sm.flush(true);
				setSPUsed(true);
				output.append("   handleLongOperator(" + currentMethodCode[pc] + ", sp);\n");
				output.append("   sp = sp - 2;\n");
				pc++;
				requiredIncludes.print("extern void handleLongOperator(unsigned char code, int32* sp);\n");
				break;
			case RawByteCodes.lconst_0_opcode:
			case RawByteCodes.lconst_1_opcode:
				sm.push(Size.INT, "0");
				sm.push(Size.INT, "" + (currentMethodCode[pc] - RawByteCodes.lconst_0_opcode));
				pc++;
				break;
			case RawByteCodes.dconst_0_opcode:
			case RawByteCodes.dconst_1_opcode:
				sm.flush(true);
				setSPUsed(true);
				output.append("      *(double*)sp = (double)" + (currentMethodCode[pc] - RawByteCodes.dconst_0_opcode)
						+ ";\n");
				output.append("      sp+=2;\n");
				pc++;
				break;
			case RawByteCodes.lshl_opcode:
			case RawByteCodes.lshr_opcode:
			case RawByteCodes.lushr_opcode:
				sm.flush(true);
				setSPUsed(true);
				if (currentMethodCode[pc] == RawByteCodes.lshl_opcode) {
					output.append("   handleLSHL(sp);\n");
					requiredIncludes.print("extern void handleLSHL(int32* sp);\n");
				} else {
					output.append("   handleLSHR(sp);\n");
					requiredIncludes.print("extern void handleLSHR(int32* sp);\n");
				}
				output.append("   sp--;\n");
				pc++;
				break;
			case RawByteCodes.tableswitch_opcode: {
				SwitchBNode sbnode = (SwitchBNode) bnode;
				Raw_tableswitch tableSwitch = (Raw_tableswitch) sbnode.getTableSwitch();
				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;

				int srcSize = normalizeProducersSize(bnode, entryStack.size() - 1);
				if (srcSize == Size.DONTCARE) {
					srcSize = Size.INT;
				}

				String key_type = getTypeCast(srcSize);
				int index = 0;
				localVariables.print("   " + key_type + " key_" + key_type + ";\n");
				sm.pop("   key_" + key_type + "", srcSize);
				output.append("   switch (key_" + key_type + " - " + tableSwitch.getLow() + ") {\n");
				for (long offset : tableSwitch.offsets) {
					output.append("   case " + index + ":\n");
					BNode target = sbnode.findTarget(offset);
					handleBranch(sm, currentMethodCode[pc], pc, target.getAddress(), true);
					output.append("      goto L" + target.getAddress() + ";\n");
					index++;
				}
				output.append("   default:\n");
				BNode target = sbnode.findTarget(tableSwitch.defaultVal);
				handleBranch(sm, currentMethodCode[pc], pc, target.getAddress(), true);
				output.append("      goto L" + target.getAddress() + ";\n");
				output.append("   }\n");
				pc += 16 + tableSwitch.offsets.length * 4;
			}
				break;
			case RawByteCodes.lookupswitch_opcode:
				SwitchBNode sbnode = (SwitchBNode) bnode;
				Raw_lookupswitch lookupSwitch = (Raw_lookupswitch) sbnode.getTableSwitch();
				ProducerConsumerStack entryStack = bnode.getAinfo().entryStack;

				int srcSize = normalizeProducersSize(bnode, entryStack.size() - 1);
				if (srcSize == Size.DONTCARE) {
					srcSize = Size.INT;
				}

				String key_type = getTypeCast(srcSize);
				localVariables.print("   " + key_type + " key_" + key_type + ";\n");
				sm.pop("   key_" + key_type + "", srcSize);
				output.append("   switch (key_" + key_type + ") {\n");
				for (Pair pair : lookupSwitch.pairs) {
					output.append("   case " + pair.match + ":\n");
					BNode target = sbnode.findTarget(pair.offset);
					handleBranch(sm, currentMethodCode[pc], pc, target.getAddress(), true);
					output.append("      goto L" + target.getAddress() + ";\n");
				}
				output.append("   default:\n");
				BNode target = sbnode.findTarget(lookupSwitch.defaultVal);
				handleBranch(sm, currentMethodCode[pc], pc, target.getAddress(), true);
				output.append("      goto L" + target.getAddress() + ";\n");
				output.append("   }\n");
				pc += 12 + lookupSwitch.pairs.length * 8;
				break;
			case RawByteCodes.i2d_opcode:
				sm.flush(true);
				localVariables.print("   int32 lsb_int32;\n");
				output.append("      lsb_int32 = *(--sp);\n");
				output.append("      *(double*)sp = (double)lsb_int32;\n");
				output.append("      sp+=2;\n");
				pc++;
				break;
			case RawByteCodes.i2l_opcode:
				localVariables.print("   int32 lsb_int32;\n");
				localVariables.print("   int32 msb_int32;\n");
				sm.pop("      lsb_int32", Size.INT);
				output.append("      if (lsb_int32 < 0) {\n");
				output.append("         msb_int32 = -1;\n");
				output.append("      } else {\n");
				output.append("         msb_int32 = 0;\n");
				output.append("      }\n");
				sm.push(Size.INT, "msb_int32");
				sm.push(Size.INT, "lsb_int32");
				pc++;
				break;
			case RawByteCodes.f2d_opcode: {
				sm.flush(true);
				localVariables.print("   double d_ouble;\n");
				output.append("   d_ouble = (double) (*(float*) (sp - 1));\n");
				output.append("   *(double*) (sp - 1) = d_ouble;\n");
				output.append("   sp++;\n");
				pc++;
				break;
			}
			case RawByteCodes.dup_x2_opcode:
				localVariables.print("   int32 value1;\n");
				localVariables.print("   int32 value2;\n");
				localVariables.print("   int32 value3;\n");
				sm.pop("      value1", Size.INT);
				sm.pop("      value2", Size.INT);
				sm.pop("      value3", Size.INT);
				sm.push(Size.INT, "value1");
				sm.push(Size.INT, "value3");
				sm.push(Size.INT, "value2");
				sm.push(Size.INT, "value1");
				pc++;
				break;
			case RawByteCodes.dup2_opcode:
				localVariables.print("   int32 value1;\n");
				localVariables.print("   int32 value2;\n");
				sm.pop("      value1", Size.INT);
				sm.pop("      value2", Size.INT);
				sm.push(Size.INT, "value2");
				sm.push(Size.INT, "value1");
				sm.push(Size.INT, "value2");
				sm.push(Size.INT, "value1");
				pc++;
				break;
			case RawByteCodes.dup_x1_opcode:
				localVariables.print("   int32 value1;\n");
				localVariables.print("   int32 value2;\n");
				sm.pop("      value1", Size.INT);
				sm.pop("      value2", Size.INT);
				sm.push(Size.INT, "value1");
				sm.push(Size.INT, "value2");
				sm.push(Size.INT, "value1");
				pc++;
				break;

			case RawByteCodes.dload_0_opcode:
			case RawByteCodes.dload_1_opcode:
			case RawByteCodes.dload_2_opcode:
			case RawByteCodes.dload_3_opcode:
				int index = (currentMethodCode[pc] - RawByteCodes.dload_0_opcode);
				sm.push(Size.INT, sm.getLocal(index, getOriginalAddress(pc)));
				sm.push(Size.INT, sm.getLocal(index + 1, getOriginalAddress(pc)));
				pc++;
				break;
			case RawByteCodes.i2s_opcode:
				localVariables.print("   short int sresult;\n");
				sm.pop("      sresult", Size.INT);
				sm.push(Size.INT, "sresult");
				pc++;
				break;
			case RawByteCodes.lneg_opcode:
				localVariables.print("   uint32 lsb_uint32;\n");
				localVariables.print("   uint32 msb_uint32;\n");
				sm.pop("      lsb_uint32", Size.INT);
				sm.pop("      msb_uint32", Size.INT);
				output.append("      neg(&msb_uint32, &lsb_uint32);\n");
				sm.push(Size.INT, "msb_uint32");
				sm.push(Size.INT, "lsb_uint32");
				requiredIncludes.print("extern void neg(uint32* msb, uint32* lsb);\n");
				pc++;
				break;
			case RawByteCodes.l2i_opcode:
				localVariables.print("   int32 lsi;\n");
				sm.pop("      lsi", Size.INT);
				sm.pop(null, Size.INT);
				sm.push(Size.INT, "lsi");
				pc++;
				break;
			case RawByteCodes.l2f_opcode:
				localVariables.print("   float f_lsi;\n");
				sm.pop("      f_lsi", Size.FLOAT);
				sm.pop(null, Size.FLOAT);
				sm.push(Size.FLOAT, "f_lsi");
				pc++;
				break;
			case RawByteCodes.monitorenter_opcode:
			case RawByteCodes.monitorexit_opcode:
				localVariables.print("   unsigned char* cobj;\n");
				sm.popRef("cobj");

				checkObject(output, localVariables, pc, labelsManager, null, "   ", sm, "cobj");

				String isEnter;
				if (currentMethodCode[pc] == RawByteCodes.monitorenter_opcode) {
					isEnter = "1";
				} else {
					isEnter = "0";
				}
				output.append("   handleMonitorEnterExit((Object*)cobj, " + isEnter + ", sp, \"\");\n");
				pc++;
				requiredIncludes
						.print("extern unsigned char handleMonitorEnterExit(Object* lockObj, unsigned char isEnter, int32* sp, const char* fromMethod);\n");
				break;
			case RawByteCodes.fconst_0_opcode:
			case RawByteCodes.fconst_1_opcode:
			case RawByteCodes.fconst_2_opcode: {
				sm.push(Size.FLOAT, "" + (currentMethodCode[pc] - RawByteCodes.fconst_0_opcode));
				pc++;
				break;
			}
			case RawByteCodes.fcmpg_opcode:
			case RawByteCodes.fcmpl_opcode: {
				String right;
				String left;

				if (sm.isCached(0) && sm.isCached(1)) {
					right = sm.peekTop(1, Size.INT);
					left = sm.peekTop(2, Size.INT);

					sm.pop(null, Size.INT);
					sm.pop(null, Size.INT);
				} else {

					localVariables.print("   float lsb_float;\n");
					localVariables.print("   float msb_float;\n");

					sm.pop("      lsb_float", Size.FLOAT);
					sm.pop("      msb_float", Size.FLOAT);

					left = "msb_float";
					right = "lsb_float";
				}

				output.append("    if (" + left + " > " + right + ") {\n");
				output.append("        res = 1;\n");
				output.append("     } else if (" + right + " > " + left + ") {\n");
				output.append("        res = -1;\n");
				output.append("     } else {\n");
				output.append("        res = 0;\n");
				output.append("     }\n");

				int valueSize = getConsumerSize(pc, 0);

				if (valueSize == Size.DONTCARE) {
					valueSize = Size.BYTE;
				}
				localVariables.print("   uint8 res;\n");
				sm.push(valueSize, "(uint8)res");
				pc++;
				break;
			}
			case RawByteCodes.i2f_opcode:
				localVariables.print("   int i_nt;\n");
				sm.pop("      i_nt", Size.INT);
				sm.push(Size.FLOAT, "i_nt");
				pc++;
				break;
			case RawByteCodes.f2i_opcode:
				localVariables.print("   float f_loat;\n");
				sm.pop("      f_loat", Size.FLOAT);
				sm.push(Size.INT, "f_loat");
				pc++;
				break;
			case RawByteCodes.f2l_opcode:
				localVariables.print("   float f_loat;\n");
				sm.pop("      f_loat", Size.FLOAT);
				sm.push(Size.INT, "0");
				sm.push(Size.INT, "f_loat");
				pc++;
				break;

			case RawByteCodes.swap_opcode: {
				localVariables.print("   float f_loat1;\n");
				localVariables.print("   float f_loat2;\n");
				sm.pop("      f_loat1", Size.FLOAT);
				sm.pop("      f_loat2", Size.FLOAT);
				sm.push(Size.INT, "f_loat1");
				sm.push(Size.INT, "f_loat2");
				pc++;
				break;
			}
			case RawByteCodes.wide_opcode: {
				int opcode = currentMethodCode[pc + 1];
				int inx = (currentMethodCode[pc + 2] << 8) & 0xffff;
				inx |= (currentMethodCode[pc + 3] & 0xff);

				switch (opcode) {
				case RawByteCodes.iinc_opcode: {
					short localVarNo = currentMethodCode[pc + 4];
					short constVal = currentMethodCode[pc + 5];
					int size = getLocalVariableSize(localVarNo, bnode.getOriginalAddress());
					String cast = getTypeCast(size);
					output.append("   " + sm.getLocal(localVarNo, bnode.getOriginalAddress()) + " = (" + cast + ")"
							+ sm.getLocal(localVarNo, bnode.getOriginalAddress()) + " + " + constVal + ";\n");
					break;
				}
				case RawByteCodes.iload_opcode:
				case RawByteCodes.fload_opcode:
				case RawByteCodes.aload_opcode: {
					int valueSize;

					valueSize = getConsumerSize(pc, 0);

					if (valueSize == Size.DONTCARE) {
						valueSize = getProducersSize(pc, 0);
					}

					srcSize = getLocalVariableSize(inx, bnode.getOriginalAddress());
					String srcCast = getTypeCast(srcSize);

					sm.load(srcSize, inx, pc);
					sm.push(valueSize, "(" + srcCast + ")" + sm.getLocal(inx, bnode.getOriginalAddress()));
					break;
				}
				case RawByteCodes.lload_opcode:
				case RawByteCodes.dload_opcode:
					sm.push(Size.INT, sm.getLocal(inx, getOriginalAddress(pc)));
					sm.push(Size.INT, sm.getLocal(inx + 1, getOriginalAddress(pc)));
					break;
				case RawByteCodes.istore_opcode:
				case RawByteCodes.fstore_opcode:
				case RawByteCodes.astore_opcode: {
					int valueSize;
					switch (opcode) {
					case RawByteCodes.istore_opcode:
					case RawByteCodes.istore_0_opcode:
					case RawByteCodes.istore_1_opcode:
					case RawByteCodes.istore_2_opcode:
					case RawByteCodes.istore_3_opcode:
						valueSize = getIStoreSize(bnode, currentMethodCode[pc]);
						break;
					default:
						valueSize = Size.FLOAT;
					}

					sm.setLocal(valueSize, inx, pc);

					String dst;
					if (bnode.isRedundant()) {
						dst = null;
					} else {
						dst = "   " + sm.getLocal(inx, bnode.getOriginalAddress());
					}
					sm.pop(dst, valueSize);
				}
					break;
				case RawByteCodes.lstore_opcode:
				case RawByteCodes.dstore_opcode:
					sm.pop("   " + sm.getLocal(inx + 1, getOriginalAddress(pc)), Size.INT);
					sm.pop("   " + sm.getLocal(inx, getOriginalAddress(pc)), Size.INT);
					break;
				case RawByteCodes.ret_opcode:
					break;
				}
				if (opcode == RawByteCodes.iinc_opcode) {
					pc += 6;
				} else {
					pc += 4;
				}
				break;
			}
			case RawByteCodes.nop_opcode:
			case RawByteCodes.pop2_opcode:
			case RawByteCodes.dup2_x1_opcode:
			case RawByteCodes.dup2_x2_opcode:

			default:
				throw new Exception("unsupported byte code [" + Integer.toHexString(currentMethodCode[pc]) + "]");
			}
			if (compileOne) {
				return;
			}
		}
	}

	public abstract void addUserIncludes(NoDuplicatesMemorySegment requiredIncludes, String includes);

	static boolean synchronizedSupported(Method referredMethod) {
		return (!(referredMethod.isStatic() || referredMethod.isNative())) && referredMethod.isSynchronized();
	}

	public void setSPUsed(boolean b) {
		spUsed |= b;
	}

	private int getOriginalAddress(int pc) {
		BNode bnode = toolBox.getDependencyExtent().getBNodeFromMethod(toolBox.getCurrentClassName(),
				javaMethod.getName(), javaMethod.getSignature(), pc);
		return bnode.getOriginalAddress();
	}

	protected void handleLabel(StackManager sm) {
		sm.flush(true);
	}

	protected void handleBranch(StackManager sm, byte opcode, int pc, int i, boolean doit) throws Exception {
		if ((opcode != RawByteCodes.lookupswitch_opcode) && (opcode != RawByteCodes.tableswitch_opcode)) {
			sm.flush(doit);
		}
	}

	private int getProducersSize(int pc, int index) throws Exception {
		BNode bnode = toolBox.getDependencyExtent().getBNodeFromMethod(toolBox.getCurrentClassName(),
				javaMethod.getName(), javaMethod.getSignature(), pc);

		ProducerConsumerStack exitStack = bnode.getAinfo().exitStack;
		int stackCellIndex = exitStack.size() - 1 - index;

		BNode consumer = exitStack.getCell(stackCellIndex).getConsumer();
		int producerSize = normalizeProducersSize(consumer, stackCellIndex);
		return producerSize;
	}

	private int getConsumerSize(int pc, int index) throws Exception {
		BNode bnode = toolBox.getDependencyExtent().getBNodeFromMethod(toolBox.getCurrentClassName(),
				javaMethod.getName(), javaMethod.getSignature(), pc);

		ProducerConsumerStack exitStack = bnode.getAinfo().exitStack;
		int stackCellIndex = exitStack.size() - 1 - index;

		BNode consumer = exitStack.getCell(stackCellIndex).getConsumer();
		int consumerSize = normalizeConsumerSize(consumer, stackCellIndex);

		return consumerSize;
	}

	public static String getPointerCast(int size) {
		StringBuffer type = new StringBuffer();

		type.append("(");
		type.append(getTypeCast(size));
		type.append("*)");
		return type.toString();
	}

	public static String getTypeCast(int size) {
		String type = "";

		switch (size) {
		case Size.BYTE:
			type = "int8";
			break;
		case Size.SHORT:
			type = "int16";
			break;
		case Size.INT:
			type = "int32";
			break;
		case Size.FLOAT:
			type = "float";
			break;
		default:
			type = "int32";
			break;
		}
		return type;
	}

	public static int getReturntypeSize(Method method) throws Exception {
		Type type = method.getReturnType();
		return type2Size(type);
	}

	private static int type2Size(Type type) throws Exception {
		int size = ClassfileUtils.getSizeOfType(type);
		switch (size) {
		case 1:
		case 8:
			return Size.BYTE;
		case 16:
			return Size.SHORT;
		case 32:
			return Size.INT;
		case 64:
			return Size.LONG;
		}
		return Size.INT;
	}

	private int getArgumentSize(MethodCallBNode consumer, int stackCellIndex) throws ClassNotFoundException, Exception {
		Method referredMethod;

		referredMethod = getCalleeMethodFromBNode(consumer);

		MethodOrFieldDesc methodDesc = getCalleeMethodDescriptorFromBNode(consumer);

		if (interpretMethod(referredMethod, methodDesc, toolBox.getCregistry())) {
			return Size.INT;
		}

		int top = consumer.getAinfo().entryStack.size() - 1;

		Type[] args = referredMethod.getArgumentTypes();

		if (args != null) {
			int index = args.length;
			while (index > 0) {
				Type arg = args[index - 1];
				if (type2Size(arg) == Size.LONG) {
					if ((top == stackCellIndex) || (top - 1 == stackCellIndex)) {
						return Size.INT;
					}
					top--;
				} else {
					if (top == stackCellIndex) {
						return type2Size(arg);
					}
				}

				index--;

				top--;
			}
		}

		if (consumer.getOpCode() != RawByteCodes.invokestatic_opcode) {
			if (top == stackCellIndex) {
				return Size.INT;
			}
		}
		throw new Exception("Unexpected stack cell index");
	}

	private Method getCalleeMethodFromBNode(MethodCallBNode consumer) throws ClassNotFoundException, Exception {
		Method referredMethod;
		if (consumer.getOpCode() == RawByteCodes.invokevirtual_opcode) {
			referredMethod = ((VirtualMethodCallBNode) consumer).getCallee();
		} else if (consumer.getOpCode() == RawByteCodes.invokeinterface_opcode) {
			MethodOrFieldDesc methodDesc = ((InterfaceMethodCallBNode) consumer).getMethodDescriptor();
			referredMethod = ClassfileUtils.findDeclaringInterface(methodDesc.getClassName(), methodDesc.getName(),
					methodDesc.getSignature()).getMethod();
		} else {
			int methodNumber;

			methodNumber = currentMethodCode[consumer.getAddress() + 1] << 8;
			methodNumber |= (currentMethodCode[consumer.getAddress() + 2] & 0xff);

			MethodOrFieldDesc methodDesc = toolBox.getPatcher().getMethodDescriptor(methodNumber);

			referredMethod = ClassfileUtils.findMethod(methodDesc.getClassName(), methodDesc.getName(),
					methodDesc.getSignature()).getMethod();
		}
		return referredMethod;
	}

	private MethodOrFieldDesc getCalleeMethodDescriptorFromBNode(MethodCallBNode consumer)
			throws ClassNotFoundException, Exception {
		MethodOrFieldDesc methodDesc;
		if (consumer.getOpCode() == RawByteCodes.invokevirtual_opcode) {
			methodDesc = ((VirtualMethodCallBNode) consumer).getMethodDescriptor();
		} else if (consumer.getOpCode() == RawByteCodes.invokeinterface_opcode) {
			methodDesc = ((InterfaceMethodCallBNode) consumer).getMethodDescriptor();
		} else {
			int methodNumber;

			methodNumber = currentMethodCode[consumer.getAddress() + 1] << 8;
			methodNumber |= (currentMethodCode[consumer.getAddress() + 2] & 0xff);

			methodDesc = toolBox.getPatcher().getMethodDescriptor(methodNumber);
		}
		return methodDesc;
	}

	private int normalizeProducersSize(BNode consumer, int stackCellIndex) throws Exception {
		if (consumerSupported(consumer.getOpCode())) {
			ProducerConsumerStack entryStack = consumer.getAinfo().entryStack;
			ProducerConsumerCellInfo consumedCell = entryStack.getCell(stackCellIndex);
			Iterator<BNode> producers = consumedCell.getProducers().iterator();
			int size = Size.DONTCARE;

			while (producers.hasNext()) {
				int producerSize = getProducerSize(producers.next(), stackCellIndex);
				if (producerSize > size) {
					size = producerSize;
				}
			}
			return size;
		} else {
			return Size.INT;
		}
	}

	private boolean consumerSupported(byte opCode) {
		switch (opCode) {
		case RawByteCodes.fcmpg_opcode:
		case RawByteCodes.fadd_opcode:
		case RawByteCodes.fneg_opcode:
		case RawByteCodes.fsub_opcode:
		case RawByteCodes.fmul_opcode:
		case RawByteCodes.fdiv_opcode:
		case RawByteCodes.fcmpl_opcode:
		case RawByteCodes.lookupswitch_opcode:
		case RawByteCodes.ineg_opcode:
		case RawByteCodes.ishr_opcode:
		case RawByteCodes.ishl_opcode:
		case RawByteCodes.aastore_opcode:
		case RawByteCodes.fastore_opcode:
		case RawByteCodes.bastore_opcode:
		case RawByteCodes.castore_opcode:
		case RawByteCodes.sastore_opcode:
		case RawByteCodes.lastore_opcode:
		case RawByteCodes.iastore_opcode:
		case RawByteCodes.newarray_opcode:
		case RawByteCodes.arraylength_opcode:
		case RawByteCodes.putfield_opcode:
		case RawByteCodes.putstatic_opcode:
		case RawByteCodes.freturn_opcode:
		case RawByteCodes.return_opcode:
		case RawByteCodes.areturn_opcode:
		case RawByteCodes.ireturn_opcode:
		case RawByteCodes.ifeq_opcode:
		case RawByteCodes.ifne_opcode:
		case RawByteCodes.iflt_opcode:
		case RawByteCodes.ifge_opcode:
		case RawByteCodes.ifgt_opcode:
		case RawByteCodes.ifle_opcode:
		case RawByteCodes.if_icmpeq_opcode:
		case RawByteCodes.if_icmpne_opcode:
		case RawByteCodes.if_icmplt_opcode:
		case RawByteCodes.if_icmpge_opcode:
		case RawByteCodes.if_icmpgt_opcode:
		case RawByteCodes.if_icmple_opcode:
		case RawByteCodes.istore_opcode:
		case RawByteCodes.istore_0_opcode:
		case RawByteCodes.istore_1_opcode:
		case RawByteCodes.istore_2_opcode:
		case RawByteCodes.istore_3_opcode:
		case RawByteCodes.fstore_0_opcode:
		case RawByteCodes.fstore_1_opcode:
		case RawByteCodes.fstore_2_opcode:
		case RawByteCodes.fstore_3_opcode:
		case RawByteCodes.invokestatic_opcode:
		case RawByteCodes.invokespecial_opcode:
		case RawByteCodes.invokevirtual_opcode:
		case RawByteCodes.invokeinterface_opcode:
		case RawByteCodes.dup_opcode:
		case RawByteCodes.athrow_opcode:
		case RawByteCodes.astore_0_opcode:
		case RawByteCodes.astore_1_opcode:
		case RawByteCodes.astore_2_opcode:
		case RawByteCodes.astore_3_opcode:
		case RawByteCodes.astore_opcode:
		case RawByteCodes.imul_opcode:
		case RawByteCodes.idiv_opcode:
		case RawByteCodes.iadd_opcode:
		case RawByteCodes.irem_opcode:
		case RawByteCodes.isub_opcode:
		case RawByteCodes.i2b_opcode:
		case RawByteCodes.i2c_opcode:
		case RawByteCodes.if_acmpeq_opcode:
		case RawByteCodes.if_acmpne_opcode:
		case RawByteCodes.instanceof_opcode:
		case RawByteCodes.faload_opcode:
		case RawByteCodes.aaload_opcode:
		case RawByteCodes.baload_opcode:
		case RawByteCodes.caload_opcode:
		case RawByteCodes.saload_opcode:
		case RawByteCodes.iaload_opcode:
		case RawByteCodes.laload_opcode:
		case RawByteCodes.tableswitch_opcode:
			return true;
		}
		return false;
	}

	private int normalizeConsumerSize(BNode consumer, int stackCellIndex) throws Exception {
		byte opCode = consumer.getOpCode();
		if (consumerSupported(opCode)) {
			switch (opCode) {
			case RawByteCodes.fadd_opcode:
			case RawByteCodes.fneg_opcode:
			case RawByteCodes.fsub_opcode:
			case RawByteCodes.fmul_opcode:
			case RawByteCodes.fdiv_opcode:
				return Size.FLOAT;
			case RawByteCodes.fcmpg_opcode:
			case RawByteCodes.fcmpl_opcode:
				return Size.INT;
			case RawByteCodes.lookupswitch_opcode:
			case RawByteCodes.tableswitch_opcode:
				return Size.DONTCARE;
			case RawByteCodes.getfield_opcode:
				return Size.INT;
			case RawByteCodes.ineg_opcode:
				return Size.DONTCARE;
			case RawByteCodes.ishr_opcode:
			case RawByteCodes.iushr_opcode:
			case RawByteCodes.ishl_opcode: {
				ProducerConsumerStack entryStack = consumer.getAinfo().entryStack;
				if (entryStack.size() - 1 == stackCellIndex) {
					return Size.BYTE;
				} else if (entryStack.size() - 1 == stackCellIndex) {
					return Size.DONTCARE;
				}
				break;
			}
			case RawByteCodes.faload_opcode:
			case RawByteCodes.aaload_opcode:
			case RawByteCodes.baload_opcode:
			case RawByteCodes.caload_opcode:
			case RawByteCodes.saload_opcode:
			case RawByteCodes.iaload_opcode:
			case RawByteCodes.laload_opcode: {
				ProducerConsumerStack entryStack = consumer.getAinfo().entryStack;

				if (entryStack.size() - 1 == stackCellIndex) {
					return Size.DONTCARE;
				} else if (entryStack.size() - 2 == stackCellIndex) {
					return Size.INT;
				}
				break;
			}
			case RawByteCodes.aastore_opcode:
			case RawByteCodes.fastore_opcode:
			case RawByteCodes.bastore_opcode:
			case RawByteCodes.castore_opcode:
			case RawByteCodes.sastore_opcode:
			case RawByteCodes.iastore_opcode: {
				ProducerConsumerStack entryStack = consumer.getAinfo().entryStack;

				if (entryStack.size() - 1 == stackCellIndex) {
					switch (opCode) {
					case RawByteCodes.iastore_opcode:
					case RawByteCodes.fastore_opcode:
					case RawByteCodes.aastore_opcode:
					case RawByteCodes.castore_opcode:
						return Size.INT;
					case RawByteCodes.bastore_opcode:
						return Size.BYTE;
					case RawByteCodes.sastore_opcode:
						return Size.SHORT;
					}
				} else if (entryStack.size() - 2 == stackCellIndex) {
					return Size.DONTCARE;
				} else if (entryStack.size() - 3 == stackCellIndex) {
					return Size.INT;
				}
				break;
			}
			case RawByteCodes.lastore_opcode: {
				ProducerConsumerStack entryStack = consumer.getAinfo().entryStack;

				if (entryStack.size() - 1 == stackCellIndex) {
					return Size.INT;
				} else if (entryStack.size() - 2 == stackCellIndex) {
					return Size.INT;
				} else if (entryStack.size() - 3 == stackCellIndex) {
					return Size.DONTCARE;
				} else if (entryStack.size() - 4 == stackCellIndex) {
					return Size.INT;
				}
				break;
			}
			case RawByteCodes.newarray_opcode: {
				return Size.SHORT;
			}
			case RawByteCodes.arraylength_opcode: {
				return Size.INT;
			}
			case RawByteCodes.putstatic_opcode:
			case RawByteCodes.putfield_opcode: {
				if (stackCellIndex == (consumer.getAinfo().entryStack.size() - 1)) {
					short fsize;

					fsize = (short) (currentMethodCode[consumer.getAddress() + 3] & 0xff);

					switch (fsize & 0xfc) {
					case 16:
						return Size.SHORT;
					case 8:
						return Size.BYTE;
					case 32:
					case 64:
					default:
						return Size.INT;
					}
				} else if (stackCellIndex == (consumer.getAinfo().entryStack.size() - 2)) {
					return Size.INT;
				}
			}
				break;
			case RawByteCodes.freturn_opcode:
			case RawByteCodes.return_opcode:
			case RawByteCodes.areturn_opcode:
				return Size.INT;
			case RawByteCodes.ireturn_opcode:
				return getReturntypeSize(javaMethod);
			case RawByteCodes.ifeq_opcode:
			case RawByteCodes.ifne_opcode:
			case RawByteCodes.iflt_opcode:
			case RawByteCodes.ifge_opcode:
			case RawByteCodes.ifgt_opcode:
			case RawByteCodes.ifle_opcode:
				return Size.DONTCARE;
			case RawByteCodes.if_icmpeq_opcode:
			case RawByteCodes.if_icmpne_opcode:
			case RawByteCodes.if_icmplt_opcode:
			case RawByteCodes.if_icmpge_opcode:
			case RawByteCodes.if_icmpgt_opcode:
			case RawByteCodes.if_icmple_opcode: {
				return Size.DONTCARE;
			}
			case RawByteCodes.if_acmpeq_opcode:
			case RawByteCodes.if_acmpne_opcode:
				return Size.INT;
			case RawByteCodes.instanceof_opcode:
				return Size.INT;
			case RawByteCodes.fstore_opcode:
			case RawByteCodes.fstore_0_opcode:
			case RawByteCodes.fstore_1_opcode:
			case RawByteCodes.fstore_2_opcode:
			case RawByteCodes.fstore_3_opcode:
				return Size.INT;
			case RawByteCodes.istore_opcode:
			case RawByteCodes.istore_0_opcode:
			case RawByteCodes.istore_1_opcode:
			case RawByteCodes.istore_2_opcode:
			case RawByteCodes.istore_3_opcode: {
				return getIStoreSize(consumer, opCode);
			}
			case RawByteCodes.invokespecial_opcode:
			case RawByteCodes.invokestatic_opcode:
			case RawByteCodes.invokevirtual_opcode:
			case RawByteCodes.invokeinterface_opcode:
				return getArgumentSize((MethodCallBNode) consumer, stackCellIndex);
			case RawByteCodes.athrow_opcode: {
				ProducerConsumerStack entryStack = consumer.getAinfo().entryStack;
				int top = entryStack.size() - 1;
				while (top >= 0) {
					if (top == stackCellIndex) {
						return Size.BYTE;
					}
					top--;
				}
				throw new Exception("Unexpected stack cell index");
			}
			case RawByteCodes.i2b_opcode:
			case RawByteCodes.i2c_opcode:
				return Size.BYTE;
			case RawByteCodes.idiv_opcode:
			case RawByteCodes.isub_opcode:
			case RawByteCodes.imul_opcode:
			case RawByteCodes.iadd_opcode:
			case RawByteCodes.irem_opcode:
			case RawByteCodes.ior_opcode:
			case RawByteCodes.ixor_opcode:
			case RawByteCodes.iand_opcode:
				return Size.DONTCARE;
			}
		}
		return Size.INT;
	}

	private int getIStoreSize(BNode consumer, byte opCode) {
		int index;
		if (opCode == RawByteCodes.istore_opcode) {
			index = consumer.getRawBytes()[1];
		} else {
			index = opCode - RawByteCodes.istore_0_opcode;
		}
		return getLocalVariableSize(index, consumer.getOriginalAddress() + 1);
	}

	private int getProducerSize(BNode producer, int stackCellIndex) throws Exception {
		switch (producer.getOpCode()) {
		case RawByteCodes.fadd_opcode:
		case RawByteCodes.fneg_opcode:
		case RawByteCodes.fmul_opcode:
		case RawByteCodes.fdiv_opcode:
		case RawByteCodes.fsub_opcode:
			return Size.FLOAT;
		case RawByteCodes.aaload_opcode:
		case RawByteCodes.faload_opcode:
		case RawByteCodes.iaload_opcode:
		case RawByteCodes.laload_opcode:
			return Size.INT;
		case RawByteCodes.baload_opcode:
		case RawByteCodes.caload_opcode:
			return Size.BYTE;
		case RawByteCodes.saload_opcode:
			return Size.SHORT;
		case RawByteCodes.newarray_opcode:
			return Size.INT;
		case RawByteCodes.arraylength_opcode: {
			return Size.SHORT; // In HVM arrays cannot be longer that MAX_SHORT
		}
		case RawByteCodes.getstatic_opcode:
		case RawByteCodes.getfield_opcode: {
			short fsize;

			fsize = (short) (currentMethodCode[producer.getAddress() + 3] & 0xff);

			switch (fsize & 0xfc) {
			case 64:
			case 32:
				return Size.INT;
			case 16:
				return Size.SHORT;
			case 8:
				return Size.BYTE;
			}
			break;
		}
		case RawByteCodes.sipush_opcode:
			return Size.SHORT;
		case RawByteCodes.instanceof_opcode:
			return Size.BYTE;
		case RawByteCodes.fload_opcode:
		case RawByteCodes.fload_0_opcode:
		case RawByteCodes.fload_1_opcode:
		case RawByteCodes.fload_2_opcode:
		case RawByteCodes.fload_3_opcode:
			return Size.FLOAT;
		case RawByteCodes.iload_opcode:
		case RawByteCodes.iload_0_opcode:
		case RawByteCodes.iload_1_opcode:
		case RawByteCodes.iload_2_opcode:
		case RawByteCodes.iload_3_opcode: {
			int index;
			if (producer.getOpCode() == RawByteCodes.iload_opcode) {
				index = producer.getRawBytes()[1];
			} else {
				index = producer.getOpCode() - RawByteCodes.iload_0_opcode;
			}
			if (producer.getAinfo().exitStack.size() == (stackCellIndex + 1)) {
				return getLocalVariableSize(index, producer.getOriginalAddress());
			}
			break;
		}
		case RawByteCodes.iconst_0_opcode:
		case RawByteCodes.iconst_1_opcode:
		case RawByteCodes.iconst_2_opcode:
		case RawByteCodes.iconst_3_opcode:
		case RawByteCodes.iconst_4_opcode:
		case RawByteCodes.iconst_5_opcode:
		case RawByteCodes.iconst_m1_opcode:
		case RawByteCodes.bipush_opcode:
			if (producer.getAinfo().exitStack.size() == (stackCellIndex + 1)) {
				return Size.BYTE;
			}
			break;
		case RawByteCodes.invokevirtual_opcode:
		case RawByteCodes.invokespecial_opcode:
		case RawByteCodes.invokeinterface_opcode:
		case RawByteCodes.invokestatic_opcode: {
			MethodCallBNode methodCall = (MethodCallBNode) producer;
			Method referredMethod = getCalleeMethodFromBNode(methodCall);

			int size = getReturntypeSize(referredMethod);
			if (size <= Size.INT) {
				if (producer.getAinfo().exitStack.size() == (stackCellIndex + 1)) {
					return size;
				}
			} else {
				if ((producer.getAinfo().exitStack.size() == (stackCellIndex + 1))
						|| (producer.getAinfo().exitStack.size() == (stackCellIndex + 2))) {
					return Size.INT;
				}
			}
			break;
		}
		case RawByteCodes.athrow_opcode:
			return Size.INT;
		case RawByteCodes.ior_opcode:
		case RawByteCodes.ixor_opcode:
		case RawByteCodes.iushr_opcode:
		case RawByteCodes.iand_opcode:
		case RawByteCodes.irem_opcode:
		case RawByteCodes.imul_opcode:
		case RawByteCodes.ishr_opcode:
		case RawByteCodes.ishl_opcode:
		case RawByteCodes.isub_opcode:
		case RawByteCodes.idiv_opcode:
		case RawByteCodes.iadd_opcode: {
			ProducerConsumerStack exitStack = producer.getAinfo().exitStack;
			int dstSize = normalizeConsumerSize(exitStack.peek().getConsumer(), exitStack.size() - 1);
			if (dstSize == Size.DONTCARE) {
				dstSize = Size.INT;
			}
			return dstSize;
		}
		case RawByteCodes.i2b_opcode:
		case RawByteCodes.i2c_opcode:
			return Size.BYTE;
		default:
			return Size.INT;
		}
		return Size.INT;
	}

	private int getLocalVariableSize(int index, int pc) {
		LocalVariableTable localVariableTable = javaMethod.getLocalVariableTable();
		if (localVariableTable != null) {
			LocalVariable var = localVariableTable.getLocalVariable(index, pc);
			if (var != null) {
				return getVariableSize(var);
			} else {
				LocalVariable[] variables = localVariableTable.getLocalVariableTable();
				int size = Size.DONTCARE;
				for (int i = 0; i < variables.length; i++) {
					var = variables[i];
					if (var.getIndex() == index) {
						int currentSize = getVariableSize(var);
						if (currentSize > size) {
							size = currentSize;
						}
					}
				}
				if (size == Size.DONTCARE) {
					size = Size.INT;
				}
				return size;
			}
		}
		return Size.INT;
	}

	public static int getVariableSize(LocalVariable var) {
		String sig = Utility.signatureToString(var.getSignature());
		if (sig.equals("byte")) {
			return Size.BYTE;
		} else if (sig.equals("short")) {
			return Size.SHORT;
		} else if (sig.equals("boolean")) {
			return Size.BYTE;
		} else if (sig.equals("float")) {
			return Size.FLOAT;
		} else {
			return Size.INT;
		}
	}

	private void adjustArrayPointer(StringBuffer output, int elementSize, String obj, String index) {
		switch (elementSize) {
		case 1:
			output.append("      " + obj + " = " + obj + " + sizeof(Object) + 2 + " + index + ";\n");
			break;
		case 2:
			output.append("      " + obj + " = " + obj + " + sizeof(Object) + 2 + (" + index + " << 1);\n");
			break;
		case 4:
			output.append("      " + obj + " = " + obj + " + sizeof(Object) + 2 + (" + index + " << 2);\n");
			break;
		case 8:
			output.append("      " + obj + " = " + obj + " + sizeof(Object) + 2 + (" + index + " << 3);\n");
			break;
		}
	}

	private int getElementSize(int pc) {
		int elementSize = 0;
		switch (currentMethodCode[pc]) {
		case RawByteCodes.faload_opcode:
		case RawByteCodes.fastore_opcode:
			elementSize = 4;
			break;
		case RawByteCodes.aaload_opcode:
		case RawByteCodes.aastore_opcode:
			elementSize = 4;
			break;
		case RawByteCodes.baload_opcode:
		case RawByteCodes.bastore_opcode:
			elementSize = 1;
			break;
		case RawByteCodes.caload_opcode:
		case RawByteCodes.castore_opcode:
			elementSize = 4;
			break;
		case RawByteCodes.saload_opcode:
		case RawByteCodes.sastore_opcode:
			elementSize = 2;
			break;
		case RawByteCodes.laload_opcode:
		case RawByteCodes.lastore_opcode:
			elementSize = 8;
			break;
		case RawByteCodes.iaload_opcode:
		case RawByteCodes.iastore_opcode:
			elementSize = 4;
			break;
		}
		return elementSize;
	}

	private void handleExceptionOccurred(StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc,
			LabelsManager labelsManager, String returnValueString, String indent, StackManager sm,
			MethodOrFieldDesc methodDesc, String exceptionVariable, MethodEntryPoints entrypoints,
			String noExceptionCondition, boolean negateExceptionValue) throws Exception {
		boolean testIt = false;

		if (methodDesc != null) {
			if (mayThrowExceptions(methodDesc.getClassName(), methodDesc.getName(), methodDesc.getSignature())) {
				testIt = true;
			}
		} else {
			testIt = true;
		}

		if (testIt) {
			output.append(indent + "   if (" + noExceptionCondition + ") {\n");
		}

		if (returnValueString != null) {
			output.append(indent + returnValueString);
			setSPUsed(true);
		} else {
			if (testIt) {
				output.append(indent + "      ;\n");
			}
		}

		if (testIt) {
			output.append(indent + "   }\n");
			output.append(indent + "   else\n");
			output.append(indent + "   {\n");
			if (negateExceptionValue) {
				output.append(indent + "   " + exceptionVariable + " = -" + exceptionVariable + ";\n");
			}
			handleIt(output, localVariables, pc, labelsManager, indent, sm, exceptionVariable, entrypoints);
			output.append(indent + "   }\n");
		}
	}

	protected boolean mayThrowExceptions(String className, String targetMethodName, String targetMethodSignature) {
		return true;
	}

	private void handleIt(StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc,
			LabelsManager labelsManager, String indent, StackManager sm, String exceptionVariable,
			MethodEntryPoints entrypoints) throws Exception {
		setSPUsed(true);
		if (hasExceptionHandlers(javaMethod)) {
			output.append(indent + "      sp++;\n");
			output.append(indent + "      pc = " + pc + ";\n");
			labelsManager.jumpTo(sm, true);
			output.append(indent + "      excep = " + exceptionVariable + ";\n");
			output.append(indent + "      goto " + LabelsManager.LThrowIt + ";\n");

			localVariables.print("   unsigned short pc;\n");
			labelsManager.generateThrowIt();
		} else {
			output.append(indent + "      fp[0] = *sp;\n");
			if (entrypoints.useCombinedReturnType()) {
				exceptionVariable = "-" + exceptionVariable;
			}
			output.append(generateReturnStatement(indent + "      return " + exceptionVariable + ";\n"));
		}
	}

	private void adjustStackAndCheckObject(StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc,
			LabelsManager labelsManager, int numArgs, String getObjectInfo, String indent, StackManager sm)
			throws Exception {
		output.append(indent + "   sp -= " + numArgs + ";\n");
		setSPUsed(true);

		if (currentMethodCode[pc] != RawByteCodes.invokestatic_opcode) {
			boolean checkIt = nullPointerCheckRequired(pc, numArgs);

			if (checkIt) {
				output.append(indent + "   i_obj = (Object*) (pointer) *sp;\n");
				checkObject(output, localVariables, pc, labelsManager, getObjectInfo, indent, sm, "i_obj");
				localVariables.print("   Object* i_obj;\n");
			} else if ((getObjectInfo != null) && (getObjectInfo.trim().length() > 0)) {
				output.append(indent + "   i_obj = (Object*) (pointer) *sp;\n");
				output.append(indent + getObjectInfo);
				localVariables.print("   Object* i_obj;\n");
			}
		}
	}

	protected boolean nullPointerCheckRequired(int pc, int numArgs) {
		BNode bnode = (BNode) toolBox.getDependencyExtent().getBNodeFromMethod(toolBox.getCurrentClassName(),
				javaMethod.getName(), javaMethod.getSignature(), pc);
		return nullPointerCheckRequired(numArgs, bnode);
	}

	public static boolean nullPointerCheckRequired(int numArgs, BNode bnode) {
		AbstractStack stackLayout = bnode.getStackLayout();
		StackCell cell = stackLayout.getAt(stackLayout.getSize() - numArgs);

		boolean checkIt = true;

		if (cell.content instanceof RefType) {
			if (((RefType) cell.content).getState() == RefState.NONNULL) {
				checkIt = false;
			}
		}
		return checkIt;
	}

	static void checkObject(StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc,
			LabelsManager labelsManager, String getObjectInfo, String indent, StackManager sm, String obj)
			throws Exception {
		output.append(indent + "      if (" + obj + " == 0) {\n");
		output.append(indent + "         pc = " + pc + ";\n");
		labelsManager.jumpTo(sm);
		output.append(indent + "         goto " + LabelsManager.LThrowNullPointer + ";\n");
		output.append(indent + "      }\n");
		if (getObjectInfo != null) {
			output.append(indent + getObjectInfo);
		}

		labelsManager.generateThrowNullPointer();
		localVariables.print("   unsigned short pc;\n");
	}

	public static boolean interpretMethod(Method referredMethod, MethodOrFieldDesc methodDesc,
			ICompilationRegistry registry) {
		if ((referredMethod.getCode() != null) && (!Compiler.compileMethod(registry, referredMethod, methodDesc))) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean hasExceptionHandlers(Method javaMethod) {
		Code codeAttr = javaMethod.getCode();
		if (codeAttr.getExceptionTable().length > 0) {
			return true;
		}
		return false;
	}

	private static boolean isExceptionHandler(Method javaMethod, int pc) {
		Code codeAttr = javaMethod.getCode();
		if (codeAttr != null) {
			CodeException[] handlers = codeAttr.getExceptionTable();
			for (CodeException codeException : handlers) {
				if (codeException.getHandlerPC() == pc) {
					return true;
				}
			}
		}
		return false;
	}

	private int handleOneArgBranch(StackManager sm, byte[] currentMethodCode, int pc, StringBuffer output, BNode bnode,
			NoDuplicatesMemorySegment localVariables) throws Exception {
		short code = (short) (currentMethodCode[pc] & 0xff);

		int size = normalizeProducersSize(bnode, bnode.getAinfo().entryStack.size() - 1);

		String type = getTypeCast(size);

		String comparison = null;
		String operator = "";
		String left;

		if (sm.isCached(0)) {
			left = sm.peekTop(1, size);
			sm.pop(null, size);
		} else {
			localVariables.print("   " + type + " msb_" + type + ";\n");
			sm.pop("      msb_" + type + "", size);
			left = "msb_" + type + "";
		}

		switch ((byte) code) {
		case RawByteCodes.ifnull_opcode:
		case RawByteCodes.ifeq_opcode:
			operator = "==";
			break;
		case RawByteCodes.ifnonnull_opcode:
		case RawByteCodes.ifne_opcode:
			operator = "!=";
			break;
		case RawByteCodes.iflt_opcode:
			operator = "<";
			break;
		case RawByteCodes.ifge_opcode:
			operator = ">=";
			break;
		case RawByteCodes.ifgt_opcode:
			operator = ">";
			break;
		case RawByteCodes.ifle_opcode:
			operator = "<=";
			break;
		}

		comparison = "(" + left + " " + operator + " 0)";
		output.append("      if " + comparison + " {\n");
		short branchbyte1 = (short) (currentMethodCode[pc + 1] & 0xff);
		short branchbyte2 = (short) (currentMethodCode[pc + 2] & 0xff);
		short offset = (short) ((branchbyte1 << 8) | branchbyte2);
		yield(output, offset);
		handleBranch(sm, currentMethodCode[pc], pc, pc + offset, false);
		output.append("         goto L" + (pc + offset) + ";\n");
		output.append("      }\n");
		return 3;
	}

	protected void yield(StringBuffer output, short offset) {
		if (offset <= 0) {
			if (toolBox.getObserver().isMethodUsed("vm.ClockInterruptHandler", "enable", "()V")) {
				String pp;
				if (spUsed) {
					pp = "sp";
				} else {
					int stackOffset = Compiler.getMaxLocals(javaMethod.getCode());
					pp = "&fp[" + (stackOffset + 2) + "]";
				}
				output.append("   yieldToScheduler(" + pp + ");\n");
				requiredIncludes.print("extern int16 yieldToScheduler(int32 *sp);\n");
			}
		}
	}

	private int handleTwoArgBranch(StackManager sm, byte[] currentMethodCode, int pc, StringBuffer output, BNode bnode,
			NoDuplicatesMemorySegment localVariables) throws Exception {
		short code = (short) (currentMethodCode[pc] & 0xff);

		String comparison = null;

		int topSize = normalizeProducersSize(bnode, bnode.getAinfo().entryStack.size() - 1);
		int topm1Size = normalizeProducersSize(bnode, bnode.getAinfo().entryStack.size() - 2);

		String lsbType = getTypeCast(topSize);
		String msbType = getTypeCast(topm1Size);

		String left;
		String right;
		String operator = "";

		if (sm.isCached(0) && sm.isCached(1)) {
			right = sm.peekTop(1, topSize);
			left = sm.peekTop(2, topm1Size);

			sm.pop(null, topSize);
			sm.pop(null, topSize);
		} else {

			localVariables.print("   " + lsbType + " lsb_" + lsbType + ";\n");
			localVariables.print("   " + msbType + " msb_" + msbType + ";\n");

			sm.pop("      lsb_" + lsbType + "", topSize);
			sm.pop("      msb_" + msbType + "", topm1Size);

			left = "msb_" + msbType + "";
			right = "lsb_" + lsbType + "";
		}

		switch ((byte) code) {
		case RawByteCodes.if_icmpeq_opcode:
			operator = "==";
			break;
		case RawByteCodes.if_icmpne_opcode:
			operator = "!=";
			break;
		case RawByteCodes.if_icmplt_opcode:
			operator = "<";
			break;
		case RawByteCodes.if_icmpge_opcode:
			operator = ">=";
			break;
		case RawByteCodes.if_icmpgt_opcode:
			operator = ">";
			break;
		case RawByteCodes.if_icmple_opcode:
			operator = "<=";
			break;
		}

		comparison = "(" + left + " " + operator + " " + right + ")";

		output.append("      if " + comparison + " {\n");
		short branchbyte1 = (short) (currentMethodCode[pc + 1] & 0xff);
		short branchbyte2 = (short) (currentMethodCode[pc + 2] & 0xff);
		short offset = (short) ((branchbyte1 << 8) | branchbyte2);
		yield(output, offset);
		handleBranch(sm, currentMethodCode[pc], pc, pc + offset, false);
		output.append("         goto L" + (pc + offset) + ";\n");
		output.append("      }\n");
		return 3;
	}

	@Override
	public String getCurrentMethod() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("class ");
		buffer.append(toolBox.getCurrentClassName());
		buffer.append(": ");
		buffer.append(this.javaMethod.getName());
		buffer.append("(");
		buffer.append(this.javaMethod.getSignature());
		buffer.append(") - ");
		buffer.append(bnode.toString());
		return buffer.toString();
	}

	private class AOTStaticFieldEmitter extends StaticFieldEmitter {
		public AOTStaticFieldEmitter(int pc, AOTToolBox toolBox, byte[] currentMethodCode,
				NoDuplicatesMemorySegment localVariables, StringBuffer output, StackManager sm,
				NoDuplicatesMemorySegment requiredIncludes, BNode bnode) {
			super(pc, toolBox, currentMethodCode, localVariables, output, sm, requiredIncludes, bnode);
		}

		@Override
		protected void a_addUserIncludes(NoDuplicatesMemorySegment requiredIncludes, String includes) {
			addUserIncludes(requiredIncludes, includes);

		}

		@Override
		protected int a_normalizeConsumerSize(BNode bnode, int i) throws Exception {
			return normalizeConsumerSize(bnode, i);
		}
	}

	private class AOTInvokeSpecialEmitter extends InvokeSpecialEmitter {
		AOTInvokeSpecialEmitter(StackManager sm, StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc,
				LabelsManager labelsManager, BNode bnode, byte[] currentMethodCode,
				NoDuplicatesMemorySegment requiredIncludes, MethodOrFieldDesc methodDesc, AOTToolBox toolBox,
				MethodEntryPoints entrypoints) {
			super(sm, output, localVariables, pc, labelsManager, bnode, currentMethodCode, requiredIncludes,
					methodDesc, toolBox, entrypoints);
		}

		@Override
		protected void a_setSPUsed(boolean b) {
			setSPUsed(b);
		}

		@Override
		protected boolean a_mayThrowExceptions(String className, String name, String signature) {
			return mayThrowExceptions(className, name, signature);
		}

		@Override
		protected int a_normalizeProducersSize(BNode bnode, int i) throws Exception {
			return normalizeProducersSize(bnode, i);
		}

		@Override
		protected void a_adjustStackAndCheckObject(StringBuffer output, NoDuplicatesMemorySegment localVariables,
				int pc, LabelsManager labelsManager, int numArgs, String getObjectInfo, String string, StackManager sm)
				throws Exception {
			adjustStackAndCheckObject(output, localVariables, pc, labelsManager, numArgs, getObjectInfo, string, sm);
		}

		@Override
		protected void a_handleExceptionOccurred(StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc,
				LabelsManager labelsManager, String returnValueString, String string, StackManager smCopy,
				MethodOrFieldDesc methodDesc, String exceptionVariable, MethodEntryPoints entrypoints,
				String noExceptionCondition, boolean negateExceptionValue) throws Exception {
			handleExceptionOccurred(output, localVariables, pc, labelsManager, returnValueString, string, smCopy,
					methodDesc, exceptionVariable, entrypoints, noExceptionCondition, negateExceptionValue);
		}
	}
}
