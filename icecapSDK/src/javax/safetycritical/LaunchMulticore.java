package javax.safetycritical;

public final class LaunchMulticore extends Launcher {

	public LaunchMulticore(Safelet<?> app, int level) {
		super(app, level, true);
	}

	@Override
	void start() {
		startwithOS();
	}
}
