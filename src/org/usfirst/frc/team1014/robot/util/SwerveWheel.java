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
		driveSpeedController.set(speed);
		swerveSpeedController.set(angle);
	}
	
	public void center() {
		encoder.reset();
	}
	
	private double rotateGraph(double angle) {
		angle = angle % 1;
		if(angle < .25)
			return angle*4d;
		if(angle > .75)
			return (angle-1d)*4d;
		return (angle-.5)*4d;
	}

	public double getAngle()
	{
		return ((double) encoder.get()) / encoderCPR;
	}
}
