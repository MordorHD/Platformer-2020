package javaJG.geometry;

import java.awt.Dimension;

public interface DimensionOperations {

	public default void addSize(double width, double height) {
		setWidth(getWidth() + width);
		setHeight(getHeight() + height);
	}

	public default void subtractSize(double width, double height) {
		setWidth(getWidth() - width);
		setHeight(getHeight() - height);
	}

	public default void multiplySize(double width, double height) {
		setWidth(getWidth() * width);
		setHeight(getHeight() * height);
	}

	public default void divideSize(double width, double height) {
		setWidth(getWidth() / width);
		setHeight(getHeight() / height);
	}

	public default void moduloSize(double width, double height) {
		setWidth(getWidth() % width);
		setHeight(getHeight() % height);
	}

	public double getWidth();

	public double getHeight();

	public default Dimension getSize() {
		return new Dimension((int)getWidth(), (int)getHeight());
	}

	public default double[] setSize(Dimension size) {
		return setSize(size.getWidth(), size.getHeight());
	}

	public default double[] setSize(double width, double height) {
		return new double[] { setWidth(width), setHeight(height) };
	}

	public double setWidth(double width);

	public double setHeight(double height);
}
