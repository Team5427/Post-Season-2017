package org.usfirst.frc5427.graphPanelClient;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.logging.Level;
import java.util.logging.Logger;

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

		while (true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(NetworkTablesClient.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			double x = table.getNumber("X", 0.0);
			double y = table.getNumber("Y", 0.0);
			System.out.println("X: " + x + "Y: " + y);
		}
	}
}
