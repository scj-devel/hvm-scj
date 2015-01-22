package icecaptools.stackanalyser;

import java.util.ArrayList;
import java.util.Iterator;

import icecaptools.RawByteCodes.RawBytecode;

public class ArrayRefType extends RefType {
    private ArrayList<RawBytecode> pushers;
     
    public ArrayRefType(RawBytecode pusher)
    {
        this();
        pushers.add(pusher);
    }
    
    private ArrayRefType()
    {
        pushers = new ArrayList<RawBytecode>();
    }
    
    @Override
    public String print() {
        return "arrayref:" + state.toString();
    } 

    public Iterator<RawBytecode> getPushers() {
        return pushers.iterator();
    }
    
    @Override
    public UnknownType copy() {
        ArrayRefType cp = new ArrayRefType();
        Iterator<RawBytecode> iterator = pushers.iterator();
        while (iterator.hasNext())
        {
            cp.pushers.add(iterator.next());
        }
        cp.setState(getState());
        cp.identicleWith = identicleWith;
        return cp;
    }
}
