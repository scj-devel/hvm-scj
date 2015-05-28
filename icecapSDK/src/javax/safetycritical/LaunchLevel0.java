package javax.safetycritical;

public class LaunchLevel0 extends Launcher {

	public LaunchLevel0(Safelet<?> app) {
		super(app, 0);
	}

	@Override
	protected void start() {
		startLevel0();
	}
}
