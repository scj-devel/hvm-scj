package test;

public class TestDeterminant {

    private static class Vector {
        private short[] vector;

        public Vector(short n) {
            vector = new short[n];
        }

        public void setPosition(short index, short value) {
            vector[index] = value;
        }

        public short getPosition(short index) {
            return vector[index];
        }

        public short getDimension() {
            return (short) vector.length;
        }
    }

    private static class Matrix {

        Vector[] matrix;

        public Matrix(short n) {
            matrix = new Vector[n];
            for (byte i = 0; i < n; i++)
            {
                matrix[i] = new Vector(n);
            }
        }

        public void setPosition(short row, short column, short value) {
            matrix[row].setPosition(column, value);
        }

        public short getPosition(short row, short column) {
            return matrix[row].getPosition(column);
        }

        public short getDimension() {
            return (short) matrix.length;
        }

        public void substituteColumn(short col, Vector b) {
            for (short i = 0; i < matrix.length; i++) {
                setPosition(i, col, b.getPosition(i));
            }
        }

        public Matrix copy() {
            Matrix m = new Matrix((short) matrix.length);
            for (short i = 0; i < m.getDimension(); i++) {
                for (short j = 0; j < m.getDimension(); j++) {
                    m.setPosition(i, j, getPosition(i, j));
                }
            }
            return m;
        }
    }

    private static class Calculate {

        public static short determinant(Matrix m) {
            if (m.getDimension() == 2) {
                return (short) (m.getPosition((short)0, (short)0) * m.getPosition((short)1, (short)1) - m.getPosition((short)1, (short)0) * m.getPosition((short)0, (short)1));
            } else {
                short sum = 0;
                for (short i = 0; i < m.getDimension(); i++) {
                    Matrix subM = createSubM(i, m);
                    
                    byte sign;
                    if ((i & 1) == 1)
                    {
                        sign = -1;
                    }
                    else
                    {
                        sign = 1;
                    }

                    sum += m.getPosition((short) 0, i) * sign * determinant(subM);
                }
                return sum;
            }
        }

        public static Matrix createSubM(short i, Matrix m) {
            Matrix subM = new Matrix((short) (m.getDimension() - 1));

            for (short row = 1; row < m.getDimension(); row++) {
                short index = 0;
                for (short col = 0; col < m.getDimension(); col++) {
                    if (col == i) {
                        ;
                    } else {
                        subM.setPosition((short) (row - 1), index, m.getPosition(row, col));
                        index++;
                    }
                }
            }

            return subM;
        }

        public static short[] solutionsCramer(Matrix A, Vector b) {
            short[] result = new short[b.getDimension()];
            for (short i = 0; i < A.getDimension(); i++) {
                Matrix m = A.copy();
                m.substituteColumn(i, b);
                short num = (short) (determinant(m) / determinant(A));
                result[i] = num;
            }
            return result;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    private static boolean test() {
        short solutions[];

        Matrix A = new Matrix((short) 3);

        A.setPosition((short)0, (short)0, (short)2);
        A.setPosition((short)0, (short)1, (short)1);
        A.setPosition((short)0, (short)2, (short)-1);

        A.setPosition((short)1, (short)0, (short)-3);
        A.setPosition((short)1, (short)1, (short)-1);
        A.setPosition((short)1, (short)2, (short)2);

        A.setPosition((short)2, (short)0, (short)-2);
        A.setPosition((short)2, (short)1, (short)1);
        A.setPosition((short)2, (short)2, (short)2);

        Vector b = new Vector((short)3);
        b.setPosition((short)0, (short)8);
        b.setPosition((short)1, (short)-11);
        b.setPosition((short)2, (short)-3);

        solutions = Calculate.solutionsCramer(A, b);
        if (2 == solutions[0]) {
            if (3 == solutions[1]) {
                if (-1 == solutions[2]) {
                    return false;
                }
            }
        }
        return true;
    }
}
