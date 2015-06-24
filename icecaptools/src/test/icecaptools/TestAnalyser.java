package test.icecaptools;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class TestAnalyser {

	@Test
    public void testHashSet() throws Exception {
        HashSet<String> set = new HashSet<String>();
        
        set.add("test");
        set.add(new String("test"));
        set.add(new StringBuffer("test").toString());
        
        assertTrue(set.size() == 1);
        
        assertTrue(set.contains("test"));
        
        assertFalse(set.contains("Test"));        
    }
}
