package javax.safetycritical;

import vm.MachineFactory;

public final class LaunchMulticore extends Launcher {

	public LaunchMulticore(Safelet<?> app, int level, MachineFactory mFactory) {
		super(app, level, true, mFactory);
	}

	public LaunchMulticore(Safelet<?> app, int level) {
		super(app, level, true);
	}

	protected void init() {
		initMultiCoreBehaviour();
	}

	@Override
	protected void start() {
		startwithOS();
	}
}
