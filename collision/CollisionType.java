package platformer.collision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javaJG.geometry.SimpleShape2D;

public final class CollisionType {

	private static final List<CollisionType> values;

	static {
		values = new ArrayList<CollisionType>(CollisionTypeSets.BUILT_IN_TYPES());
	}

	public static int size() {
		return values.size();
	}

	public static boolean remove(CollisionType t) {
		if (CollisionTypeSets.BUILT_IN_TYPES().contains(t)) // not allowing removal of built in types
			return false;
		return values.remove(t);
	}

	public static boolean contains(String name) {
		return valueOf(name) != null;
	}

	public static CollisionType valueOf(String name) {
		for (CollisionType value : values)
			if (value.name.equals(name))
				return value;
		return null;
	}

	public static CollisionType[] values() {
		return values.toArray(new CollisionType[values.size()]);
	}

	public static Iterator<CollisionType> iterator() {
		return values.iterator();
	}

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	private boolean walkable;

	public boolean isWalkable() {
		return walkable;
	}
	
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	private boolean visible;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	private Layer layer;
	
	public Layer getLayer() {
		return layer;
	}
	
	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	private GraphicalCollisionComponent gcc;
	
	public GraphicalCollisionComponent getGraphicalCollisionComponent() {
		return gcc;
	}

	public void setGraphicsCollisionComponent(GraphicalCollisionComponent gcc) {
		this.gcc = gcc;
	}

	private SimpleShape2D modifiedBounds;

	public SimpleShape2D getCollidableBounds() {
		return modifiedBounds;
	}

	public boolean modifyColliding() {
		return modifiedBounds != null;
	}

	public void setCollidableBounds(SimpleShape2D bounds) {
		modifiedBounds = bounds;
	}

	private SimpleShape2D modifiedDrawnBounds;

	public SimpleShape2D getDrawnBounds() {
		return modifiedDrawnBounds;
	}

	public boolean modifyDrawn() {
		return modifiedDrawnBounds != null;
	}

	public void setDrawnBounds(SimpleShape2D bounds) {
		modifiedDrawnBounds = bounds;
	}

	private WorldBehaviour worldBehaviour;

	public WorldBehaviour getWorldBehaviour() {
		return worldBehaviour;
	}

	public void setWorldBehaviour(WorldBehaviour worldBehaviour) {
		this.worldBehaviour = worldBehaviour;
	}

	private EntityBehaviour entityBehavoiur;

	public EntityBehaviour getEntityBehaviour() {
		return entityBehavoiur;
	}

	public void setEntityBehaviour(EntityBehaviour entityBehaviour) {
		this.entityBehavoiur = entityBehaviour;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (!o.getClass().equals(CollisionType.class))
			return false;
		return (o.toString().equals(toString()));
	}

}
