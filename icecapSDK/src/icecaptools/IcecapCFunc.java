package icecaptools;

/* Methods annotated with this attribute will get the signature 
 * specified in 'signature'. This can be used to program interrupt
 * handlers, since they sometimes are required to have a specific name
 */
public @interface IcecapCFunc {
    String signature() default "";
    String requiredIncludes() default "";
    String hasReturnValue() default "false";
}
