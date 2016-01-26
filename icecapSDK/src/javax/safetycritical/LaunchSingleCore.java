package javax.safetycritical;

import vm.MachineFactory;

public abstract class LaunchSingleCore extends Launcher {

	public LaunchSingleCore(Safelet<?> app, int level, MachineFactory mFactory) {
		super(app, level, mFactory);
	}

	public LaunchSingleCore(Safelet<?> app, int level) {
		super(app, level);
	}

	protected void init() {
		initSingleCoreBehaviour();
	}
}
