package agents;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import helpers.Pit;
import helpers.Point;

public class Map {

	private int[][] map;
	
	public Map() {
		map = new int[1][1];
	}
	public Map(int X, int Y) {
		map = new int[X][Y];
	}

	public int getSizeX() {
		return map.length;
	}

	public int getSizeY() {
		return map[0].length;
	}
	
	public void redefine(int X, int Y) {
		int[][] newMap = new int[X][Y];
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				newMap[x][y] = map[x][y];
			}
		}
		map = newMap;
	}
	
	public int get(int x, int y) {
		if (new Point(x,y).isOutOfBounds(getSizeX(), getSizeY())) return 100000;
		return map[x][y];
	}
	
	public void set(int x, int y, int value) {
		map[x][y] = value;
	}
	
	public boolean canMove (Point me, int dir) {
		if (dir == Pit.NORTH) {
			if (get(me.x,me.y-1) > 0) return false;
		} else if (dir == Pit.SOUTH) {
			if (get(me.x,me.y+1) > 0) return false;
		} else if (dir == Pit.EAST) {
			if (get(me.x+1,me.y) > 0) return false;
		} else if (dir == Pit.WEST) {
			if (get(me.x-1,me.y) > 0) return false;
		}
		if (dir == Pit.NORTH) {
			if (me.y-1 < 0) return false;
		} else if (dir == Pit.SOUTH) {
			if (me.y+1 > map[0].length) return false;
		} else if (dir == Pit.EAST) {
			if (me.x+1 > map.length) return false;
		} else if (dir == Pit.WEST) {
			if (me.x-1 < 0) return false;
		}
		return true;
	}
	
	public void printMap() {
		System.out.println ("Current map:");
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				System.out.print (map[x][y] + "\t");
			}
			System.out.println ("|");
		}
	}
}
