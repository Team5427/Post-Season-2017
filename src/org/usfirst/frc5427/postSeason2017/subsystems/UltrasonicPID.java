package org.usfirst.frc5427.postSeason2017.subsystems;

import org.usfirst.frc5427.postSeason2017.Robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class UltrasonicPID extends PIDSubsystem
{
	public Ultrasonic ultra;
	
	public SpeedControllerGroup scgControlled;
	
	public UltrasonicPID(int ping, int echo)
	{
		super(0.033, 0, 0.03);
		ultra = new Ultrasonic(ping, echo);
		ultra.setAutomaticMode(true);
		
		scgControlled = Robot.driveTrain.drive_Right;
		
		this.setOutputRange(-1, 1);
		this.setAbsoluteTolerance(0.1);
	}

	@Override
	protected double returnPIDInput()
	{
		return ultra.getRangeInches();
	}

	@Override
	protected void usePIDOutput(double output)
	{
		scgControlled.pidWrite(output);
	}

	@Override
	protected void initDefaultCommand()
	{
	}
}
