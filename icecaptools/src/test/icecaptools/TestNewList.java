package test.icecaptools;

import icecaptools.NewList;
import junit.framework.TestCase;

public class TestNewList extends TestCase {

    public void testNewList() throws Exception {
        NewList newList1 = new NewList("java.lang.String;java.lang.Object");
        String actual = newList1.toString();
        assertEquals(actual, "java.lang.Object;java.lang.String");

        newList1.addElement("java.lang.String");

        actual = newList1.toString();
        assertEquals(actual, "java.lang.Object;java.lang.String");

        NewList newList2 = new NewList("java.lang.String;java.lang.Object");

        assertTrue(newList1.equals(newList2));

        newList2.addElement("java.lang.Raw");

        assertFalse(newList1.equals(newList2));

        assertFalse(newList1.equals(null));

        newList1.addElement("java.lang.Fail");

        newList1.merge(newList2);

        assertEquals("java.lang.Fail;java.lang.Object;java.lang.Raw;java.lang.String", newList1.toString());

        assertEquals("java.lang.Object;java.lang.Raw;java.lang.String", newList2.toString());

        assertTrue(newList2.lessThanOrEquals(newList1));

        assertTrue(new NewList().lessThanOrEquals(newList2));

        assertTrue(new NewList().lessThanOrEquals(newList1));

        assertFalse(newList2.lessThanOrEquals(new NewList()));

        assertFalse(newList1.lessThanOrEquals(new NewList()));

        assertFalse(newList1.lessThanOrEquals(null));

        newList2.addElement("");

        assertEquals("java.lang.Object;java.lang.Raw;java.lang.String", newList2.toString());

        NewList copy = new NewList(newList2);

        assertEquals("java.lang.Object;java.lang.Raw;java.lang.String", copy.toString());
    }

    public void testMerge() {
        NewList list1 = new NewList("a;c;e");
        NewList list2 = new NewList("b");

        list1.merge(list2);

        assertEquals("a;b;c;e", list1.toString());
        
        list2 = new NewList("a;c");
        list1.merge(list2);
        
        assertEquals("a;b;c;e", list1.toString());
    }

    public void testLessThan() {
        NewList list1 = new NewList("a");
        NewList list2 = new NewList("a");
        
        assertTrue(list1.lessThanOrEquals(list2));
    
        list1 = new NewList("a;b");
        list2 = new NewList("a");
        
        assertTrue(list2.lessThanOrEquals(list1));
        
        list1 = new NewList("a;b;d");
        list2 = new NewList("a;d");
        
        assertTrue(list2.lessThanOrEquals(list1));
        
        assertFalse(list1.lessThanOrEquals(list2));
        
        list1 = new NewList("a");
        list2 = new NewList("b");

        assertFalse(list1.lessThanOrEquals(list2));
    }
}
