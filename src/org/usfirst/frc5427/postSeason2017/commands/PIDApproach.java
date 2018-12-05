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
		straightPID = new PIDStraightMovement(0.2);// TODO: maximum speed, add
													// to config
		ultraPID.setSetpoint(24);
		
		ultraPID.disable();
		
		straightPID.start();
	}
	
	@Override
	protected void execute()
	{
		
		if (ultraPID.ultra.getRangeInches() <= 48 && !ultraPID.enabled)
		{
			SmartDashboard.putNumber("Start", ultraPID.getDistance());
			straightPID.setRamping(false);
			ultraPID.enable();
			ultraPID.enabled = true;
		}
	}
	
	@Override
	protected boolean isFinished()
	{
		SmartDashboard.putNumber("Setpoint" , ultraPID.getSetpoint());
		SmartDashboard.putNumber("Ultra Dist", ultraPID.getDistance());
		SmartDashboard.putNumber("Error" , (ultraPID.getDistance() - ultraPID.getSetpoint()));
//		return Math.abs(ultraPID.getDistance() - ultraPID.getSetpoint()) <= 0.5;
		return false;
	}
	
	protected void end()
	{
		ultraPID.disable();
		straightPID.end();
	}
}
