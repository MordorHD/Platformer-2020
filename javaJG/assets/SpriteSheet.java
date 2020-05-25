package javaJG.assets;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage spriteSheet;
	
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	
	private BufferedImage[] sprites1D;
	
	public BufferedImage[] getSprites() {
		return sprites1D;
	}
	
	private BufferedImage[][] sprites;
	
	public BufferedImage get(int x, int y) {
		return sprites[x][y];
	}

	public SpriteSheet(BufferedImage spritesheet, double offsetX, double offsetY, double width, double height) {
		this.spriteSheet = spritesheet;
		sprites = new BufferedImage[(int) (spritesheet.getWidth() / (width + offsetX))][(int) (spritesheet.getHeight()
				/ (height + offsetY))];
		sprites1D = new BufferedImage[lengthX() * (lengthY() + 1)];
		for (int x = 0; x < sprites.length; x++) 
			for (int y = 0; y < sprites[x].length; y++) {
				sprites[x][y] = spritesheet.getSubimage((int) (x * width + offsetX), (int) (y * height + offsetY),
						(int) width, (int) height);
				sprites1D[x * (y + 1)] = sprites[x][y];
			}
		
	}

	public SpriteSheet(BufferedImage spritesheet, double offsetX, double offsetY, int numX, int numY) {
		this(spritesheet, offsetX, offsetY, (double) spritesheet.getWidth() / numX,
				(double) spritesheet.getHeight() / numY);
	}

	public SpriteSheet(BufferedImage spritesheet, int numX, int numY) {
		this(spritesheet, 0, 0, numX, numY);
	}

	public SpriteSheet(BufferedImage spritesheet, double width, double height) {
		this(spritesheet, 0, 0, width, height);
	}
	
	public int lengthX() {
		return sprites.length;
	}
	
	public int lengthY() {
		return sprites.length > 0 ? 0 : sprites[0].length;
	}

}
