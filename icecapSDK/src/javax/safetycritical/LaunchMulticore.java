package javax.safetycritical;

public class LaunchMulticore extends Launcher {

	public LaunchMulticore(Safelet<?> app, int level) {
		super(app, level, true);
	}

	@Override
	protected void start() {
		startwithOS();
	}
}
