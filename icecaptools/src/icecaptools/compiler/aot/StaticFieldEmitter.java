package icecaptools.compiler.aot;

import icecaptools.BNode;
import icecaptools.IcecapCVar;
import icecaptools.NativeFieldInfo;
import icecaptools.RawByteCodes;
import icecaptools.compiler.FieldInfo;
import icecaptools.compiler.NoDuplicatesMemorySegment;
import icecaptools.stackanalyser.ProducerConsumerStack;

public abstract class StaticFieldEmitter {

    private int pc;
    private AOTToolBox toolBox;
    private byte[] currentMethodCode;
    private NoDuplicatesMemorySegment localVariables;
    private StringBuffer output;
    private StackManager sm;
    private NoDuplicatesMemorySegment requiredIncludes;
    private BNode bnode;

    public StaticFieldEmitter(int pc, AOTToolBox toolBox, byte[] currentMethodCode, NoDuplicatesMemorySegment localVariables, StringBuffer output, StackManager sm, NoDuplicatesMemorySegment requiredIncludes, BNode bnode) {
        this.pc = pc;
        this.toolBox = toolBox;
        this.currentMethodCode = currentMethodCode;
        this.localVariables = localVariables;
        this.output = output;
        this.sm = sm;
        this.requiredIncludes = requiredIncludes;
        this.bnode = bnode;
    }

    public FieldAccessEmitter getEmitter() {
        if (currentMethodCode[pc] == RawByteCodes.putstatic_opcode) {
            return new PutStaticFieldEmitter();
        } else {
            return new GetStaticFieldEmitter();
        }
    }

    private abstract class GeneralFieldAccessEmitter implements FieldAccessEmitter {
        protected int foffset;
        protected short fsize;
        protected int classIndex;
        protected FieldInfo fInfo;
        protected NativeFieldInfo nInfo;
        protected String type;

        protected void setup() {
            FieldInfo[] fieldOffsets = toolBox.getPatcher().getFieldOffsets();
            fInfo = fieldOffsets[pc + 4];

            classIndex = (currentMethodCode[pc + 1] << 8) & 0xffff;
            classIndex |= (currentMethodCode[pc + 2] & 0xff);

            fsize = (short) (currentMethodCode[pc + 3] & 0xff);
            short byte1 = (short) (currentMethodCode[pc + 4] & 0xff);
            short byte2 = (short) (currentMethodCode[pc + 5] & 0xff);

            foffset = ((byte1 << 8) | byte2);
            String containingClass = toolBox.getPatcher().getClassName(classIndex);
            nInfo = toolBox.getObserver().isNativeField(containingClass, fInfo);

            switch (fsize & 0xfc) {
            case 16:
                type = "int16";
                break;
            case 8:
                type = "int8";
                break;
            case 32:
            case 64:
            default:
                type = "int32";
                break;
            }
        }
    }

    private class PutStaticFieldEmitter extends GeneralFieldAccessEmitter {

        public void performFieldAccess() throws Exception {

            setup();

            int srcSize;
            switch (fsize & 0xfc) {
            case 16:
                srcSize = Size.SHORT;
                break;
            case 8:
                srcSize = Size.BYTE;
                break;
            case 32:
            case 64:
            default:
                srcSize = Size.INT;
                break;
            }

            if ((fsize >> 3) > 4) {
                localVariables.print("   int32 msb_int32;\n");
            }
            localVariables.print("   " + type + " lsb_" + type + ";\n");

            sm.pop("      lsb_" + type + "", srcSize);

            if (nInfo != null) {
                putStaticNativeField();
            } else {
                if ((fInfo != null) && (!fInfo.isFloat)) {
                } else {
                    localVariables.print("   unsigned char* sdata;\n");
                    output.append("      sdata = classData + " + (foffset >> 3) + ";\n");
                }
                requiredIncludes.print("extern const unsigned char *classData;\n");

                if ((fsize >> 3) > 4) {
                    sm.pop("      msb_int32", Size.INT);
                }

                if ((fInfo != null) && (!fInfo.isFloat)) {
                    output.append("      ((struct ");
                    output.append(fInfo.getStructName());
                    output.append(" *)classData) -> ");
                    output.append(fInfo.getStructMemberName());
                    output.append(" = lsb_" + type + ";\n");
                    if ((fsize & 0xfc) > 32) {
                        output.append("      ((struct ");
                        output.append(fInfo.getStructName());
                        output.append(" *)classData) -> ");
                        output.append(fInfo.getStructMemberLSBName());
                        output.append(" = msb_int32;\n");
                    }
                } else {
                    switch (fsize & 0xfc) {
                    case 64:
                        output.append("      *(int32*) &sdata[4] = msb_int32;\n");
                    case 32:
                        output.append("      *((int32*) sdata) = lsb_" + type + ";\n");
                        break;
                    case 16:
                        output.append("      *((signed short*) sdata) = lsb_" + type + ";\n");
                        break;
                    case 8:
                        output.append("      *((signed char*) sdata) = lsb_" + type + ";\n");
                        break;
                    }
                }
            }

            requiredIncludes.print("extern const ClassInfo *classes;\n");
        }

        private void putStaticNativeField() {
            IcecapCVar cvar = nInfo.getCVar();
            String expression = cvar.expression();
            String includes = cvar.requiredIncludes();

            
            if (includes == null) {
                requiredIncludes.print("extern " + type + " " + nInfo.getField().getName() + ";\n");
            } else {
                a_addUserIncludes(requiredIncludes, includes);
            }
        
            if ((fsize >> 3) > 4) {
                sm.pop("      msb_int32", Size.INT);
            }
            
            String fieldName;
            if (expression == null) {
                fieldName = nInfo.getField().getName();
            } else {
                fieldName = expression;
            }

            switch (fsize & 0xfc) {
            case 64:
                localVariables.print("   unsigned char* sdata;\n");
                if (expression == null) {
                    output.append("      sdata = (unsigned char*) &" + nInfo.getField().getName() + ";\n");
                } else {
                    output.append("      sdata = (unsigned char*) &" + expression + ";\n");
                }
                output.append("      *(int32*) &sdata[4] = msb_int32;\n");
                output.append("      *((int32*) sdata) = lsb_" + type + ";\n");
                break;
            case 32:
                output.append("      " + fieldName + " = lsb_" + type + ";\n");
                break;
            case 16:
                output.append("      " + fieldName + " = lsb_" + type + ";\n");
                break;
            case 8:
                output.append("      " + fieldName + " = lsb_" + type + ";\n");
                break;
            }
        }
    }

    private class GetStaticFieldEmitter extends GeneralFieldAccessEmitter {
        public void performFieldAccess() throws Exception {
            setup();

            ProducerConsumerStack exitStack = bnode.getAinfo().exitStack;

            int dstSize = a_normalizeConsumerSize(bnode, exitStack.size() - 1);

            if (dstSize == Size.DONTCARE) {
                dstSize = Size.INT;
            }

            if (nInfo != null) {
                getStaticNativeField();
            } else {
                if ((fInfo != null) && (!fInfo.isFloat)) {
                } else {
                    localVariables.print("   unsigned char* sdata;\n");
                    output.append("      sdata = classData + " + (foffset >> 3) + ";\n");
                }
                requiredIncludes.print("extern const unsigned char *classData;\n");

                if ((fInfo != null) && (!fInfo.isFloat)) {
                    StringBuffer buffer = new StringBuffer();
                    if ((fsize & 0xfc) > 32) {
                        buffer.append("((struct ");
                        buffer.append(fInfo.getStructName());
                        buffer.append(" *)classData) -> ");
                        buffer.append(fInfo.getStructMemberLSBName());
                        sm.push(Size.INT, buffer.toString());
                        buffer = new StringBuffer();
                    }
                    buffer.append("((struct ");
                    buffer.append(fInfo.getStructName());
                    buffer.append(" *)classData) -> ");
                    buffer.append(fInfo.getStructMemberName());

                    sm.push(dstSize, buffer.toString());
                } else {
                    switch (fsize & 0xfc) {
                    case 64:
                        sm.push(Size.INT, "*(int32*) &sdata[4]");
                    case 32:
                        sm.push(dstSize, "*(int32*) sdata");
                        break;
                    case 16:
                        sm.push(dstSize, "*(signed short*) sdata");
                        break;
                    case 8:
                        sm.push(dstSize, "*(signed char*) sdata");
                        break;
                    }
                }
            }
            requiredIncludes.print("extern const ClassInfo *classes;\n");
        }

        private void getStaticNativeField() {
            IcecapCVar cvar = nInfo.getCVar();
            String expression = cvar.expression();
            String includes = cvar.requiredIncludes();

            
            if (includes == null) {
                requiredIncludes.print("extern " + type + " " + nInfo.getField().getName() + ";\n");
            } else {
                a_addUserIncludes(requiredIncludes, includes);
            }
            String fieldName;
            
            if (expression == null) {
                fieldName = nInfo.getField().getName();
            } else {
                fieldName = expression;
            }
            
            switch (fsize & 0xfc) {
            case 64:
                localVariables.print("   unsigned char* sdata;\n");
                if (expression == null) {
                    output.append("      sdata = (unsigned char*) &" + nInfo.getField().getName() + ";\n");
                } else {
                    output.append("      sdata = (unsigned char*) &" + expression + ";\n");
                }
                sm.push(Size.INT, "*(int32*) &sdata[4]");
                sm.push(Size.INT, "*(int32*) sdata");
                break;
            case 32:
                
                sm.push(Size.INT, "(int32)" + fieldName);
                break;
            case 16:
                sm.push(Size.SHORT, fieldName);
                break;
            case 8:
                sm.push(Size.BYTE, fieldName);
                break;
            }
        }
    }

    protected void handleNativeField(StringBuffer output, NativeFieldInfo nInfo, String type) {
        IcecapCVar cvar = nInfo.getCVar();
        String expression = cvar.expression();
        String includes = cvar.requiredIncludes();

        if (expression == null) {
            output.append("      sdata = (unsigned char*) &" + nInfo.getField().getName() + ";\n");
        } else {
            output.append("      sdata = (unsigned char*) &" + expression + ";\n");
        }
        if (includes == null) {
            requiredIncludes.print("extern " + type + " " + nInfo.getField().getName() + ";\n");
        } else {
            a_addUserIncludes(requiredIncludes, includes);
        }
    }

    protected abstract void a_addUserIncludes(NoDuplicatesMemorySegment requiredIncludes2, String includes);

    protected abstract int a_normalizeConsumerSize(BNode bnode, int i) throws Exception;

}
