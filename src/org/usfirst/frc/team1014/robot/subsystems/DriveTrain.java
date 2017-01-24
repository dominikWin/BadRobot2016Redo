
package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.RobotMap;
import org.usfirst.frc.team1014.robot.util.SpeedControllerNormalizer;
import org.usfirst.frc.team1014.robot.util.SwerveWheel;
import org.usfirst.frc.team1014.robot.util.Vector2d;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	private static final double ENCODER_CPR = 414.1666d;
	private static final double L = 1, W = 1;
	private static DriveTrain instance;
	SpeedControllerNormalizer normalizer;

	public static DriveTrain getInstance()
	{
		if (instance == null)
			instance = new DriveTrain();
		return instance;
	}

	SwerveWheel wheelA, wheelB, wheelC, wheelD;
	IMU imu;
	SerialPort mxpPort;

	private DriveTrain()
	{
		super();
		wheelA = new SwerveWheel(RobotMap.DRIVE_MOTOR_A, RobotMap.PIVOT_MOTOR_A, RobotMap.PIVOT_ENCODER_AA,
				RobotMap.PIVOT_ENCODER_AB, ENCODER_CPR);
		wheelB = new SwerveWheel(RobotMap.DRIVE_MOTOR_B, RobotMap.PIVOT_MOTOR_B, RobotMap.PIVOT_ENCODER_BA,
				RobotMap.PIVOT_ENCODER_BB, ENCODER_CPR);
		wheelC = new SwerveWheel(RobotMap.DRIVE_MOTOR_C, RobotMap.PIVOT_MOTOR_C, RobotMap.PIVOT_ENCODER_CA,
				RobotMap.PIVOT_ENCODER_CB, ENCODER_CPR);
		wheelD = new SwerveWheel(RobotMap.DRIVE_MOTOR_D, RobotMap.PIVOT_MOTOR_D, RobotMap.PIVOT_ENCODER_DA,
				RobotMap.PIVOT_ENCODER_DB, ENCODER_CPR);
		mxpPort = new SerialPort(57600, SerialPort.Port.kMXP);
		imu = new IMU(mxpPort, (byte) 127);
		normalizer = new  SpeedControllerNormalizer();
	}

	public void drive(double rotation, Vector2d translation, boolean fieldCentric)
	{
		if(Math.abs(rotation) < .2)
			rotation = 0;
		if(Math.abs(translation.getY()) < .2)
			translation.setY(0);
		if(Math.abs(translation.getX()) < .2)
			translation.setX(0);
		
		double robotAngle = Math.toRadians(imu.getYaw());
		
		System.out.println("ANGLE: " + robotAngle);
		System.out.println("IN: " + translation);
		
		if (fieldCentric) {
			double x;
			double y;
			x = translation.getX() * Math.cos(robotAngle) - translation.getY() * Math.sin(robotAngle);
			y = translation.getY() * Math.cos(robotAngle) + translation.getX() * Math.sin(robotAngle);
			translation.setX(x);
			translation.setY(y);
		}
		System.out.println("OUT: " + translation);
		
		double a = translation.getX() - rotation * L / 2;
		double b = translation.getX() + rotation * L / 2;
		double c = translation.getY() - rotation * W / 2;
		double d = translation.getY() + rotation * W / 2;

		wheelA.drive(-Math.atan2(b, c), speed(b, c), 1, normalizer);
		wheelB.drive(-Math.atan2(b, d), speed(b, d), 2, normalizer);
		wheelC.drive(-Math.atan2(a, d), speed(a, d), 3, normalizer);
		wheelD.drive(-Math.atan2(a, c), speed(a, c), 4, normalizer);
		
		normalizer.run();
		normalizer.clear();
		
		System.out.println();
	}
	
	public void zeroRotation() {
		imu.zeroYaw();
	}
	
	public void center() {
		wheelA.center();
		wheelB.center();
		wheelC.center();
		wheelD.center();
	}

	private double speed(double x, double y)
	{
		return Math.sqrt(x * x + y * y);
	}

	@Override
	protected void initDefaultCommand()
	{
	}

}
