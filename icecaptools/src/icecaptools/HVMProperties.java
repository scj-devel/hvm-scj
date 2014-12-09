package icecaptools;

public interface HVMProperties {

    public String getProgmemStart();

    public String getProgmemEnd();

    public String getNewlineSequence();

    public boolean includeMethodAndClassNames();
    
    public boolean isIncludeJMLMethods();

    public void setIncludeJMLMethods(boolean includeJMLMethods);

	public String getProperty(String string);
}
