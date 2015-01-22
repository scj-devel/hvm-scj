package icecaptools;

import icecaptools.stackanalyser.AbstractStack;
import icecaptools.stackanalyser.RefType;
import icecaptools.stackanalyser.AbstractStack.StackCell;
import icecaptools.stackanalyser.RefType.RefState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class BNodeUtils {

    public static void collectExceptions(MethodEntryPoints method) throws Exception {
        ArrayList<BNode> bnodes = method.getBNodes();
        LinkedList<String> exceptionsThrown = new LinkedList<String>();

        for (BNode bNode : bnodes) {
            boolean doIt = true;

            switch (bNode.getOpCode()) {
            case RawByteCodes.ireturn_opcode: // IllegalMonitorException may
                                              // always be thrown by return.
                                              // Don't catch this one
            case RawByteCodes.lreturn_opcode:
            case RawByteCodes.freturn_opcode:
            case RawByteCodes.dreturn_opcode:
            case RawByteCodes.areturn_opcode:
            case RawByteCodes.return_opcode:
                doIt = false;
                break;
            case RawByteCodes.getfield_opcode: {
                AbstractStack stackLayout = bNode.getStackLayout();

                StackCell cell = stackLayout.getAt(stackLayout.getSize() - 1);

                if (cell.content instanceof RefType) {
                    if (((RefType) cell.content).getState() == RefState.NONNULL) {
                        doIt = false;
                    }
                }
                break;
            }
            case RawByteCodes.putfield_opcode: {
                FieldAccessBNode fieldAccess = (FieldAccessBNode) bNode;

                int fieldSize = fieldAccess.getSize();

                if (fieldSize > 0) {
                    AbstractStack stackLayout = bNode.getStackLayout();

                    StackCell cell = stackLayout.getAt(stackLayout.getSize() - 1 - fieldSize);

                    if (cell.content instanceof RefType) {
                        if (((RefType) cell.content).getState() == RefState.NONNULL) {
                            doIt = false;
                        }
                    }
                }

                break;
            }
            case RawByteCodes.invokespecial_opcode: {
                MethodCallBNode methodCall = (MethodCallBNode) bNode;

                int numArgs = methodCall.getNumArgs();

                boolean checkIt = true;

                if (numArgs >= 0) {
                    AbstractStack stackLayout = bNode.getStackLayout();

                    StackCell cell = stackLayout.getAt(stackLayout.getSize() - 1 - numArgs);

                    if (cell.content instanceof RefType) {
                        if (((RefType) cell.content).getState() == RefState.NONNULL) {
                            checkIt = false;
                        }
                    }
                }
                if (checkIt) {
                    doIt = true;
                }
            }
                break;
            default:
                doIt = true;
            }

            if (doIt) {
                if (bNode.throwsExceptions()) {
                    Iterator<String> exceptions = bNode.getExceptionsThrown();
                    while (exceptions.hasNext()) {
                        String next = exceptions.next();
                        if (!exceptionsThrown.contains(next)) {
                            exceptionsThrown.add(next);
                        }
                    }
                }
            }
        }

        if (exceptionsThrown.size() > 0) {
            method.setMayThrowException();
        }
    }
}
