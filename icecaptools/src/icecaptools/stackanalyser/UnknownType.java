package icecaptools.stackanalyser;

public class UnknownType {
    @Override
    public boolean equals(Object other) {
        if (other.getClass() == UnknownType.class)
        {
            return true;            
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString() {
        return print();
    }
    
    public String print()
    {
        return "unknown";
    }
    
    public UnknownType copy()
    {
        return new UnknownType();
    }
}
