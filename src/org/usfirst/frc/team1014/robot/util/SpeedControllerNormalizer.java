package org.usfirst.frc.team1014.robot.util;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.SpeedController;

public class SpeedControllerNormalizer {

	double maxSpeed = 0;

	private class UnsetSpeedController {
		private UnsetSpeedController(SpeedController speedController, double speed) {
			controller = speedController;
			this.speed = speed;
		}

		SpeedController controller;
		double speed;
	}

	private List<UnsetSpeedController> controllers = new ArrayList<>();

	public void add(SpeedController controller, double speed) {
		maxSpeed = Math.max(Math.abs(speed), Math.abs(maxSpeed));
		controllers.add(new UnsetSpeedController(controller, speed));
	}

	public void run() {
		if (maxSpeed <= 1)
			controllers.forEach((c) -> c.controller.set(c.speed));
		else
			controllers.forEach((c) -> c.controller.set(c.speed / maxSpeed));
	}

	public void clear() {
		controllers.clear();
	}
}
