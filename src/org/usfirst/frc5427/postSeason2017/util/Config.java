package org.usfirst.frc5427.postSeason2017.util;

/**
 * This file will store ALL of the variables, offsets, measurements, etc. that
 * our robot will use during the year. All variables are to be static, and
 * nothing in this file should ever have to be initiated.
 * 
 * @author Andrew Kennedy, Bo Corman
 */
public class Config {

	/**
	 * The name of our program as per the robot.
	 */
	public static final String PROGRAM_NAME = "Team5427RoboCode";

	/**
	 * Stores whether the robot code is in debug mode or not.
	 */
	public static final boolean DEBUG_MODE = true;

	/**
	 * Stores whether the robot code is sending messages to the log file.
	 */
	public static final boolean LOGGING = true;

	/******************** PWM PORTS *******************/
	/**
	 * The PWM value for the front right motor of the drive train.
	 */
	public static final int FRONT_RIGHT_MOTOR = 0;

	/**
	 * The PWM value for the rear right motor of the drive train.
	 */
	public static final int REAR_RIGHT_MOTOR = 1;

	/**
	 * The PWM value for the front left motor of the drive train.
	 */
	public static final int FRONT_LEFT_MOTOR = 2;

	/**
	 * The PWM value for the rear left motor of the drive train.
	 */
	public static final int REAR_LEFT_MOTOR = 3;

	/*************************************************/

	/******************** CONTROLLER PORTS *******************/
	/**
	 * The port associated with the main joystick.
	 */
	public static final int JOYSTICK_PORT = 0;

	/**
	 * The port associated with a second joystick.
	 */
	public static final int ALT_JOYSTICK_PORT = 0;

	/**
	 * The mode designating that we are using one joystick.
	 */
	public static final int ONE_JOYSTICK = 0;

	/**
	 * The mode designating that we are using two joysticks.
	 */
	public static final int TWO_JOYSTICKS = 1;

	/**
	 * Stores what mode of controller use we are currently using.
	 */
	public static final int JOYSTICK_MODE = ONE_JOYSTICK;
	/*********************************************************/

	/******************** PID VALUES *******************/
	/**
	 * The update period used inside of the PIDControllers.
	 */
	public static final double PID_UPDATE_PERIOD = 0.01;

	/**
	 * The power used to move short distances in autonomous.
	 */
	public static final double PID_STRAIGHT_POWER_SHORT = 0.5;

	/**
	 * The power used to move medium distances in autonomous.
	 */
	public static final double PID_STRAIGHT_POWER_MED = .4;

	/**
	 * The power used to move long distances in autonomous.
	 */
	public static final double PID_STRAIGHT_POWER_LONG = 0.7;

	/**
	 * The power used to coast distances in autonomous.
	 */
	public static final double PID_STRAIGHT_COAST_POWER = 0.01;

	/**
	 * The distance the robot needs to travel before the straight PID loop
	 * activates.
	 */
	public static final double PID_STRAIGHT_ACTIVATE_DISTANCE = 20;

	/**
	 * The tolerance, in degrees, used while moving straight.
	 */
	public static final double PID_STRAIGHT_TOLERANCE = 3;
	
	/**
	 * The tolerance, in inches, used when moving to a distance.
	 */
	public static final double PID_DISTANCE_TOLERANCE = 5;

	/**
	 * The starting power used while turning to an angle.
	 */
	public static final double PID_TURN_POWER = 0.1;

	/**
	 * The default setpoint for a turn.
	 */
	public static final double PID_TURN_SETPOINT = 90;

	/**
	 * The tolerance, in degrees, used while turning.
	 */
	public static final double PID_TURN_TOLERANCE = 3;

	/**
	 * The P value used for moving straight.
	 */
	public static final double PID_STRAIGHT_P = .085;

	/**
	 * The I value used for moving straight.
	 */
	public static final double PID_STRAIGHT_I = 0.008333;

	/**
	 * The D value used for moving straight.
	 */
	public static final double PID_STRAIGHT_D = .001042;

	/**
	 * The P value used for coasting .
	 */
	public static final double PID_STRAIGHT_COAST_P = 0.275;

	/**
	 * The I value used for coasting.
	 */
	public static final double PID_STRAIGHT_COAST_I = 0.012333;

	/**
	 * The D value used for coasting.
	 */
	public static final double PID_STRAIGHT_COAST_D = 0.0;

	/**
	 * The P value used for turning.
	 */
	public static final double PID_TURN_P = 0.01;

	/**
	 * The I value used for turning.
	 */
	public static final double PID_TURN_I = 0.0002;

	/**
	 * The D value used for turning.
	 */
	public static final double PID_TURN_D = 0.06;
	/***************************************************/

	/******************** INCREMENT *******************/
	/**
	 * The linear increment used in PIDControllers.
	 */
	public static final double PID_STRAIGHT_LINEAR_INCREMENT = .003;

	/**
	 * The exponential increment used in PIDControllers.
	 */
	public static final double PID_STRAIGHT_EXPONENTIAL_INCREMENT = 1.05;

	/**
	 * The increment used while driving.
	 */
	public static final double DRIVE_SPEED_INCREMENT_VALUE = .01;
	/**************************************************/

	/******************** AUTO CHOOSER *******************/
	/**
	 * The default option in the autonomous chooser.
	 */
	public static final int AUTO_NONE = -1;

	/**
	 * The right selection for choosing the starting position in the autonomous
	 * chooser.
	 */
	public static final int RIGHT = 1;

	/**
	 * The center selection for choosing the starting position in the autonomous
	 * chooser.
	 */
	public static final int CENTER = 2;

	/**
	 * The left selection for choosing the starting the position in the autonomous
	 * chooser.
	 */
	public static final int LEFT = 3;

	/**
	 * The switch selection for choosing the destination in the autonomous chooser.
	 */
	public static final int SWITCH = 1;

	/**
	 * The scale selection for choosing the destination in the autonomous chooser.
	 */
	public static final int SCALE = 2;
	/****************************************************/

	/******************** MISCELLANEOUS *******************/
	/**
	 * The value used in PIDControllers to determine when to switch to using the PID
	 * loop.
	 */
	public static final double POST_INCR_SWITCH_TO_PID = .1;

	/**
	 * The value used to determine when to use the driving increment.
	 */
	public static final double DRIVE_INCREMENT_WAIT_VALUE = .01;

	public static final double FALLING_THRESHOLD_DEGREES = 15.0;
	
	public static final double OFF_BALANCE_THRESHOLD_DEGREES = 8.0;
	
	public static final double ENCODER_DISTANCE_OFFSET = 0.9752;
	/******************************************************/
}
