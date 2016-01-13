package icecaptools.compiler;

import icecaptools.AnalysisObserver;
import icecaptools.MethodOrFieldDesc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StaticInitializersManager {

    private ArrayList<MethodOrFieldDesc> initializers;
    private HashMap<MethodOrFieldDesc, Integer> methodNumbers;
    private ByteCodePatcher patcher;
    private int numberOfClassInitializers;

    public StaticInitializersManager(ByteCodePatcher patcher) {
        initializers = new ArrayList<MethodOrFieldDesc>();
        methodNumbers = new HashMap<MethodOrFieldDesc, Integer>();
        this.patcher = patcher;
        numberOfClassInitializers = 0;
    }

    public int getNumberOfClassInitializers() {
        return numberOfClassInitializers;
    }

    public void registerStaticInitializer(MethodOrFieldDesc method, int methodNumber) {
        initializers.add(method);
        methodNumbers.put(method, new Integer(methodNumber));
    }

    public boolean hasClassInitializer(int classIndex) {
        Iterator<MethodOrFieldDesc> iterator = initializers.iterator();
        while (iterator.hasNext()) {
            MethodOrFieldDesc next = iterator.next();
            String className = next.getClassName();
            int index = patcher.getClassNumber(className);
            if (index == classIndex) {
                return true;
            }
        }
        return false;
    }

    public StringBuffer getDecleration(AnalysisObserver observer) throws Exception {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> usedClassInitializers = observer.getUsedClassInitializers();
        ArrayList<Integer> ciSequence = new ArrayList<Integer>();
        while (usedClassInitializers.hasNext())
        {
            String nextUsedClassInitializer = usedClassInitializers.next();
            MethodOrFieldDesc desc = findDescriptor(nextUsedClassInitializer);
            if (desc != null)
            {
                Integer methodNumber = methodNumbers.get(desc);
                ciSequence.add(methodNumber);
                numberOfClassInitializers++;
            }
        }
        if (numberOfClassInitializers > 0)
        {
            buffer.append("RANGE static const short _classInitializerSequence[");
            buffer.append(numberOfClassInitializers + "] PROGMEM = { ");
            Iterator<Integer> methods = ciSequence.iterator();
            while (methods.hasNext())
            {
                Integer nextMethod = methods.next();
                buffer.append(nextMethod.intValue());
                if (methods.hasNext())
                {
                    buffer.append(", ");
                }
            }
            buffer.append(" };\n");
        }
        return buffer;
    }

    private MethodOrFieldDesc findDescriptor(String nextUsedClassInitializer) {
        Iterator<MethodOrFieldDesc> iterator = initializers.iterator();
        while (iterator.hasNext())
        {
            MethodOrFieldDesc nextDesc = iterator.next();
            if (nextDesc.getClassName().equals(nextUsedClassInitializer))
            {
                return nextDesc;
            }
        }
        return null;
    }

    
}
