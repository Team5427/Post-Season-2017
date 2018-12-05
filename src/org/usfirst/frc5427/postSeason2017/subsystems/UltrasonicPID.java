package org.usfirst.frc5427.postSeason2017.subsystems;

import org.usfirst.frc5427.postSeason2017.Robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UltrasonicPID extends PIDSubsystem
{
	public Ultrasonic ultra;
	
	public SpeedControllerGroup scgControlled;
	
	public boolean enabled;
	
	public double currentDistance;
	
	public UltrasonicPID(int ping , int echo)
	{
		super(0.2 , 0.0 , 0.8);
		ultra = new Ultrasonic(ping , echo);
		ultra.setAutomaticMode(true);
		
		scgControlled = Robot.driveTrain.drive_Right;
		
		
		this.setOutputRange(-1 , 1);
		this.setAbsoluteTolerance(0.1);
		
		enabled = false;
	}
	
	public void updateDistance()
	{
		this.currentDistance = ultra.getRangeInches();
	}
	
	public double getDistance()
	{
		return this.currentDistance;
	}
	
	@Override
	protected double returnPIDInput()
	{
		return ultra.getRangeInches();
	}
	
	@Override
	protected void usePIDOutput(double output)
	{
		SmartDashboard.putNumber("Right Motor" , output);
		SmartDashboard.putNumber("Left Motor", Robot.driveTrain.drive_Left.get());
		scgControlled.pidWrite(-output);
	}
	
	@Override
	protected void initDefaultCommand()
	{
	}
	
	@Override
	public void disable()
	{
		super.disable();
		enabled = false;
	}
}
