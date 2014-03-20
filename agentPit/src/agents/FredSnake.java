package agents;

import java.util.LinkedList;

import pits.Pit;
import helpers.Point;

public class FredSnake {	

	private int length;
	private Point oldApple;
	private Map map;
	
	private int APPLE = -1;
	private int EMPTY = 0;
	
	public FredSnake() {
		map = new Map();
		length = 1;
		//This code is executed when the snake is born:
		System.out.println ("Hello, world! I AM A PYYYTTTHHOOONNN!!!");
	}
	
	public int move(Point head, Point apple) { //MUST STAY
		
		if (oldApple == null) oldApple = apple;
		if (head.x == oldApple.x && head.y == oldApple.y) //Apple eaten
			length++;
		oldApple = apple;
		int maxX = Math.max(Math.max(head.x, apple.x)+1, map.getSizeX());
		int maxY = Math.max(Math.max(head.y, apple.y)+1, map.getSizeY());
		if (maxX > map.getSizeX() || maxY > map.getSizeY()) 
			map.redefine(maxX+1,maxY+1);
		map.set(apple.x, apple.y, APPLE);
		for (int x = 0; x < map.getSizeX(); x++) {
			for (int y = 0; y < map.getSizeY(); y++) {
				if (map.get(x,y) != APPLE && map.get(x, y) != EMPTY) {
					map.set(x, y, map.get(x,y)-1);
				}
			}
		}
		map.set(head.x, head.y, length);
//		
//		int dir = Pit.SOUTH;
//		if (head.x < apple.x) dir = Pit.EAST;
//		else if (head.x > apple.x) dir = Pit.WEST; 
//		else if (head.y > apple.y) dir = Pit.NORTH; 
//		else if (head.y < apple.y) dir = Pit.SOUTH; 
//		
//		int i = 0;
//		while (!map.canMove(head, dir)) {
//			dir = newDir (dir);
//			if (i++ > 4) break;
//		}
//		System.out.println (map.getSizeX());
//		return dir;
		return getNextMove(head, apple);
	}
	
	private int newDir(int dir) {
		switch (dir) {
			case Pit.NORTH:
				return Pit.EAST;
			case Pit.EAST:
				return Pit.SOUTH;
			case Pit.SOUTH:
				return Pit.WEST;
			case Pit.WEST:
				return Pit.NORTH;
		}
		return Pit.SOUTH;
	}
	
	private int getNextMove(Point start, Point end) {
		Map m = new Map(map.getSizeX(), map.getSizeY());
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(new Node(end, 1));
		while (!queue.isEmpty()) {
			Node n = queue.poll();
			if (n.p.isOutOfBounds(m.getSizeX(), m.getSizeY())) continue;
			if (m.get(n.p.x, n.p.y) != 0) continue;
			if (n.p.x == start.x && n.p.y == start.y) break;
			if (map.get(n.p.x, n.p.y) > manhattan(start, n.p)) continue;
			m.set(n.p.x, n.p.y, n.c);
			queue.add(new Node(new Point(n.p.x+1, n.p.y), n.c+1));
			queue.add(new Node(new Point(n.p.x-1, n.p.y), n.c+1));
			queue.add(new Node(new Point(n.p.x, n.p.y+1), n.c+1));
			queue.add(new Node(new Point(n.p.x, n.p.y-1), n.c+1));
		}
		int min = 10000000; int dir = Pit.SOUTH;
		if (m.get(start.x+1, start.y) != 0 && m.get(start.x+1,start.y) < min) {
			min = m.get(start.x+1, start.y);
			dir = Pit.EAST;
		}
		if (m.get(start.x-1, start.y) != 0 && m.get(start.x-1, start.y) < min) {
			min = m.get(start.x-1, start.y);
			dir = Pit.WEST;
		}
		if (m.get(start.x, start.y+1) != 0 && m.get(start.x, start.y+1) < min) {
			min = m.get(start.x, start.y+1);
			dir = Pit.SOUTH;
		}
		if (m.get(start.x, start.y-1) != 0 && m.get(start.x, start.y-1) < min) {
			min = m.get(start.x, start.y-1);
			dir = Pit.NORTH;
		}
		if (min == 10000000) {
			if (map.get(start.x+1, start.y) == EMPTY) return Pit.EAST;
			if (map.get(start.x-1, start.y) == EMPTY) return Pit.WEST;
			if (map.get(start.x, start.y+1) == EMPTY) return Pit.SOUTH;
			if (map.get(start.x, start.y-1) == EMPTY) return Pit.NORTH;
			
		}
//		map.printMap();
//		m.printMap();
		return dir;
	}
	
	private int manhattan(Point a, Point b) {
		return Math.abs(a.x-b.x) + Math.abs(a.y-b.y);
	}
	
}
