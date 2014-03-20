package pits;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import gameMaster.GameBase;
import gameMaster.Player;
import helpers.Bicell;
import helpers.Diff;
import helpers.Particle;
import helpers.Point;
import GUI.GUI;

public class FootballPracticePit implements Pit ,GameBase{
	
	private int size;
	private Bicell b;
	
	public FootballPracticePit(){
		this.size = 10;
	}
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numPlayersPerGame() {
		return 1;
	}

}
