package javaJG.panel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JPanel;

import javaJG.component.Component;
import javaJG.component.Request;
import javaJG.geometry.component.BoundingBox2D;

public class Panel extends JPanel
		implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	private static final long serialVersionUID = -8642448286493339992L;

	private List<Component> nodes = new ArrayList<Component>();

	public List<Component> getChildren() {
		return nodes;
	}

	private List<BoundingBox2D> boundingBoxes = new ArrayList<BoundingBox2D>();

	public List<BoundingBox2D> getBoundingBoxes() {
		return boundingBoxes;
	}

	public void addComponent(Component component) {
		getChildren().add(component);
	}

	public void addComponent(Component component, double x, double y) {
		getChildren().add(component);
		component.setX(x);
		component.setY(y);
	}

	private Component focused;

	public void setFocused(Component c) {
		focused = c;
	}

	public Panel() {
		setFocusable(true);
		requestFocus();
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
	}

	public TimerTask createNewTask() {
		return new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		};
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Component n : getChildren())
			n.draw(g);
		for (BoundingBox2D box : getBoundingBoxes())
			box.draw(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (focused != null)
			focused.handleRequest(Request.KEY_TYPED, e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (focused != null)
			focused.handleRequest(Request.KEY_PRESSED, e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (focused != null)
			focused.handleRequest(Request.KEY_RELEASED, e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		for (Component n : getChildren())
			n.handleRequest(Request.MOUSE_WHEEL, e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		for (Component n : getChildren())
			n.handleRequest(Request.MOUSE_DRAGGED, e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (Component n : getChildren())
			n.handleRequest(Request.MOUSE_MOVED, e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (Component n : getChildren())
			n.handleRequest(Request.MOUSE_CLICKED, e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Component n : getChildren())
			n.handleRequest(Request.MOUSE_PRESSED, e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (Component n : getChildren())
			n.handleRequest(Request.MOUSE_RELEASED, e);

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
