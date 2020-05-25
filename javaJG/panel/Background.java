package javaJG.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javaJG.geometry.Translation;

public class Background {

	private static interface Stretching {

		public default double[] stretch() {
			return null;
		}

	}

	public static class BackgroundImage extends Background implements Stretching, Translation {

		private BufferedImage img;

		public BufferedImage getImage() {
			return img;
		}

		public void setImage(BufferedImage img) {
			this.img = img;
		}

		public BackgroundImage(BufferedImage img) {
			this.img = img;
		}

		@Override
		public double offsetX() {
			return 0;
		}

		@Override
		public double offsetY() {
			return 0;
		}
	}

	public static class BackgroundColor extends Background {

		private Color color;

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public BackgroundColor(Color color) {
			this.color = color;
		}
	}

	public abstract static class CustomBackground extends Background implements Translation {

		public abstract void paint(Graphics g);

		@Override
		public double offsetX() {
			return 0;
		}

		@Override
		public double offsetY() {
			return 0;
		}
	}

}
