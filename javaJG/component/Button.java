package javaJG.component;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import java.awt.Color;

import javaJG.assets.DrawingHelper;
import javaJG.assets.Font;
import javaJG.event.EventHandler;

public class Button extends Component {

	/**
	 * Text on the button
	 */
	private final String text;
	/**
	 * font of the text on the button
	 */
	private Font font;
	/**
	 * Color assets
	 */
	private Color currentColor = Color.GRAY;

	public Color staticColor = Color.GRAY;
	public Color hoverColor = Color.LIGHT_GRAY;
	public Color holdColor = Color.DARK_GRAY;

	private EventHandler<MouseEvent> onAction;

	public EventHandler<MouseEvent> getOnAction() {
		return onAction;
	}

	public void setOnAction(EventHandler<MouseEvent> onAction) {
		this.onAction = onAction;
	}

	public Button() {
		this("");
	}

	public Button(String text) {
		super(200, 40);
		this.text = text;
		font = new Font("Arial", 25);
	}

	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void doRequest(String request, AWTEvent e) {
		switch (request) {
		case MOUSE_CLICKED:
			if (onAction != null)
				onAction.handle((MouseEvent) e);
			break;
		case MOUSE_RELEASED_OUTSIDE:
			currentColor = staticColor;
			break;
		case MOUSE_PRESSED:
			currentColor = holdColor;
			break;
		case MOUSE_RELEASED:
			currentColor = hoverColor;
			break;
		case MOUSE_ENTERED:
			currentColor = hoverColor;
			break;
		case MOUSE_EXITED:
			currentColor = staticColor;
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(currentColor);
		g2.fillRoundRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), 10, 10);
		g2.setColor(Color.BLACK);
		DrawingHelper.drawCenteredString(g, text, (int) (getX() + getWidth() / 2), (int) (getY() + getHeight() / 2 + font.getTextAscent() / 2));
	}

}
