package test;

import java.util.ArrayList;

public class TestInvokeVirtual1 {

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

    public static String[] test(String[] args) {
        ArrayList<Polygon> figures = new ArrayList<Polygon>();
        figures.add(new Square(2));
        figures.add(new Rectangle(2, 3));
        figures.add(new Circle(3));

        int sum = 0;
        for (Polygon polygon : figures) {
            sum += polygon.area();
        }
        if (sum == 37) {
            return null;
        } else {
            return args;
        }
    }

}
