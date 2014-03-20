package agents;

import helpers.Point;
import pits.Pit;

public class Snake {

	//lastDirection, 1 = West, 2 = East, South = 3, North = 4

	int lastDirection = 5;
	int newDirection = 1;

	public Snake() {
		//This code is executed when the snake is born:
		lastDirection = 5;
		System.out.println ("Hello, world! I AM A PYYYTTTHHOOONNN!!!");
	}

	public int move(Point head, Point apple) { //MUST STAY
		//This code is executed at every step:
		//First move, is not restricted and should be able to move in any direction
		if (head.x < apple.x){
			newDirection = 2;
		}
		else if (head.x == apple.x){
			if (head.y > apple.y){
				newDirection = 4;
			}
			else {
				newDirection = 3;
			}
		}
		else {
			newDirection = 1;
		}
		
		if (newDirection == lastDirection){
			lastDirection = turnt (newDirection);
			return turnt (newDirection);
		}
		else {
			lastDirection = newDirection;
			return newDirection;
		}
	}
	
	private int turnt(int d) {
		if (d == 1) { 
			return Pit.NORTH;
		} 
		else if (d == 2) {
			return Pit.SOUTH;
		}
		else if (d == 3) {
			return Pit.EAST;
		}
		else {
			return Pit.WEST;
		}
	}
}
