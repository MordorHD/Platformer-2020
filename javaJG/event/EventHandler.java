package javaJG.event;

import java.awt.AWTEvent;

/*
 * This interface handles MouseEvents or KeyEvents
 */
@FunctionalInterface
public interface EventHandler<E extends AWTEvent> {
	public abstract void handle(E event);
}
