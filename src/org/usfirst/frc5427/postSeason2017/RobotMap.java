// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc5427.postSeason2017;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	public static SpeedController driveTrainFrontRight;
	public static SpeedController driveTrainFrontLeft;
	public static SpeedController driveTrainRearLeft;
	public static SpeedController driveTrainRearRight;
	public static RobotDrive driveTrainRobotDrive4;

	public static void init()
	{
		driveTrainFrontRight = new Talon(0);
		LiveWindow.addActuator("DriveTrain", "FrontRight", (Talon) driveTrainFrontRight);

		driveTrainFrontLeft = new Talon(2);
		LiveWindow.addActuator("DriveTrain", "FrontLeft", (Talon) driveTrainFrontLeft);

		driveTrainRearLeft = new Talon(3);
		LiveWindow.addActuator("DriveTrain", "RearLeft", (Talon) driveTrainRearLeft);

		driveTrainRearRight = new Talon(1);
		LiveWindow.addActuator("DriveTrain", "RearRight", (Talon) driveTrainRearRight);

		driveTrainRobotDrive4 = new RobotDrive(driveTrainFrontLeft, driveTrainRearLeft, driveTrainFrontRight,
				driveTrainRearRight);

		driveTrainRobotDrive4.setSafetyEnabled(true);
		driveTrainRobotDrive4.setExpiration(0.1);
		driveTrainRobotDrive4.setSensitivity(0.5);
		driveTrainRobotDrive4.setMaxOutput(1.0);
		driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
	}
}
