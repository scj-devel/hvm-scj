package icecaptools.stripper;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InstructionTargeter;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.NOP;
import org.apache.bcel.generic.TargetLostException;

public class Stripper {
    /**
    * Strips specified methods in specified class
    * @param inputFolder    Input folder of the class
    * @param inputPackage   Input package
    * @param inputClass     Input class name
    * @param methods        Array of methods to strip
    * @param exceptionClassName Name of the exception class
    * @param exceptionMethodName Name of the method to call (usually "<init>")
    * @param exceptionMethodSignature Signature of the called method
    * @return returns stripped class
    * @throws ClassNotFoundException
    */
    public JavaClass stripClass(org.apache.bcel.util.Repository repository, String inputClass, Method[] methods, String exceptionClassName, String exceptionMethodName, String exceptionMethodSignature) throws ClassNotFoundException {
        Repository.setRepository(repository);
        JavaClass clazz = Repository.lookupClass(inputClass);

        ConstantPoolGen cp = new ConstantPoolGen(clazz.getConstantPool());
        // Method[] methods = clazz.getMethods();

        int exceptionClassCpIndex = cp.addClass(exceptionClassName);
        int exceptionMethodCpIndex = cp.addMethodref(exceptionClassName, exceptionMethodName, exceptionMethodSignature);

        MethodGen mg;
        Method stripped;

        for (int i = 0; i < methods.length; i++) {
            // skip abstract, native and constructor methods
            if (!(methods[i].isAbstract() || methods[i].isNative() || methods[i].getName().equals("<init>"))) {
                mg = new MethodGen(methods[i], clazz.getClassName(), cp);
                stripped = stripMethod(mg, cp, exceptionClassCpIndex, exceptionMethodCpIndex);
                if (stripped != null) {
                    methods[i] = stripped;
                }
            }
        }

        clazz.setConstantPool(cp.getFinalConstantPool());
        // don't save, just return
        // clazz.dump(clazz.getClassName() + ".class");

        return clazz;
    }

    /**
     * Strips method and inserts code that throws specified exception
     *
     * @param mg
     *            MethodGen containing method to strip
     * @param cp
     *            ConstantPoolGen of the method
     * @param cp_class_index
     *            Index of exception class to throw in the constant pool
     * @param cp_method_index
     *            Index of exception method (usually constructor) to call
     * @return stripped method that throws specified exception
     * @see NullPointerException
     */
    private Method stripMethod(MethodGen mg, ConstantPoolGen cp, int cp_class_index, int cp_method_index) {
        InstructionList il = mg.getInstructionList();
        // InstructionFactory ig = new InstructionFactory(cp);

        // Add NOP instruction in the end, to handle references of deleted
        // instructions
        InstructionHandle ih_end = il.getEnd(); // save old end of the
                                                // instruction list
        il.append(new NOP());                   // append dummy NOP
        InstructionHandle ih_nop = il.getEnd(); // get the new end

        try {
            il.delete(il.getStart(), ih_end); // remove all instructions till
                                              // old end
            mg.removeLocalVariables();        // remove all local variables references
            mg.removeNOPs();                  // remove dummy NOP
        } catch (TargetLostException e) {
            InstructionHandle[] targets = e.getTargets();
            for (int i = 0; i < targets.length; i++) {
                InstructionTargeter[] targeters = targets[i].getTargeters();
                for (int j = 0; j < targeters.length; j++)
                    //update target to our dummy NOP instruction
                    targeters[j].updateTarget(targets[i], ih_nop);
             }
        }

        il.append(new NEW(cp_class_index));
        il.append(new DUP());
        il.append(new INVOKESPECIAL(cp_method_index));
        il.append(new ATHROW());

        // il.append(ig.createInvoke("java.lang.NullPointerException", "<init>",
        // Type.VOID, new Type[] {}, Constants.INVOKESPECIAL));
        mg.setMaxStack();
        Method m = mg.getMethod();
        il.dispose();

        return m;
    }
}
