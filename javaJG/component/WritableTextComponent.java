package javaJG.component;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javaJG.assets.Arrays;
import javaJG.assets.attribute.Attribute;
import javaJG.assets.attribute.Variable;
import javaJG.event.KeyCode;


/**
 * Implementation: 17.03.
 * 
 * Last edit: 18.03.
 */
public abstract class WritableTextComponent extends TextComponent implements KeyCode {

	/**
	 * TextArea has an ArrayList<String> called allowedCharacters and those are
	 * characters that you are allowed to type. The user can decide which characters
	 * are allowed. He might choose to only take DIGITS or ALPHABET.
	 */

	public static final String[] ALPHABET = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "h", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "ä", "ö", "ü", "ß", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "H", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z", "Ä", "Ö", "Ü" };
	public static final String[] DIGITS = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
	public static final String[] SYMBOLS = { "?", "´", "#", "'", "+", "*", "~", "<", ">", "|", ",", ";", ".", ":", "-",
			"_", "!", "\"", "§", "²", "³", "$", "%", "&", "/", "{", "}", "(", ")", "[", "]", "\\", "^", "°" };
	public static final String[] STANDARD_ASSETS = { "     ", " ", "\n" };

	public static final ArrayList<String> STANDARD_CHARACTERS = Arrays.createArrayList(ALPHABET, DIGITS, SYMBOLS,
			STANDARD_ASSETS);

	/**
	 * List of allowed characters
	 */
	private final ArrayList<String> allowedCharacters;
	/**
	 * Saves position where the mouse was pressed to where it was dragged.
	 */
	private LinkedList<int[]> selected = new LinkedList<>();
	/**
	 * Position of the line/cursor
	 */
	private int cursor = -1;
	/**
	 * True when you can edit the text area
	 */
	private boolean editable = true;

	public WritableTextComponent() {
		this("", STANDARD_CHARACTERS);
	}

	public WritableTextComponent(String text) {
		this(text, STANDARD_CHARACTERS);
	}

	public WritableTextComponent(ArrayList<String> allowedCharacters) {
		this("", allowedCharacters);
	}

	/**
	 * TextArea with standard font is created
	 * 
	 * @param text              initial text
	 * @param allowedCharacters list of allowed characters
	 */
	public WritableTextComponent(String text, ArrayList<String> allowedCharacters) {
		super(200, 150);
		append(text);
		this.allowedCharacters = allowedCharacters;
		this.setFocusable(true);
	}

	@Override
	public void doRequest(String request, AWTEvent e) {
		if (request == KEY_PRESSED)
			keyPressed((KeyEvent) e);
		if (request == MOUSE_PRESSED) {
			mousePressed((MouseEvent) e);
			selected.clear();
		}
		if (request == MOUSE_DRAGGED)
			mouseDragged((MouseEvent) e);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, (int)getWidth() - 1, (int)getHeight() - 1);
		g2.setFont(font.asset());

		Color color = textColor;
		Color bcolor = backgroundColor;
		Color bstroke = backgroundStroke;

		int x = 2, y = 2;
		for (int i = 0; i < content.size(); i++) {
			String character = content.getPrimaryValue(i);

			if (character.equals("\n")) {
				if (cursor == i) {
					g2.setColor(Color.BLACK);
					g2.drawLine(x, y, x, y + font.getLineHeight());
				}
				x = 2;
				y += font.getLineHeight();
				continue;
			}

			Attribute attribute = attributes.containsKey(i) ? attributes.get(i) : content.getSecondaryValue(i);
			try {
				bcolor = (Color) attribute.getValue(Variable.BACKGROUNDCOLOR);
			} catch (ClassCastException | IllegalArgumentException e) {
			}
			try {
				bstroke = (Color) attribute.getValue(Variable.BACKGROUNDSTROKE);
			} catch (ClassCastException | IllegalArgumentException e) {
			}
			try {
				color = (Color) attribute.getValue(Variable.TEXTCOLOR);
			} catch (ClassCastException | IllegalArgumentException e) {

			}

			g2.setColor(bcolor);
			g2.fillRect(x, y, font.getTextWidth(character), font.getLineHeight());
			g2.setColor(bstroke);
			g2.drawRect(x, y, font.getTextWidth(character), font.getLineHeight());
			g2.setColor(color);
			g2.drawString(character, x, y + font.getTextAscent());

			color = textColor;
			bcolor = backgroundColor;
			bstroke = backgroundStroke;

			if (cursor == i) {
				g2.setColor(Color.BLACK);
				g2.drawLine(x, y, x, y + font.getLineHeight());
			}

			x += font.getTextWidth(character);
		}
		
		if (cursor == content.size()) {
			g2.setColor(Color.BLACK);
			g2.drawLine(x, y, x, y + font.getLineHeight());
		}

		x = 2;
		y = 2;
		for (int i = 0; i < content.size(); i++) {
			String character = content.getPrimaryValue(i);

			if (character.equals("\n")) {
				x = 2;
				y += font.getLineHeight();
				continue;
			}
			x += font.getTextWidth(character);
		}
	}

	/**
	 * Sets the variable editable
	 * 
	 * @param editable true when editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	private void keyPressed(KeyEvent e) {
		String key = String.valueOf(e.getKeyCode());

		if (e.getKeyCode() == LEFT)
			if (cursor > 0)
				cursor--;

		if (e.getKeyCode() == RIGHT)
			if (cursor < content.size())
				cursor++;

		if (e.getKeyCode() == UP) {
			int[] pos = getPos(cursor);
			if ((pos[1] = pos[1] - font.getSize()) >= 0)
				cursor = getIndex(pos);
		}

		if (e.getKeyCode() == DOWN) {
			int[] pos = getPos(cursor);
			pos[1] += font.getSize();
			cursor = getIndex(pos);
		}

		if (!editable)
			return;

		if (e.getKeyCode() == BACKSPACE && cursor > 0)
			content.remove(cursor-- - 1);

		if (e.getKeyCode() == DELETE && cursor < content.size())
			content.remove(cursor);

		if (e.getKeyCode() == ENTER)
			key = "\n";

		if (e.getKeyCode() == TAB)
			key = "     ";

		if (allowedCharacters.contains(key))
			content.put(cursor++, key, STANDARD());
	}

	/**
	 * Reverses getIndex() function
	 * 
	 * @param index of the letter
	 * @return position of the letter
	 */
	private int[] getPos(int index) {
		int x = 2, y = 2;
		FOR: for (int i = 0; i < content.size(); i++) {
			String c = content.getPrimaryValue(i);

			if (i == index)
				return new int[] { x, y };

			switch (c) {
			case "\n":
				x = 2;
				y += font.getTextAscent() + font.getTextDescent();
				if (i == index)
					return new int[] { x, y };
				continue FOR;
			}

			x += font.getTextWidth(c);
		}
		if (index == content.size())
			return new int[] { x, y };
		return new int[] { 2, 2 };
	}

	/**
	 * 
	 * @param pos position on the component
	 * @return the index of the letter
	 */
	private int getIndex(int[] pos) {
		int px = pos[0];
		int py = pos[1];
		
		int x = 2, y = 2;

		FOR: for (int i = 0; i < content.size(); i++) {
			String c = content.getPrimaryValue(i);

			switch (c) {
			case "\n":
				if (py > y - 2 && py < y + font.getSize() - 2)
					return i;

				x = 2;
				y += font.getTextAscent() + font.getTextDescent();
				continue FOR;
			}

			if (Math.abs(px - x) <= font.getTextWidth(c) / 2 && py > y - 2 && py < y + font.getSize() - 2) {
				return i;
			}

			x += font.getTextWidth(c);
		}
		return content.size();
	}

	private void mousePressed(MouseEvent e) {
		int mx = (int) Math.abs(e.getX() - getX());
		int my = (int) Math.abs(e.getY() - getY());

		cursor = getIndex(Arrays.toPrimitive(Arrays.array(mx, my)));
	}

	private void mouseDragged(MouseEvent e) {
		int mx = (int) Math.abs(e.getX() - getX());
		int my = (int) Math.abs(e.getY() - getY());

		selected.add(Arrays.toPrimitive(Arrays.array(mx, my)));

	}

}
