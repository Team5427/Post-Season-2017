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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5427.postSeason2017.commands.*;
import org.usfirst.frc5427.postSeason2017.subsystems.*;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements PIDOutput {

	Command autonomousCommand;

	public static OI oi;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static DriveTrain driveTrain;
	public static AHRS ahrs;
	public static PIDController turnControllerStraight;
	public static PIDController turnControllerRotate;
	public static PIDController turnControllerRotate2;
	public static Encoder encoderLeft;
	public static Encoder encoderRight;

	public double rightMotorSpeed = 0;
	public double setpoint = 0;

	//GraphPanel graph = new GraphPanel();

	static double kPS = 0.085000;
	static double kIS = 0.008333;
	static double kDS = 0.001042;

	// Needs to be tuned
	// static double kPR = 0.01;
	static double kPR = 0.01;
	// static double kIR = 0.0005;
	static double kIR = 0.0002;
	// static double kDR = 0.05;
	static double kDR = 0.06;

	static double kPT = 0.01;
	static double kIT = 0.00121;
	static double kDT = 0;

	static double kToleranceDegrees = 1.0f;
	double rotateToAngleRate = 0;

	float startYaw = 0;
	static double startTime;

	static double targetYaw = 150;
	static int countsBtwnTenths = 2;
	static int counter = 0;
	static double rampedSetpoint;

	static boolean timeLoop;
	static long loopPreviousTime;
	static long loopCurrentTime;
	static long targetTime;

	// static boolean negative;
	// TODO research motion profiling

	public boolean ramp(double desiredSetPoint) {
		// double curYaw=ahrs.getYaw();
		if (desiredSetPoint <= rampedSetpoint) {
			rampedSetpoint = desiredSetPoint;
			return false;
		}
		rampedSetpoint += 2;
		SmartDashboard.putNumber("RampedSetpoint", rampedSetpoint);

		// System.out.print("CY:"+curYaw);

		return true;
	}

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		RobotMap.init();

		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		driveTrain = new DriveTrain();

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.

		// instantiate the command used for the autonomous period
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

		autonomousCommand = new AutonomousCommand();
		try {

			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			/*
			 * Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or
			 * SerialPort.Port.kUSB
			 */
			/*
			 * See
			 * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/
			 * for details.
			 */
			ahrs = new AHRS(SPI.Port.kMXP) {
				@Override
				public double pidGet() {
					return ahrs.getYaw();
				}
			};

		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
		}

		turnControllerStraight = new PIDController(kPS, kIS, kDS, ahrs, this);
		turnControllerStraight.setInputRange(-180.0f, 180.0f);
		turnControllerStraight.setOutputRange(-1.0, 1.0);
		turnControllerStraight.setAbsoluteTolerance(kToleranceDegrees);
		turnControllerStraight.setContinuous(true);
		turnControllerStraight.startLiveWindowMode();

		turnControllerRotate = new PIDController(kPR, kIR, kDR, ahrs, this);
		turnControllerRotate.setInputRange(-180.0f, 180.0f);
		turnControllerRotate.setOutputRange(-1, 1);
		turnControllerRotate.setAbsoluteTolerance(kToleranceDegrees);
		turnControllerRotate.setContinuous(true);
		turnControllerRotate.startLiveWindowMode();

		turnControllerRotate2 = new PIDController(kPT, kIT, kDT, ahrs, this);
		turnControllerRotate2.setInputRange(-180.0f, 180.0f);
		turnControllerRotate2.setOutputRange(-0.5, 0.5);
		turnControllerRotate2.setAbsoluteTolerance(kToleranceDegrees);
		turnControllerRotate2.setContinuous(true);
		turnControllerRotate2.startLiveWindowMode();
		turnControllerRotate2.disable();

		LiveWindow.addActuator("turnControllerStraight", "PID Table", turnControllerStraight);
		LiveWindow.addActuator("Navx", "Ahrs", ahrs);
		// SmartDashboard.putData("dfsdfds",
		// turnControllerStraight.getSmartDashboardType());
		// LiveWindow.addActuator("drivetrain", "Drivetrain",
		// turnControllerStraight.getPIDController());
		LiveWindow.run();

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
		oi = new OI();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// SmartDashboard.putNumber("RampedSetpoint",
		// currentManualRampingSetpoint);
		Scheduler.getInstance().run();
	}

	public void testInit() {
		ahrs.reset();
		// turnControllerStraight.setSetpoint(0);
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		startTime = System.nanoTime() / 1000000000.;
		rightMotorSpeed = 0;
		turnControllerStraight.enable();
		// setPoint = 0;
		// rightMotorSpeed = 0;
		//
		// turnControllerRotate.setSetpoint(0);
		// ahrs.reset();
		// try {
		// Thread.sleep(500);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// startTime = System.nanoTime()/1000000000.;
		// turnControllerRotate.enable();
		// rampedSetpoint=ahrs.getYaw();
		//// counter=0;
		//
		// targetTime = 40000000;
		// loopPreviousTime = System.nanoTime();
	}

	/**
	 * This function is called periodically during test mode - around 50 times a
	 * second
	 */
	public void testPeriodic() {
		double currentRotationRate = rotateToAngleRate;
		//TODO Change Values
		//graph.update(Math.random()*100, 0);
		
		SmartDashboard.putNumber("PID Output: ", rotateToAngleRate);
		SmartDashboard.putNumber("Yaw Textbox", ahrs.getYaw());
		SmartDashboard.putNumber("Yaw: ", ahrs.getYaw());
		SmartDashboard.putNumber("Setpoint: ", setpoint);
		// SmartDashboard.putNumber("P: ", kPR);
		// SmartDashboard.putNumber("I: ", kIR);
		// SmartDashboard.putNumber("D: ", kDR);
		SmartDashboard.putNumber("Yaw and Setpoint Difference: ", setpoint - ahrs.getYaw());
		// SmartDashboard.putNumber("RampedSetpoint", rampedSetpoint);
		// Code for straight
		if (System.nanoTime() / 1000000000. - startTime < 3.85) {
			driveTrain.robotDrive4.setLeftRightMotorOutputs(-(currentRotationRate), rightMotorSpeed);
			if (rightMotorSpeed > -.3) {
				rightMotorSpeed -= .006;
			}
			if (rightMotorSpeed < -.3) {
				rightMotorSpeed = -.3;
			}
		} else if (System.nanoTime() / 1000000000. - startTime < 7.85) {
			if (turnControllerStraight.isEnabled()) {
				turnControllerStraight.disable();
				turnControllerRotate.enable();
				targetTime = 40000000;
				loopPreviousTime = System.nanoTime();
				rampedSetpoint = ahrs.getYaw();
				targetYaw = 90;
				rightMotorSpeed = 0;
			}
			loopCurrentTime = System.nanoTime();
			if (loopCurrentTime - loopPreviousTime >= targetTime) {
				ramp(targetYaw);
				loopPreviousTime = loopCurrentTime;
			}
			driveTrain.robotDrive4.drive(-.3, 0);
			driveTrain.robotDrive4.setLeftRightMotorOutputs(-currentRotationRate, currentRotationRate);
			turnControllerRotate.setSetpoint(rampedSetpoint);
		} else if (System.nanoTime() / 1000000000. - startTime < 10.9) {
			if (turnControllerRotate.isEnabled()) {
				turnControllerRotate.disable();
				turnControllerStraight.enable();
				ahrs.reset();
			}
			driveTrain.robotDrive4.setLeftRightMotorOutputs(-(currentRotationRate), rightMotorSpeed);
			if (rightMotorSpeed > -.3) {
				rightMotorSpeed -= .006;
			}
			if (rightMotorSpeed < -.3) {
				rightMotorSpeed = -.3;
			}
		} else {
			turnControllerStraight.disable();
			driveTrain.stop();
		}

		//
		//// counter++;
		// double currentRotationRate = rotateToAngleRate;
		// //SmartDashboard.putNumber("RampedSetpoint",
		// currentManualRampingSetpoint);
		//// if(counter>=countsBtwnTenths)
		//// {
		//// if(ramp(targetYaw))
		//// counter=0;
		//// }
		//
		// loopCurrentTime = System.nanoTime();
		// if(loopCurrentTime-loopPreviousTime>=targetTime)
		// {
		// ramp(targetYaw);
		// loopPreviousTime = loopCurrentTime;
		// }
		// if(System.nanoTime()/1000000000. - startTime < 4)
		// {
		//// setPoint = 90;
		//
		// SmartDashboard.putNumber("PID Output: ", rotateToAngleRate);
		// SmartDashboard.putNumber("Yaw Textbox" , ahrs.getYaw());
		// SmartDashboard.putNumber("Yaw: ", ahrs.getYaw());
		//// SmartDashboard.putNumber("Setpoint: ", setPoint);
		// SmartDashboard.putNumber("P: ", kPR);
		// SmartDashboard.putNumber("I: ", kIR);
		// SmartDashboard.putNumber("D: ", kDR);
		// SmartDashboard.putNumber("Yaw and Setpoint Difference: ", setPoint -
		// ahrs.getYaw());
		// SmartDashboard.putNumber("RampedSetpoint", rampedSetpoint);
		//
		//
		//
		//// driveTrain.robotDrive4.drive(-.3, 0);
		// driveTrain.robotDrive4.setLeftRightMotorOutputs(-(currentRotationRate),
		// currentRotationRate);
		// System.out.println(currentRotationRate);
		// turnControllerRotate.setSetpoint(rampedSetpoint); //setPoint
		//
		// // For 2nd PID loop
		// //turnControllerRotate2.setSetpoint(setPoint);
		//
		/// * if (ahrs.getYaw() > 85f && turnControllerRotate.getI() != kIT) {
		// turnControllerRotate.reset();
		// turnControllerRotate.setPID(kPR, kIT, kDR);
		// turnControllerRotate.enable();
		// //turnControllerRotate2.enable();
		// }*/
		//
		//// if(setPoint<90)
		//// setPoint+=.9;
		////
		//// if(setPoint>90)
		//// setPoint = 90;
		//// if(rightMotorSpeed>-.3)
		//// rightMotorSpeed-=0.006;
		////
		//// if(rightMotorSpeed<-.3)
		//// rightMotorSpeed = -.3;
		// }
		//// else if(System.nanoTime()/1000000000. - startTime < 4)
		//// {
		//// if(b)
		//// {
		//// turnControllerStraight.disable();
		//// turnControllerStraight.stopLiveWindowMode();
		//// turnControllerRotate.enable();
		//// }
		////
		//// SmartDashboard.putNumber("PID Output: ", rotateToAngleRate);
		//// SmartDashboard.putNumber("Yaw: ", ahrs.getYaw());
		////
		//// driveTrain.robotDrive4.setLeftRightMotorOutputs(-(currentRotationRate),
		// rightMotorSpeed);
		////
		//// turnControllerRotate.setSetpoint(setPoint);
		////
		//// if(setPoint<90)
		//// setPoint+=.9;
		////
		//// if(setPoint>90)
		//// setPoint = 90;
		////
		//// b = false;
		//// }
		// else
		// {
		// turnControllerRotate.disable();
		// turnControllerRotate.stopLiveWindowMode();
		// driveTrain.stop();
		// }

	}

	public void pidWrite(double output) {

		// TODO Auto-generated method stub
		if (output != 0) {
			// System.out.println("Output: " + output);
		}
		rotateToAngleRate = output;

	}

}
