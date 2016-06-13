package icecaptools;

/* Native methods annotated with this attribute will get the functionbody as 
 * specified in 'functionBody'. This can be used to program some C functions
 * in Java source. It is similar to the use of 'asm' directive in C which is used
 * to insert assembler instructions into the C code.
 */
public @interface IcecapInlineNative {
	String functionBody() default "{\n   return -1;\n   }\n";
	String requiredIncludes() default "";
}
