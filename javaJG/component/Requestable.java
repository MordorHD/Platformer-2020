package javaJG.component;

import java.awt.AWTEvent;

/**
 * Implementation: 15.03.
 */
public interface Requestable extends Request {
	public abstract void handleRequest(String request, AWTEvent e);
}