package org.usfirst.frc.team1014.robot.util;

import org.opencv.core.Mat;

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

	public SwerveWheel(int driveMotorPin, int swerveMotorPin, int encoderAPin, int encoderBPin, double encoderCPR) {
		this.encoderCPR = encoderCPR;
		driveSpeedController = new CANTalon(driveMotorPin);
		swerveSpeedController = new CANTalon(swerveMotorPin);
		encoder = new Encoder(encoderAPin, encoderBPin);
	}

	public void drive(double angle, double speed, int id, SpeedControllerNormalizer normalizer) {
		angle /= 2d * Math.PI;
		double turn_speed = rotateFunc(angle);
//		System.out.print(id + ": [" + getAngle() + " " + id + "R: " + angle + "] ");
		if ((angle - getAngle()) % 1 > .25 && (angle - getAngle()) % 1 < .75)
			speed = -speed;
		normalizer.add(driveSpeedController, speed);
		swerveSpeedController.set(turn_speed);
	}

	private double rotateFunc(double angle) {
		angle -= getAngle();
		angle = angle % 1d;
		if (angle < .25)
			return angle * 4;
		if (angle > .75)
			return (angle - 1) * 4;
		return (angle - .5) * 4;
	}

	public void center() {
		encoder.reset();
	}

	public double getAngle() {
		return ((double) encoder.get()) / encoderCPR;
	}
}
