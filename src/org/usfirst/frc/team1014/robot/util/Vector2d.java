package org.usfirst.frc.team1014.robot.util;

public class Vector2d {
	private double x, y;

	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public void normalize()
	{
		double magnitude = magnitude();
		x /= magnitude;
		y /= magnitude;
	}

	public double magnitude()
	{
		return Math.sqrt(x * x + y * y);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return new Vector2d(x, y);
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
