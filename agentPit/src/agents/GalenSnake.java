package agents;

import java.util.LinkedList;

import helpers.Point;
import pits.Pit;

public class GalenSnake {

	//lastDirection, 1 = West, 2 = East, South = 3, North = 4

	int lastDirection = 5;
	int newDirection = 1;
	LinkedList<Point> queue;
	Point oldApple;
	
	public GalenSnake() {
		//This code is executed when the snake is born:
		queue = new LinkedList<Point>();
		lastDirection = 5;
		System.out.println ("Hello, world! I AM A PYYYTTTHHOOONNN!!!");
	}

	public int move(Point head, Point apple) { //MUST STAY
		//This code is executed at every step:
		queue.add(head); //Add to the back of the queue
		if (apple.equals(oldApple)) {
			queue.poll(); // Remove from the front of the queue
		}
		oldApple = apple;
		if (head.x < apple.x){
			newDirection = Pit.EAST;
		}
		else if (head.x == apple.x){
			if (head.y > apple.y){
				newDirection = Pit.NORTH;
			}
			else {
				newDirection = Pit.SOUTH;
			}
		}
		else {
			newDirection = Pit.WEST;
		}
		
//		if (blurnt (lastDirection, newDirection)) {
		if (queue.contains(getPoint(head, newDirection))) {
		System.out.println("Turning");
			lastDirection = turnt (newDirection);
			return turnt (newDirection);
		}
		else {
			System.out.println("Going straight");
			lastDirection = newDirection;
			return newDirection;
		}
	}
	
	private Point getPoint(Point h, int d) {
		if (d == Pit.NORTH) {
			return new Point(h.x, h.y-1);
		}
		else if (d == Pit.SOUTH) {
			return new Point(h.x, h.y+1);
		}
		else if (d == Pit.EAST) {
			return new Point(h.x+1, h.y);
		}
		else {
			return new Point(h.x-1, h.y);
		}
		
	}
	
	private boolean blurnt(int l, int n) {
		if (n == Pit.SOUTH) {
			return l == Pit.NORTH;
		}
		if (n == Pit.NORTH) {
			return l == Pit.SOUTH;
		}
		if (n == Pit.EAST) {
			return l == Pit.WEST;
		}
		if (n == Pit.WEST) {
			return l == Pit.EAST;
		}
		return false;
	}
	
	private int turnt(int d) {
		if (d == Pit.WEST) { 
			return Pit.NORTH;
		} 
		else if (d == Pit.EAST) {
			return Pit.SOUTH;
		}
		else if (d == Pit.SOUTH) {
			return Pit.EAST;
		}
		else {
			return Pit.WEST;
		}
	}
}
