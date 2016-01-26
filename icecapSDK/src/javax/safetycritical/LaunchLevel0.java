package javax.safetycritical;

import vm.MachineFactory;

public final class LaunchLevel0 extends LaunchSingleCore {

	public LaunchLevel0(Safelet<?> app, MachineFactory mFactory) {
		super(app, 0, mFactory);
	}

	public LaunchLevel0(Safelet<?> app) {
		super(app, 0);
	}
	
	@Override
	protected void start() {
		startLevel0();
	}
}
