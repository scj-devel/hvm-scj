package test.icecaptools.compiler;

import icecaptools.IcecapIterator;
import icecaptools.ToIcecapIterator;
import icecaptools.conversion.ConversionConfiguration;

import java.util.ArrayList;

public  class TestConversionConfiguration extends ConversionConfiguration
{
    ArrayList<String> includes;
    
    public TestConversionConfiguration()
    {
        includes = new ArrayList<String>();
        includes.add("test.TestReflectForName$Number");
    }
    
    @Override
    public IcecapIterator<String> getForcedIncludes() {
        
        if (inputClass.contains("TestReflectForName"))
        {
            return new ToIcecapIterator<String>(includes.iterator());
        }
        else
        {
            return super.getForcedIncludes();
        }
    }    
}