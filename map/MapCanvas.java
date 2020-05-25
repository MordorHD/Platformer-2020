package platformer.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javaJG.geometry.Rectangle2D;
import javaJG.geometry.SimpleShape2D;
import platformer.SideScrollerMode;
import platformer.collision.Collision;
import platformer.entity.GameConstants;
import platformer.map.chunk.Chunk;

public class MapCanvas {

	private final SideScrollerMode sideScrollerMode;
	
	public MapCanvas(SideScrollerMode sideScrollerMode) {
		this.sideScrollerMode = sideScrollerMode;
	}

	/**
	 * Draws the seen map.
	 * 
	 * @param offsetX
	 * @param offsetY
	 * @return Array containing re-calculated offsetX and offsetY.
	 */
	public void draw(Graphics g, List<Chunk> chunks) {
		SimpleShape2D realBoundsOfCollision = null;
		BufferedImage image = null;
		for (Chunk chunk : chunks)
			for (Collision c : chunk.toOneDimension())
				if (c != null && c.getType() != null)
					if (c.isVisible()) {
						if (c.getType().modifyDrawn()) {
							realBoundsOfCollision = c.getType().getDrawnBounds().clone();
							realBoundsOfCollision.multiply(GameConstants.SQS, GameConstants.SQS);
							realBoundsOfCollision.add(c.getType().getDrawnBounds().getX(), c.getType().getDrawnBounds().getY());
						} else
							realBoundsOfCollision = new Rectangle2D(
									c.getX() * GameConstants.SQS,
									c.getY() * GameConstants.SQS,
									c.getWidth() * GameConstants.SQS,
									c.getHeight() * GameConstants.SQS);
						
						image = c.getType().getGraphicalCollisionComponent().isAnimated() ?
								c.getType().getGraphicalCollisionComponent().getAnimation().getFrame().getImage() :
								c.getType().getGraphicalCollisionComponent().getImage();

						if (!c.getType().getGraphicalCollisionComponent().isAnimated()
								|| c.getType().getGraphicalCollisionComponent().getAnimation().getFrame() == null)
							g.drawImage(image,
									(int) (realBoundsOfCollision.getX()),
									(int) (realBoundsOfCollision.getY()),
									(int) (realBoundsOfCollision.getWidth()),
									(int) (realBoundsOfCollision.getHeight()), null);
						else
							g.drawImage(image,
									(int) (realBoundsOfCollision.getX()),
									(int) (realBoundsOfCollision.getY()),
									(int) (realBoundsOfCollision.getWidth()),
									(int) (realBoundsOfCollision.getHeight()), null);
					}
	}
}
