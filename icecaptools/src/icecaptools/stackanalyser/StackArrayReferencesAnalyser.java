package icecaptools.stackanalyser;

import icecaptools.MethodEntryPoints;
import icecaptools.RawByteCodes.RawBytecode;
import icecaptools.RawByteCodes.Raw_putfield;
import icecaptools.stackanalyser.AbstractStack.StackCell;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

public class StackArrayReferencesAnalyser extends StackReferencesAnalyser {

    public ArrayList<FlashArrayLocation> flashArrays;

    public StackArrayReferencesAnalyser(MethodEntryPoints javaMethod, JavaClass clazz) {
        super(javaMethod, clazz);
        flashArrays = new ArrayList<FlashArrayLocation>();
    }

    @Override
    protected void handleNewArray(AbstractStack exitStack, RawBytecode pusher) throws UnexpectedTypeException {
        exitStack.popNonRef();
        StackCell cell = new StackCell();
        cell.content = new ArrayRefType(pusher);
        exitStack.push(cell);
    }

    @Override
    protected void handleANewArray(AbstractStack exitStack, RawBytecode pusher) throws UnexpectedTypeException {
        exitStack.popNonRef();
        StackCell cell = new StackCell();
        cell.content = new ArrayRefType(pusher);
        exitStack.push(cell);
    }

    @Override
    protected Field handlePutGetField(AbstractStack exitStack, RawBytecode currentRawBytecode, byte code) throws Exception {
        StackCell top = null;

        if (currentRawBytecode instanceof Raw_putfield) {
            top = exitStack.peek();

        }

        Field field = super.handlePutGetField(exitStack, currentRawBytecode, code);

        if (currentRawBytecode instanceof Raw_putfield) {
            if (field.isVolatile()) {
                if (top.content instanceof ArrayRefType) {
                    Iterator<RawBytecode> pushers = ((ArrayRefType) top.content).getPushers();
                    while (pushers.hasNext()) {
                        flashArrays.add(new FlashArrayLocation(pushers.next(), method, clazz));
                    }
                }
            }
        }
        return field;
    }

    public boolean isFlashArray(String className, String targetMethodName, String targetMethodSignature, int address) {
        for (FlashArrayLocation current : flashArrays) {
            String currentClassName = current.getClazz().getClassName();
            String currentMethodName = current.getJavaMethod().getName();
            String currentMethodSignature = current.getJavaMethod().getSignature();

            if (className.equals(currentClassName)) {
                if (targetMethodName.equals(currentMethodName)) {
                    if (targetMethodSignature.equals(currentMethodSignature)) {
                        if (current.getPusher().getAddress() == address) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
