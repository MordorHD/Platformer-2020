package javaJG.geometry.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;

import javaJG.geometry.Rectangle2D;
import javaJG.geometry.Translation;
import javaJG.panel.Animation;

public class BoundingBox2D extends Rectangle2D {

	private Translation translation;

	public Translation getTranslation() {
		return translation;
	}

	public void translate(Translation translation) {
		this.translation = translation;
	}

	private Color color = Color.BLACK;

	public void setColor(Color color) {
		this.color = color;
	}

	public BufferedImage graphic;

	public final BufferedImage getGraphic() {
		return graphic;
	}

	Animation lastAnimation = null;

	private Map<Predicate, Animation> animationMap;

	public final Map<Predicate, Animation> getAnimationMap() {
		return animationMap;
	}

	public void setAnimationMap(Map<Predicate, Animation> animationMap) {
		this.animationMap = animationMap;
	}

	private int facingDirection;

	public final int getFacingDirection() {
		return facingDirection;
	}

	public void setFacingDirection(int facing) {
		this.facingDirection = facing;
	}
	
	public void setGraphic(BufferedImage graphic) {
		this.graphic = graphic;
	}

	public BoundingBox2D() {
		this(0, 0);
	}

	public BoundingBox2D(Color c) {
		this(c, 0, 0);
	}

	public BoundingBox2D(BufferedImage g) {
		this(g, 0, 0);
	}

	public BoundingBox2D(Color c, double width, double height) {
		this(width, height);
		setColor(c);
	}

	public BoundingBox2D(BufferedImage g, double width, double height) {
		this(width, height);
		setGraphic(g);

	}

	public BoundingBox2D(double width, double height) {
		super(width, height);
	}
	
	public BoundingBox2D(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public void draw(Graphics g) {
		if (color == null && graphic == null) return;
		if (getAnimationMap() == null) {
			if (graphic == null) {
				g.setColor(color);
				g.fillRect(
						(int) (getX() + (translation != null ? translation.offsetX() : 0)),
						(int) (getY() + (translation != null ? translation.offsetY() : 0)),
						(int) getWidth(),
						(int) getHeight());
			} else {
				g.drawImage(getGraphic(),
						(int) (getX() + (translation != null ? translation.offsetX() : 0)),
						(int) (getY() + (translation != null ? translation.offsetY() : 0)),
						(int) getWidth(),
						(int) getHeight(),
						null);
			}
		} else {
			Animation currentAnimation = null;

			for (Predicate predicate : getAnimationMap().keySet())
				if (predicate.isTrue())
					currentAnimation = getAnimationMap().get(predicate);

			if (currentAnimation == null)
				currentAnimation = lastAnimation;
			else if (lastAnimation != currentAnimation) {
				if (lastAnimation != null)
					lastAnimation.reset();
				currentAnimation.resume();
			}
			
			if (currentAnimation == null)
				return;
			
			if (getFacingDirection() == 1)
				g.drawImage(currentAnimation.getFrame().getImage(),
						(int) (getX() + (translation != null ? translation.offsetX() : 0)),
						(int) (getY() + (translation != null ? translation.offsetY() : 0)),
						(int) getWidth(),
						(int) getHeight(), null);
			else
				g.drawImage(currentAnimation.getFrame().getImage(),
						(int) (getX() + (translation != null ? translation.offsetX() : 0) + getWidth()),
						(int) (getY() + (translation != null ? translation.offsetY() : 0)),
						(int) -getWidth(),
						(int) getHeight(), null);
			lastAnimation = currentAnimation;
		}
	}

}
