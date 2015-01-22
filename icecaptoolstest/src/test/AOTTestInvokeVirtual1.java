package test;

import icecaptools.IcecapCompileMe;


public class AOTTestInvokeVirtual1 {

    private abstract static class Polygon {
        abstract int area();
    }

    private static class Square extends Polygon {
        protected int x;

        Square(int x) {
            this.x = x;
        }

        @Override
        int area() {
            return x * x;
        }
    }

    private static class Rectangle extends Square {
        private int y;

        Rectangle(int x, int y) {
            super(x);
            this.y = y;
        }

        @Override
        int area() {
            return x * y;
        }
    }

    private static class Circle extends Polygon {
        private int r;

        Circle(int r) {
            this.r = r;
        }

        @Override
        int area() {
            return r * r * 3;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    private static String[] test(String[] args) {
        Polygon square = new Square(2);
        Polygon rect = new Rectangle(2, 3);
        Polygon circ = new Circle(3);
        Polygon pol;
        
        int sum = 0;
        pol = square;
        sum += pol.area();
        pol = rect;
        sum += pol.area();
        pol = circ;
        sum += pol.area();
        
        if (sum == 37) {
            return null;
        } else {
            return args;
        }
    }

}
