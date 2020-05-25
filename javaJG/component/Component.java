package javaJG.component;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javaJG.event.EventHandler;
import javaJG.geometry.DimensionOperations;
import javaJG.geometry.PositionOperations;
import javaJG.geometry.Rectangle2D;
import javaJG.geometry.SimpleShape2D;

/**
 * Subclass of every graphic
 */
public abstract class Component implements Requestable, DimensionOperations, PositionOperations {
	/**
	 * Boundary of the component
	 */
	protected SimpleShape2D bounds;

	public SimpleShape2D getBounds() {
		return bounds;
	}

	public double getX() {
		return getBounds().getX();
	}

	public double getY() {
		return getBounds().getY();
	}

	public double getWidth() {
		return getBounds().getWidth();
	}

	public double getHeight() {
		return getBounds().getHeight();
	}

	public void setBounds(SimpleShape2D shape) {
		if (shape == null)
			throw new NullPointerException("Bounds can't be set to null!");
		this.bounds = shape;
	}

	public void setBounds(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public double setX(double x) {
		return bounds.setX(x);
	}

	@Override
	public double setY(double y) {
		return bounds.setY(y);
	}

	@Override
	public double setWidth(double width) {
		return bounds.setWidth(width);
	}

	@Override
	public double setHeight(double height) {
		return bounds.setHeight(height);
	}

	/**
	 * Parent of the component
	 */
	private Component parent;

	public Component getParent() {
		return parent;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}

	/**
	 * Children of the component
	 */
	private ArrayList<Component> children = new ArrayList<Component>();

	public ArrayList<Component> getChildren() {
		return children;
	}

	boolean mouseInBounds;

	public boolean isMouseInBounds() {
		return mouseInBounds;
	}

	/**
	 * Components can be focused by pressing inside their bounds and if focusable is
	 * true.
	 */
	private boolean focusable;

	public boolean isFocusable() {
		return focusable;
	}

	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	/**
	 * MouseHandler
	 */
	private EventHandler<MouseEvent> onMouseEntered;
	private EventHandler<MouseEvent> onMouseExited;
	private EventHandler<MouseEvent> onMousePressed;
	private EventHandler<MouseEvent> onMouseReleased;
	private EventHandler<MouseEvent> onMouseClicked;
	private EventHandler<MouseEvent> onMouseDragged;
	private EventHandler<MouseEvent> onMouseMoved;
	private EventHandler<MouseWheelEvent> onMouseWheel;
	/**
	 * KeyHandler
	 */
	private EventHandler<KeyEvent> onKeyPressed;
	private EventHandler<KeyEvent> onKeyReleased;
	private EventHandler<KeyEvent> onKeyTyped;

	public Component() {
		this(0, 0);
	}

	public Component(Component parent) {
		this(parent, 0, 0);
	}

	public Component(double width, double height) {
		this(null, width, height);
	}

	public Component(Component parent, double width, double height) {
		this.parent = parent;
		this.bounds = new Rectangle2D(width, height);
		children = new ArrayList<>();
	}

	/**
	 * Draws the component to a Graphics Object.
	 */
	public void draw(Graphics g) {
		for (Component c : getChildren())
			c.draw(g);
	}

	private final void manageRequest(String request, AWTEvent e) {
		switch (request) {
		case KEY_PRESSED:
			if (onKeyPressed != null)
				onKeyPressed.handle((KeyEvent) e);
			doRequest(request, e);
			break;
		case KEY_RELEASED:
			if (onKeyReleased != null)
				onKeyReleased.handle((KeyEvent) e);
			doRequest(request, e);
			break;
		case KEY_TYPED:
			if (onKeyTyped != null)
				onKeyTyped.handle((KeyEvent) e);
			doRequest(request, e);
			break;
		case MOUSE_PRESSED:
			for (Component c : getChildren())
				if (c != null)
					c.handleRequest(request, e);

			if (mouseInBounds) {
				if (onMousePressed != null)
					onMousePressed.handle((MouseEvent) e);
				doRequest(MOUSE_PRESSED, e);
			} else
				doRequest(MOUSE_PRESSED_OUTSIDE, e);
			break;
		case MOUSE_RELEASED:
			for (Component c : getChildren())
				if (c != null)
					c.handleRequest(request, e);

			if (mouseInBounds) {
				if (onMouseReleased != null)
					onMouseReleased.handle((MouseEvent) e);
				doRequest(MOUSE_RELEASED, e);
			} else
				doRequest(MOUSE_RELEASED_OUTSIDE, e);
			break;
		case MOUSE_CLICKED:
			for (Component c : getChildren())
				if (c != null)
					c.handleRequest(request, e);

			if (mouseInBounds) {
				if (onMouseReleased != null)
					onMouseClicked.handle((MouseEvent) e);
				doRequest(MOUSE_CLICKED, e);
			} else
				doRequest(MOUSE_CLICKED_OUTSIDE, e);
			break;
		case MOUSE_WHEEL:
			for (Component c : getChildren())
				if (c != null)
					c.handleRequest(request, e);

			if (onMouseWheel != null)
				if (mouseInBounds)
					onMouseWheel.handle((MouseWheelEvent) e);
			doRequest(MOUSE_WHEEL, e);
			break;
		case MOUSE_MOVED:
			for (Component c : getChildren())
				if (c != null)
					c.handleRequest(request, e);

			boolean contains = getBounds().contains(((MouseEvent) e).getX(), ((MouseEvent) e).getY());
			if (!mouseInBounds) {
				if (contains) {
					mouseInBounds = true;
					doRequest(MOUSE_ENTERED, e);
					doRequest(MOUSE_MOVED_ENTERED, e);
					return;
				}
			} else if (!contains) {
				mouseInBounds = false;
				doRequest(MOUSE_EXITED, e);
				doRequest(MOUSE_MOVED_ENTERED, e);
				return;
			}
			if (onMouseMoved != null)
				if (mouseInBounds)
					onMouseMoved.handle((MouseEvent) e);
			doRequest(MOUSE_MOVED, e);
			break;
		case MOUSE_DRAGGED:
			for (Component c : getChildren())
				if (c != null)
					c.handleRequest(request, e);

			contains = getBounds().contains(((MouseEvent) e).getX(), ((MouseEvent) e).getY());
			if (!mouseInBounds) {
				if (contains) {
					mouseInBounds = true;
					doRequest(MOUSE_ENTERED, e);
					doRequest(MOUSE_DRAGGED_ENTERED, e);
					return;
				}
			} else if (!contains) {
				mouseInBounds = false;
				doRequest(MOUSE_EXITED, e);
				doRequest(MOUSE_DRAGGED_ENTERED, e);
				return;
			}
			if (onMouseDragged != null)
				if (mouseInBounds)
					onMouseDragged.handle((MouseEvent) e);
			doRequest(MOUSE_DRAGGED, e);
			break;
		}
	}

	@Override
	public final void handleRequest(String request, AWTEvent e) {
		switch (request) {
		case KEY_RELEASED:
			manageRequest(request, e);
			break;
		case KEY_TYPED:
			manageRequest(request, e);
			break;
		case MOUSE_PRESSED:
			manageRequest(request, e);
			break;
		case MOUSE_RELEASED:
			manageRequest(request, e);
			break;
		case MOUSE_CLICKED:
			manageRequest(request, e);
			break;
		case MOUSE_WHEEL:
			manageRequest(request, e);
			break;
		case MOUSE_MOVED:
			manageRequest(request, e);
			break;
		case MOUSE_DRAGGED:
			manageRequest(request, e);
			break;
		default:
			doRequest(request, e);
			break;
		}
	}

	protected void doRequest(String request, AWTEvent e) {

	}

	/**
	 * @return MouseHandler
	 */
	public EventHandler<MouseEvent> getOnMouseEntered() {
		return onMouseEntered;
	}

	public EventHandler<MouseEvent> getOnMouseExited() {
		return onMouseExited;
	}

	public EventHandler<MouseEvent> getOnMousePressed() {
		return onMousePressed;
	}

	public EventHandler<MouseEvent> getOnMouseReleased() {
		return onMouseReleased;
	}

	public EventHandler<MouseEvent> getOnMouseClicked() {
		return onMouseClicked;
	}

	public EventHandler<MouseEvent> getOnMouseDragged() {
		return onMouseDragged;
	}

	public EventHandler<MouseEvent> getOnMouseMoved() {
		return onMouseMoved;
	}

	public EventHandler<MouseWheelEvent> getOnMouseWheel() {
		return onMouseWheel;
	}

	/**
	 * @return KeyHandler
	 */
	public EventHandler<KeyEvent> getOnKeyPressed() {
		return onKeyPressed;
	}

	public EventHandler<KeyEvent> getOnKeyReleased() {
		return onKeyReleased;
	}

	public EventHandler<KeyEvent> getOnKeyTyped() {
		return onKeyTyped;
	}

	/**
	 * @set MouseHandler
	 */
	public void setOnMouseEntered(EventHandler<MouseEvent> handler) {
		this.onMouseEntered = handler;
	}

	public void setOnMouseExited(EventHandler<MouseEvent> handler) {
		this.onMouseExited = handler;
	}

	public void setOnMousePressed(EventHandler<MouseEvent> handler) {
		this.onMousePressed = handler;
	}

	public void setOnMouseReleased(EventHandler<MouseEvent> handler) {
		this.onMouseReleased = handler;
	}

	public void setOnMouseClicked(EventHandler<MouseEvent> handler) {
		this.onMouseClicked = handler;
	}

	public void setOnMouseDragged(EventHandler<MouseEvent> handler) {
		this.onMouseDragged = handler;
	}

	public void setOnMouseMoved(EventHandler<MouseEvent> handler) {
		this.onMouseMoved = handler;
	}

	public void setOnMouseWheel(EventHandler<MouseWheelEvent> handler) {
		this.onMouseWheel = handler;
	}

	/**
	 * @set KeyHandler
	 */
	public void setOnKeyPressed(EventHandler<KeyEvent> handler) {
		this.onKeyPressed = handler;
	}

	public void setOnKeyReleased(EventHandler<KeyEvent> handler) {
		this.onKeyReleased = handler;
	}

	public void setOnKeyTyped(EventHandler<KeyEvent> handler) {
		this.onKeyTyped = handler;
	}
}
