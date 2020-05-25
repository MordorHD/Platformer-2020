package platformer.collision;

import platformer.entity.Entity;

public interface EntityBehaviour {
	
	public default void intersecting(Collision parent, Entity e) {}
	public default void onTop(Collision parent, Entity e) {}
	public default void jumpedOff(Collision parent, Entity e) {}
	public default void jumpedOn(Collision parent, Entity e) {}
	public default void bangedHead(Collision parent, Entity e) {}
	public default void ranAgainst(Collision parent, Entity e) {}
	
}
