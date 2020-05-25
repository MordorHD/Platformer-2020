package javaJG.assets;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class DrawingHelper {

	/**
	 * Draw a String with center align.
	 *
	 * @param g    The Graphics instance.
	 * @param text The String to draw.
	 * @param x    Position x of the center.
	 * @param y    Position y of the center.
	 */
	public static void drawCenteredString(Graphics g, String text, int x, int y) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		// Draw the String
		g.drawString(text, x - metrics.stringWidth(text) / 2, y - metrics.getHeight() / 2 + metrics.getAscent());
	}

	public static void drawRightAlignedString(Graphics g, String text, int x, int y) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		// Draw the String
		g.drawString(text, x - metrics.stringWidth(text), y);
	}

}
