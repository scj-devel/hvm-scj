package javax.realtime;

public final class SizeEstimator {
	
	/**
     * The size estimation of all the elements that have been reserved.
     */
    private int estimate = 0;

	/**
     * Creates a new SizeEstimator object in the current allocation context.
     */
    public SizeEstimator() {}

    /**
     * Gets an estimate of the number of bytes needed to store all the objects reserved.
     * 
     * @return the estimated size in bytes.
     */
    public int getEstimate() {
        return this.estimate;
    }


    /**
     * Adds num times the value returned by size.getEstimate to the currently 
     * computed size of the set of reserved objects.
     * 
     * @param size  is the size.SizeEstimator whose size is to be reserved.
     * @param num   is the number of times to reserve this amount.
     */
    public void reserve(SizeEstimator size, int num) {
        this.estimate += size.getEstimate() * num;
    }

    /**
     * Adds the value returned by size.getEstimate to the currently computed 
     * size of the set of reserved objects.
     * 
     * @param size   is the size.SizeEstimator whose size is to be reserved.
     */
    public void reserve(SizeEstimator size) {
        this.estimate += size.getEstimate();
    }


    /**
     * Adds the required memory size of num instances of a clss object 
     * to the currently computed size of the set of reserved objects.
     * 
     * @param clss   is the class to take into account.
     * @param num    is the number of instances of <code>clss</code> to estimate.
     */
    public void reserve(java.lang.Class<?> clss, int num) {
        this.estimate += SizeEstimator.sizeOf(clss) * num;
    }


    /**
     * Adds the required memory size of an additional instance of an array 
     * of <code>length</code> primitive values of Class type to the currently 
     * computed size of the set of reserved objects. 
     * Class values for the primitive types shall be chosen from primitive 
     * class types such as Integer.TYPE , and Float.TYPE . 
     * The reservation shall leave room for an array of length of the 
     * primitive type corresponding to type .
     * 
     * @param length   is the number of entries in the array.
     * @param type    is the class representing a primitive type.
     */
    public void reserveArray(int length, Class<?> type) {
    	
    }

    /**
     * Adds the size of an instance of an array of length reference values 
     * to the currently computed size of the set of reserved objects.
     * 
     * @param length   is the number of entries in the array.
     */
    public void reserveArray(int length) {
    	
    }
   

    /**
     * Returns the size estimation for the allocation of an instance of
     * the specified class.
     * @param c the class to get the allocation size estimation.
     */
    private static native int sizeOf(java.lang.Class<?> c);

  


}
