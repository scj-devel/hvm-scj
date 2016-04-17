package devices.arm.ATSAMe70;
public abstract class ATSAMe70SCJTargetConfiguration extends ATSAMe70TargetConfiguration {

	@Override
	public int getJavaHeapSize() {
		return 65535;
	}
	
	protected static int getReasonableProcessStackSize() {
		return 1024; /* 4 kB */
	}
}
