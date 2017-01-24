package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1014.robot.util.Vector2d;

import edu.wpi.first.wpilibj.command.Command;

public class TeleDrive extends Command {

	@Override
	protected void end() {
	}

	@Override
	public void execute() {
		if (OI.xboxController0.isAButtonPressed())
			DriveTrain.getInstance().center();
		if(OI.xboxController0.isBButtonPressed())
			DriveTrain.getInstance().zeroRotation();
		double rotation = OI.xboxController0.getRawAxis(0);
		DriveTrain.getInstance().drive(rotation,
				new Vector2d(OI.xboxController0.getRawAxis(4), -OI.xboxController0.getRawAxis(5)), !OI.xboxController0.isLBButtonPressed());
	}

	@Override
	protected void initialize() {
		requires(DriveTrain.getInstance());
	}

	@Override
	protected void interrupted() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
