package javaJG.animation;

import javaJG.geometry.PositionOperations;

public class MovingTransition extends Transition {

	// object moves from (startX, startY) to (endX, endY)
	private double startX, startY, endX, endY;

	public double getStartX() {
		return startX;
	}
	
	public void fromX(double startX) {
		this.startX = startX;
	}
	
	public double getStartY() {
		return startY;
	}

	public void fromY(double startY) {
		this.startY = startY;
	}
	
	public double getEndX() {
		return endX;
	}

	public void toX(double endX) {
		this.endX = endX;
	}
	
	public double getEndY() {
		return endY;
	}

	public void toY(double endY) {
		this.endY = endY;
	}

	public MovingTransition() {
	}

	public MovingTransition(double startX, double startY, double endX, double endY) {
		fromX(startX);
		fromY(startY);
		toX(endX);
		toY(endY);
	}

	public MovingTransition(double endX, double endY) {
		toX(endX);
		toY(endY);
	}

	public void play(boolean usage, PositionOperations p) {
		if (usage) {
			fromX(p.getX());
			fromY(p.getY());
		}
		super.play(new Object[] {
				p,
				(endX - startX) / getDuration(),
				(endY - startY) / getDuration()
		});
	}
	
	protected void next(Object[] o) {
		((PositionOperations) o[0]).add((double) o[1], (double) o[2]);
	}

}
