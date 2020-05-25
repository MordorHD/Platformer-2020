package javaJG.geometry;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javaJG.Window;
import javaJG.event.Controller;
import javaJG.panel.GamePanel;
import javaJG.panel.Painter;

public class Line2D implements Cloneable {

	private static int mx, my;

	public static void main(String[] args) {
		Line2D t1 = new Line2D(50, 550, 1150, 50), t2 = new Line2D(50, 50, mx, my);

		Window w = new Window();
		w.setSize(1200, 600);
		GamePanel p = new GamePanel();
		p.setPainter(new Painter() {

			@Override
			public void paint(Graphics g) {
				g.drawLine((int) t1.getX1(), (int) t1.getY1(), (int) t1.getX2(), (int) t1.getY2());
				g.drawLine((int) t2.getX1(), (int) t2.getY1(), mx, my);
				t2.setX2(mx);
				t2.setY2(my);
				Point2D p = t1.intersection(t2);
				if (p != null)
					g.drawRect((int) p.getX(), (int) p.getY(), 2, 2);
			}

		});
		p.setController(new Controller() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
			}
		});

		w.setPanel(p);
		w.showWindow();

	}

	public static boolean intersects(double p0_x, double p0_y, double p1_x, double p1_y,
			double p2_x, double p2_y, double p3_x, double p3_y) {
		double s1_x, s1_y, s2_x, s2_y;
		s1_x = p1_x - p0_x;
		s1_y = p1_y - p0_y;
		s2_x = p3_x - p2_x;
		s2_y = p3_y - p2_y;

		double s, t;
		s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);
		return (s >= 0 && s <= 1 && t >= 0 && t <= 1);
	}

	public static Point2D intersection(double l1_x1, double l1_y1, double l1_x2, double l1_y2,
			double l2_x1, double l2_y1, double l2_x2, double l2_y2) {
		double s1_x, s1_y, s2_x, s2_y;
		s1_x = l1_x2 - l1_x1;
		s1_y = l1_y2 - l1_y1;
		s2_x = l2_x2 - l2_x1;
		s2_y = l2_y2 - l2_y1;

		double s = (-s1_y * (l1_x1 - l2_x2) + s1_x * (l1_y1 - l2_y2)) / (-s2_x * s1_y + s1_x * s2_y),
				t = (s2_x * (l1_y1 - l2_y2) - s2_y * (l1_x1 - l2_x2)) / (-s2_x * s1_y + s1_x * s2_y);
		if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
			return new Point2D(l1_x1 + (t * s1_x), l1_y1 + (t * s1_y));
		return null;
	}

	private double x1, y1, x2, y2;

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public Line2D() {

	}

	public Line2D(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public static double calcLength(double p1, double p2) {
		return Math.abs(p1 - p2);
	}

	public static double calcSimpleLength(double px1, double py1, double px2, double py2) {
		return Math.pow(px1 + px2, 2) + Math.pow(py1 + py2, 2);
	}

	public static double calcLength(double px1, double py1, double px2, double py2) {
		return Math.sqrt(Math.pow(px1 + px2, 2) + Math.pow(py1 + py2, 2));
	}

	@Override
	public Line2D clone() {
		return new Line2D(x1, y1, x2, y2);
	}

	public boolean intersects(Line2D line) {
		return intersects(x1, y1, x2, y2, line.x1, line.y1, line.x2, line.y2);
	}

	public Point2D intersection(Line2D line) {
		return intersection(x1, y1, x2, y2, line.x1, line.y1, line.x2, line.y2);
	}

	public boolean intersects(Rectangle2D r) {
		return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	public boolean intersects(double x, double y, double w, double h) {
		double m = (y2 - y1) / (x2 - x1);
		double p = x2 < x1 ? x2 : x1, q = y2 < y1 ? y2 : y1;

		boolean on_left = (x1 < x && x2 < x), on_right = (x1 > x + w && x2 > x + w), on_top = (y1 < y && y2 < y),
				on_bottom = (y1 > y + h && y2 > y + h);

		if (!on_left && !on_right && !on_top && !on_bottom) {
			if (((y < (m * (x - p) + q)) && (y + h > (m * (x - p) + q)))
					|| ((y < (m * (x + w - p) + q)) && (y + h > (m * (x + w - p) + q)))) {
				return true;
			}
			if ((x < (((y - q) / m) + p) && x + w > (((y - q) / m) + p))
					|| (x < (((y + h - m) / q) + p) && x + w > (((y + h - q) / m) + p))) {
				return true;
			}
		}
		return false;
	}
}
