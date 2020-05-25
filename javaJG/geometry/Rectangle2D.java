package javaJG.geometry;

public class Rectangle2D extends SimpleShape2D {

	public Rectangle2D() {
	}

	public Rectangle2D(final double width, final double height) {
		super(width, height);
	}

	public Rectangle2D(final double x, final double y, final double width, final double height) {
		super(x, y, width, height);
	}

	public Rectangle2D getBounds() {
		return this;
	}

	@Override
	public boolean contains(double x, double y) {
		return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
	}

	@Override
	public boolean intersects(SimpleShape2D shape) {
		if (shape instanceof Rectangle2D)
			return Math.abs(getCenterX() - shape.getCenterX()) < (getWidth() + shape.getWidth()) / 2
					&& Math.abs(getCenterY() - shape.getCenterY()) < (getHeight() + shape.getHeight()) / 2;

		if (shape instanceof Ellipse2D) {
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
					{ getX() + getWidth(), getY() + getHeight() },
					{ getX(), getY() + getHeight() },
					{ getX() + getWidth(), getY() },
					{ getX(), getY() } };

			final double[] closestPoint = corners[inBoundsIndex];

			return shape.contains(closestPoint[0], closestPoint[1]);
		}

		if (shape instanceof RightTriangle2D) {
			Line2D[] triangle = {
					new Line2D(shape.getX(), shape.getY(), shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight()),
					new Line2D(shape.getX(), shape.getY(), shape.getX(), shape.getHeight()),
					new Line2D(shape.getX(), shape.getY() + shape.getHeight(), shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight())
			};
			Line2D[] rectangle = {
					new Line2D(getX(), getY(), getX() + getWidth(), getY()),
					new Line2D(getX(), getY(), getX(), getY() + getHeight()),
					new Line2D(getX() + getWidth(), getY(), getX() + getWidth(), getY() + getHeight()),
					new Line2D(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight())
			};
			for (Line2D lt : triangle)
				for (Line2D lr : rectangle)
					if (lt.intersects(lr))
						return true;
			return false;
		}
		return shape.intersects(this);
	}

	public Rectangle2D intersection(Rectangle2D r) {
		double x = Math.max(getX(), r.getX());
		double y = Math.max(getY(), r.getY());
		double width = r.getX() <= getX() ? Math.abs(getX() - (r.getX() + r.getWidth())) : Math.abs(getX() + getWidth() - r.getX());
		double height = r.getY() <= getY() ? Math.abs(getY() - (r.getY() + r.getHeight())) : Math.abs(getY() + getHeight() - r.getY());

		return width < r.getWidth() + getWidth() && height < r.getHeight() + getHeight() ? new Rectangle2D(x, y, width, height) : null;
	}

}
