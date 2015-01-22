package icecaptools.conversion;

import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.ICompilationRegistry;

import java.util.ArrayList;
import java.util.HashMap;

public class DependencyRestrictor {

    private HashMap<String, ArrayList<MethodOrFieldDesc>> whiteList;

    private HashMap<String, ArrayList<MethodOrFieldDesc>> blackList;

    private ICompilationRegistry cregistry;

    public DependencyRestrictor(ICompilationRegistry cregistry) {
        initWhiteList();
        initBlackList();
        this.cregistry = cregistry;
    }

    private void initBlackList() {
        blackList = new HashMap<String, ArrayList<MethodOrFieldDesc>>();
        
        ArrayList<MethodOrFieldDesc> LambdaMetafactoryExcludes = new ArrayList<MethodOrFieldDesc>();

        MethodOrFieldDesc metafactory = new MethodOrFieldDesc("java.lang.invoke.LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;");
        LambdaMetafactoryExcludes.add(metafactory);

        blackList.put("java.lang.invoke.LambdaMetafactory", LambdaMetafactoryExcludes);
        

        ArrayList<MethodOrFieldDesc> stringBufferExcludes = new ArrayList<MethodOrFieldDesc>();

        MethodOrFieldDesc appendFloat = new MethodOrFieldDesc("java.lang.StringBuffer", "append", "(F)Ljava/lang/StringBuffer;");
        stringBufferExcludes.add(appendFloat);

        blackList.put("java.lang.StringBuffer", stringBufferExcludes);

        ArrayList<MethodOrFieldDesc> stringBuilderExcludes = new ArrayList<MethodOrFieldDesc>();

        appendFloat = new MethodOrFieldDesc("java.lang.StringBuilder", "append", "(F)Ljava/lang/StringBuilder;");
        stringBuilderExcludes.add(appendFloat);

        blackList.put("java.lang.StringBuilder", stringBuilderExcludes);

        ArrayList<MethodOrFieldDesc> stringExcludes = new ArrayList<MethodOrFieldDesc>();

        MethodOrFieldDesc newWithArray = new MethodOrFieldDesc("java.lang.String", "<init>", "([BII)V");
        stringExcludes.add(newWithArray);

        blackList.put("java.lang.String", stringExcludes);

        ArrayList<MethodOrFieldDesc> floatExcludes = new ArrayList<MethodOrFieldDesc>();

        MethodOrFieldDesc floatToString = new MethodOrFieldDesc("java.lang.Float", "toString", "()Ljava/lang/String;");
        floatExcludes.add(floatToString);

        blackList.put("java.lang.Float", floatExcludes);
    }

    protected void initWhiteList() {
        whiteList = new HashMap<String, ArrayList<MethodOrFieldDesc>>();

        ArrayList<MethodOrFieldDesc> includedClassMethods = new ArrayList<MethodOrFieldDesc>();

        includedClassMethods.add(new MethodOrFieldDesc("java.lang.Class", "getName", "()Ljava/lang/String;"));

        whiteList.put("java.lang.Class", includedClassMethods);

        ArrayList<MethodOrFieldDesc> includedThreadMethods = new ArrayList<MethodOrFieldDesc>();

        includedThreadMethods.add(new MethodOrFieldDesc("java.lang.Thread", "run", "()V"));

        whiteList.put("java.lang.Thread", includedThreadMethods);

        ArrayList<MethodOrFieldDesc> includedThrowableMethods = new ArrayList<MethodOrFieldDesc>();

        includedThrowableMethods.add(new MethodOrFieldDesc("java.lang.Throwable", "<init>", "()V"));
        includedThrowableMethods.add(new MethodOrFieldDesc("java.lang.Throwable", "<init>", "(Ljava/lang/String;)V"));
        includedThrowableMethods.add(new MethodOrFieldDesc("java.lang.Throwable", "getMessage", "()Ljava/lang/String;"));
        includedThrowableMethods.add(new MethodOrFieldDesc("java.lang.Throwable", "toString", "()Ljava/lang/String;"));
        includedThrowableMethods.add(new MethodOrFieldDesc("java.lang.Throwable", "getLocalizedMessage", "()Ljava/lang/String;"));
        includedThrowableMethods.add(new MethodOrFieldDesc("java.lang.Throwable", "fillInStackTrace", "()Ljava/lang/Throwable;"));

        whiteList.put("java.lang.Throwable", includedThrowableMethods);
    }

    public boolean skipMethod(String clazz, String targetMethodName, String targetMethodSignature) {
        if (whiteList.containsKey(clazz)) {
            MethodOrFieldDesc m = new MethodOrFieldDesc(clazz, targetMethodName, targetMethodSignature);
            ArrayList<MethodOrFieldDesc> includedMethods = whiteList.get(clazz);
            if (!includedMethods.contains(m)) {
                return true;
            }
        }

        if (blackList.containsKey(clazz)) {
            MethodOrFieldDesc m = new MethodOrFieldDesc(clazz, targetMethodName, targetMethodSignature);
            ArrayList<MethodOrFieldDesc> excludedMethods = blackList.get(clazz);
            if (excludedMethods.contains(m)) {
                return true;
            }
        }

        if (cregistry.isMethodExcluded(clazz, targetMethodName, targetMethodSignature)) {
            return true;
        }
        return false;
    }
}
