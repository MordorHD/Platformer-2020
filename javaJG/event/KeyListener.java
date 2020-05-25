package javaJG.event;

import java.awt.event.KeyEvent;

public interface KeyListener {
	
	public void keyPressed(KeyEvent e);

	public void keyReleased(KeyEvent e);

	public void keyTyped(KeyEvent e);
}
