package javax.safetycritical;

import vm.MachineFactory;

public final class LaunchLevel1 extends LaunchSingleCore {

	public LaunchLevel1(Safelet app, MachineFactory mFactory) {
		super(app, 1, mFactory);
	}

	public LaunchLevel1(Safelet app) {
		super(app, 1);
	}

	@Override
	protected void start() {
		startLevel1_2();
	}
}
