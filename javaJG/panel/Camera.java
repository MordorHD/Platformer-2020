package javaJG.panel;

import javaJG.animation.MovingTransition;
import javaJG.assets.Align;
import javaJG.assets.Insets;
import javaJG.geometry.Point2D;
import javaJG.geometry.Translation;
import javaJG.geometry.component.BoundingBox2D;

public class Camera extends Point2D {

	Panel panel;

	private int align;

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	private Insets constraints;

	public void setConstraints(Insets constraints) {
		this.constraints = constraints;
	}

	private boolean constraintLeft = true, constraintRight = true, constraintTop = true, constraintBottom = true;

	public void constraintLeft() {
		constraintLeft = true;
	}

	public void noConstraintLeft() {
		constraintLeft = false;
	}

	public void constraintRight() {
		constraintLeft = true;
	}

	public void noConstraintRight() {
		constraintLeft = false;
	}

	public void constraintTop() {
		constraintLeft = true;
	}

	public void noConstraintTop() {
		constraintLeft = false;
	}

	public void constraintBottom() {
		constraintLeft = true;
	}

	public void noConstraintBottom() {
		constraintLeft = false;
	}

	private Translation translation;

	public Translation getTranslation() {
		return translation;
	}

	public void setTranslation(Translation translation) {
		this.translation = translation;
	}

	private BoundingBox2D binding;

	public BoundingBox2D getBinding() {
		return binding;
	}

	public void bind(BoundingBox2D binding) {
		this.binding = binding;
	}

	public void bindWithTransition(long duration, long delay, BoundingBox2D binding) {
		if (this.binding == null)
			throw new NullPointerException("Previous binding must not be null!");
		bindWithTransition(duration, delay, this.binding.getX(), this.binding.getY(), binding);
	}

	public void bindWithTransition(long duration, long delay, double startX, double startY, BoundingBox2D binding) {
		transition(
				duration,
				delay,
				startX,
				startY,
				binding.getX(),
				binding.getY());
		this.binding = binding;
	}

	public void transition(long duration, long delay, double startX, double startY, double endX, double endY) {
		new CameraTransition(duration, delay, startX, startY, endX, endY).play();
	}

	public void transition(MovingTransition t) {
		CameraTransition ct = new CameraTransition(t.getDuration(), t.getDelay(), t.getStartX(), t.getStartY(), t.getEndX(), t.getEndY());
		ct.setOnStart(t.getOnStart());
		ct.setOnEnd(t.getOnEnd());
		ct.play();
	}

	private volatile boolean isLocked;

	public boolean isLocked() {
		return isLocked;
	}

	public void lock() {
		this.isLocked = true;
	}

	public void unlock() {
		this.isLocked = false;
	}

	public void update() {
		if (isLocked)
			return;
		setX(computeOffsetX());
		setY(computeOffsetY());
	}

	public double computeOffsetX() {
		double alignX = Align.check(align, panel.getSize()).getX();
		double offsetX = (getBinding() != null ? getBinding().getX() : 0)
				+ (getTranslation() != null ? getTranslation().offsetX() : 0);
		if (constraints != null && panel.getWidth() < constraints.right - constraints.left) {
			if (constraintLeft && offsetX < constraints.left + alignX)
				offsetX = constraints.left + alignX;
			else if (constraintRight && offsetX > constraints.right - alignX)
				offsetX = constraints.right - alignX;
		}
		return offsetX - alignX;
	}

	public double computeOffsetY() {
		double alignY = Align.check(align, panel.getSize()).getY();
		double offsetY = (getBinding() != null ? getBinding().getY() : 0)
				+ (getTranslation() != null ? getTranslation().offsetY() : 0);
		if (constraints != null && panel.getHeight() < constraints.bottom - constraints.top) {
			if (constraintTop && offsetY < constraints.top + alignY)
				offsetY = constraints.top + alignY;
			else if (constraintBottom && offsetY > constraints.bottom - alignY)
				offsetY = constraints.bottom - alignY;
		}
		return offsetY - alignY;
	}

	public Camera() {

	}

	public Camera(int align) {
		setAlign(align);
	}

	public Camera(Translation translation) {
		setTranslation(translation);
	}

	public Camera(int align, Translation translation) {
		setAlign(align);
		setTranslation(translation);
	}

	public Camera(BoundingBox2D box) {
		bind(box);
	}

	public Camera(int align, BoundingBox2D box) {
		setAlign(align);
		bind(box);
	}

	public Camera(int align, BoundingBox2D box, Translation translation) {
		setTranslation(translation);
		bind(box);
	}

	private class CameraTransition extends MovingTransition {

		CameraTransition(long duration, long delay, double startX, double startY, double endX, double endY) {
			duration(duration);
			fromX(startX);
			fromY(startY);
			setX(startX);
			setY(startY);
			toX(endX);
			toY(endY);
		}

		void play() {
			lock();
			super.play(false, Camera.this);
		}

		protected void end(Object[] args) {
			unlock();
		}

	}
}
