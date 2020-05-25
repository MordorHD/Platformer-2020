package javaJG.event;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface Controller extends MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	public default void mouseClicked(MouseEvent e) {

	}

	public default void mousePressed(MouseEvent e) {

	}

	public default void mouseReleased(MouseEvent e) {

	}

	public default void mouseMoved(MouseEvent e) {

	}

	public default void mouseDragged(MouseEvent e) {

	}

	public default void mouseWheel(MouseWheelEvent e) {

	}

	public default void keyPressed(KeyEvent e) {

	}

	public default void keyReleased(KeyEvent e) {

	}

	public default void keyTyped(KeyEvent e) {

	}
}
