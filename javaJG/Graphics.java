package javaJG;

import java.awt.BasicStroke;
import java.awt.Color;

public abstract class Graphics extends java.awt.Graphics2D {
	
	Color fill, stroke;
	
	public void fill(Color c) {
		fill = c;
	}

	public void fill(int gray, int a) {
		fill = new Color(gray, gray, gray, a);
	}

	public void fill(int r, int g, int b) {
		fill = new Color(r, g, b);
	}

	public void fill(int r, int g, int b, int a) {
		fill = new Color(r, g, b, a);
	}
	
	public void stroke(Color c) {
		this.stroke = c;
	}

	public void stroke(int gray, int a) {
		setStroke(new BasicStroke(gray, gray, gray, a));
	}

	public void stroke(int r, int g, int b) {
		setColor(new Color(r, g, b));
	}

	public void stroke(int r, int g, int b, int a) {
		setColor(new Color(r, g, b, a));
	}

	public void rect(double x, double y, double width, double height) {
		fillRect((int) x, (int) y, (int) width, (int) height);
		drawRect((int) x, (int) y, (int) width, (int) height);
	}

	public void ellipse(double x, double y, double width, double height) {
		fillOval((int) x, (int) y, (int) width, (int) height);
		drawOval((int) x, (int) y, (int) width, (int) height);
	}

	public void oval(double x, double y, double width, double height) {
		fillOval((int) x, (int) y, (int) width, (int) height);
		drawOval((int) x, (int) y, (int) width, (int) height);
	}

	public void triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		fillPolygon(new int[] { (int) x1, (int) x2, (int) x3 }, new int[] { (int) y1, (int) y2, (int) y3 }, 3);
		drawPolygon(new int[] { (int) x1, (int) x2, (int) x3 }, new int[] { (int) y1, (int) y2, (int) y3 }, 3);
	}

}
