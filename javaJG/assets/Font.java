package javaJG.assets;

import java.awt.FontMetrics;
import java.awt.Toolkit;

public class Font {

	/**
	 * can be obtained with asset()
	 */
	private java.awt.Font font;

	private FontMetrics metrics;

	/**
	 * @param name of the font
	 * @param size of the font
	 */
	@SuppressWarnings("deprecation")
	public Font(String name, int size) {
		font = new java.awt.Font(name, java.awt.Font.PLAIN, size);
		metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
	}

	/**
	 * @return assets ergo the font
	 */
	public java.awt.Font asset() {
		return font;
	}

	/**
	 * @return size of the font
	 */
	public int getSize() {
		return font.getSize();
	}

	/**
	 * @return name of the font
	 */
	public String getName() {
		return font.getName();
	}

	/**
	 * @return width of a string
	 * 
	 * @param str input
	 */
	public int getTextWidth(String str) {
		int x = 0;

		String[] splitted = str.split("\n");

		for (String s : splitted)
			x = (int) Math.max(getWidth(s), x);

		return x;
	}

	/**
	 * @param c char to calculate the width
	 * @return the width of the char
	 */
	public float getWidth(char c) {
		return metrics.charWidth(c) * getSize();
	}

	/**
	 * This method takes in a string and calculates the width. It ignores \n
	 * 
	 * @param str character sequence
	 * @return width of the character sequence
	 */
	public float getWidth(String str) {
		float x = 0;
		for (int i = 0; i < str.length(); i++)
			x += getWidth(str.charAt(i));
		return x;
	}

	/**
	 * Calculates line height.
	 * 
	 * @return line height
	 */
	public int getLineHeight() {
		return getTextAscent() + getTextDescent();
	}

	/**
	 * @return text ascent
	 */
	public int getTextAscent() {
		return font.getSize();
	}

	/**
	 * @return text descent
	 */
	public int getTextDescent() {
		return 0;
	}

	/**
	 * Calculates the height of a paragraph. It checks for \n
	 * 
	 * @param str paragraph to check
	 * @return height of the paragraph
	 */
	public float getTextHeight(String str) {
		String[] splitted = str.split("\n");

		return splitted.length * getLineHeight();
	}

}
