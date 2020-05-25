package javaJG.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import javaJG.assets.ConnectedList;
import javaJG.assets.Font;
import javaJG.assets.attribute.Attribute;
import javaJG.assets.attribute.Variable;


/**
 * Implementation: 17.03.
 * 
 * Last edit: 18.03.
 */
public abstract class TextComponent extends Component {

	/**
	 * Color attributes
	 */
	protected Color backgroundColor = Color.WHITE;
	protected Color textColor = Color.BLACK;
	protected Color backgroundStroke = Color.WHITE;
	/**
	 * Text font of the component
	 */
	protected Font font;
	/**
	 * First argument: Text that the component contains Second argument: Attributes
	 * that are applied to that text
	 */
	protected ConnectedList<String, Attribute> content = new ConnectedList<>();
	/**
	 * Contains all attributes that should be used at a specific index
	 */
	protected Map<Integer, Attribute> attributes = new HashMap<>();

	/**
	 * @return the standard attributes
	 */
	public final Attribute STANDARD() {
		return new Attribute("<textcolor=" + textColor + ";backgroundcolor=" + backgroundColor + ";backgroundstroke="
				+ backgroundStroke + ";>");
	}

	public TextComponent() {
		this(null, 0, 0);
	}

	public TextComponent(Component parent) {
		this(parent, 0, 0);
	}

	public TextComponent(float width, float height) {
		this(null, width, height);
	}

	public TextComponent(Component parent, float width, float height) {
		super(parent, width, height);
		font = new Font("Arial", 15);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (String str : content.getPrimaryList())
			builder.append(str);

		return builder.toString();
	}

	/**
	 * Sets the text font
	 * 
	 * @param font new font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Sets the text font
	 * 
	 * @param name   of font
	 * @param size   of font
	 * @param smooth
	 */
	public void setFont(String name, int size) {
		font = new Font(name, size);
	}

	/**
	 * Sets the color of rectangles drawn behind a letter
	 * 
	 * @param color RGB value
	 */
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}

	/**
	 * Sets the stroke of rectangles drawn behind a letter
	 * 
	 * @param color RGB value
	 */
	public void setBackgroundStroke(Color color) {
		this.backgroundStroke = color;
	}

	/**
	 * Sets the color of the text
	 * 
	 * @param color RGB value
	 */
	public void setTextColor(Color color) {
		this.textColor = color;
	}

	/**
	 * Appends the text list
	 * 
	 * @param str  content
	 * @param attr attributes of the string
	 */
	public void append(String str, String attr) {
		for (int i = 0; i < str.length(); i++)
			content.put(String.valueOf(str.charAt(i)), new Attribute(attr));
	}

	/**
	 * Appends the text list
	 * 
	 * @param str content
	 */
	public void append(String str) {
		append(str, "<value=0>");
	}

	/**
	 * @param index when to start using the attribute
	 * @param attr  list of all attributes to be added
	 */
	public void appendAttribute(Integer index, String attr) {
		try {
			this.attributes.get(index).appendAttribute(attr);
		} catch (Exception e) {
			this.attributes.put(index, new Attribute(attr));
		}
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.setFont(font.asset());

		Color color = textColor;
		Color bcolor = backgroundColor;
		Color bstroke = backgroundStroke;

		int x = 2, y = 2;
		for (int i = 0; i < content.size(); i++) {
			String character = content.getPrimaryValue(i);

			if (character.equals("\n")) {
				x = 2;
				y += font.getLineHeight();
				continue;
			}

			Attribute attribute = attributes.containsKey(i) ? attributes.get(i) : content.getSecondaryValue(i);
			try {
				bcolor = (Color) attribute.getValue(Variable.BACKGROUNDCOLOR);
			} catch (Exception e) {
			}
			try {
				bstroke = (Color) attribute.getValue(Variable.BACKGROUNDSTROKE);
			} catch (Exception e) {
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

			x += font.getTextWidth(character);
		}
	}

}
