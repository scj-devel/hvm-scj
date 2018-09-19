package test.same70.configuration;


public abstract class SCJTargetConfigurationSAME extends TargetConfigurationSAME {

	@Override
	public int getJavaHeapSize() {
		return 65535;  // HSO: remember to set this heap size in 
		               // sameProject -> Toolchain ->ARM/GNU C Compiler -> Symbols
	}
	
	@Override
	public String[][] getBuildCommands() {
		String[][] buildCommands = super.getBuildCommands();
		String[][] newbuildCommands = new String[buildCommands.length + 1][];
		int nextIndex = 0;
		newbuildCommands[nextIndex++] = buildCommands[0];
		newbuildCommands[nextIndex++] = 
			new String[] { ConfigSAME.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
				"-c",
				"-Wall",
				"-mthumb",				
				"-mcpu=cortex-m7",
				"-mfloat-abi=softfp",
				"-mfpu=fpv5-sp-d16",
				"arm7_interrupt.s"
			}; 
		
		for (int inx = 1; inx <  buildCommands.length; inx++)
		{
			newbuildCommands[nextIndex++] = buildCommands[inx];
		}
		return newbuildCommands;
	}
	
	protected static int getReasonableProcessStackSize() {
		return 1024; /* 420, 1024, 4096 */
	}
}
