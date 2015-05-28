package javax.safetycritical;

public class LaunchLevel1 extends Launcher {

	public LaunchLevel1(Safelet<?> app) {
		super(app, 1);
	}
	
	@Override
	protected void start() {
		startLevel1_2();
	}
}
