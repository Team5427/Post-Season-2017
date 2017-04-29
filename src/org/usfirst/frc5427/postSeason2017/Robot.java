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
public class Robot extends IterativeRobot implements PIDOutput  {

    Command autonomousCommand;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static AHRS ahrs;
	public static PIDController turnControllerStraight;
	public static PIDController turnControllerRotate;


	static double kPS = 0.085000;
	static double kIS = 0.008333;
//	static double kDS = 0;
	static double kDS = 0.001042;

	static double kToleranceDegrees = 1.0f;
	double rotateToAngleRate = 0;

		float startYaw = 0;

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
        //(which it very likely will), subsystems are not guaranteed to be
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
		
		LiveWindow.addActuator("turnControllerStraight", "PID Table", turnControllerStraight);
		LiveWindow.addActuator("Navx", "Ahrs", ahrs);
//		SmartDashboard.putData("dfsdfds", turnControllerStraight.getSmartDashboardType());
//		LiveWindow.addActuator("drivetrain", "Drivetrain", turnControllerStraight.getPIDController());
    	LiveWindow.run();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
		   oi = new OI();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
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
       if (autonomousCommand != null) autonomousCommand.cancel();
    
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    double rightMotorSpeed = 0;
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	double currentRotationRate = rotateToAngleRate;
    	
    	if(System.nanoTime()/1000000000 - startTime < 7)
    	{
    		 SmartDashboard.putNumber("PID Output: ", rotateToAngleRate);
    		 SmartDashboard.putNumber("Yaw: ", ahrs.getYaw());
    		
//    		driveTrain.robotDrive4.drive(-.3, 0);
    		driveTrain.robotDrive4.setLeftRightMotorOutputs(-(currentRotationRate), rightMotorSpeed);
    		
    		if(rightMotorSpeed>-.3)
    			rightMotorSpeed-=0.006;
    		
    		if(rightMotorSpeed<-.3)
    			rightMotorSpeed = -.3;
    		
    		
    		
    	}
    	else
    	{
    		turnControllerStraight.disable();
    		turnControllerStraight.stopLiveWindowMode();
    		driveTrain.stop();
    	}
    	
    	
    	
    }
    public double startTime;
    
    public void testInit()
    {
    	turnControllerStraight.setSetpoint(0);
    	ahrs.reset();
    	try {
    		Thread.sleep(500);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	startTime = System.nanoTime()/1000000000.;
    	turnControllerStraight.enable();
    

    }
	public void pidWrite(double output) {
		
		// TODO Auto-generated method stub
		if (output != 0) {
			//System.out.println("Output: " + output);
		}
		rotateToAngleRate = output;

	}
   
}
