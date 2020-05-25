package javaJG.geometry;

public interface PositionOperations {

	public default void add(double x, double y) {
		setX(getX() + x);
		setY(getY() + y);
	}

	public default void subtract(double x, double y) {
		setX(getX() - x);
		setY(getY() - y);
	}

	public default void multiply(double x, double y) {
		setX(getX() * x);
		setY(getY() * y);
	}

	public default void divide(double x, double y) {
		setX(getX() / x);
		setY(getY() / y);
	}

	public default void modulo(double x, double y) {
		setX(getX() % x);
		setY(getY() % y);
	}

	public double getX();

	public double getY();
	
	public default Point2D getPos() {
		return new Point2D(getX(), getY());
	}
	
	public default double[] setPos(Point2D p) {
		return setPos(p.getX(), p.getY());
	}
	
	public default double[] setPos(double x, double y) {
		return new double[] { setX(x), setY(y) };
	}

	public double setX(double x);

	public double setY(double y);
}
