package devices.arm.ATSAMe70;

public abstract class ATSAMe70SCJTargetConfiguration extends ATSAMe70TargetConfiguration {

	@Override
	public int getJavaHeapSize() {
		return 65535;
	}
	
	@Override
	public String[][] getBuildCommands() {
		String[][] buildCommands = super.getBuildCommands();
		String[][] newbuildCommands = new String[buildCommands.length + 1][];
		int nextIndex = 0;
		newbuildCommands[nextIndex++] = buildCommands[0];
		newbuildCommands[nextIndex++] = 
			new String[] { ATSAMe70Config.ATMEL_TOOLCHAIN_bin + "arm-none-eabi-gcc.exe",
				"-c",
				"-Wall",
				"-mthumb",				
				"-mcpu=cortex-m7",
				"-mfloat-abi=softfp",
				"-mfpu=fpv5-sp-d16",
				"arm7_interrupt.s"
				//"x86_32_interrupt.s"  // HSO: added x86_32_interrupt.s
			}; 
		
		for (int inx = 1; inx <  buildCommands.length; inx++)
		{
			newbuildCommands[nextIndex++] = buildCommands[inx];
		}
		return newbuildCommands;
	}
	
	protected static int getReasonableProcessStackSize() {
		return 1024; /* 4 kB */
	}
}
