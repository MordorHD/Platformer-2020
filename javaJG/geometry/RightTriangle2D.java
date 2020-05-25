package javaJG.geometry;

import java.awt.Graphics;

import javaJG.Window;
import javaJG.panel.GamePanel;
import javaJG.panel.Painter;

public class RightTriangle2D extends SimpleShape2D {

	public static void main(String[] args) {
		RightTriangle2D t1 = new RightTriangle2D(50, 50, 200, 100), t2 = new RightTriangle2D(40, 40, 160, 60);
		
		Window w = new Window(); 
		w.setSize(1200, 600);
		GamePanel p = new GamePanel();
		p.setPainter(new Painter() {

			@Override
			public void paint(Graphics g) {
				g.drawPolygon(new int[] { (int) t1.getX(), (int)(t1.getX() + t1.getWidth()), (int) t1.getX()}, 
						new int[] { (int) t1.getY(), (int)(t1.getY() + t1.getHeight()), (int)(t1.getY() + t1.getHeight())}, 3);
				g.drawPolygon(new int[] { (int) t2.getX(), (int)(t2.getX() + t2.getWidth()), (int) t2.getX()}, 
						new int[] { (int) t2.getY(), (int)(t2.getY() + t2.getHeight()), (int)(t2.getY() + t2.getHeight())}, 3);
			}
			
		});
		w.setPanel(p);
		w.showWindow();
	}
	
	public RightTriangle2D() {
	}

	public RightTriangle2D(double width, double height) {
		super(width, height);
	}

	public RightTriangle2D(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public boolean contains(double x, double y) {
		x -= getX();
		y -= getY();

		double fx = getWidth() > 0 && getHeight() > 0 ? x * (-getHeight() / getWidth()) + getHeight() :
				getWidth() > 0 && getHeight() < 0 ? x * (getHeight() / getWidth()) :
				getWidth() < 0 && getHeight() < 0 ? x * (-getHeight() / getWidth()) :
				getWidth() < 0 && getHeight() > 0 ? -(x - getWidth()) * (getHeight() / getWidth()) : 0;

		return getWidth() > 0 && getHeight() > 0 && x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight() ? y < fx :
				getWidth() > 0 && getHeight() < 0 && x >= 0 && x <= getWidth() && y >= getHeight() && y <= 0 ? y < fx :
				getWidth() < 0 && getHeight() < 0 && x >= getWidth() && x <= 0 && y >= getHeight() && y <= 0 ? y < fx :
				getWidth() < 0 && getHeight() > 0 && x >= getWidth() && x <= 0 && y >= 0 && y <= getHeight() ? y < fx : false;
	}

	@Override
	public boolean intersects(SimpleShape2D shape) {
		if (shape instanceof Rectangle2D) {
			Line2D[] triangle = {
					new Line2D(getX(), getY(), getX() + getWidth(), getY() + getHeight()),
					new Line2D(getX(), getY(), getX(), getHeight()),
					new Line2D(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight())
			};
			Line2D[] rectangle = {
					new Line2D(shape.getX(), shape.getY(), shape.getX() + shape.getWidth(), shape.getY()),
					new Line2D(shape.getX(), shape.getY(), shape.getX(), shape.getY() + shape.getHeight()),
					new Line2D(shape.getX() + shape.getWidth(), shape.getY(), shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight()),
					new Line2D(shape.getX(), shape.getY() + shape.getHeight(), shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight())
			};
			for (Line2D lt : triangle)
				for (Line2D lr : rectangle)
					if (lt.intersects(lr))
						return true;
			return false;
		}

		if (shape instanceof RightTriangle2D) {
			Line2D[] triangle = {
					new Line2D(getX(), getY(), getX() + getWidth(), getY() + getHeight()),
					new Line2D(getX(), getY(), getX(), getHeight()),
					new Line2D(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight())
			};
			Line2D[] triangle2 = {
					new Line2D(shape.getX(), shape.getY(), shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight()),
					new Line2D(shape.getX(), shape.getY(), shape.getX(), shape.getHeight()),
					new Line2D(shape.getX(), shape.getY() + shape.getHeight(), shape.getX() + shape.getWidth(), shape.getY() + shape.getHeight())
			};
			for (Line2D lt : triangle)
				for (Line2D lt2 : triangle2)
					if (lt.intersects(lt2))
						return true;
			return false;
		}
		return shape.intersects(this);
	}
	
	public RightTriangle2D intersection(RightTriangle2D r) {
		double x = Math.max(getX(), r.getX());
		double y = Math.max(getY(), r.getY());
		double width = r.getX() <= getX() ? Math.abs(getX() - (r.getX() + r.getWidth())) : Math.abs(getX() + getWidth() - r.getX());
		double height = r.getY() <= getY() ? Math.abs(getY() - (r.getY() + r.getHeight())) : Math.abs(getY() + getHeight() - r.getY());

		return width < r.getWidth() + getWidth() && height < r.getHeight() + getHeight() ? new RightTriangle2D(x, y, width, height) : null;
	}

}
