package jmp123.gui;

import java.awt.Color;

public class star {
	private int x,y;
	private int speed;
	private int size;
	private double angle;
	private Color color;
	public int getSpeed()
	{
		return speed;
	}
	public void setSpeed(int sp)
	{
		this.speed=sp;
	}
	public int getX()
	{
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}
