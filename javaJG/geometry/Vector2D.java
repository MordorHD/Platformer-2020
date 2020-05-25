package javaJG.geometry;

public class Vector2D implements PositionOperations {
	
	public static Vector2D fromAngle(double angle) {
		return new Vector2D(Math.sin(angle), Math.cos(angle));
	}
	
	private double x, y;
	
	public Vector2D() {
		
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double angle() {
		return Math.atan(x / y);
	}
	
	public void normalize() {
		setMagnitude(1);
	}
	
	public void setMagnitude(double mag) {
		double angle = angle();
		x = Math.sin(angle) * mag; 
		y = Math.cos(angle) * mag;
	}
	
	public Vector2D add(Vector2D vector) {
		add(vector.x, vector.y);
		return this;
	}
	
	public Vector2D subtract(Vector2D vector) {
		subtract(vector.x, vector.y);
		return this;
	}
	
	public Vector2D multiply(Vector2D vector) {
		multiply(vector.x, vector.y);
		return this;
	}
	
	public Vector2D divide(Vector2D vector) {
		divide(vector.x, vector.y);
		return this;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double setX(double x) {
		return this.x = x;
	}

	@Override
	public double setY(double y) {
		return this.y = y;
	}
}
