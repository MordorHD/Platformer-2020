package javaJG.component;

public interface Request {
	public static final String MOUSE_ENTERED = "mouseEntered";
	public static final String MOUSE_EXITED = "mouseExited";
	public static final String MOUSE_MOVED_ENTERED = "mouseMovedEntered";
	public static final String MOUSE_MOVED_EXITED = "mouseMovedExited";
	public static final String MOUSE_DRAGGED_ENTERED = "mouseDraggedEntered";
	public static final String MOUSE_DRAGGED_EXITED = "mouseDraggedExited";
	public static final String MOUSE_MOVED = "mouseMoved";
	public static final String MOUSE_DRAGGED = "mouseDragged";
	public static final String MOUSE_CLICKED = "mouseClicked";
	public static final String MOUSE_PRESSED = "mousePressed";
	public static final String MOUSE_RELEASED = "mouseReleased";
	public static final String MOUSE_WHEEL = "mouseWheel";
	public static final String MOUSE_PRESSED_OUTSIDE = "mousePressedOutside";
	public static final String MOUSE_RELEASED_OUTSIDE = "mouseReleasedOutside";
	public static final String MOUSE_CLICKED_OUTSIDE = "mouseClickedOutside";

	public static final String KEY_PRESSED = "keyPressed";
	public static final String KEY_RELEASED = "keyReleased";
	public static final String KEY_TYPED = "keyTyped";
}
