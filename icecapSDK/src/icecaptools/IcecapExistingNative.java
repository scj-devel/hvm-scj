package icecaptools;

/* This attribute should only be applied to native functions.
 * 
 * The native function that gets called will be taken from the
 * 'signature' value.
 * 
 * The native function should have parameters and return types
 * corresponding to what the HVM expects. * 
 * 
 */
public @interface IcecapExistingNative {
    String signature() default "";
}
