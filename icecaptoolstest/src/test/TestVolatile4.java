package test;

public class TestVolatile4 {

    private static class ConstantData {
        public volatile int NUM1 = 42;
        public volatile byte[] bytes = { 0x00, 0x01, 0x02, 0x03 };
        public volatile int NUM2 = 43;
    }

    public static void main(String[] args) {
        ConstantData cdata = new ConstantData();
        devices.System.lockROM();

        if (cdata.NUM1 == 42) {
            if (cdata.bytes != null) {
                if (cdata.bytes.length == 4) {
                    int sum = 0;

                    for (int i = 0; i < 4; i++) {
                        sum += cdata.bytes[i];
                    }

                    if (sum == 6) {
                        if (cdata.NUM2 == 43) {
                            args = null;
                        }
                    }
                }
            }
        }
    }
}
