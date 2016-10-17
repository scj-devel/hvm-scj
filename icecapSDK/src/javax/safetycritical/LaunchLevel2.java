package javax.safetycritical;

import vm.MachineFactory;

public final class LaunchLevel2 extends LaunchSingleCore {

	public LaunchLevel2(Safelet app, MachineFactory mFactory) {
		super(app, 2, mFactory);
	}
	
	public LaunchLevel2(Safelet app) {
		super(app, 2);
	}
	
	@Override
	protected void start() {
		startLevel1_2();
	}
}
