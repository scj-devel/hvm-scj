package icecaptools;

import icecaptools.compiler.utils.CallGraph;
import icecaptools.conversion.ConversionConfiguration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class StackDepthAnalyser {

    private CallGraph callGraph;
    private LinkedList<MethodIdentifier> maxStack;

    public StackDepthAnalyser(CallGraph callGraph) {
        this.callGraph = callGraph;
    }

    void analyse(ConversionConfiguration config) {
        maxStack = maxDepth(null, config.getEntryPointClassName(), config.getEntryPointMethodName(), config.getEntryPointMethodSignature(), new Stack<MethodIdentifier>(), new HashMap<MethodIdentifier, LinkedList<MethodIdentifier>>());
    }

    @SuppressWarnings("unchecked")
    private LinkedList<MethodIdentifier> maxDepth(MethodIdentifier current, String className, String methodName, String methodSignature, Stack<MethodIdentifier> callStack, HashMap<MethodIdentifier, LinkedList<MethodIdentifier>> previousResults) {
        if (previousResults.containsKey(current))
        {
            return (LinkedList<MethodIdentifier>) previousResults.get(current).clone();
        }
        
        IcecapIterator<MethodIdentifier> callees = callGraph.getCallees(className, methodName, methodSignature);

        LinkedList<MethodIdentifier> maxStack = new LinkedList<MethodIdentifier>();
        
        while (callees.hasNext()) {
            
            MethodIdentifier next = callees.next();
            
            if (!callStack.contains(next)) {
                callStack.push(next);
                LinkedList<MethodIdentifier> maxStackCallee = maxDepth(next, next.getClassName(), next.getName(), next.getSignature(), callStack, previousResults);
                if (maxStackCallee.size() > maxStack.size())
                {
                    maxStack = maxStackCallee;
                }
                callStack.pop();
            }
        }
        if (current != null)
        {
            maxStack.addFirst(current);
            previousResults.put(current, maxStack);
        }
        return (LinkedList<MethodIdentifier>) maxStack.clone();
    }
    
    int calculateMaxDepth() {
        return maxStack.size();
    }
    
    LinkedList<MethodIdentifier> getMaxStack()
    {
        return maxStack;
    }
}
