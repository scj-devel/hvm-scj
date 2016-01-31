package test;

import vm.VMTest;

public class TestReturnTypes {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = testByte();
        failed |= testShort();
        failed |= testInt();                
        
        TestReturnTypes test= new TestReturnTypes();
        failed |= test.testByteVirt();
        failed |= test.testShortVirt();
        failed |= test.testIntVirt();
        
        VMTest.markResult(failed);
    }

    public boolean testByteVirt() {
        try {
            byte b = returnByteVirt(false);
            if (b != 42) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        try {
            returnByteVirt(true);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    public byte returnByteVirt(boolean throwException) throws Exception {
        if (throwException) {
            throw new Exception();
        }
        return 42;
    }
    
    public static boolean testByte() {
        try {
            byte b = returnByte(false);
            if (b != 42) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        try {
            returnByte(true);
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    public static byte returnByte(boolean throwException) throws Exception {
        if (throwException) {
            throw new Exception();
        }
        return 42;
    }
    
    public static boolean testShort() {
        try {
            short s = returnShort(false);
            if (s != 0x4242) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        try {
            returnShort(true);
            return true;
        } catch (Exception e) {
        }

        return false;
    }
    
    public static short returnShort(boolean throwException) throws Exception {
        if (throwException) {
            throw new Exception();
        }
        return 0x4242;
    }
    
    public boolean testShortVirt() {
        try {
            short s = returnShortVirt(false);
            if (s != 0x4242) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        try {
            returnShortVirt(true);
            return true;
        } catch (Exception e) {
        }

        return false;
    }
    
    public short returnShortVirt(boolean throwException) throws Exception {
        if (throwException) {
            throw new Exception();
        }
        return 0x4242;
    }
    
    public static boolean testInt() {
        try {
            int i = returnInt(false);
            if (i != 0x42424242) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        try {
            returnInt(true);
            return true;
        } catch (Exception e) {
        }

        return false;
    }
    
    public static int returnInt(boolean throwException) throws Exception {
        if (throwException) {
            throw new Exception();
        }
        return 0x42424242;
    }
    
    public boolean testIntVirt() {
        try {
            int i = returnIntVirt(false);
            if (i != 0x42424242) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        try {
            returnIntVirt(true);
            return true;
        } catch (Exception e) {
        }

        return false;
    }
    
    public int returnIntVirt(boolean throwException) throws Exception {
        if (throwException) {
            throw new Exception();
        }
        return 0x42424242;
    }
}
