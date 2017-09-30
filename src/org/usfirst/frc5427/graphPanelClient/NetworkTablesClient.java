//package org.usfirst.frc5427.graphPanelClient;
//
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.usfirst.frc.team5427.robot.util.Log;
//
//public class NetworkTablesClient {
//	
//	double x = 10;
//	double y = 10;
//	
//	public static void main (String [] args)
//	{
//		new NetworkTablesClient().run();
//	}
//	  
//	public void run()
//	{
//		NetworkTable.setClientMode();
//		NetworkTable.setIPAddress("10.54.27.71");
//		NetworkTable table = NetworkTable.getTable("dataTable");
//
//		while (true)
//		{
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}		
//
//			table.putNumber("X", x);
//			table.putNumber("Y", y);
//			System.out.println("X: "+x+"; Y: "+y);
//			x += 0.05;
//			y += 1.0;
//		}
//	}
//}
