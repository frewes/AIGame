package pits;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import helpers.Bicell;
import helpers.Diff;
import helpers.Particle;
import helpers.Point;
import GUI.GUI;

public class FootballPracticePit implements Pit {
	
	private int size;
	private Bicell b;
	
	public FootballPracticePit(int gridSize) {
		this.size = gridSize;
		b = new Bicell(8, 3);
	}

	@Override
	public String getScore() {
		return "0";
	}

	@Override
	public boolean step() {
		b.setSpeed(1, 0);
		b.step();
		return true;
	}

	@Override
	public int getIconHeight() {
		return size;
	}

	@Override
	public int getIconWidth() {
		return size;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int X, int Y) {
		g.setColor(Color.green);
		
		g.fillRect(X, Y, size, size);
		b.draw(g, Color.red);
	}

	@Override
	public String getName() {
		return "Football Practice Pit";
	}

	@Override
	public String getEndMessage() {
		return "Physics broked";
	}

}
