package platformer.collision;

import java.awt.image.BufferedImage;

import javaJG.panel.Animation;
import javaJG.panel.Animation.KeyFrame;
import platformer.Settings;

public class GraphicalCollisionComponent {

	private final BufferedImage img;
	private Animation animation;
	private BufferedImage[] animationImages;

	public GraphicalCollisionComponent(BufferedImage img) {
		this.img = img;
	}

	public GraphicalCollisionComponent(BufferedImage img, String folder, double width, double height) {
		this.img = img;
		animation = Settings.loadAnimation(folder, 100, width, height);
	}

	public GraphicalCollisionComponent(BufferedImage img, BufferedImage[] images) {
		this.img = img;

		animation = new Animation(false);

		for (final BufferedImage image : images) {
			animation.addFrame(new KeyFrame(100, image));
		}
		animationImages = images;
	}

	public boolean isAnimated() {
		return animation != null;
	}

	public BufferedImage getImage() {
		return img;
	}

	public BufferedImage[] getAnimationImages() {
		return animationImages;
	}

	public Animation getAnimation() {
		return animation;
	}

}
