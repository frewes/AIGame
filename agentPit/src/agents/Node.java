package agents;

import helpers.Point;

public class Node {
	
	public Point p;
	public int c;	
	
	public Node(Point p, int cost) {
		this.p = p;
		this.c = cost;
	}

}
