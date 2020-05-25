package javaJG.panel;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.TimerTask;

import javaJG.component.Button;
import javaJG.component.Component;
import javaJG.component.Request;
import javaJG.event.Controller;
import javaJG.geometry.component.BoundingBox2D;
import javaJG.panel.Background.BackgroundImage;
import javaJG.panel.Background.CustomBackground;

public class GamePanel extends Panel {

	private static final long serialVersionUID = 7624366293053340670L;

	private int preferredFrameRate = 120;

	public void setPreferredFramerate(int rate) {
		if (rate <= 0)
			throw new IllegalArgumentException("Framerate must be larger than 0");
		preferredFrameRate = rate;
	}

	public static class EscapeMenu extends Component {

		private boolean escMenuShown;

		private boolean pause;

		private int exitKeyCode;

		private GamePanel parent;

		public Button resume, exit;

		private void setParent(GamePanel parent) {
			this.parent = parent;
			if (resume == null)
				getChildren().add(resume = new Button("Resume") {
					{
						setOnAction(e -> {
							escMenuShown = false;
							if (pause)
								parent.setPaused(false);
						});
					}
				});
			if (exit == null)
				getChildren().add(exit = new Button("Quit") {
					{
						setOnAction(e -> {
							System.exit(0);
						});
					}
				});
			resume.setParent(this);
			exit.setParent(this);
		}

		public EscapeMenu(boolean pause, int exitKeyCode) {
			this.pause = pause;
			setFocusable(true);
			this.exitKeyCode = exitKeyCode;
		}

		@Override
		public void doRequest(String request, AWTEvent e) {
			switch (request) {
				case Request.KEY_RELEASED:
					if (((KeyEvent) e).getKeyCode() == exitKeyCode) {
						escMenuShown = !escMenuShown;
						if (pause)
							parent.setPaused(escMenuShown);
					}
				break;
			}
		}

		@Override
		public double getWidth() {
			return parent.getWidth() * 2 / 3;
		}

		@Override
		public double getHeight() {
			return parent.getHeight() * 2 / 3;
		}

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(parent.getWidth() / 6, parent.getHeight() / 6, (int) getWidth(), (int) getHeight());
			if (resume != null)
				resume.setBounds(parent.getWidth() / 3, parent.getHeight() / 3, 200, 40);
			if (exit != null)
				exit.setBounds(parent.getWidth() / 3, parent.getHeight() / 2, 200, 40);
			super.draw(g);
		}
	}

	private Background background;

	public void setBackground(Background background) {
		this.background = background;
	}

	private EscapeMenu escMenu;

	public void setEscapeMenu(EscapeMenu escMenu) {
		if (escMenu.parent != null)
			throw new IllegalArgumentException("This Object was already assigned to a GamePanel");
		this.escMenu = escMenu;
		escMenu.setParent(this);
	}

	private volatile boolean paused;

	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		paused = true;
	}

	public void resume() {
		paused = false;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	private Camera camera;

	public void setCamera(Camera camera) {
		camera.panel = this;
		this.camera = camera;
	}

	private Controller controller;

	public void setController(Controller controller) {
		this.controller = controller;
	}

	private Painter painter;

	public void setPainter(Painter painter) {
		this.painter = painter;
	}

	private Painter overlayPainter;

	public void setOverlayPainter(Painter painter) {
		overlayPainter = painter;
	}

	private Painter foregroundPainter;

	public void setForegroundPainter(Painter painter) {
		foregroundPainter = painter;
	}

	private Loop loop;

	public void setLoop(Loop loop) {
		this.loop = loop;
	}

	private long lastTimeStamp = System.currentTimeMillis();

	private long frameCount;

	public long frameCount() {
		return frameCount;
	}

	@Override
	public TimerTask createNewTask() {
		return new TimerTask() {
			@Override
			public void run() {
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastTimeStamp) > 1000 / preferredFrameRate) {
					if (loop != null && !paused)
						loop.handle(lastTimeStamp = currentTime);
					repaint();
					frameCount++;
				}
			}
		};
	}

	@Override
	public void paintComponent(Graphics g) {
		if (background == null)
			g.clearRect(0, 0, getWidth(), getHeight());
		else if (background instanceof Background.BackgroundColor) {
			g.setColor(((Background.BackgroundColor) background).getColor());
			g.fillRect(0, 0, getWidth(), getHeight());
		} else if (background instanceof Background.BackgroundImage) {
			int x = 0, y = 0;
			int width = 0, height = 0;
			if (((BackgroundImage) background).stretch() != null) {
				width += (int) ((BackgroundImage) background).stretch()[0] * 2;
				height += (int) ((BackgroundImage) background).stretch()[1] * 2;
				x -= width / 2;
				y -= height / 2;
			}
			x += (int) ((BackgroundImage) background).offsetX();
			x += (int) ((BackgroundImage) background).offsetY();

			g.drawImage(((Background.BackgroundImage) background).getImage(),
					x, y,
					getWidth() + width,
					getHeight() + height, null);
		} else if (background instanceof Background.CustomBackground) {
			int x = (int) ((CustomBackground) background).offsetX();
			int y = (int) ((CustomBackground) background).offsetY();
			g.translate(x, y);
			((CustomBackground) background).paint(g);
			g.translate(-x, -y);
		}
		if (camera != null) {
			camera.update();
			g.translate(-(int) camera.getX(), -(int) camera.getY());
		}

		if (painter != null)
			painter.paint(g);

		for (Component n : getChildren())
			n.draw(g);

		for (BoundingBox2D box : getBoundingBoxes()) {
			box.draw(g);
		}

		if (foregroundPainter != null)
			foregroundPainter.paint(g);

		if (camera != null)
			g.translate((int) camera.getX(), (int) camera.getY());

		if (overlayPainter != null)
			overlayPainter.paint(g);

		if (escMenu != null && escMenu.escMenuShown)
			escMenu.draw(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		super.keyTyped(e);
		if (controller != null && !paused)
			controller.keyTyped(e);
		if (escMenu != null)
			escMenu.handleRequest(Request.KEY_TYPED, e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (controller != null && !paused)
			controller.keyPressed(e);
		if (escMenu != null)
			escMenu.handleRequest(Request.KEY_PRESSED, e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		if (controller != null && !paused)
			controller.keyReleased(e);
		if (escMenu != null)
			escMenu.handleRequest(Request.KEY_RELEASED, e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		if (controller != null && !paused)
			controller.mouseWheel(e);
		if (escMenu != null && escMenu.escMenuShown)
			escMenu.handleRequest(Request.MOUSE_WHEEL, e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if (controller != null && !paused)
			controller.mouseDragged(e);
		if (escMenu != null && escMenu.escMenuShown)
			escMenu.handleRequest(Request.MOUSE_DRAGGED, e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if (controller != null && !paused)
			controller.mouseMoved(e);
		if (escMenu != null && escMenu.escMenuShown)
			escMenu.handleRequest(Request.MOUSE_MOVED, e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (controller != null && !paused)
			controller.mouseClicked(e);
		if (escMenu != null && escMenu.escMenuShown)
			escMenu.handleRequest(Request.MOUSE_CLICKED, e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if (controller != null && !paused)
			controller.mousePressed(e);
		if (escMenu != null && escMenu.escMenuShown)
			escMenu.handleRequest(Request.MOUSE_PRESSED, e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if (controller != null && !paused)
			controller.mouseReleased(e);
		if (escMenu != null && escMenu.escMenuShown)
			escMenu.handleRequest(Request.MOUSE_RELEASED, e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
