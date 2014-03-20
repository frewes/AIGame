/** DO NOT MODIFY **/

package helpers;

public class Point {
	
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isOutOfBounds(int width, int height) {
		boolean b = false;
		b = b || (x < 0);
		b = b || (x >= width);
		b = b || (y < 0);
		b = b || (y >= height);
		return b;
	}

}
