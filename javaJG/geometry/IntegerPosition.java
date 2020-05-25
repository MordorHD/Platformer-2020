package javaJG.geometry;

/**
 * Same as position but only integers.
 */
public class IntegerPosition implements Cloneable, PositionOperations {

	public static int[] BLOCK_SIZE = new int[] { 30, 30 };

	private int x, y;

	/**
	 * Divides the given position by the game constants square size. If you want to
	 * get an Integer array as output consider using toIndexedPosition(x, y).
	 * 
	 * @param p Position to be indexed.
	 * @return IntegerPosition of the indexed one.
	 */
	public static IntegerPosition toIndexedPosition(Point2D p) {
		return new IntegerPosition((int) (p.getX() / BLOCK_SIZE[0]), (int) (p.getY() / BLOCK_SIZE[1]));
	}

	public static int[] toIndexedPosition(final double x, final double y) {
		return new int[] { (int) (x / BLOCK_SIZE[0]), (int) (y / BLOCK_SIZE[1]) };
	}

	public IntegerPosition() {

	}

	public IntegerPosition(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[" + x + ", " + y + "]";
	}

	@Override
	public Point2D clone() {
		return new Point2D(x, y);
	}

	public void reposition(final IntegerPosition newPos) {
		reposition(newPos.x, newPos.y);
	}

	public void reposition(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double setX(double x) {
		return this.x = (int) x;
	}

	@Override
	public double setY(double y) {
		return this.x = (int) y;
	}

}
