package javaJG.geometry;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleShape2D implements Shape, Cloneable, PositionOperations, DimensionOperations {

	public static boolean isIntersecting(SimpleShape2D s1, SimpleShape2D s2) {
		return s1.intersects(s2);
	}

	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

	public List<ChangeListener> getChangeListeners() {
		return listeners;
	}

	public void addListener(ChangeListener l) {
		listeners.add(l);
	}

	private double x, y, width, height;

	public SimpleShape2D() {

	}

	public SimpleShape2D(double width, double height) {
		this(0, 0, width, height);
	}

	public SimpleShape2D(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract boolean contains(double x, double y);

	public abstract boolean intersects(SimpleShape2D shape);

	@Override
	public String toString() {
		return getClass().getName() + "[" + x + ", " + y + ", " + width + ", " + height + "]";
	}

	@Override
	public SimpleShape2D clone() {
		final SimpleShape2D ref = this;
		return new SimpleShape2D(x, y, width, height) {

			@Override
			public boolean contains(double x, double y) {
				return ref.contains(x, y);
			}

			@Override
			public boolean intersects(SimpleShape2D shape) {
				return ref.intersects(shape);
			}

		};
	}

	public SimpleShape2D getBounds() {
		return this;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getCenterX() {
		return x + width / 2;
	}

	public double getCenterY() {
		return y + height / 2;
	}

	public double setX(double newX) {
		double oldX = x;
		x = newX;
		for (ChangeListener listener : listeners) {
			if (oldX != x)
				listener.onPositionChange(oldX, y, newX, y);
			listener.onPositionCall(oldX, y, newX, y);
		}
		return oldX;
	}

	public double setY(double newY) {
		double oldY = y;
		y = newY;
		for (ChangeListener listener : listeners) {
			if (oldY != y)
				listener.onPositionChange(x, oldY, x, newY);
			listener.onPositionCall(x, oldY, x, newY);
		}
		return oldY;
	}

	public double setWidth(double newWidth) {
		double oldWidth = width;
		width = newWidth;
		for (ChangeListener listener : listeners) {
			if (width != oldWidth)
				listener.onDimensionChange(oldWidth, height, newWidth, height);
			listener.onDimensionCall(oldWidth, height, newWidth, height);
		}
		return oldWidth;
	}

	public double setHeight(double newHeight) {
		double oldHeight = height;
		height = newHeight;
		for (ChangeListener listener : listeners) {
			if (height != oldHeight)
				listener.onDimensionChange(width, oldHeight, width, height);
			listener.onDimensionCall(width, oldHeight, width, height);
		}
		return oldHeight;
	}
}
