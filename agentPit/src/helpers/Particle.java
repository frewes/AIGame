package helpers;

public class Particle {
	
	public double x, y;
	public double angle;
	public double speed;
	
	public Particle(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = 0;

	}
	
	public void step() {
		x += speed*Math.cos(Math.toRadians(angle));
		y += speed*Math.sin(Math.toRadians(angle));
	}
	
	public void addToAngle(double amt) {
		angle += amt;
		angle = angle % 360;
	}

}
