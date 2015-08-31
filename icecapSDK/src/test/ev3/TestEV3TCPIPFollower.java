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

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.LaunchMulticore;
import javax.safetycritical.ManagedMemory;
import javax.safetycritical.ManagedThread;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import com.UDPCommunication;

import devices.ev3.Motor;
import devices.ev3.MotorPort;
import devices.ev3.MotorPort.MotorPortID;
import devices.ev3.support.EV3Support;

public class TestEV3TCPIPFollower {
	static Motor m1;
	static Motor m2;
	static Motor[] motor = new Motor[2];
	static EV3Support actor;

	private static class Executor implements Runnable{

		@Override
		public void run() {
			String[] msg = UDPCommunication.receiveMsg();
			devices.Console.println("received: " + msg[1]);
			actor.getCommand(msg[1]);
		}
		
	}
	
	private static class MyThd extends ManagedThread {
		Executor executor;
		public MyThd(PriorityParameters priority, PeriodicParameters release, StorageParameters storage, Mission m) {
			super(priority,  storageParameters_Handlers2);
			UDPCommunication.createReceiver();
			
			executor = new Executor();
		}

		@Override
		public void run() {
			for(;;){
				ManagedMemory.enterPrivateMemory(5000, executor);
				actor.action();
			}
		}
	}

	private static class MyMission extends Mission {

		@Override
		protected void initialize() {
			

			MyThd myPEH1 = new MyThd(new PriorityParameters(2), null, storageParameters_Handlers2, this);
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
			MotorPort port = new MotorPort(MotorPortID.B);
			m1 = new Motor(port);

			MotorPort port1 = new MotorPort(MotorPortID.C);
			m2 = new Motor(port1);

			motor[0] = m2;
			motor[1] = m1;
			
			actor = new EV3Support(motor);
		}
	}

	static StorageParameters storageParameters_Sequencer;
	static StorageParameters storageParameters_Handlers2;

	public static void main(String[] args) {
		storageParameters_Sequencer = new StorageParameters(Const.OUTERMOST_SEQ_BACKING_STORE,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM, Const.IMMORTAL_MEM, Const.MISSION_MEM);

		storageParameters_Handlers2 = new StorageParameters(Const.PRIVATE_BACKING_STORE * 5,
				new long[] { Const.HANDLER_STACK_SIZE }, Const.PRIVATE_MEM * 2, 0, 0);

		devices.Console.println("\n***** test multicore wait and notify main.begin *****");
		new LaunchMulticore(new MyApp(), 2);
		devices.Console.println("***** test multicore wait and notify main.end *****");

	}
}
