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
		ultraPID.setSetpoint(12);
		
		ultraPID.disable();
		
		straightPID.start();
	}
	
	@Override
	protected void execute()
	{
		SmartDashboard.putNumber("Distance", ultraPID.ultra.getRangeInches());
		if (ultraPID.ultra.getRangeInches() < 24 && !ultraPID.enabled)
		{
			straightPID.setRamping(false);
			ultraPID.enable();
			ultraPID.enabled = true;
			SmartDashboard.putNumber("Start", ultraPID.ultra.getRangeInches());
		}
	}
	
	@Override
	protected boolean isFinished()
	{
		return Math.abs(ultraPID.getPosition() - ultraPID.getSetpoint()) <= 0.5;
	}
	
	protected void end()
	{
		ultraPID.disable();
		straightPID.end();
	}
}
