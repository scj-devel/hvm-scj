package test;

import util.StringUtil;

public class TestParseInt {

	public static void main(String[] args) {
		String str = "19200";
		
		int res = StringUtil.parseInt(str);
		
		if (res == 19200)
		{
			res = StringUtil.parseInt("0");
			if (res == 0)
			{
				res = StringUtil.parseInt("1");
				if (res == 1)
				{
					res = StringUtil.parseInt("" + Integer.MAX_VALUE);
					if (res == Integer.MAX_VALUE)
					{
						args = null;
					}					
				}
			}
		}
	}
}
