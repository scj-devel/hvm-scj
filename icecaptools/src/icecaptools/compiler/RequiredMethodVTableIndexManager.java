package icecaptools.compiler;

public class RequiredMethodVTableIndexManager extends RequiredEntryManager {

    public RequiredMethodVTableIndexManager(IDGenerator idGen, boolean supportLoading)
    {
    	super(supportLoading);
        requiredEntries.add(idGen.getUniqueId("tasks/ProcessScheduler", "getNextProcess", "(B)B", "TASKS_PROCESSCHEDULER_GETNEXTPROCESS_VTABLEINDEX"));        
    }
}
