package icecaptools.compiler;

import icecaptools.AnalysisObserver;
import icecaptools.IcecapTool;
import icecaptools.MethodOrFieldDesc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class InterfaceManager {
    private HashMap<String, List<String>> interfaces;
    private HashMap<String, MemorySegment> interfaceMatrixDecls;
    private ArrayList<String> interfacesInMap;

    private HashMap<Integer, String> classInterfaces;

    private HashMap<MethodOrFieldDesc, Integer> interfaceMethodIndices;

    public InterfaceManager(AnalysisObserver observer) {
        interfaces = new HashMap<String, List<String>>();
        interfaceMatrixDecls = new HashMap<String, MemorySegment>();
        interfaceMethodIndices = new HashMap<MethodOrFieldDesc, Integer>();
        interfacesInMap = new ArrayList<String>();
        classInterfaces = new HashMap<Integer, String>();
    }

    public void registerClass(String clazz, String _interface) {
        List<String> implementors = interfaces.get(_interface);
        if (implementors == null) {
            implementors = new LinkedList<String>();
            interfaces.put(_interface, implementors);
        }
        if (!implementors.contains(clazz)) {
            implementors.add(clazz);
        }
    }

    public void createInterfaceTableDecl(IcecapTool manager, IDGenerator idGen) throws Exception {
        Iterator<String> interfaceIterator = interfaces.keySet().iterator();
        while (interfaceIterator.hasNext()) {
            String currentInterface = interfaceIterator.next();
            int yDimension;

            yDimension = getNumberOfInterfaceMethods(currentInterface) + 1;

            int methodNumber = 0;

            while (methodNumber < yDimension - 1) {
                JavaClass clazz = Repository.lookupClass(currentInterface);
                Method[] methods = clazz.getMethods();
                Method method = methods[methodNumber];
                MethodOrFieldDesc desc = new MethodOrFieldDesc(currentInterface, method.getName(), method.getSignature());
                interfaceMethodIndices.put(desc, new Integer(methodNumber));
                methodNumber++;
            }

            this.interfaceMatrixDecls.put(currentInterface, new MemorySegment(manager.getProperties()));
            this.interfacesInMap.add(currentInterface);
        }
    }

    private int getNumberOfInterfaceMethods(String currentInterface) throws ClassNotFoundException {
        JavaClass clazz = Repository.lookupClass(currentInterface);
        Method[] methods = clazz.getMethods();
        if (methods != null) {
            return methods.length;
        } else {
            return 0;
        }
    }

    public Iterator<String> getInterfaces() {
        return interfacesInMap.iterator();
    }

    public int getInterfaceIndex(String className) throws Exception {
        Iterator<String> interfaces = getInterfaces();
        int index = 0;
        className = className.replace("/", ".");
        while (interfaces.hasNext()) {
            String next = interfaces.next();
            if (next.equals(className)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int getInterfaceMethodIndex(String className, String methodName, String methodSignature) throws Exception {
        MethodOrFieldDesc desc = new MethodOrFieldDesc(className.replace("/", "."), methodName, methodSignature);
        Iterator<MethodOrFieldDesc> keys = interfaceMethodIndices.keySet().iterator();
        while (keys.hasNext()) {
            MethodOrFieldDesc next = keys.next();
            if (next.equalsWithClass(className, desc)) {
                return interfaceMethodIndices.get(next).intValue();
            }
        }
        throw new Exception("could not get index for [" + className + "]");
    }

    public int getNumberOfInterfaces() {
        return interfaceMatrixDecls.keySet().size();
    }

    public String generateImplementors(AnalysisObserver observer, ByteCodePatcher patcher, IcecapTool manager) throws Exception {
        HashMap<String, List<String>> classes = new HashMap<String, List<String>>();

        Iterator<String> interfaceIterator = interfaces.keySet().iterator();

        while (interfaceIterator.hasNext()) {
            String nextInterface = interfaceIterator.next();
            if (observer.isInterfaceUsed(nextInterface)) {
                List<String> implementors = interfaces.get(nextInterface);
                Iterator<String> classIterator = implementors.iterator();
                while (classIterator.hasNext()) {
                    String nextClass = classIterator.next();
                    List<String> implementedInterfaces = classes.get(nextClass);
                    if (implementedInterfaces == null) {
                        implementedInterfaces = new ArrayList<String>();
                        classes.put(nextClass, implementedInterfaces);
                    }
                    implementedInterfaces.add(nextInterface);

                }
            }
        }

        MemorySegment buffer = new MemorySegment(manager.getProperties());

        Iterator<String> classIterator = classes.keySet().iterator();
        while (classIterator.hasNext()) {
            String nextClass = classIterator.next();
            int classNumber = patcher.getClassNumber(nextClass);

            List<String> interfaces = classes.get(nextClass);
            String name = "class" + classNumber + "interfaces";

            buffer.appendCode("RANGE static const unsigned short " + name + "[" + (interfaces.size() + 1) + "] PROGMEM = { ", (interfaces.size() + 1) * 2);

            classInterfaces.put(new Integer(classNumber), name);

            buffer.print(interfaces.size() + ", ");
            interfaceIterator = interfaces.iterator();
            while (interfaceIterator.hasNext()) {
                String nextInterface = interfaceIterator.next();
                buffer.print("" + getInterfaceIndex(nextInterface));
                if (interfaceIterator.hasNext()) {
                    buffer.print(", ");
                }
            }
            buffer.print(" };\n");
        }
        return buffer.toString();
    }

    public String getClassInterfaceName(int classNumber) {
        return classInterfaces.get(new Integer(classNumber));
    }
}
