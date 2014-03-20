/** DO NOT MODIFY **/
//TODO: Modify score passing such that there's a red and blue score.
//TODO: Fix the closing condition.
package GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pits.MultiSnakePit;
import pits.FootballPracticePit;
import pits.Pit;
import pits.SnakePit;

public class GUI {

	private static JFrame frame;
	private static JPanel panel;
	private static Pit pit;
	private static JCheckBox pauseButton;
	private static boolean die = false;
	private static JSpinner speedSpinner;
	private static JLabel scoreLabel;
	private static int speed = 10;
	private static Timer timer;
	
	public static int GRID_SIZE = 50; //The grid is 100 tiles across.
	public static int SQUARE_SIZE = 8; //Each grid tile is 5 pixels square.
	
	public static void main(String[] args) {
		if (setup()) {
			gameTime();
		}
	}
	
	private static boolean setup() {
		panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		c.insets = new Insets(3, 1, 3, 1);
		JSpinner gSpin = new JSpinner(new SpinnerNumberModel(50, 3, 1000, 1));
		JSpinner tSpin = new JSpinner(new SpinnerNumberModel(8, 1, 100, 1));
		String[] pits = { "Snake", "Snakes", "Physics"};
		JComboBox games = new JComboBox(pits);

		c.gridwidth = 2;
		panel.add(new JLabel("Shall we play a game?"), c);
		c.gridy++;
		panel.add(games, c);
		c.gridy++; c.gridwidth = 1;
		panel.add(new JLabel("Grid size:"), c);
		c.gridx++;
		panel.add(gSpin, c);
		c.gridy++; c.gridx = 0;
		panel.add(new JLabel("Tile size (px):"), c);
		c.gridx++;
		panel.add(tSpin, c);
		Font f = new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 20);
		for (Component comp : panel.getComponents()) comp.setFont(f);

		if (JOptionPane.showOptionDialog(frame, panel, "Get ready", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, null, null) == JOptionPane.OK_OPTION) {
			GRID_SIZE = (Integer) gSpin.getValue();
			SQUARE_SIZE = (Integer) tSpin.getValue();
			if (games.getSelectedIndex() == 0) {
				pit = new SnakePit(GRID_SIZE);
			} else if (games.getSelectedIndex() == 1) {
				pit = new MultiSnakePit(GRID_SIZE);
			} else if (games.getSelectedIndex() == 2) {
				pit = new FootballPracticePit(GRID_SIZE*SQUARE_SIZE);
			}
			return true;
		}
		return false;
	}
	
	private static void gameTime() {
		timer = new Timer(100, timerListener);
		Font font = new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 20);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		c.weightx = 0.5; c.weighty = 0.5; c.ipady = 10;
		pauseButton = new JCheckBox("Pause");
		
		JButton stepButton = new JButton("Step");
		stepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pauseButton.isSelected()) {
					action();
				}
			} });
		JPanel miniPanel = new JPanel();
		miniPanel.setLayout(new GridBagLayout());
		
		miniPanel.add(pauseButton, c);
		c.gridx++;
		miniPanel.add(stepButton, c);
		c.gridx = 0; c.gridy++;
		speedSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
		speedSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				speed = (Integer)speedSpinner.getValue();
				timer.setDelay(1000/speed);
			}
		});
		miniPanel.add(new JLabel("Speed:"), c);
		c.gridx++;
		miniPanel.add(speedSpinner, c);
		c.gridx = 0; c.gridy++; c.gridwidth = 2;
		scoreLabel = new JLabel("Score: " + 0);
		miniPanel.add(scoreLabel, c);
		
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		c.weightx = 0.5; c.weighty = 0.5;
		panel.add(new JLabel("Welcome to the " + pit.getName()), c);
		
		c.gridy++; c.gridheight = 4;
		panel.add(new JLabel(pit), c);
		c.gridx++; c.gridheight = 1;
		c.gridy++; panel.add(miniPanel, c);
		for (Component comp : panel.getComponents()) comp.setFont(font);
		for (Component comp : miniPanel.getComponents()) comp.setFont(font);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		timer.start();
	}
	
	private static ActionListener timerListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (pauseButton.isSelected()) return;
			action();
		}
	};
	
	private static void action() {
		if (!pit.step() || die) die();
		scoreLabel.setText(pit.getScore());
		scoreLabel.setSize(scoreLabel.getPreferredSize());
		frame.repaint();
		frame.pack();
	}
	
	private static void die() {
		timer.stop();
		int reply = JOptionPane.showConfirmDialog(frame, 
				pit.getEndMessage() + " Play Again?",
				"Game Over", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			frame.dispose();
			if (pit instanceof SnakePit) {
				pit = new SnakePit(GRID_SIZE);
			} else if (pit instanceof MultiSnakePit) {
				pit = new MultiSnakePit(GRID_SIZE);
			} else if (pit instanceof FootballPracticePit) {
				pit = new FootballPracticePit(GRID_SIZE*SQUARE_SIZE);
			}
			gameTime();
		} else {
			frame.dispose();
			main(null);
		}
	}
}
