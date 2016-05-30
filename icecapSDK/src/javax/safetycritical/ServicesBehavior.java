package javax.safetycritical;

abstract class ServicesBehavior {
	
	abstract int getDefaultCeiling();

	abstract void setCeiling(Object target, int ceiling);
	
	abstract String getNameOfCurrentMemoryArea(); 

}


