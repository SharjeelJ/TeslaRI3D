package org.usfirst.frc.team1234.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	// Code Programmed by Sharjeel Junaid (https://www.facebook.com/sharjeel.junaid)

	/*
	 * 	START OF README
	 * 
	 * 	Wiring:
	 * 		Front Left Drive Motor = Port 2
	 * 		Front Right Drive Motor = Port 6
	 *  	Back Left Drive Motor = Port 3
	 *  	Back Right Drive Motor = Port 5
	 *  	Arm Motor = Port 1
	 *  	Left Wheel Motor (Or Lift Motor 1) = Port 4
	 *  	Right Wheel Motor (Or Lift Motor 2) = Port 7
	 *  
	 *  Buttons:
	 *  	A = Wheels Move To Fire Ball (Or Lift Moves Up)
	 *  	B = Wheels Move To Pull Ball Back In (Or Lift Moves Down)
	 *  	X = Arm Motor Moves To Push Ball Into The Mechanism
	 *  	Y = Arm Motor Moves Up Away From The Ground
	 *  	LB = Toggle Between 50% & 100% Power For The Mechanism Connected (Wheels / Lift)
	 *  	RB = Toggle Between 50% & 100% Power For The Drivetrain
	 *  	Back = Toggle Between 50% & 100% Power For The Arm Motor
	 *  	*** THE POWER VALUES CAN BE CUSTOMLY SET FROM THE DRIVERSTATION DISPLAY WINDOW AND NEED TO BE BETWEEN 0-100 (USE OUT OF BOUND / OVERCLOCKED VALUES AT YOUR OWN RISK) ***
	 *  
	 *  END OF README
	 */
	
	// Initialize Joystick & X + Y Axis
	private Joystick stick1 = new Joystick(0);
	private double stick1Y = 0;
	private double stick1X = 0;

	// Initialize Left & Right Drive Train Powers
	private double leftPower = 0;
	private double rightPower = 0;

	// Initialize Talon SRX Motors (The Number In () Is The Port # Of What Is Connected Where)
	private CANTalon frontLeftDriveMotor = new CANTalon(2);
	private CANTalon frontRightDriveMotor = new CANTalon(6);
	private CANTalon backLeftDriveMotor = new CANTalon(3);
	private CANTalon backRightDriveMotor = new CANTalon(5);
	private CANTalon armMotor = new CANTalon(1);
	private CANTalon leftWheelMotor = new CANTalon(4);
	private CANTalon rightWheelMotor = new CANTalon(7);

	// Initialize Variables To Scale Drivetrain & Arm Motor Power & Wheels / Lift Mechanism Power
	private double armMotorPower = 1;
	private double drivetrainPower = 1;
	private double mechanismPower = 1;

	// Initialize An Array To Store The Status Of The Joystick Buttons
	private boolean[] stick1BtnPressed = new boolean[10];

	// Code Run When The Robot Is Turned On
	public void robotInit()
	{
		// Adds Useful Values / Information To The Driver Station Window
		SmartDashboard.putNumber("Arm Motor Power (%)", armMotorPower * 100);
		SmartDashboard.putNumber("Drivetrain Power (%)", drivetrainPower * 100);
		SmartDashboard.putNumber("Wheels OR Lift Power (%)", mechanismPower * 100);
	}

	// Code Grabs Inputted Values From The Driver Station Dashboard Window
	public void disabledPeriodic()
	{
		getDisplayValues();
	}

	// Code Run Non Stop During The Teleop Mode
	public void teleopPeriodic() 
	{
		// Gets The Values From The Driver Station Dashboard
		getDisplayValues();

		// A Button (Wheels Fire / Lift Moves Up)
		if (stick1.getRawButton(1) && stick1BtnPressed[1] == false) 
		{
			stick1BtnPressed[1] = true;
			leftWheelMotor.set(1 * mechanismPower);
			rightWheelMotor.set(-1 * mechanismPower);

		}
		// Stops The Motor(s)
		else if (stick1.getRawButton(1) == false && stick1BtnPressed[1]) 
		{
			stick1BtnPressed[1] = false;
			leftWheelMotor.set(0);
			rightWheelMotor.set(0);
		}

		// B Button (Wheels Pull In / Lift Moves Down)
		if (stick1.getRawButton(2) && stick1BtnPressed[2] == false) 
		{
			stick1BtnPressed[2] = true;
			leftWheelMotor.set(-1 * mechanismPower);
			rightWheelMotor.set(1 * mechanismPower);
		}
		// Stops The Motor(s)
		else if (stick1.getRawButton(2) == false && stick1BtnPressed[2]) 
		{
			stick1BtnPressed[2] = false;
			leftWheelMotor.set(0);
			rightWheelMotor.set(0);
		}

		// X Button (Move Arm Forward)
		if (stick1.getRawButton(3) && stick1BtnPressed[3] == false) 
		{
			stick1BtnPressed[3] = true;
			armMotor.set(1 * armMotorPower);
		}
		// Stops The Motor(s)
		else if (stick1.getRawButton(3) == false && stick1BtnPressed[3]) 
		{
			stick1BtnPressed[3] = false;
			armMotor.set(0);
		}

		// Y Button (Move Arm Backwards)
		if (stick1.getRawButton(4) && stick1BtnPressed[4] == false) 
		{
			stick1BtnPressed[4] = true;
			armMotor.set(-1 * armMotorPower);
		}
		// Stops The Motor(s)
		else if (stick1.getRawButton(4) == false && stick1BtnPressed[4]) 
		{
			stick1BtnPressed[4] = false;
			armMotor.set(0);
		}

		// LB Button (Toggles Between 50% / 100% Wheel Or Lift Motors Power)
		if (stick1.getRawButton(5) && stick1BtnPressed[5] == false) 
		{
			stick1BtnPressed[5] = true;
			mechanismPower = 0.5;
		}
		// Toggles The Motor Power To 100%
		else if (stick1.getRawButton(5) && stick1BtnPressed[5] == true) 
		{
			stick1BtnPressed[5] = false;
			mechanismPower = 1;
		}

		// RB Button (Toggles Between 50% / 100% Drivetrain Motors Power)
		if (stick1.getRawButton(6) && stick1BtnPressed[6] == false) 
		{
			stick1BtnPressed[6] = true;
			drivetrainPower = 0.5;
		}
		// Toggles The Motor Power To 100%
		else if (stick1.getRawButton(6) && stick1BtnPressed[6] == true) 
		{
			stick1BtnPressed[6] = false;
			drivetrainPower = 1;
		}

		// Back Button (Toggles Between 50% / 100% Arm Motor Power)
		if (stick1.getRawButton(7) && stick1BtnPressed[7] == false) 
		{
			stick1BtnPressed[7] = true;
			armMotorPower = 0.5;
		}
		// Toggles The Motor Power To 100%
		else if (stick1.getRawButton(7) && stick1BtnPressed[7] == true) 
		{
			stick1BtnPressed[7] = false;
			armMotorPower = 1;
		}

		// Calls The Method To Get & Store The Input From The Joystick
		getInput();

		// Calls The Method To Update The Driver Station Window's Values
		updateDisplay();

		// Passes On The Input Grabbed From The Above Method To Move The Robot Around
		drive();
	}

	// Method To Get Called To Get The Input From The User
	public void getInput() 
	{
		// Gets The X & Y Axis Input From The Joystick's Left Stick
		stick1Y = stick1.getY();
		stick1X = stick1.getX();

		// Calculates The Final X & Y Values To Send To The Motors
		double x = (0.5*stick1X*stick1X*stick1X) + (0.5*stick1X);
		double y = (0.5*stick1Y*stick1Y*stick1Y) + (0.5*stick1Y);

		// If The Start Button Is Pressed Stops The Motors
		if (stick1.getRawButton(8) == false) 
		{
			leftPower = -(y-x) * drivetrainPower;
			rightPower = (y+x) * drivetrainPower;
		}
		// Stops The Motor(s)
		else 
		{
			leftPower = 0;
			rightPower = 0;
		}
	}

	// Method To Update The Visually Presented Data In The GUI Driver Station Window
	public void updateDisplay()
	{
		SmartDashboard.putNumber("Arm Motor Power (%)", armMotorPower * 100);
		SmartDashboard.putNumber("Drivetrain Power (%)", drivetrainPower * 100);
		SmartDashboard.putNumber("Wheels OR Lift Power (%)", mechanismPower * 100);
	}

	// Method To Get The Value From The Dash Board Window
	public void getDisplayValues()
	{
		armMotorPower = SmartDashboard.getNumber("Arm Motor Power (%)", armMotorPower) / 100;
		drivetrainPower = SmartDashboard.getNumber("Drivetrain Power (%)", drivetrainPower) / 100;
		mechanismPower = SmartDashboard.getNumber("Wheels OR Lift Power (%)", mechanismPower) / 100;
	}

	// Method To Set The Motors Speeds To What Has Been Stored From The getInput Method
	public void drive() 
	{
		frontLeftDriveMotor.set(leftPower);
		backLeftDriveMotor.set(leftPower);
		frontRightDriveMotor.set(rightPower);
		backRightDriveMotor.set(rightPower);
	}

}
