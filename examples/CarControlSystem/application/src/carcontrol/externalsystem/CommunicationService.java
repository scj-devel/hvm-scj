package carcontrol.externalsystem;

public interface CommunicationService {
	
	void sendToCar(byte message);
	byte receiveFromCar();

}
