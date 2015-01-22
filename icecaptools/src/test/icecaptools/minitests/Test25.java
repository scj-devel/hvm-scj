package test.icecaptools.minitests;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Test25 {

    public static void main(String[] args) {
        LinkedList<String> head = new LinkedList<String>();
        
        head.add("HelloWorld");
        head.add("Some more tokens");
        
        StringTokenizer tokenizer = new StringTokenizer("a;b;c", ";");        
        while (tokenizer.hasMoreTokens()) {
            String next = tokenizer.nextToken();
            head.add(next);
        }
        
        Arrays.sort(head.toArray());
    }
}
