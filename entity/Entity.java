package platformer.entity;

import java.util.List;
import javaJG.geometry.Rectangle2D;
import javaJG.geometry.SimpleShape2D;
import platformer.collision.Collision;
import platformer.configuration.CallType;
import platformer.configuration.ConfigModule;
import platformer.configuration.SuperType;
import platformer.map.chunk.Chunk;
import platformer.map.chunk.MapChunker;

/**
 * 
 */
public abstract class Entity extends Collision {

	/**
	 * ConfigModule of map. Invokes methods in update()
	 */
	private final ConfigModule module;
	/**
	 * States the entity can have. Set in update() by checking velocity.
	 */
	protected boolean onFloor, jumping, falling = true, running, standing, runningAgainstWall;
	/**
	 * Velocity in x and y direction.
	 */
	public double velocityX, velocityY;
	/**
	 * Statistics of the entity.
	 * 
	 * @see StatsProperties
	 */
	public StatsProperties stats;
	/**
	 * Map this entity is on.
	 */
	protected MapChunker mapChunker;
	/**
	 * Last time the player was hit.
	 */
	private long lastHit;

	/**
	 * Creates an entity with standard size on initial position. Entities can only
	 * be instantiated when extending.
	 * 
	 * @param initialX Initial x position of the entity.
	 * @param initialY Initial y position of the entity.
	 * @param map      Map the entity is on.
	 * @param stats    Statistic of the entity.
	 */
	protected Entity(final double initialX, final double initialY, ConfigModule module, MapChunker chunker,
			StatsProperties stats) {
		super(initialX, initialY, GameConstants.SQS, GameConstants.SQS);
		this.mapChunker = chunker;
		this.stats = stats;
		this.module = module;
	}

	/**
	 * @return current instance of MapChunker
	 */
	public MapChunker chunkerInstance() {
		return mapChunker;
	}

	/**
	 * Lowers HP of the entity when not in invincible phase.
	 * 
	 * @param dmg Damage that is applied to the entity.
	 */
	public void applyDamage(final double dmg) {
		final long currentTime = System.currentTimeMillis();
		if (currentTime >= lastHit + stats.HIT_COOLDOWN) {
			stats.HP -= dmg;
			lastHit = currentTime;
		}
	}

	/**
	 * Also updates jumping, falling, standing, runningAgainstWall, onFloor.
	 */
	public void update(List<Chunk> chunks) {
		if (module != null) {
			module.invoke(this, SuperType.ONLOOP, CallType.DO);
			module.invoke(this, SuperType.ONLOOP, CallType.POS,
					Double.valueOf((int) (getX() / GameConstants.SQS)),
					Double.valueOf((int) (getY() / GameConstants.SQS)));
			module.invoke(this, SuperType.ONLOOP, CallType.ZONE,
					Double.valueOf((int) (getX() / GameConstants.SQS)),
					Double.valueOf((int) (getY() / GameConstants.SQS)));
		}

		onFloor = false;
		runningAgainstWall = false;

		boolean intersects = false;

		SimpleShape2D bounds = null;
		for (Chunk chunk : chunks)
			for (Collision c : chunk.toOneDimension())
				if (c != null && c.getType() != null) {
					if (!c.isActive())
						continue;
					if (c.getType().modifyColliding()) {
						bounds = c.getType().getCollidableBounds().clone();
						bounds.add(c.getX() * GameConstants.SQS, c.getY() * GameConstants.SQS);
					} else {
						bounds = new Rectangle2D(
								c.getX() * GameConstants.SQS,
								c.getY() * GameConstants.SQS,
								c.getWidth() * GameConstants.SQS,
								c.getHeight() * GameConstants.SQS);
					}
					{ // checking x
						if (velocityX > 0 && new Rectangle2D(getX() - 5 + getWidth(), getY(), velocityX, getHeight())
								.intersects(bounds)) {
							if (c.getType().isWalkable()) {
								double diff;
								if ((diff = Math.abs(bounds.getY() - (getY() + getHeight()))) < 10) // if the diff is
																									// not so high,
																									// just walk on top
																									// of it
									add(0, -diff);
								else {
									if (c.getType().getEntityBehaviour() != null)
										c.getType().getEntityBehaviour().ranAgainst(c, this);
									velocityX = 0;
									setX(bounds.getX() - getWidth() + 5);
									runningAgainstWall = true;
								}
							}
							intersects = true;
						}

						if (velocityX < 0
								&& new Rectangle2D(getX() + velocityX + 5, getY(), velocityX * -1 - 5, getHeight())
										.intersects(bounds)) {
							if (c.getType().isWalkable()) {
								double diff;
								if ((diff = Math.abs(bounds.getY() - (getY() + getHeight()))) < 10) // if the diff is
																									// not so high,
																									// just walk on top
																									// of it
									add(0, -diff);
								else {
									if (c.getType().getEntityBehaviour() != null)
										c.getType().getEntityBehaviour().ranAgainst(c, this);
									velocityX = 0;
									setX(bounds.getX() + bounds.getWidth() - 5);
									runningAgainstWall = true;
								}
							}
							intersects = true;
						}
					}
					{ // checking y
						if (velocityY > 0
								&& new Rectangle2D(getX() + 5, getY() + getHeight(), getWidth() - 10, velocityY)
										.intersects(bounds)) {
							if (c.getType().isWalkable()) {
								if (c.getType().getEntityBehaviour() != null)
									c.getType().getEntityBehaviour().jumpedOn(c, this);
								velocityY = 0;
								setY(bounds.getY() - getHeight());
								onFloor = true;
							}
							intersects = true;
						}

						if (velocityY < 0
								&& new Rectangle2D(getX() + 5, getY() + velocityY, getWidth() - 10, velocityY * -1)
										.intersects(bounds)) {
							if (c.getType().isWalkable()) {
								if (c.getType().getEntityBehaviour() != null)
									c.getType().getEntityBehaviour().bangedHead(c, this);
								velocityY = 0;
								setY(bounds.getY() + bounds.getHeight());
							}
							intersects = true;
						}
					}
					if (c.getType().getEntityBehaviour() != null)
						if (intersects) {
							if (module != null) {
								module.invoke(this, SuperType.ONLOOP, CallType.TYPE, c.getType().toString());
							}
							c.getType().getEntityBehaviour().intersecting(c, this);
						}
					intersects = false;
				}

		falling = velocityY > 0;
		jumping = velocityY <= 0 && !onFloor;
		running = velocityX != 0 && onFloor;
		standing = !jumping && !falling && !standing && !running && !runningAgainstWall;

		add(velocityX, velocityY);

		if (velocityX > 0) {
			if (onFloor)
				velocityX -= GameConstants.FLOOR_AIR_RESISTANCE;
			else
				velocityX -= GameConstants.AIR_RESISTANCE;
			if (velocityX < 0)
				velocityX = 0;
		}
		if (velocityX < 0) {
			if (onFloor)
				velocityX += GameConstants.FLOOR_AIR_RESISTANCE;
			else
				velocityX += GameConstants.AIR_RESISTANCE;
			if (velocityX > 0)
				velocityX = 0;
		}
		velocityY += GameConstants.GRAVITY;
	}
}
