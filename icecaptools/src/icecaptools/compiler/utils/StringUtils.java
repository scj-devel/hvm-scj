package icecaptools.compiler.utils;

public class StringUtils {

	public static interface StringAction
	{
		void action(String str);
	}
	
	public static void forEachLine(String includes, StringAction todo) {
		for (String string : includes.split("\n")) {
			todo.action(string);
		}
	}
}
