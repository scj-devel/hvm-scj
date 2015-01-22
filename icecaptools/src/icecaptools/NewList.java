package icecaptools;

import java.util.HashMap;
import java.util.StringTokenizer;

public class NewList {

    private String[] newList;
    private int top;

    private static HashMap<String, String> strings;
    private static int instanceCount;

    static {
        strings = new HashMap<String, String>();
        instanceCount = 0;
    }

    public NewList(String news) {
        this(1);
        StringTokenizer tokenizer = new StringTokenizer(news, ";");
        while (tokenizer.hasMoreTokens()) {
            addElement(tokenizer.nextToken());
        }
    }

    public NewList(int initialCapacity) {
        newList = new String[initialCapacity];
        top = 0;
        instanceCount++;
    }

    private void insertElement(String element) {
        if (top < newList.length) {
            newList[top++] = element;
        } else {
            String[] copy = new String[(newList.length + 1) * 2];
            for (int i = 0; i < top; i++) {
                copy[i] = newList[i];
            }
            newList = copy;
            insertElement(element);
        }
    }

    public NewList(NewList other) {
        this(other.top);
        String[] otherEntries = other.newList;
        for (int i = 0; i < other.top; i++) {
            newList[i] = otherEntries[i];
        }
        top = other.top;
    }

    public NewList() {
        this(1);
    }

    public void addElement(String nextElement) {
        if ((nextElement != null) && (nextElement.trim().length() > 0)) {
            if (strings.containsKey(nextElement)) {
                nextElement = strings.get(nextElement);
            } else {
                strings.put(nextElement, nextElement);
            }

            int i;
            for (i = 0; i < top; i++) {
                if (newList[i] == nextElement) {
                    return;
                }
            }

            insertElement(nextElement);

            i = 0;
            while (i < top - 1) {
                if (newList[i].compareTo(newList[i + 1]) <= 0) {
                    i++;
                } else {
                    String temp = newList[i];
                    newList[i] = newList[i + 1];
                    newList[i + 1] = temp;
                    if (i > 0) {
                        i--;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < top; i++) {
            String current = newList[i];
            buffer.append(current);
            if (i + 1 < top) {
                buffer.append(";");
            }
        }
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NewList) {
            NewList other = (NewList) obj;
            if (other.top == top) {
                for (int i = 0; i < top; i++) {
                    String otherNext = other.newList[i];
                    String thisNext = newList[i];
                    if (!(otherNext == thisNext)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int merge(NewList other) {
        int addedEntries = 0;
        if (other.top != 0) {
            String[] otherElements = other.newList;
            String[] thisElements = newList;

            String[] result = new String[other.top + top];

            int otherTop = 0;
            int thisTop = 0;
            int resultTop = 0;

            while ((thisTop < top) && (otherTop < other.top)) {
                String thisElement = thisElements[thisTop];
                String otherElement = otherElements[otherTop];

                if (thisElement == otherElement) {
                    result[resultTop++] = thisElement;
                    thisTop++;
                    otherTop++;
                } else {
                    if (thisElement.compareTo(otherElement) < 0) {
                        result[resultTop++] = thisElement;
                        thisTop++;
                    } else {
                        result[resultTop++] = otherElement;
                        otherTop++;
                    }
                }
            }

            while (otherTop < other.top) {
                String otherElement = otherElements[otherTop++];
                result[resultTop++] = otherElement;
            }

            while (thisTop < top) {
                String thisElement = thisElements[thisTop++];
                result[resultTop++] = thisElement;
            }

            addedEntries = resultTop - this.top;
            if (addedEntries > 0) {
                this.newList = result;
            }
            this.top = resultTop;
        }
        return addedEntries;
    }

    public String[] getElementsAsArray() {
        String[] result = new String[top];
        for (int i = 0; i < top; i++) {
            result[i] = newList[i];
        }
        return result;
    }

    public boolean lessThanOrEquals(NewList other) {
        if (other != null) {
            int otherTop = 0;
            String[] otherElements = other.newList;

            for (int i = 0; i < top; i++) {
                String thisElement = newList[i];

                boolean found = false;
                while (otherTop < other.top) {
                    String otherElement = otherElements[otherTop++];
                    if (thisElement == otherElement) {
                        found = true;
                        break;
                    }
                }

                if (found == false) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return top;
    }

    public static int getInstanceCount() {
        return instanceCount;
    }
}
