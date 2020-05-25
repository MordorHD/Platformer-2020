package javaJG.geometry;

public class Point2D implements Cloneable, PositionOperations {

	private double x;
	private double y;
	
	private ChangeListener cl = new ChangeListener() {};
	
	public void setChangeListener(ChangeListener cl) {
		this.cl = cl;
	}

	public Point2D() {

	}

	public Point2D(final double x, final double y) {
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
	
	public boolean equals(final Point2D p) {
		return x == p.x && y == p.y;
	}

	public void reposition(final Point2D newPos) {
		reposition(newPos.x, newPos.y);
	}

	public void reposition(final double x, final double y) {
		setX(x);
		setY(y);
	}

	public double setX(final double x) {
		double oldX = x;
		this.x = x;
		cl.onPositionCall(oldX, y, x, y);
		if (oldX != x)
			cl.onPositionChange(oldX, y, x, y);
		return oldX;
	}

	public double setY(final double y) {
		double oldY = y;
		this.y = y;
		cl.onPositionCall(x, y, x, y);
		if (oldY != y)
			cl.onPositionChange(x, oldY, x, y);
		return oldY;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
