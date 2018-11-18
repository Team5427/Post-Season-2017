package org.usfirst.frc5427.postSeason2017.commands;

import org.usfirst.frc5427.postSeason2017.Robot;
import org.usfirst.frc5427.postSeason2017.subsystems.UltrasonicPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDApproach extends Command
{
	UltrasonicPID ultraPID;
	PIDStraightMovement straightPID;
	
	public PIDApproach()
	{
		ultraPID = Robot.ultra;
	}
	
	protected void initialize()
	{
		straightPID = new PIDStraightMovement(0.3);// TODO: maximum speed, add
													// to config
		ultraPID.setSetpoint(2.5);
		
		straightPID.start();
	}
	
	@Override
	protected void execute()
	{
		if (ultraPID.ultra.getRangeInches() < 24)
		{
			straightPID.setRamping(false);
			ultraPID.enable();
		}
	}
	
	@Override
	protected boolean isFinished()
	{
		System.out.println(Math.abs(ultraPID.getPosition() - ultraPID.getSetpoint()));
		return Math.abs(ultraPID.getPosition() - ultraPID.getSetpoint()) <= 0.5;
	}
	
	protected void end()
	{
		System.out.print("DONE");
		ultraPID.disable();
		straightPID.end();
	}
}
