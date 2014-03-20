package pits;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Random;

import GUI.GUI;
import helpers.Point;
import agents.DuelSnakeBlue;
import agents.DuelSnakeRed;

public class MultiSnakePit implements Pit {

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int UP = 4;

	public static final int APPLE = -1;
	public static final int EMPTY = 0;
	public static final int RED = 0;
	public static final int BLUE = 1;
	
	private Point apple;
	private Point blueHead;
	private Point redHead;
	private DuelSnakeBlue blueSnake;
	private DuelSnakeRed redSnake;
	public int blueLength = 1;
	public int redLength = 1;
	private int[][][] grid;
	private int size;
	private boolean redDead = false;
	private boolean blueDead = false;
	
	public MultiSnakePit(int gridSize) {
		size = gridSize;
		
		grid = new int[gridSize][gridSize][2];
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				grid[x][y][0] = EMPTY;
			}
		}
		blueHead = new Point (gridSize/4, gridSize/2);
		redHead = new Point (3*gridSize/4, gridSize/2);
		grid[blueHead.x][blueHead.y][0] = blueLength;
		grid[blueHead.x][blueHead.y][1] = BLUE;
		grid[redHead.x][redHead.y][0] = redLength;
		grid[redHead.x][redHead.y][1] = RED;
		placeApple();
		
		blueSnake = new DuelSnakeBlue();
		redSnake = new DuelSnakeRed();
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int X, int Y) {
		g.setColor(Color.white);
		g.fillRect(X, Y, getIconWidth(), getIconHeight());
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				Color color = Color.black;
				if (grid[x][y][0] == EMPTY) continue;
				if (grid[x][y][0] == APPLE) color = Color.green; //Apple
				else if (grid[x][y][1] == RED) color = Color.red;
				else if (grid[x][y][1] == BLUE) color = Color.blue;
				g.setColor(color);
				g.fillRect(X+(x*GUI.SQUARE_SIZE), Y+(y*GUI.SQUARE_SIZE),
						GUI.SQUARE_SIZE, GUI.SQUARE_SIZE);
			}
		}
	}

	public boolean step() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (grid[x][y][0] != EMPTY && grid[x][y][0] != APPLE) grid[x][y][0]--;
			}
		}
		
		int dir = redSnake.move(redHead, blueHead, apple);
		if (dir == DOWN) redHead.y++;
		else if (dir == UP) redHead.y--;
		else if (dir == LEFT) redHead.x--;
		else if (dir == RIGHT) redHead.x++;
		
		if (redHead.isOutOfBounds(size, size)) {
			redDead = true;
			return false; //RED DEAD
		}
		else {
			int val = grid[redHead.x][redHead.y][0];
			if (val == EMPTY) { 
				grid[redHead.x][redHead.y][0] = redLength; 
				grid[redHead.x][redHead.y][1] = RED; 
			} else if (val == APPLE) {
				redLength++;
				grid[redHead.x][redHead.y][0] = redLength; 
				grid[redHead.x][redHead.y][1] = RED; 
				placeApple();
			} else { //Hit a snake; die.
				grid[redHead.x][redHead.y][0] = APPLE; //RED SNAKE HAS DIED
				redDead = true;
				return false;
			}
		}
		
		dir = blueSnake.move(blueHead, redHead, apple);
		if (dir == DOWN) blueHead.y++;
		else if (dir == UP) blueHead.y--;
		else if (dir == LEFT) blueHead.x--;
		else if (dir == RIGHT) blueHead.x++;
		
		if (blueHead.isOutOfBounds(size, size)) {
			blueDead = true;
			return false; //BLUE DEAD
		}
		else {
			int val = grid[blueHead.x][blueHead.y][0];
			if (val == EMPTY) { 
				grid[blueHead.x][blueHead.y][0] = blueLength; 
				grid[blueHead.x][blueHead.y][1] = BLUE; 
			} else if (val == APPLE) {
				blueLength++;
				grid[blueHead.x][blueHead.y][0] = blueLength; 
				grid[blueHead.x][blueHead.y][1] = BLUE; 
				placeApple();
			} else { //Hit a snake; die.
				grid[blueHead.x][blueHead.y][0] = APPLE; //BLUE SNAKE HAS DIED 
				blueDead = true;
				return false;
			}
		}
		return true;
	}
	
	private void placeApple() {
		while (true) {
			int x = new Random().nextInt(size);
			int y = new Random().nextInt(size);
			if (grid[x][y][0] == EMPTY) {
				grid[x][y][0] = APPLE;
				apple = new Point(x, y);
				break;
			}	
		}
	}

	public String getScore() {
		String s = "Red: " + (redLength-1) + " --- ";
		s = s + "Blue: " + (blueLength-1);
		return s;
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
		return "Dual Snake Duel Pit";
	}

	public String getEndMessage() {
		String s = "The ";
		if (redDead) s += " red ";
		else if (blueDead) s += " blue ";
		s += " snake died! ";
		if (redLength > blueLength) {
			s += " The red snake got more apples, ";
		} else if (redLength < blueLength) {
			s += " The blue snake got more apples, ";
		} else {
			s += " Both snakes got the same number of apples, ";
		}
		
		if (blueDead && redLength >= blueLength) {
			s += "so Red wins! ";
		} else if (redDead && redLength <= blueLength) {
			s += "so Blue wins! ";
		} else {
			s += "so it's a tie!";
		}
		return s;
	}

}
