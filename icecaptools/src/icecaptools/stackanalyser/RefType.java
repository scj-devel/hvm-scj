package icecaptools.stackanalyser;

public class RefType extends UnknownType {

    public int identicleWith;
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType) {
            RefType other = (RefType) obj;
            if (state == other.state) {
                if (identicleWith == other.identicleWith)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public enum RefState {
        NONNULL, NULL, UNKNOWN
    }

    protected RefState state;

    public RefType() {
        state = RefState.UNKNOWN;
        identicleWith = -1;
    }

    @Override
    public String print() {
        return "ref:" + state.toString();
    }

    public RefState getState() {
        return state;
    }

    public void setState(RefState state) {
        this.state = state;
    }

    @Override
    public UnknownType copy() {
        RefType copy = new RefType();
        copy.state = state;
        copy.identicleWith = identicleWith;
        return copy;
    }

    public void merge(RefType other) {
        if (state == other.state)
        {
        }
        else
        {
            state = RefState.UNKNOWN;
        }
        
        if (identicleWith == other.identicleWith)
        {
            
        }
        else
        {
            identicleWith = -1;
        }
    }
}
