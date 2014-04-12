package pits;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Random;

import GUI.GUI;
import gameMaster.GameBase;
import gameMaster.Player;
import helpers.Pit;
import helpers.Point;
import agents.FredSnake;
import agents.Snake;

public class SnakePit implements Pit ,GameBase{

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int UP = 4;

	private static final int APPLE = -1;
	private static final int EMPTY = 0;
	
	private Point apple;
	private Point head;
	private Snake snake;
	private int snakeLength = 1;
	private int[][] grid;
	private int size;

	public SnakePit(){
		this(10);
	}
	public SnakePit(int gridSize) {
		size = gridSize;
		grid = new int[gridSize][gridSize];
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				grid[x][y] = EMPTY;
			}
		}
		grid[gridSize/2][gridSize/2] = snakeLength;
		head = new Point (gridSize/2, gridSize/2);
		placeApple();
		snake = new Snake();
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int X, int Y) {
		g.setColor(Color.white);
		g.fillRect(X, Y, getIconWidth(), getIconHeight());
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (grid[x][y] == EMPTY) continue;
				Color color = Color.green; //Snake
				if (grid[x][y] == APPLE) color = Color.red; //Apple
				g.setColor(color);
				g.fillRect(X+(x*GUI.SQUARE_SIZE), Y+(y*GUI.SQUARE_SIZE),
						GUI.SQUARE_SIZE, GUI.SQUARE_SIZE);
			}
		}
	}

	public boolean step() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (grid[x][y] != EMPTY && grid[x][y] != APPLE) grid[x][y]--;
			}
		}
		int dir = snake.move(head, apple);
		if (dir == DOWN) head.y++;
		else if (dir == UP) head.y--;
		else if (dir == LEFT) head.x--;
		else if (dir == RIGHT) head.x++;

		if (head.isOutOfBounds(size, size)) return false;
		else {
			int val = grid[head.x][head.y];
			if (val == EMPTY) { 
				grid[head.x][head.y] = snakeLength; 
			} else if (val == APPLE) {
				snakeLength++;
				grid[head.x][head.y] = snakeLength; 
				placeApple();
			} else { //Hit a snake; die.
				grid[head.x][head.y] = APPLE; 
				return false;
			}
		}
		return true;
	}
	
	private void placeApple() {
		while (true) {
			int x = new Random().nextInt(size);
			int y = new Random().nextInt(size);
			if (grid[x][y] == EMPTY) {
				grid[x][y] = APPLE;
				apple = new Point(x, y);
				break;
			}	
		}
	}

	public String getScore() {
		return "Score = " + (snakeLength-1);
	}

	@Override
	public int getIconHeight() {
		return size * GUI.SQUARE_SIZE;
	}

	@Override
	public int getIconWidth() {
		return size * GUI.SQUARE_SIZE;
	}

	@Override
	public String getName() {
		return "Snake Pit";
	}

	@Override
	public String getEndMessage() {
		return "A snake has died.  You ate " + getScore() + " apples.";
	}

	@Override
	public void init(List<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean progress() {
		return step();
	}

	@Override
	public Map<Player, Integer> score() {
		return null;
	}

	@Override
	public int numPlayersPerGame() {
		return 1;
	}

}
