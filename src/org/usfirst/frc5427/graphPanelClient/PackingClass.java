package org.usfirst.frc5427.graphPanelClient;

import java.util.Scanner;

public class PackingClass {
	
	//Bellow is a list of which bytes mean what in the double word
	//First Byte:: OnTarget
	//SecondByte:: isRobotLeftOfTarget
	//ThirdByte:: isRobotRightOfTarget
	//
	
	public static void main(String[]args)
	{
		//Code to test bytesToDouble(b)
//		Scanner kb= new Scanner(System.in);
//		int x = 0;
//		byte[] b = new byte[10];
//		do
//		{
//		System.out.print("Enter the byte index: "+x);
//		b[x]=kb.nextByte();
//		x++;
//		}
//		while(x<10);
//		System.out.print("The double is:" + bytesToDouble(b));
	}
	
	
	public static  double bytesToDouble(byte [] buff){
		Double d = new Double(0);
		for(int x=0; x<buff.length;x++)
		{
			d += buff[x]*Math.pow(2, x);
		}
		return d;
	}
	
	@Deprecated
	public static byte[] doubleToBytes(double d)
	{
		return null;
	}
	

}
