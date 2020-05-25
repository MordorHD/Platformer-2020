package javaJG.component;

import java.awt.Color;

import javaJG.geometry.Rectangle2D;
import javaJG.geometry.SimpleShape2D;

import java.awt.AWTEvent;

/**
 * Simple text unit
 */
public class TextLabel extends TextComponent {

	public TextLabel() {
		this("");
	}

	public TextLabel(String content) {
		setBackgroundColor(new Color(255, 255, 255, 1));
		setBackgroundStroke(new Color(255, 255, 255, 1));
		append(content);
	}

	@Override
	public SimpleShape2D getBounds() {
		return new Rectangle2D(bounds.getX(), bounds.getY(), font.getTextWidth(toString()) + 2,
				font.getTextHeight(toString()));
	}

	@Override
	public void doRequest(String request, AWTEvent e) {

	}

}
