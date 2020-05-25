package javaJG.assets;

import java.awt.Dimension;

import javaJG.geometry.Point2D;

public interface Align {

	public static final int CORNER = 0;
	public static final int CENTER = 1;
	public static final int TOP_LEFT = 2;
	public static final int TOP_CENTER = 3;
	public static final int TOP_RIGHT = 4;
	public static final int RIGHT_TOP = 5;
	public static final int RIGHT_CENTER = 6;
	public static final int RIGHT_BOTTOM = 7;
	public static final int BOTTOM_RIGHT = 8;
	public static final int BOTTOM_CENTER = 9;
	public static final int BOTTOM_LEFT = 10;
	public static final int LEFT_BOTTOM = 11;
	public static final int LEFT_CENTER = 12;
	public static final int LEFT_TOP = 13;
	
	public static Point2D check(int m, Dimension d) {
		switch (m) {
		case CORNER:
			return new Point2D();
		case CENTER:
			return new Point2D(d.getWidth() / 2, d.getHeight() / 2);
		default:
			return null;
		}
	}
	
}
