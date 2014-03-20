package agents;

import pits.Pit;
import gameMaster.Player;
import helpers.Point;

public class DuelSnakeBlue implements Player{

	public DuelSnakeBlue() {
		//This is executed when the snake is born: 
	}
	
	public int move (Point head, Point opponent, Point apple) { //MUST STAY
		//This is executed every turn:
		return Pit.SOUTH;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
