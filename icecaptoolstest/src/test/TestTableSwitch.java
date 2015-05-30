package test;

import vm.VMTest;

public class TestTableSwitch {
    private static final int CONSTANT_Class = 7;
    private static final int CONSTANT_Fieldref = 9;
    private static final int CONSTANT_Methodref = 10;
    private static final int CONSTANT_InterfaceMethodref = 11;
    private static final int CONSTANT_String = 8;
    private static final int CONSTANT_Integer = 3;
    private static final int CONSTANT_Float = 4;
    private static final int CONSTANT_Long = 5;
    private static final int CONSTANT_Double = 6;
    private static final int CONSTANT_NameAndType = 12;
    private static final int CONSTANT_Utf8 = 1;

    public static void main(String[] args) throws Exception {
        VMTest.markResult(test());
    }

	public static boolean test() throws Exception {
		byte tag = CONSTANT_Utf8;
        switch (tag) {
        case CONSTANT_Class:
            throw new Exception("CONSTANT_Class unimplemented");
        case CONSTANT_Fieldref:
            throw new Exception("CONSTANT_Fieldref unimplemented");
        case CONSTANT_Methodref:
            throw new Exception("CONSTANT_Methodref unimplemented");
        case CONSTANT_InterfaceMethodref:
            throw new Exception("CONSTANT_InterfaceMethodref unimplemented");
        case CONSTANT_String:
            throw new Exception("CONSTANT_String unimplemented");
        case CONSTANT_Integer:
            throw new Exception("CONSTANT_Integer unimplemented");
        case CONSTANT_Float:
            throw new Exception("CONSTANT_Float unimplemented");
        case CONSTANT_Long:
            throw new Exception("CONSTANT_Long unimplemented");
        case CONSTANT_Double:
            throw new Exception("CONSTANT_Double unimplemented");
        case CONSTANT_NameAndType:
            throw new Exception("CONSTANT_NameAndType unimplemented");
        case CONSTANT_Utf8:
            switch (tag) {
            case CONSTANT_Long:
                throw new Exception("CONSTANT_Long unimplemented");
            case CONSTANT_Double:
                throw new Exception("CONSTANT_Double unimplemented");
            case CONSTANT_NameAndType:
                throw new Exception("CONSTANT_NameAndType unimplemented");
            default:
                switch (tag) {
                case CONSTANT_Integer:
                    throw new Exception("CONSTANT_Long unimplemented");
                case CONSTANT_Double:
                    throw new Exception("CONSTANT_Double unimplemented");
                case CONSTANT_NameAndType:
                    throw new Exception("CONSTANT_NameAndType unimplemented");
                }
                return false;
            }
        default:
            throw new Exception("Unexpected tag: " + tag);
        }
	}
}
