package icecaptools.stackanalyser;

import icecaptools.RawByteCodes.RawBytecode;

import java.util.Iterator;
import java.util.Stack;

public class AbstractStack {

    public static class StackCell {
        public UnknownType content;

        public StackCell() {
            content = new UnknownType();
        }

        private StackCell(boolean noInit) {
        }

        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            buffer.append(content.toString());
            buffer.append("]");
            return buffer.toString();
        }

        public StackCell copy() {
            StackCell cell = new StackCell(true);

            cell.content = content.copy();

            return cell;
        }

        public void merge(StackCell other) throws Exception {
            UnknownType left, right;
            left = content;
            right = other.content;

            if (left.getClass() == UnknownType.class) {
                content = right.copy();
            } else if (left.getClass() == NonRefType.class) {
                if (right.getClass() == UnknownType.class) {
                } else if (right.getClass() == NonRefType.class) {
                } else if (right.getClass() == RefType.class) {
                    throw new Exception("Unmergeable merge");
                } else if (right.getClass() == ArrayRefType.class) {
                    throw new Exception("Unmergeable merge");
                } else {
                    throw new Exception("Unmergeable merge");
                }
            } else if (left.getClass() == RefType.class) {
                if (right.getClass() == UnknownType.class) {
                } else if (right.getClass() == NonRefType.class) {
                    throw new Exception("Unmergeable merge");
                } else if (right.getClass() == RefType.class) {
                    ((RefType)left).merge((RefType) right);
                } else if (right.getClass() == ArrayRefType.class) {
                    content = right.copy();
                    ((RefType)content).merge((RefType) left);
                } else {
                    throw new Exception("Unmergeable merge");
                }
            } else if (left.getClass() == ArrayRefType.class) {
                if (right.getClass() == UnknownType.class) {
                } else if (right.getClass() == NonRefType.class) {
                    throw new Exception("Unmergeable merge");
                } else if (right.getClass() == RefType.class) {
                    ((RefType)left).merge((RefType) right);
                } else if (right.getClass() == ArrayRefType.class) {
                    ((RefType)left).merge((RefType) right);
                } else {
                    throw new Exception("Unmergeable merge");
                }
            } else {
                throw new Exception("Unmergeable merge");
            }
        }
    }

    private Stack<StackCell> stack;

    public AbstractStack() {
        stack = new Stack<StackCell>();
    }

    public AbstractStack copy() {
        AbstractStack copy = new AbstractStack();

        Iterator<StackCell> iterator = stack.iterator();
        while (iterator.hasNext()) {
            copy.stack.push(iterator.next().copy());
        }

        return copy;
    }

    public void pushUnknown() {
        StackCell cell = new StackCell();
        push(cell);
    }

    public void pushRef() {
        StackCell cell = new StackCell();
        cell.content = new RefType();
        push(cell);
    }

    public void pushArrayRef(RawBytecode pusher) {
        StackCell cell = new StackCell();
        cell.content = new ArrayRefType(pusher);
        push(cell);
    }

    public void pushNonRef() {
        StackCell cell = new StackCell();
        cell.content = new NonRefType();
        push(cell);
    }

    public void popNonRef() throws UnexpectedTypeException {
        StackCell top = stack.pop();
        if (top.content instanceof RefType) {
            throw new UnexpectedTypeException();
        }
    }

    public StackCell popRef() throws UnexpectedTypeException {
        StackCell top = stack.pop();
        if (top.content instanceof NonRefType) {
            throw new UnexpectedTypeException();
        }
        return top;
    }

    public StackCell popAny() {
        return stack.pop();
    }

    public void push(StackCell top) {
        stack.push(top);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractStack) {
            AbstractStack other = (AbstractStack) obj;
            if (other.stack.size() == stack.size()) {
                int i = 0;
                while (i < stack.size()) {
                    UnknownType otherElement = other.stack.get(i).content;
                    UnknownType thisElement = stack.get(i).content;
                    if (!otherElement.equals(thisElement)) {
                        return false;
                    }
                    i++;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (stack.size() == 0) {
            buffer.append("--");
        } else {
            for (int i = 0; i < stack.size(); i++) {
                buffer.append("[" + i + "]: ");
                buffer.append(stack.get(i).toString());
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    public int getSize() {
        return stack.size();
    }

    public byte[] getStackInfo() {
        int stackSize = stack.size();
        if (stackSize > 0) {
            int bytesRequired = (stackSize - 1) >> 3;
            bytesRequired++;
            byte[] stackInfo = new byte[bytesRequired];
            populateStackInfo(stackInfo);
            return stackInfo;
        } else {
            return new byte[0];
        }

    }

    private void populateStackInfo(byte[] stackInfo) {
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).content instanceof RefType) {
                int byteNo = i / 8;
                int bitNo = i % 8;

                stackInfo[byteNo] |= 1 << bitNo;
            }
        }
    }

    public StackCell getAt(int index) {
        return stack.get(index);
    }

    public StackCell peek() {
        return stack.peek();
    }

    public void setAt(int i, StackCell element) {
        stack.set(i, element);
    }

    public void merge(AbstractStack other) throws Exception {
        if (stack.size() == other.stack.size()) {
            for (int i = 0; i < stack.size(); i++) {
                StackCell thisCell;
                StackCell otherCell;
                thisCell = stack.get(i);
                otherCell = other.stack.get(i);
                thisCell.merge(otherCell);
            }
        } else {
            throw new Exception("Unmergable merge attempted");
        }
    }

    public void clear(int index) {
        for (int i = 0; i < stack.size(); i++)
        {
            StackCell current = stack.elementAt(i);
            if (current.content instanceof RefType)
            {
                RefType refType = (RefType) current.content;
                if (refType.identicleWith == index)
                {
                    refType.identicleWith = -1;
                }
            }
        }
        
    }

    public Object[] getArray() {
        return stack.toArray();
    }
}
