package icecaptools.stackanalyser;

public class NonRefType extends UnknownType {
    @Override
    public boolean equals(Object other) {
        if (other.getClass() == NonRefType.class)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String print() {
        return "nonRef";
    }

    @Override
    public UnknownType copy() {
        return new NonRefType();
    }
}
