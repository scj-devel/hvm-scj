package carcontrol.externalsystem;

import java.io.IOException;

import carcontrol.io.CommunicationDevice;
import carcontrol.io.CommunicationDeviceImpl;

public class ExternalSystem {
	
	CommunicationService service; 
	
	public ExternalSystem() {
		
		this.service = new CommunicationServiceImpl();
				
	}
	
	public void stop() {
//		try {
//			//CommunicationDeviceImpl.getInstance().send(Message.STOP);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void start() {
		service.sendToCar(Message.START);
	}

	public void park() {
		service.sendToCar(Message.PARK);
	}
	
	public void reverse() {
		service.sendToCar(Message.REVERSE);
	}
	
	public void drive() {
		service.sendToCar(Message.DRIVE);
	}
	
	public void accelerate() {
		service.sendToCar(Message.ACCELERATE);
	}
	
	public void brake() {
		service.sendToCar(Message.BRAKE);
	}
}
