package platformer.entity;

public class StatsProperties {
	/**
	 * Health points of the Entity.
	 */
	public double HP;
	/**
	 * Damage the Entity can do on other entities.
	 */
	public double DMG;
	/**
	 * Time the Entity is invincible after a hit in ms.
	 */
	public int HIT_COOLDOWN = 500;
	/**
	 * Speed the Entity moves in x direction.
	 */
	public double RUNNING_SPEED;
	/**
	 * Speed the Entity can accelerate in x direction
	 */
	public double acceleration;
	/**
	 * Speed at which the jump begins.
	 */
	public double JUMP_HEIGHT;
	/**
	 * Number of coins the Entity has collected.
	 */
	public int NUMBER_OF_COINS;
	/**
	 * Maximum health points an Entity can have.
	 */
	public double MAX_HP;

	public StatsProperties(final double HP, final double MAX_HP, final double DMG, final double RUNNING_SPEED,
			final double acceleration, final double JUMP_HEIGHT) {
		this.HP = HP;
		this.MAX_HP = MAX_HP;
		this.DMG = DMG;
		this.RUNNING_SPEED = RUNNING_SPEED;
		this.acceleration = acceleration;
		this.JUMP_HEIGHT = JUMP_HEIGHT;
	}
}
