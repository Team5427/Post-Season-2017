package org.usfirst.frc5427.graphPanelClient;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class NetworkTablesClient {
	public static void main (String [] args)
	{
		new NetworkTablesClient().run();
	}
	
	public void run()
	{
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.54.27.71");
		NetworkTable table = NetworkTable.getTable("dataTable");
	}

}
