package javax.safetycritical;

public final class LaunchLevel0 extends Launcher {

	public LaunchLevel0(Safelet<?> app) {
		super(app, 0);
	}

	@Override
	void start() {
		startLevel0();
	}
}
