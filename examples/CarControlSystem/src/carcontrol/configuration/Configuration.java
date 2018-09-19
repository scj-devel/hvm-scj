package carcontrol.configuration;

import java.util.ArrayList;
import java.util.Hashtable;


public abstract class Configuration {
	
	public static ArrayList<Long> missionMemSizes = new ArrayList<Long>();
	
	public static ArrayList<String> SOnames = new ArrayList<String>();
	
	public static Hashtable<String, SOConfig> table = new Hashtable<String, SOConfig>();
	
	public abstract void initMissionMemSizes();
	public abstract void initSOConfigTable();

}
