package javax.safetycritical;

public final class LaunchLevel2 extends Launcher {

	public LaunchLevel2(Safelet<?> app) {
		super(app, 2);
	}

	@Override
	void start() {
		startLevel1_2();
	}
}
