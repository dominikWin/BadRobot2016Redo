package org.usfirst.frc.team1014.robot.util;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class SwerveWheel {

	private static final int ANGLE_DIFF_COEFFICIENT = 1;

	SpeedController driveSpeedController;
	SpeedController swerveSpeedController;
	Encoder encoder;

	/*
	 * CPR is Counts per Rotation and is the product of encoders internal CPR
	 * and the gear ratio.
	 */
	double encoderCPR;

	public SwerveWheel(int driveMotorPin, int swerveMotorPin, int encoderAPin, int encoderBPin, double encoderCPR)
	{
		this.encoderCPR = encoderCPR;
		driveSpeedController = new CANTalon(driveMotorPin);
		swerveSpeedController = new CANTalon(swerveMotorPin);
		encoder = new Encoder(encoderAPin, encoderBPin);
	}

	public void drive(double angle, double speed, int id)
	{
		double turn_speed = angle;
		System.out.print(id + ": [" + getAngle() + " " + id + "R: " + angle + "] ");
		driveSpeedController.set(speed);
		swerveSpeedController.set(turn_speed);
	}
	
	public void center() {
		encoder.reset();
	}

	public double getAngle()
	{
		return ((double) encoder.get()) / encoderCPR;
	}
}
