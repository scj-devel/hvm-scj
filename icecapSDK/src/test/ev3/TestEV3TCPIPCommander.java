/**************************************************************************
 * File name  : TestSCJWaitAndNotify1.java
 * 
 * This code is available under the license:
 * Creative Commons, http://creativecommons.org/licenses/by-nc-nd/3.0/
 * It is free for non-commercial use. 
 * 
 * VIA University College, Horsens, Denmark, 2014
 * Hans Soendergaard, hso@viauc.dk
 * 
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/

package test.ev3;

import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.EV3Support;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.ManagedThread;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import com.UDPCommunication;

import devices.ev3.Motor;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;
import icecaptools.IcecapCompileMe;

public class TestEV3TCPIPCommander {
	static Motor m1;
	static Motor m2;
	static Motor[] motor = new Motor[2];
	
	
	private static class MyThd extends ManagedThread {

		public MyThd(PriorityParameters priority, StorageParameters storage, Mission m) {
			super(priority, storage);
			UDPCommunication.createReceiver();
		}

		@Override
		@IcecapCompileMe
		public void run() {
			for(;;){
				//long free = getMemory().memoryConsumed();

				String[] msg = UDPCommunication.receiveMsg();
				
				//long free1 = getMemory().memoryConsumed();
				
				EV3Support.action(this, msg[1], motor);
				
				//long free2 = getMemory().memoryConsumed();
				
//				if(free != free1 || free1 != free2){
//					devices.Console.println("free: " + free + " free1: " + free1 + " free2: " + free2);
//				}
				
			}
		}
	}

	private static class MyPEH1 extends PeriodicEventHandler {
		int count = 0;

		public MyPEH1(PriorityParameters priority, PeriodicParameters release, StorageParameters storage, Mission m) {
			super(priority, release, storage);
			UDPCommunication.createSender("10.42.0.255");
		}

		@Override
		@IcecapCompileMe
		public void handleAsyncEvent() {

			count ++;
			giveCommand(count);
			if(count == 12)
				count = 0;
			
//			if(count == 12){
//				devices.Console.println("commander exit");
//				TCPIPCommunication.closeBroadcastSender();
//				m.requestTermination();
//			}
		}
		
		private void giveCommand(int count){
			switch(count){
			case 1:
				devices.Console.println("forward");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('F', 10));
				break;
			case 2:
				devices.Console.println("backward");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('B', 10));
				break;
			case 3:
				devices.Console.println("forward");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('F', 10));
				break;
			case 4:
				devices.Console.println("move faster");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('C',20));
				break;
			case 5:
				devices.Console.println("park");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('P'));
				break;
			case 6:
				devices.Console.println("start");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('S'));
				break;
			case 7:
				devices.Console.println("move slower");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('C',10));
				break;
			case 8:
				devices.Console.println("park");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('P'));
				break;
			case 9:
				devices.Console.println("turn left");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('L', 10, 1));
				break;
			case 10:
				devices.Console.println("turn right");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('R', 10, 1));
				break;
			case 11:
				devices.Console.println("forward");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('F', 10));
				break;
			case 12:
				devices.Console.println("park");
				UDPCommunication.sendBroadcastMsg(EV3Support.generateCommand('P'));
				break;
			default:
				;
			}
			
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {
			
			MotorPort port = new MotorPort(MotorPortID.B);
			m1 = new Motor(port);

			MotorPort port1 = new MotorPort(MotorPortID.C);
			m2 = new Motor(port1);

			motor[0] = m2;
			motor[1] = m1;
			
			MyThd thd = new MyThd(new PriorityParameters(2), storageParameters_Handlers, this);
			thd.register();

			MyPEH1 myPEH1 = new MyPEH1(new PriorityParameters(2),
					new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()),
							new RelativeTime(1000, 0, Clock.getRealtimeClock())),
					storageParameters_Handlers, this);
			myPEH1.register();
			
		}

		@Override
		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

	}

	private static class MySequencer extends MissionSequencer<Mission> {
		private Mission mission;
		private int count = 0;

		public MySequencer(PriorityParameters priority, StorageParameters storage) {
			super(priority, storage);
			mission = new MyMission();
		}

		@Override
		protected Mission getNextMission() {
			if (count == 1) {
				devices.Console.println("MySeq.count: " + count + "; null");
				return null;
			} else {
				count++;
				return mission;
			}
		}
	}

	private static class MyApp implements Safelet<Mission> {

		@Override
		public MissionSequencer<Mission> getSequencer() {
			return new MySequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY), storageParameters_Sequencer);
		}

		@Override
		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}

		@Override
		public void initializeApplication() {
		}
	}

	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers;

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers = new StorageParameters(Const.PRIVATE_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM*2, 0, 0);

		devices.Console.println("\n***** test multicore wait and notify main.begin *****");
		new LaunchMulticore(new MyApp(), 2);
		devices.Console.println("***** test multicore wait and notify main.end *****");

	}
}
