package via.embedded.carcontrol.device;


public class EngineImpl implements Engine {

	@Override
	public void engineOn() {
		System.out.println("EngineImpl.engineOn");
		// start motor
		// head lights on
		// tail lights on
		
	}

	@Override
	public void engineOff() {
		// stop motor
		// head lights off
		// tail lights off

	}

}
