package icecaptools.compiler.utils;

import icecaptools.compiler.ConstantGenerator;

public class ClassInheritanceMatrix {

    private int dimension;
    private byte[] bits;
    private int tupac;

    public ClassInheritanceMatrix(int d) {
        tupac = 0;
        dimension = 1;
        while (dimension < d) {
            dimension = dimension + dimension;
            tupac++;
        }

        int bitsRequired = dimension * dimension;
        int bytesRequired = bitsRequired / 8;
        if (bitsRequired % 8 > 0) {
            bytesRequired++;
        }
        bits = new byte[bytesRequired];
    }

    public void setInherits(int subClass, int superClass) {
        int bitIndex = (subClass << tupac) + superClass;
        int byteIndex = bitIndex >> 3;
        byte b = bits[byteIndex];

        b = (byte) (b | (1 << (bitIndex & 0x7)));

        bits[byteIndex] = b;
    }

    public boolean getInherits(int subClass, int superClass) {
        int bitIndex = (subClass << tupac) + superClass;
        int byteIndex = bitIndex >> 3;
        byte b = bits[byteIndex];
        b = (byte) (b & (1 << (bitIndex & 0x7)));
        return b != 0;
    }

    public StringBuffer getMatrix() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("RANGE const uint8 tupac = " + tupac + ";\n");
        buffer.append("RANGE static const unsigned char _inheritanceMatrix[" + bits.length + "] PROGMEM = { ");
        for (int i = 0; i < bits.length; i++)
        {
            byte b = bits[i];
            buffer.append("0x").append(ConstantGenerator.HEXES.charAt((b & 0xF0) >> 4)).append(ConstantGenerator.HEXES.charAt((b & 0x0F)));
            if (i < bits.length - 1)
            {
                buffer.append(",");
            }
        }
        buffer.append(" };\n");
        
        return buffer;
    }

	public int getMatrixSize() {
		return bits.length;
	}
}
