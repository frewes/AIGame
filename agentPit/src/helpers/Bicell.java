package helpers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bicell {
	
	public static int WHEEL_RADIUS = 1;
	
	private Particle l, r;
	private int length, height;
	private double lSpeed, rSpeed;
	
	public Bicell(int length, int height) {
		l = new Particle(100, 100, 0);
		r = new Particle(100+length, 100, 0);
		this.length = length;
		this.height = height;
	
	}
	
	public void setSpeed(double left, double right) {
		lSpeed = left;
		rSpeed = right;
	}
	
	public void step() {
		//Left:
		double arc = lSpeed*WHEEL_RADIUS;
		double theta = arc/length;
		//Find global angle.
		double xDiff = r.x - l.x; 
		double yDiff = r.y - l.y; 
		double alpha = Math.atan2(yDiff, xDiff);
		double xL = r.x + length*Math.cos(theta-alpha);
		double yL = r.y + length*Math.sin(theta-alpha);
		
		//Right: 
		arc = rSpeed*WHEEL_RADIUS;
		theta = arc/length;
		//Find global angle.
		xDiff = l.x - r.x; 
		yDiff = l.y - r.y; 
		 alpha = Math.atan2(-yDiff, -xDiff);
		double xR = l.x + length*Math.cos(theta-alpha);
		double yR = l.y + length*Math.sin(theta-alpha);

		l.x = xL; l.y = yL; r.x = xR; r.y = yR;
	}
	
	public void draw(Graphics g1, Color c) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(c);
		g.setStroke(new BasicStroke(height));
		
		g.setColor(c);
		g.drawLine((int)l.x, (int)l.y, (int)r.x, (int)r.y);
	}

}
