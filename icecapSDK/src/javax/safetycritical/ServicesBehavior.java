package javax.safetycritical;

abstract class ServicesBehavior {
	
	abstract ManagedSchedulable currentManagedSchedulable();

	abstract int getDefaultCeiling();

	abstract void setCeiling(Object target, int ceiling);
	
	abstract String getNameOfCurrentMemoryArea(); 

}


