package platformer.runtime;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javaJG.panel.Loop;
import javaJG.panel.Painter;
import javaJG.Window;
import javaJG.geometry.Point2D;
import javaJG.assets.Align;
import javaJG.assets.Insets;
import javaJG.event.KeyCode;
import javaJG.panel.Background;
import javaJG.panel.Camera;
import javaJG.panel.GamePanel;
import javaJG.panel.GamePanel.EscapeMenu;
import platformer.collision.Layer;
import platformer.configuration.CallType;
import platformer.configuration.ConfigModule;
import platformer.configuration.SuperType;
import platformer.configuration.VarType;
import platformer.entity.GameConstants;
import platformer.entity.Player;
import platformer.entity.StatsProperties;
import platformer.map.Map;
import platformer.map.MapCanvas;
import platformer.map.MapReader;
import platformer.map.chunk.MapChunker;

public class Runtime {

	public static Window window = new Window();

	static double computeOffsetX(Map boundary, double alignX) {
		if (alignX > boundary.largestX() * GameConstants.SQS - window.getWidth() / 2)
			alignX = boundary.largestX() * GameConstants.SQS - window.getWidth() / 2;
		if (alignX < boundary.lowestX * GameConstants.SQS + window.getWidth() / 2)
			alignX = boundary.lowestX * GameConstants.SQS + window.getWidth() / 2;
		return alignX;
	}

	static double computeOffsetY(Map boundary, double alignY) {
		if (alignY > boundary.largestY() * GameConstants.SQS - window.getHeight() / 2)
			alignY = boundary.largestY() * GameConstants.SQS - window.getHeight() / 2;
		if (alignY < boundary.lowestY * GameConstants.SQS + window.getHeight() / 2)
			alignY = boundary.lowestY * GameConstants.SQS + window.getHeight() / 2;
		return alignY;
	}

	public static void main(String[] args) {
		window.setSize(800, 800);
		GamePanel panel = new GamePanel();
		panel.setPreferredFramerate(60);
		panel.setEscapeMenu(new EscapeMenu(true, KeyCode.ESCAPE));
		window.setPanel(panel);
		
		MapReader reader = new MapReader(Runtime.class.getResource("Labyrinth_2/map.xml"));
		ConfigModule module = new ConfigModule(reader.getProperties().config);
		module.invoke(null, SuperType.ONSTART, CallType.DO);
		
		MapChunker c = new MapChunker(5, reader.getProperties().getMap());
		MapCanvas canvas = new MapCanvas(reader.getProperties().sideScrollerMode);
		Player player = new Player(new Point2D(reader.getProperties().playerPosition[0], reader.getProperties().playerPosition[1]),
				module, c, new StatsProperties(5, 5, 3, 4.5, 1, 15));
		panel.setController(player);
		panel.getBoundingBoxes().add(player);

		Camera camera = new Camera();
		camera.bind(player);
		camera.setAlign(Align.CENTER);
		camera.setConstraints(new Insets(
				reader.getProperties().lowestX * (1 + GameConstants.SQS),
				reader.getProperties().lowestY * (1 + GameConstants.SQS),
				reader.getProperties().largestX * (1 + GameConstants.SQS),
				reader.getProperties().largestY * (2 + GameConstants.SQS)));
		
		Object value = module.getVar(VarType.BACKGROUNDIMAGE);
		Background b;
		if (value == null)
			b = new Background.BackgroundColor(Color.DARK_GRAY);
		else
			b = new Background.BackgroundImage((BufferedImage) value) {

			@Override
			public double offsetX() {
				return camera.getX() * 0.1;
			}

			@Override
			public double[] stretch() {
				return new double[] { 3000, 0 };
			}

		};

		panel.setCamera(camera);
		panel.setBackground(b);
		panel.setLoop(new Loop() {

			@Override
			public void handle(long time) {
				player.update(c.getVisibleChunks(Layer.GROUND.getZ(),
						player.getX(),
						player.getY(),
						window.getWidth(),
						window.getHeight()));
			}

		});
		panel.setPainter(new Painter() {

			@Override
			public void paint(Graphics g) {
				canvas.draw(g, c.getVisibleChunks(0,
						camera.getX() + window.getWidth() / 2,
						camera.getY() + window.getHeight() / 2,
						window.getWidth(),
						window.getHeight()));
				canvas.draw(g, c.getVisibleChunks(1,
						camera.getX() + window.getWidth() / 2,
						camera.getY() + window.getHeight() / 2,
						window.getWidth(),
						window.getHeight()));
				canvas.draw(g, c.getVisibleChunks(2,
						camera.getX() + window.getWidth() / 2,
						camera.getY() + window.getHeight() / 2,
						window.getWidth(),
						window.getHeight()));
			}

		});

		panel.setForegroundPainter(new Painter() {

			@Override
			public void paint(Graphics g) {
				canvas.draw(g, c.getVisibleChunks(3,
						camera.getX() + window.getWidth() / 2,
						camera.getY() + window.getHeight() / 2,
						window.getWidth(),
						window.getHeight()));
			}

		});

		panel.setOverlayPainter(new Painter() {

			@Override
			public void paint(Graphics g) {
				player.drawOverlay(g);
			}

		});

		window.showWindow();
		camera.bindWithTransition(800, 1, -1000, 0, player);
	}
}
