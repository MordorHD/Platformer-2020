package platformer.collision;

import javaJG.geometry.component.BoundingBox2D;
import platformer.XMLWritable;

public class Collision extends BoundingBox2D implements XMLWritable {
	/**
	 * Type of the collision.
	 */
	private CollisionType type;

	/**
	 * Returns the CollisionType of this Collision.
	 * 
	 * @return Collision type.
	 */
	public CollisionType getType() {
		return type;
	}

	/**
	 * Changes the type of this Collision.
	 * 
	 * @param type New CollisionType.
	 * @return this
	 */
	public Collision setType(CollisionType type) {
		this.type = type;
		visible = type.isVisible();
		layer = type.getLayer() == null ? Layer.GROUND : type.getLayer();
		return this;
	}

	private Layer layer;
	
	public Layer getLayer() {
		return layer;
	}
	
	public void setLayer(Layer layer) {
		this.layer = layer;
	}
	
	/**
	 * If the Object is visible. Object with visible = false are not drawn, but not
	 * ignored by entities.<br>
	 * May be changed with setVisibility(value).
	 */
	private boolean visible = true;

	/**
	 * If this Collision is seen by the Player.
	 * 
	 * @return Visibility of the Collision.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Changes the visibility of this Collision.
	 * 
	 * @param visible If visible.
	 * @throws IllegalArgumentException If the CollsionType specifies that this
	 *                                  Collision can't be visible.
	 */
	public void setVisibility(final boolean visible) {
		if (visible && !(type == null) && !type.isVisible())
			throw new IllegalArgumentException("Visibility can't be set to true on a collision with type " + type + "!");
		this.visible = visible;
	}

	/**
	 * If the Object is not active, it is ignored by all entities.<br>
	 * May be changed with closeActivity() or activate().
	 */
	private boolean active = true;

	/**
	 * If the Collision can be interacted with by any Player.
	 * 
	 * @return Activity of the Collision.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Activates this Collision, makes it able to collide with other entities.
	 */
	public void activate() {
		this.active = true;
	}

	/**
	 * Deactivates this Collision, makes it unable to collide with other entities.
	 */
	public void closeActivity() {
		this.active = false;
	}

	/**
	 * Constructs a Collision Object on specified layer with initial size.
	 * 
	 * @param layer  Layer the Collision is on.
	 * @param width  Width of the Collision.
	 * @param height Height of the Collision.
	 */
	public Collision(final double width, final double height) {
		super(width, height);
	}

	/**
	 * Constructs a Collision Object on specified layer with initial size and
	 * initial position.
	 * 
	 * @param layer  Layer the Collision is on.
	 * @param x      Position in x direction.
	 * @param y      Position in y direction.
	 * @param width  Width of the Collision.
	 * @param height Height of the Collision.
	 */
	public Collision(final double x, final double y, final double width, final double height) {
		super(x, y, width, height);
	}

	@Override
	public String toXML() {
		String xml = "<Collision type=\"" + type + "\"";
		xml += ">" + getX() + ", " + getY() + "</Collision>";
		return xml;
	}

	@Override
	public Collision clone() {
		Collision copy = new Collision(getX(), getY(), getWidth(), getHeight()).setType(type);
		return copy;
	}

	/**
	 * 
	 * @param c Collision to compare to.
	 * @return If types are equal and they share the same bounds.
	 */
	public boolean equals(Collision c) {
		return super.equals(c) && c.getType() == getType();
	}

}
