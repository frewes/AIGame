package helpers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Diff {

	private double x, y;
	public double rSpeed, lSpeed;
	private double angle;
	private int length, height;
		
	public Diff(Point center, int length, int height) {
		this.x = center.x;
		this.y = center.y;
		this.angle = 45;
		this.rSpeed = 0;
		this.lSpeed = 0;
		this.length = length;
		this.height = height;
	}
	
	public void step() {
		double translation = (lSpeed + rSpeed)/2;
		double rotation = (lSpeed - rSpeed)/length;
		x += translation*Math.cos(Math.toRadians(angle));
		y += translation*Math.sin(Math.toRadians(angle));
		angle = (angle + rotation)%360;
	}
	
	public void addToAngle(int a) {
		angle = (angle + a)%360;
	}
	
	/**
	rSpeed = a + d
	lSpeed = a
	
	l = -1
	r = 1
	 */
	
	public void draw(Graphics g1, Color c) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(c);
		g.setStroke(new BasicStroke(height));
		
		double xL = x - length*Math.cos(Math.toRadians(90-angle));
		double yL = y + length*Math.sin(Math.toRadians(90-angle));
		double xR = x + length*Math.cos(Math.toRadians(90-angle));
		double yR = y - length*Math.sin(Math.toRadians(90-angle));
		g.setColor(c);
		g.drawLine((int)xL, (int)yL, (int)xR, (int)yR);
	}
}
