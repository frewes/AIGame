/** DO NOT MODIFY **/

package helpers;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
Coordinate system:
 --> +x East
|
V
+y
South
**/

public interface Pit extends Icon {

	public static final int WEST = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int NORTH = 4;

	public String getScore();
	
	public boolean step();
	
	@Override
	public int getIconHeight();
	
	@Override
	public int getIconWidth();
	
	@Override
	public void paintIcon(Component c, Graphics g, int X, int Y);
	
	public String getName();
	
	public String getEndMessage();
}
