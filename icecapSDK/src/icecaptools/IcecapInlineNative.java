package icecaptools;

public @interface IcecapInlineNative {
	String functionBody() default "{\n   return -1;\n   }\n";
	String requiredIncludes() default "";
}
