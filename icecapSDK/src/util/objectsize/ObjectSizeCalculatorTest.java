/*
 * Copyright (C) 2013 - 2016 Kyrylo Holodnov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *
 */
package util.objectsize;

//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.util.Collection;
//
//import static ObjectSizeCalculator.sizeOf;
//import static java.util.Arrays.asList;
//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.assertThat;

/**
 * @author Kyrylo Holodnov
 */
//@RunWith(Parameterized.class)
public class ObjectSizeCalculatorTest {

    private final Object object;
    private final String tcName;
    private final long result64bit;
    private final long result32bit;

    public ObjectSizeCalculatorTest(Object object, String tcName, long result64bit, long result32bit) {
        this.object = object;
        this.tcName = tcName;
        this.result64bit = result64bit;
        this.result32bit = result32bit;
    }

//    @Parameterized.Parameters
//    public static Collection<Object[]> instancesToTest() {
//        return asList(new Object[]{null, "null object", 0, 0},
//                new Object[]{new Object(), "new Object()", 16, 8},
//                new Object[]{"a", "String \"a\"", 56, 32},
//                new Object[]{"", "Empty String", 56, 32},
//                new Object[]{"abc", "String \"abc\"", 64, 40},
//                new Object[]{new long[]{1, 2, 3}, "new long[]{1, 2, 3}", 48, 40},
//                new Object[]{new int[]{4, 5, 6}, "new int[]{4, 5, 6}", 32, 24},
//                new Object[]{new byte[]{7, 8, 9}, "new byte[]{7, 8, 9}", 24, 16},
//                new Object[]{new boolean[]{true, false, true}, "new boolean[]{true, false, true}", 24, 16},
//                new Object[]{new char[]{'a', 'b', 'c'}, "new char[]{'a', 'b', 'c'}", 32, 24},
//                new Object[]{new short[]{10, 11, 12}, "new short[]{10, 11, 12}", 32, 24},
//                new Object[]{new float[]{1.0f, 2.0f, 3.0f}, "new float[]{1.0f, 2.0f, 3.0f}", 32, 24},
//                new Object[]{new double[]{4.0, 5.0, 6.0}, "new double[]{4.0, 5.0, 6.0}", 48, 40},
//                new Object[]{Integer.valueOf(2013), "Integer.valueOf(2013)", 24, 16},
//                new Object[]{new Integer[]{Integer.valueOf(1)}, "new Integer[]{Integer.valueOf(1)}", 56, 32},
//                new Object[]{new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)},
//                        "new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)}",
//                        120, 72},
//                new Object[]{new Integer[][]{new Integer[]{Integer.valueOf(1)}, new Integer[]{Integer.valueOf(2)}, new Integer[]{Integer.valueOf(3)}},
//                        "new Integer[][]{new Integer[]{Integer.valueOf(1)}, new Integer[]{Integer.valueOf(2)}, new Integer[]{Integer.valueOf(3)}}",
//                        216, 120});
//    }
//
//    @Test
//    public void testSizeOf() throws IllegalAccessException {
//        long size64bit = sizeOf(object, true);
//        assertThat("SizeOf for " + tcName + " and 64-bit JVM does not match", size64bit, is(result64bit));
//        //System.out.println("SizeOf for " + tcName + " and 64-bit JVM " + size64bit); 
//        long size32bit = sizeOf(object, false);
//        assertThat("SizeOf for " + tcName + " and 32-bit JVM does not match", size32bit, is(result32bit));
//        //System.out.println("SizeOf for " + tcName + " and 32-bit JVM " + size32bit);
//    }
}
