
package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.RobotMap;
import org.usfirst.frc.team1014.robot.util.SwerveWheel;
import org.usfirst.frc.team1014.robot.util.Vector2d;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	private static final double ENCODER_CPR = 414.1666d;
	private static final double L = 1, W = 1;
	private static DriveTrain instance;

	public static DriveTrain getInstance()
	{
		if (instance == null)
			instance = new DriveTrain();
		return instance;
	}

	SwerveWheel wheelA, wheelB, wheelC, wheelD;

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
	}

	public void drive(double rotation, Vector2d translation)
	{
		if(Math.abs(rotation) < .2)
			rotation = 0;
		if(Math.abs(translation.getY()) < .2)
			translation.setY(0);
		if(Math.abs(translation.getX()) < .2)
			translation.setX(0);
		
		rotation *= -1;
		
		double angleAVG = wheelA.getAngle();
		angleAVG += rotation;
		System.out.print("AVG: " + angleAVG + " ");

		wheelA.drive(rotation, translation.getY(), 1);
//		wheelB.drive(angleAVG - wheelB.getAngle(), translation.getY(), 2);
//		wheelC.drive(angleAVG - wheelC.getAngle(), translation.getY(), 2);
//		wheelD.drive(angleAVG - wheelD.getAngle(), translation.getY(), 2);
		
		System.out.println();
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
