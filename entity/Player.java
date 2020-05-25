package platformer.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javaJG.event.Controller;
import javaJG.geometry.Point2D;
import javaJG.geometry.component.Predicate;
import javaJG.panel.Animation;
import platformer.Settings;
import platformer.collision.CollisionType;
import platformer.configuration.ConfigModule;
import platformer.map.chunk.Chunk;
import platformer.map.chunk.MapChunker;

/**
 * Player class represents the main collision. Also can take user input.
 */
public class Player extends Entity implements Controller {
	/**
	 * Bunch of animations loaded static
	 */
	private static final Map<String, Animation> animations;

	private static final BufferedImage HEART = Settings.loadImage("Heart_Full.png"),
			HEART_EMPTY = Settings.loadImage("Heart_Empty.png");

	static {
		animations = Settings.loadAnimations("Player", 100, 16, 16);

		System.out.println(animations.get("Standing"));
		System.out.println(animations.get("Running"));
		System.out.println(animations.get("Jumping"));
		System.out.println(animations.get("Falling"));

		animations.get("Falling").loopFrom(2);
	}
	/**
	 * Variable to keep track of which key is pressed.
	 */
	private boolean UP, LEFT, RIGHT;

	public Player(Point2D position,
			ConfigModule module,
			MapChunker chunker,
			StatsProperties stats) {
		super(position.getX(), position.getY(), module, chunker, stats);
		setGraphic(animations.get("Standing").getFrames().get(0).getImage());
		Map<Predicate, Animation> map = new HashMap<Predicate, Animation>();
		map.put(new Predicate() {

			@Override
			public boolean isTrue() {
				return standing;
			}

		}, Player.animations.get("Standing"));

		map.put(new Predicate() {

			@Override
			public boolean isTrue() {
				return onFloor && (running || runningAgainstWall);
			}

		}, Player.animations.get("Running"));

		map.put(new Predicate() {

			@Override
			public boolean isTrue() {
				return jumping;
			}

		}, Player.animations.get("Jumping"));

		map.put(new Predicate() {

			@Override
			public boolean isTrue() {
				return falling;
			}

		}, Player.animations.get("Falling"));
		setAnimationMap(map);
	}

	@Override
	public void update(List<Chunk> chunks) {
		if (UP && onFloor)
			velocityY = -stats.JUMP_HEIGHT;
		if (LEFT) {
			if (velocityX > -stats.RUNNING_SPEED) {
				velocityX -= 1;
				if (velocityX < -stats.RUNNING_SPEED)
					velocityX = -stats.RUNNING_SPEED;
			}
		}
		if (RIGHT) {
			if (velocityX < stats.RUNNING_SPEED) {
				velocityX += 1;
				if (velocityX > stats.RUNNING_SPEED)
					velocityX = stats.RUNNING_SPEED;
			}
		}
		super.update(chunks);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				UP = true;
			break;
			case KeyEvent.VK_A:
				LEFT = true;
				setFacingDirection(-1);
			break;
			case KeyEvent.VK_D:
				RIGHT = true;
				setFacingDirection(1);
			break;
			default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				UP = false;
				if (jumping)
					velocityY *= GameConstants.JUMPING_CUT;
			break;
			case KeyEvent.VK_A:
				LEFT = false;
				if (RIGHT)
					setFacingDirection(1);
			break;
			case KeyEvent.VK_D:
				RIGHT = false;
				if (LEFT)
					setFacingDirection(-1);
			break;
			default:
			break;
		}
	}

	/**
	 * Draws coin/heart counters in the corners.
	 * 
	 * @param g
	 */
	public void drawOverlay(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		g.drawString(stats.NUMBER_OF_COINS + "", platformer.runtime.Runtime.window.getWidth() - 50, 17);
		g.drawImage(CollisionType.valueOf("COIN").getGraphicalCollisionComponent().getAnimation().getFrame().getImage(),
				platformer.runtime.Runtime.window.getWidth() - 40, 5, 20, 20, null);
		for (int i = 0; i < Math.ceil(stats.MAX_HP); i++)
			if (i < stats.HP)
				g.drawImage(HEART, 20 + (i % 8) * 25, 10 + (i / 8) * 25, 30, 30, null);
			else
				g.drawImage(HEART_EMPTY, 20 + (i % 8) * 25, 10 + (i / 8) * 25, 30, 30, null);
	}
}
