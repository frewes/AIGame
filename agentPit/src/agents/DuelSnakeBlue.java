package agents;

import pits.Pit;
import helpers.Point;

public class DuelSnakeBlue {

	public DuelSnakeBlue() {
		//This is executed when the snake is born: 
	}
	
	public int move (Point head, Point opponent, Point apple) { //MUST STAY
		//This is executed every turn:
		return Pit.SOUTH;
	}

}
