package javaJG.geometry;

public class Ellipse2D extends SimpleShape2D {

	public Ellipse2D() {
	}

	public Ellipse2D(final double width, final double height) {
		super(width, height);
	}

	public Ellipse2D(final double x, final double y, final double width, final double height) {
		super(x, y, width, height);
	}

	public Ellipse2D(final Point2D pos, final double width, final double height) {
		this(pos.getX(), pos.getY(), width, height);
	}

	public Ellipse2D getBounds() {
		return this;
	}

	@Override
	public boolean contains(double x, double y) {
		return Math.pow(getCenterX() - x, 2) / Math.pow(getWidth() / 2, 2)
				+ Math.pow(getCenterY() - y, 2) / Math.pow(getHeight() / 2, 2) <= 1;
	}

	@Override
	public boolean intersects(SimpleShape2D shape) {
		if (shape instanceof Rectangle2D) {
			Rectangle2D[] quadrants = { new Rectangle2D(getX(), getY(), getWidth() / 2, getHeight() / 2),
					new Rectangle2D(getX() + getWidth() / 2, getY(), getWidth() / 2, getHeight() / 2),
					new Rectangle2D(getX(), getY() + getHeight() / 2, getWidth() / 2, getHeight() / 2),
					new Rectangle2D(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() / 2, getHeight() / 2) };

			int inBoundsIndex = 0;

			int inBoundsCount = 0;

			for (int i = 0; i < 4; i++)
				if (shape.intersects(quadrants[i])) {
					inBoundsCount++;
					inBoundsIndex = i;
				}

			if (inBoundsCount == 0)
				return false;
			else if (inBoundsCount > 1)
				return true;

			final double[][] corners = {
					{ shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight() },
					{ shape.getX(), shape.getY() + shape.getHeight() },
					{ shape.getX() + shape.getWidth(), shape.getY() },
					{ shape.getX(), shape.getY() } };

			final double[] closestPoint = corners[inBoundsIndex];

			return contains(closestPoint[0], closestPoint[1]);
		}
		if (shape instanceof RightTriangle2D) {
			throw new UnsupportedOperationException();
		}
		return shape.intersects(this);
	}

}
