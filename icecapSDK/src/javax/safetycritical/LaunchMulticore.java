package javax.safetycritical;


public final class LaunchMulticore extends Launcher {

	public LaunchMulticore(Safelet<?> app, int level) {
		super(app, level, true);
		

		initMultiCoreBehaviour();
		
		createImmortalMemory();
	}

	@Override
	void start() {
		startwithOS();
	}
}
