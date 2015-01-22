package test;

public class TestClassClass {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    public static String[] test(String[] args) {
        Class<Object[]> clazz = Object[].class;
        Class<?> type = clazz.getComponentType();
        if (type == Object.class) {
            @SuppressWarnings("rawtypes")
            Class<Class[]> clazz1 = Class[].class;
            type = clazz1.getComponentType();
            if (type == Class.class) {
                return null;
            }
        }
        return args;
    }

}
