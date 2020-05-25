package platformer.map.chunk;

import platformer.collision.Collision;
import platformer.map.Map;

/**
 * Additionally saves the time the player has last seen this chunk.
 */
public class Chunk extends Map {

	long lastSeenByPlayer;

	public Chunk(Collision[] content, final int lowestX, final int lowestY, final int highestX,
			final int highestY) {
		super(content, lowestX, lowestY, highestX, highestY);
	}

}
