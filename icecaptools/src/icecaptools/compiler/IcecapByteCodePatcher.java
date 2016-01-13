package icecaptools.compiler;

import icecaptools.AnalysisObserver;
import icecaptools.BNode;
import icecaptools.ClassfileUtils;
import icecaptools.DynamicMethodCallVirtualMethodBNode;
import icecaptools.FieldOffsetCalculator;
import icecaptools.IcecapIterator;
import icecaptools.InterfaceMethodCallBNode;
import icecaptools.JavaArrayClass;
import icecaptools.MethodCallBNode;
import icecaptools.MethodOrFieldDesc;
import icecaptools.NewArrayBNode;
import icecaptools.NewArrayMultiBNode;
import icecaptools.RawByteCodes;
import icecaptools.VirtualMethodCallBNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class IcecapByteCodePatcher implements ByteCodePatcher {
    private static final byte INVOKE_CLONE_ONARRAY = (byte) 0xCA;
    private static final byte GETHWFIELD_OPCODE = (byte) 0xCB;
    private static final byte PUTHWFIELD_OPCODE = (byte) 0xCC;
    private static final byte NEWFLASHARRAY_OPCODE = (byte) 0xCD;

    private HashMap<String, List<BNode>> registeredBytecodes;

    private static class MethodAndNumber {
        public MethodOrFieldDesc desc;
        public int number;

        public MethodAndNumber(MethodOrFieldDesc desc, int number) {
            this.desc = desc;
            this.number = number;
        }
    }

    private HashMap<String, Integer> classNumbers;
    private HashMap<String, MethodAndNumber> methodNumbers;
    private HashMap<String, FieldInfo> objectFields;
    private HashMap<String, FieldInfo> classFields;

    private InterfaceManager iManager;

    private ArrayList<LDCConstant> constants;

    private int numberOfClasses;

    private RequiredMethodVTableIndexManager vtableIndexManager;
    private FieldOffsetCalculator foCalc;

    public IcecapByteCodePatcher(AnalysisObserver observer, IDGenerator idGen, FieldOffsetCalculator foCalc, boolean supportLoading) {
        methodNumbers = new HashMap<String, MethodAndNumber>();
        classNumbers = new HashMap<String, Integer>();
        objectFields = new HashMap<String, FieldInfo>();
        classFields = new HashMap<String, FieldInfo>();

        iManager = new InterfaceManager(observer);
        constants = new ArrayList<LDCConstant>();
        numberOfClasses = 0;

        registeredBytecodes = new HashMap<String, List<BNode>>();
        vtableIndexManager = new RequiredMethodVTableIndexManager(idGen, supportLoading);
        this.foCalc = foCalc;
    }

    @Override
    public void patch(String className, String methodName, String methodSignature, byte[] methodCode, AnalysisObserver observer, IDGenerator idGen) throws Exception {
        String key = createMethodID(className, methodName, methodSignature);
        patchNew(className, methodCode, key);
        patchInvokeVirtual(className, methodCode, key, observer, idGen);
        patchCheckcast(className, methodCode, key);
        patchINSTANCEOF(className, methodCode, key);
        patchInvokeStatic(className, methodCode, key, methodName, idGen);
        patchInvokeSpecial(className, methodCode, key, methodName, idGen);
        patchInvokeInterface(className, methodCode, key, methodName, observer, idGen);
        patchPutGetField(className, methodCode, key, observer);
        patchLDC(className, methodCode, key);
        patchNEWARRAYS(className, methodCode, key, methodName);
        patchFLASHARRAYS(className, methodCode, key, methodName, observer);
        patchInvokeDynamic(className, methodCode, key, methodName, observer);
    }

    private void patchInvokeDynamic(String className, byte[] methodCode, String key, String methodName, AnalysisObserver observer) {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, new byte[] { RawByteCodes.invokedynamic_opcode });
        while (locationIterator.hasNext()) {
            BNode nextNode = locationIterator.next();
            if (nextNode instanceof DynamicMethodCallVirtualMethodBNode) {
                DynamicMethodCallVirtualMethodBNode virtualMethodcallType = (DynamicMethodCallVirtualMethodBNode) nextNode;
                int location = nextNode.getAddress();
                String lambdaClassName = virtualMethodcallType.getLambdaClassName();

                if (classNumbers.containsKey(lambdaClassName)) {
                    int referredIndex = classNumbers.get(lambdaClassName).intValue();
                    referredIndex = referredIndex << 1;
                    methodCode[location + 1] = (byte) ((referredIndex >> 8) & 0xff);
                    methodCode[location + 2] = (byte) (referredIndex & 0xff);
                }
            }
        }
    }

    private void patchNEWARRAYS(String className, byte[] methodCode, String key, String methodName) throws Exception {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, new byte[] { RawByteCodes.anewarray_opcode, RawByteCodes.newarray_opcode, RawByteCodes.multianewarray_opcode });
        while (locationIterator.hasNext()) {
            BNode nextNode = locationIterator.next();
            int location = nextNode.getAddress();
            if (methodCode[location] == RawByteCodes.newarray_opcode) {
                int aType = methodCode[location + 1];
                patchBasicArrayType(methodCode, location, aType);
            } else if (methodCode[location] == RawByteCodes.anewarray_opcode) {
                patchReferenceArrayType(className, methodCode, location);
            } else if (methodCode[location] == RawByteCodes.multianewarray_opcode) {
                NewArrayMultiBNode multiBNode = (NewArrayMultiBNode) nextNode;
                if (multiBNode.isBasicType()) {
                    int aType = multiBNode.getBasicType();
                    patchBasicArrayType(methodCode, location, aType);
                } else {
                    patchReferenceArrayType(className, methodCode, location);
                }
                methodCode[location + 3] = (byte) multiBNode.getDimension();
            }
        }
    }

    protected void patchReferenceArrayType(String className, byte[] methodCode, int location) throws Exception {
        byte indexByte1 = methodCode[location + 1];
        byte indexByte2 = methodCode[location + 2];
        String referredClassName = ClassfileUtils.getClassName(className, indexByte1 & 0xff, indexByte2 & 0xff);
        Integer classIndex = classNumbers.get("[L" + referredClassName + ";");
        if (classIndex != null) {
            int index = classIndex.intValue();
            methodCode[location + 1] = (byte) ((index >> 8) & 0xff);
            methodCode[location + 2] = (byte) (index & 0xff);
        } else {
            StringBuffer buffer = classNumberNotFound(referredClassName);
            throw new Exception(buffer.toString());
        }
    }

    protected void patchBasicArrayType(byte[] methodCode, int location, int aType) throws Exception {
        String basicClassName = NewArrayBNode.getBasicTypeAsString(aType);
        Integer classIndex = classNumbers.get(basicClassName);
        if (classIndex != null) {
            int index = classIndex.intValue();
            methodCode[location + 1] = (byte) ((index >> 8) & 0xff);
            methodCode[location + 2] = (byte) (index & 0xff);
        } else {
            StringBuffer buffer = classNumberNotFound(basicClassName);
            throw new Exception(buffer.toString());
        }
    }

    private void patchFLASHARRAYS(String className, byte[] methodCode, String key, String methodName, AnalysisObserver observer) throws Exception {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, new byte[] { RawByteCodes.anewarray_opcode, RawByteCodes.newarray_opcode });
        while (locationIterator.hasNext()) {
            BNode newarray = locationIterator.next();
            int location = newarray.getAddress();
            if (methodCode[location] == RawByteCodes.newarray_opcode) {
                if (((NewArrayBNode) newarray).isFlashArray()) {
                    methodCode[location] = NEWFLASHARRAY_OPCODE;
                    observer.byteCodeUsed(NEWFLASHARRAY_OPCODE);
                }
            } else {
                // Not implemented
            }
        }
    }

    private StringBuffer classNumberNotFound(String basicClassName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(basicClassName);
        buffer.append(" not found in list of instantiated classes:\n");
        Iterator<String> iterator = classNumbers.keySet().iterator();
        while (iterator.hasNext()) {
            buffer.append(iterator.next());
            if (iterator.hasNext()) {
                buffer.append(", ");
            }
        }
        return buffer;
    }

    private void patchLDC(String className, byte[] methodCode, String key) throws ClassNotFoundException {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, new byte[] { RawByteCodes.ldc_opcode, RawByteCodes.ldc_w_opcode, RawByteCodes.ldc2_w_opcode });
        while (locationIterator.hasNext()) {
            int location = locationIterator.next().getAddress();
            int index;
            byte indexByte1 = methodCode[location + 1];
            byte indexByte2 = 0;
            if ((methodCode[location] == RawByteCodes.ldc_w_opcode) || (methodCode[location] == RawByteCodes.ldc2_w_opcode)) {
                indexByte2 = methodCode[location + 2];
                index = RawByteCodes.RawBytecode.bitwiseOr(RawByteCodes.RawBytecode.bitLeftShift(indexByte1 & 0xff, 8), indexByte2 & 0xff);
            } else {
                index = indexByte1 & 0xff;
            }

            LDCConstant constant = ClassfileUtils.getLDCConstant(className, index);

            index = constants.indexOf(constant);
            if (index == -1) {
                constants.add(constant);
            }
            index = constants.indexOf(constant);
            methodCode[location + 1] = (byte) ((index >> 8) & 0xff);
            methodCode[location + 2] = (byte) (index & 0xff);
        }
    }

    private void patchInvokeInterface(String className, byte[] methodCode, String key, String methodName, AnalysisObserver observer, IDGenerator idGen) throws Exception {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, new byte[] { RawByteCodes.invokeinterface_opcode });
        while (locationIterator.hasNext()) {
            InterfaceMethodCallBNode nextLocation = (InterfaceMethodCallBNode) locationIterator.next();
            int location = nextLocation.getAddress();
            int interfaceIndex;
            byte indexByte2;
            interfaceIndex = methodCode[location + 1];
            indexByte2 = methodCode[location + 2];
            MethodOrFieldDesc referredInterfaceMethod = ClassfileUtils.getMethodDesc(className, interfaceIndex & 0xff, indexByte2 & 0xff);

            JavaClass declaringclazz = ClassfileUtils.findDeclaringInterface(referredInterfaceMethod.getClassName(), referredInterfaceMethod.getName(), referredInterfaceMethod.getSignature()).getClazz();

            referredInterfaceMethod.setClassName(declaringclazz.getClassName());

            nextLocation.setMethodDescriptor(referredInterfaceMethod);

            interfaceIndex = (byte) iManager.getInterfaceIndex(referredInterfaceMethod.getClassName());

            if (interfaceIndex != -1) {
                indexByte2 = (byte) iManager.getInterfaceMethodIndex(referredInterfaceMethod.getClassName(), referredInterfaceMethod.getName(), referredInterfaceMethod.getSignature());

                int numArgs;

                try {
                    numArgs = ClassfileUtils.getNumArgs(referredInterfaceMethod);
                } catch (Exception ex) {
                    throw new Exception("Could not find method [" + referredInterfaceMethod.toString() + "]");
                }

                methodCode[location + 1] = (byte) ((interfaceIndex >> 8) & 0xff);
                methodCode[location + 2] = indexByte2;
                methodCode[location + 3] = (byte) numArgs;
                methodCode[location + 4] = (byte) (interfaceIndex & 0xff);

                byte[] jumpTable = nextLocation.getJumpTable(referredInterfaceMethod, this, idGen);
                for (int i = 0; i < jumpTable.length; i++) {
                    methodCode[location + 5 + i] = jumpTable[i];
                }
                observer.reportVtableSize(jumpTable[0]);
            } else {
                int numArgs;

                try {
                    numArgs = ClassfileUtils.getNumArgs(referredInterfaceMethod);
                } catch (Exception ex) {
                    throw new Exception("Could not find method [" + referredInterfaceMethod.toString() + "]");
                }
                methodCode[location + 1] = (byte) (-1);
                methodCode[location + 2] = (byte) (-1);
                methodCode[location + 3] = (byte) numArgs;
                methodCode[location + 4] = (byte) (-1);

                System.out.println(className + ":" + methodName + ":" + location + ": Interface '" + referredInterfaceMethod.getClassName() + "' might be invoked but is never instantiated");
            }
        }
    }

    private void patchInvokeVirtual(String className, byte[] methodCode, String key, AnalysisObserver observer, IDGenerator idGen) throws Exception {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, new byte[] { RawByteCodes.invokevirtual_opcode });
        List<BNode> convertedNodes = new LinkedList<BNode>();

        while (locationIterator.hasNext()) {
            MethodCallBNode nextLocation = (MethodCallBNode) locationIterator.next();
            int location = nextLocation.getAddress();

            byte indexByte1, indexByte2;
            indexByte1 = methodCode[location + 2];
            indexByte2 = methodCode[location + 3];
            MethodOrFieldDesc referredMethod = ClassfileUtils.getMethodDesc(className, indexByte1 & 0xff, indexByte2 & 0xff);

            nextLocation.setMethodDescriptor(referredMethod);

            short methodVtableIndex = VirtualTable.getVTableIndex(referredMethod, observer);

            vtableIndexManager.registerEntry(idGen.getUniqueId(referredMethod.getClassName(), referredMethod.getName(), referredMethod.getSignature()), methodVtableIndex);
            if (JavaArrayClass.isArrayClass(referredMethod.getClassName()) && referredMethod.getName().equals("clone") && referredMethod.getSignature().equals("()Ljava/lang/Object;")) {
                methodCode[location] = INVOKE_CLONE_ONARRAY;
                observer.byteCodeUsed(INVOKE_CLONE_ONARRAY);
            } else {
                VirtualMethodCallBNode methodCall = (VirtualMethodCallBNode) nextLocation;
                if (methodCall.getNumberOfTargets() == 1) {
                    methodCode[location] = RawByteCodes.invokespecial_opcode;
                    observer.byteCodeUsed(RawByteCodes.invokespecial_opcode);
                    methodCall.getRawBytes()[0] = RawByteCodes.invokespecial_opcode;
                    referredMethod = new MethodOrFieldDesc(methodCall.getTargetClassName(), methodCall.getMethodName(), methodCall.getMethodSig());
                    patchStaticOrSpecial(className, methodCode, methodCall.getMethodName(), location, referredMethod, idGen);
                    convertedNodes.add(nextLocation);
                } else {
                    int numArgs;

                    numArgs = ClassfileUtils.getNumArgs(referredMethod);

                    Method callee = ClassfileUtils.findMethod(referredMethod.getClassName(), referredMethod.getName(), referredMethod.getSignature()).getMethod();
                    methodCall.setCallee(callee);

                    if (methodVtableIndex > -1) {
                        methodCode[location + 1] = (byte) (Compiler.getNumReturnValues(callee) & 0x3);
                        methodCode[location + 2] = (byte) (methodVtableIndex & 0xff);
                        methodCode[location + 3] = (byte) (numArgs);
                    } else {
                        methodCode[location + 1] = 0;
                        methodCode[location + 2] = (byte) (-1);
                        methodCode[location + 3] = (byte) (numArgs);
                        System.out.println("Unable to patch invoke of " + referredMethod.toString());
                    }
                    byte[] jumpTable = methodCall.getJumpTable(referredMethod, this, idGen);
                    for (int i = 0; i < jumpTable.length; i++) {
                        methodCode[location + 4 + i] = jumpTable[i];
                    }
                    observer.reportVtableSize(jumpTable[0]);
                }
            }
        }

        Iterator<BNode> nodesToDelete = convertedNodes.iterator();
        while (nodesToDelete.hasNext()) {
            deleteRegistrant(key, nodesToDelete.next());
        }
    }

    private void patchInvokeStaticOrSpecial(byte[] codes, String className, byte[] methodCode, String key, String methodName, IDGenerator idGen) throws Exception {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, codes);
        while (locationIterator.hasNext()) {
            int location = locationIterator.next().getAddress();

            byte indexByte1, indexByte2;
            indexByte1 = methodCode[location + 2];
            indexByte2 = methodCode[location + 3];
            MethodOrFieldDesc referredMethod = ClassfileUtils.getMethodDesc(className, indexByte1 & 0xff, indexByte2 & 0xff);

            JavaClass declaringclazz = ClassfileUtils.findMethod(referredMethod.getClassName(), referredMethod.getName(), referredMethod.getSignature()).getClazz();

            referredMethod.setClassName(declaringclazz.getClassName());
            patchStaticOrSpecial(className, methodCode, methodName, location, referredMethod, idGen);
        }
    }

    private void patchStaticOrSpecial(String className, byte[] methodCode, String methodName, int location, MethodOrFieldDesc referredMethod, IDGenerator idGen) throws Exception {
        int methodNumber = getMethodNumber(referredMethod, idGen);

        if (methodNumber != -1) {
            methodCode[location + 1] = (byte) ((methodNumber >> 8) & 0xff);
            methodCode[location + 2] = (byte) (methodNumber & 0xff);
        } else {
            System.out.println(className + ":" + methodName + ":" + location + ": Method '" + referredMethod + "' might be invoked but class is never instantiated");
        }
    }

    private void patchInvokeStatic(String className, byte[] methodCode, String key, String methodName, IDGenerator idGen) throws Exception {
        patchInvokeStaticOrSpecial(new byte[] { RawByteCodes.invokestatic_opcode }, className, methodCode, key, methodName, idGen);
    }

    private void patchInvokeSpecial(String className, byte[] methodCode, String key, String methodName, IDGenerator idGen) throws Exception {
        patchInvokeStaticOrSpecial(new byte[] { RawByteCodes.invokespecial_opcode }, className, methodCode, key, methodName, idGen);
    }

    private void patchClassreference(byte[] opcodes, String className, byte[] methodCode, String key) throws Exception {
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, opcodes);
        while (locationIterator.hasNext()) {
            int location = locationIterator.next().getAddress();
            byte indexByte1, indexByte2;
            indexByte1 = methodCode[location + 1];
            indexByte2 = methodCode[location + 2];
            String referredClassName = ClassfileUtils.getClassName(className, indexByte1 & 0xff, indexByte2 & 0xff);
            boolean isInterface = false;
            int referredIndex = -1;

            if ((referredClassName == null) || JavaArrayClass.isArrayClass(referredClassName)) {
                /*
                 * Arrays of basic types handled here, not correctly though, so
                 * the check cast will not work for those
                 */
                referredClassName = "java.lang.Object";
            }

            referredIndex = iManager.getInterfaceIndex(referredClassName);

            if (referredIndex == -1) {
                if (classNumbers.containsKey(referredClassName)) {
                    referredIndex = classNumbers.get(referredClassName).intValue();
                } else {
                    isInterface = true;
                }
            } else {
                isInterface = true;
            }

            referredIndex = referredIndex << 1;
            if (isInterface) {
                referredIndex |= 0x1;
            }
            methodCode[location + 1] = (byte) ((referredIndex >> 8) & 0xff);
            methodCode[location + 2] = (byte) (referredIndex & 0xff);
        }
    }

    private void patchINSTANCEOF(String className, byte[] methodCode, String key) throws Exception {
        patchClassreference(new byte[] { RawByteCodes.instanceof_opcode }, className, methodCode, key);
    }

    private void patchCheckcast(String className, byte[] methodCode, String key) throws Exception {
        patchClassreference(new byte[] { RawByteCodes.checkcast_opcode }, className, methodCode, key);
    }

    private void patchNew(String className, byte[] methodCode, String key) throws Exception {
        patchClassreference(new byte[] { RawByteCodes.new_opcode }, className, methodCode, key);
    }

    private FieldInfo[] fieldOffsets;

    private void patchPutGetField(String className, byte[] methodCode, String key, AnalysisObserver observer) throws Exception {
        fieldOffsets = new FieldInfo[methodCode.length];
        IcecapIterator<BNode> locationIterator = getRegisteredByteCodes(key, new byte[] { RawByteCodes.getstatic_opcode, RawByteCodes.putstatic_opcode, RawByteCodes.getfield_opcode, RawByteCodes.putfield_opcode });
        while (locationIterator.hasNext()) {
            int location = locationIterator.next().getAddress();

            byte indexByte2, indexByte3;
            indexByte2 = methodCode[location + 2];
            indexByte3 = methodCode[location + 3];

            MethodOrFieldDesc referredField = ClassfileUtils.getMethodDesc(className, indexByte2 & 0xff, indexByte3 & 0xff);

            referredField = ClassfileUtils.findField(referredField.getClassName(), referredField.getName(), referredField.getSignature());

            String fieldKey = referredField.getClassName() + referredField.getName();

            HashMap<String, FieldInfo> fields;

            if ((methodCode[location] == RawByteCodes.getstatic_opcode) || (methodCode[location] == RawByteCodes.putstatic_opcode)) {
                fields = this.classFields;
            } else {
                fields = this.objectFields;
            }

            String referredClassName = referredField.getClassName();

            if (fields.containsKey(fieldKey)) {
                FieldInfo fi = fields.get(fieldKey);
                int classIndex = getClassNumber(referredField.getClassName());
                int fieldSize = fi.getSize();
                int fieldOffset = fi.getOffset();

                methodCode[location + 1] = (byte) ((classIndex >> 8) & 0xff);
                methodCode[location + 2] = (byte) (classIndex & 0xff);
                methodCode[location + 3] = (byte) (fieldSize);
                methodCode[location + 4] = (byte) ((fieldOffset >> 8) & 0xff);
                methodCode[location + 5] = (byte) (fieldOffset & 0xff);

                if (!foCalc.isHardwareObject(referredClassName)) {
                    if ((methodCode[location] == RawByteCodes.getfield_opcode) || (methodCode[location] == RawByteCodes.putfield_opcode)) {
                        fieldOffsets[location + 4] = fi;
                    }
                }
                if ((methodCode[location] == RawByteCodes.getstatic_opcode) || (methodCode[location] == RawByteCodes.putstatic_opcode)) {
                    fieldOffsets[location + 4] = fi;
                }
            } else {
                System.out.println(className + ":" + referredField.getName() + ":" + location + ": Field in object might be invoked but class is never instantiated");
            }

            if (foCalc.isHardwareObject(referredClassName)) {
                if (methodCode[location] == RawByteCodes.getfield_opcode) {
                    methodCode[location] = GETHWFIELD_OPCODE;
                    observer.byteCodeUsed(GETHWFIELD_OPCODE);
                } else if (methodCode[location] == RawByteCodes.putfield_opcode) {
                    methodCode[location] = PUTHWFIELD_OPCODE;
                    observer.byteCodeUsed(PUTHWFIELD_OPCODE);
                }
            }
        }
    }

    public FieldInfo[] getFieldOffsets() {
        return fieldOffsets;
    }

    @Override
    public void registerClassNumber(String className, int classNumber) {
        if (numberOfClasses <= classNumber) {
            numberOfClasses = classNumber + 1;
        }
        classNumbers.put(className, new Integer(classNumber));
    }

    @Override
    public int getClassNumber(String className) {
        Integer num = classNumbers.get(className);
        if (num != null) {
            return num.intValue();
        } else {
            return -1;
        }

    }

    @Override
    public String getClassName(int number) {
        Iterator<Entry<String, Integer>> entries = classNumbers.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, Integer> next = entries.next();
            if (next.getValue().intValue() == number) {
                return next.getKey();
            }
        }
        return null;
    }

    @Override
    public void registerInterface(String clazz, String _interface) {
        iManager.registerClass(clazz, _interface);
    }

    @Override
    public InterfaceManager getInterfaceManager() {
        return this.iManager;
    }

    @Override
    public void registerFieldNumber(String currentClassName, FieldInfo field, int fieldNumber, boolean isStatic) {
        String fieldKey = currentClassName + field.getName();
        if (isStatic) {
            classFields.put(fieldKey, field);
        } else {
            objectFields.put(fieldKey, field);
        }
    }

    @Override
    public ArrayList<LDCConstant> getConstants() {
        return constants;
    }

    @Override
    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    @Override
    public void registerMethodNumber(MethodOrFieldDesc currentMethod, int methodNumber, IDGenerator idGen) {
        String key = idGen.getUniqueId(currentMethod.getClassName(), currentMethod.getName(), currentMethod.getSignature());
        if (!methodNumbers.containsKey(key)) {
            methodNumbers.put(key, new MethodAndNumber(currentMethod, methodNumber));
        }
    }

    @Override
    public int getMethodNumber(MethodOrFieldDesc currentMethod, IDGenerator idGen) {
        String key = idGen.getUniqueId(currentMethod.getClassName(), currentMethod.getName(), currentMethod.getSignature());
        MethodAndNumber n = methodNumbers.get(key);
        if (n != null) {
            return n.number;
        } else {
            return -1;
        }
    }

    @Override
    public MethodOrFieldDesc getMethodDescriptor(int methodNumber) {
        Iterator<Entry<String, MethodAndNumber>> entries = methodNumbers.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, MethodAndNumber> next = entries.next();
            MethodAndNumber nextEntry = next.getValue();

            if (nextEntry.number == methodNumber) {
                return nextEntry.desc;
            }
        }
        return null;
    }

    private class RegisteredCodeIterator implements IcecapIterator<BNode> {
        private byte[] opcodes;
        private BNode next;
        private Iterator<BNode> registrants;

        public RegisteredCodeIterator(String methodID, byte[] opcodes) {
            List<BNode> registrantList = registeredBytecodes.get(methodID);
            this.opcodes = opcodes;
            next = null;

            if (registrantList != null) {
                registrants = registrantList.iterator();
                findNext();
            }
        }

        private void findNext() {
            while (registrants.hasNext()) {
                BNode nextRegistrant = registrants.next();
                boolean includeIt = false;
                for (int i = 0; i < opcodes.length; i++) {
                    if (nextRegistrant.getOpCode() == opcodes[i]) {
                        includeIt = true;
                    }
                }
                if (includeIt) {
                    next = nextRegistrant;
                    return;
                }
            }
            next = null;
        }

        @Override
        public boolean hasNext() {
            return (next != null);
        }

        @Override
        public BNode next() {
            BNode result = next;
            findNext();
            return result;
        }
    }

    public static String createMethodID(String className, String targetMethodName, String targetMethodSignature) {
        StringBuffer keyBuffer = new StringBuffer();
        keyBuffer.append(className);
        keyBuffer.append(targetMethodName);
        keyBuffer.append(targetMethodSignature);
        String key = keyBuffer.toString();
        return key;
    }

    @Override
    public void registerByteCode(String className, String targetMethodName, String targetMethodSignature, BNode node) {
        String key = createMethodID(className, targetMethodName, targetMethodSignature);
        List<BNode> list;
        if (!registeredBytecodes.containsKey(key)) {
            list = new LinkedList<BNode>();
            registeredBytecodes.put(key, list);
        } else {
            list = registeredBytecodes.get(key);
        }
        list.add(node);
    }

    private void deleteRegistrant(String key, BNode node) throws Exception {
        List<BNode> method = registeredBytecodes.get(key);
        if (!method.remove(node)) {
            throw new Exception("Couldn't find it??");
        }
    }

    private IcecapIterator<BNode> getRegisteredByteCodes(String methodID, byte[] opcodes) {
        return new RegisteredCodeIterator(methodID, opcodes);
    }

    @Override
    public RequiredMethodVTableIndexManager getRequiredVTableIndices() {
        return vtableIndexManager;
    }

}
