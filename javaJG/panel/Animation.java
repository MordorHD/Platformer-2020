package javaJG.panel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javaJG.assets.SpriteSheet;

public class Animation implements Cloneable {

	private List<KeyFrame> frames = new ArrayList<KeyFrame>();
	private KeyFrame currentFrame;
	private long lastTrigger;
	private boolean paused = true;
	private int loopingStart = 0;
	private int frameIndex = 0;

	public Animation(boolean initialyPaused) {
		paused = initialyPaused;
	}

	public Animation(boolean intitialyPaused, SpriteSheet frames, int time) {
		this(intitialyPaused);
		for (BufferedImage img : frames.getSprites())
			getFrames().add(new KeyFrame(time, img));
	}

	@Override
	public Animation clone() {
		Animation animation = new Animation(paused);
		animation.frames.clear();
		animation.frames.addAll(frames);
		animation.loopFrom(frameIndex);
		return animation;
	}

	public void disableLooping() {
		loopingStart = frames.size() - 1;
	}

	public void loopFrom(int index) {
		this.loopingStart = index;
	}

	public List<KeyFrame> getFrames() {
		return frames;
	}

	public KeyFrame getFrame() {
		if (currentFrame == null)
			return currentFrame = frames.get(0);

		final long currentTime = System.currentTimeMillis();
		if (currentTime - lastTrigger > currentFrame.getDuration()) {
			try {
				currentFrame = frames.get(frameIndex += 1);
			} catch (IndexOutOfBoundsException ex) {
				currentFrame = frames.get(frameIndex = loopingStart);
			} finally {
				lastTrigger = currentTime;
			}
		}
		return currentFrame;
	}

	public void addFrame(KeyFrame frame) {
		frames.add(frame);
	}

	public void play() {
		paused = false;
	}

	public void pause() {
		paused = true;
	}

	public void resume() {
		paused = false;
	}

	public void reset() {
		currentFrame = frames.get(frameIndex = 0);
	}

	public static final class KeyFrame {

		private final BufferedImage frame;
		private final long duration;

		public KeyFrame(long duration, BufferedImage frame) {
			this.duration = duration;
			this.frame = frame;
		}

		public BufferedImage getImage() {
			return frame;
		}

		public long getDuration() {
			return duration;
		}

	}

}
