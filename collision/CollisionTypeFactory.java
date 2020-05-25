package platformer.collision;

import javaJG.geometry.SimpleShape2D;

public class CollisionTypeFactory {

	public static CollisionTypeFactory build() {
		return new CollisionTypeFactory(new CollisionType());
	}

	private CollisionType t;

	private CollisionTypeFactory(CollisionType t) {
		this.t = t;
		t.setName("New_Type" + System.currentTimeMillis());
		t.setVisible(true);
		t.setWalkable(true);
	}

	public CollisionTypeFactory name(String name) {
		t.setName(name);
		return this;
	}

	public CollisionTypeFactory graphics(GraphicalCollisionComponent gcc) {
		t.setGraphicsCollisionComponent(gcc);
		return this;
	}

	public CollisionTypeFactory show() {
		t.setVisible(true);
		return this;
	}

	public CollisionTypeFactory hide() {
		t.setVisible(false);
		return this;
	}
	
	public CollisionTypeFactory noCollision() {
		t.setWalkable(false);
		return this;
	}

	public CollisionTypeFactory collide() {
		t.setWalkable(true);
		return this;
	}
	
	public CollisionTypeFactory layer(Layer layer) {
		t.setLayer(layer);
		return this;
	}

	public CollisionTypeFactory collide(SimpleShape2D bounds) {
		t.setCollidableBounds(bounds);
		t.setWalkable(true);
		return this;
	}

	public CollisionTypeFactory draw(SimpleShape2D bounds) {
		t.setDrawnBounds(bounds);
		return this;
	}

	public CollisionTypeFactory entityBehaviour(EntityBehaviour entityBehaviour) {
		t.setEntityBehaviour(entityBehaviour);
		return this;
	}

	public CollisionTypeFactory worldBehaviour(WorldBehaviour worldBehaviour) {
		t.setWorldBehaviour(worldBehaviour);
		return this;
	}

	public CollisionType create() {
		return t;
	}

}
