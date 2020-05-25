package platformer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import javaJG.assets.SpriteSheet;
import javaJG.panel.Animation;

public final class Settings {

	public static final File LEVEL_FILE = new File("data\\Levels");

	public static final File DESKTOP = new File(System.getProperty("user.home"));

	public static final File TMP = new File(System.getProperty("java.io.tmpdir"));

	public static final File TMP_MAP_BUILDER = new File(TMP + "\\TMP_MAP_BUILDER.xml");

	private Settings() {

	}

	public static String[] getResources(String path) {
		URL resourceFolder = Settings.class.getResource(path);
		if (resourceFolder == null)
			throw new IllegalArgumentException("Resource folder " + path + " not found!");
		try {
			return new File(resourceFolder.toURI()).list();
		} catch (URISyntaxException e) {
			throw new UnsupportedOperationException("Cannot list files for URL " + resourceFolder);
		}
	}

	public static String removeFileExtension(String input) {
		return input.substring(0, input.lastIndexOf('.'));
	}

	public static Map<String, Animation> loadAnimations(String folderName, int time, double offsetX, double offsetY, double width, double height) {
		Map<String, Animation> animations = new HashMap<String, Animation>();

		String[] names = getResources("graphics/animation/" + folderName);
		String[] cuttedExtensions = new String[names.length];

		for (int i = 0; i < names.length; i++) {
			cuttedExtensions[i] = removeFileExtension(names[i]);
			System.out.println(names[i] + ":" + cuttedExtensions[i]);
			animations.put(cuttedExtensions[i], loadAnimation(folderName + "/" + names[i], time, offsetX, offsetY, width, height));
		}

		return animations;
	}

	public static Animation loadAnimation(String fileName, int time, double offsetX, double offsetY, double width, double height) {
		try {
			return new Animation(true,
					new SpriteSheet(ImageIO.read(Settings.class.getResourceAsStream("graphics/animation/" + fileName)), offsetX, offsetY, width, height), time);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, Animation> loadAnimations(String folderName, int time, double width, double height) {
		return loadAnimations(folderName, time, 0, 0, width, height);
	}

	public static Animation loadAnimation(String fileName, int time, double width, double height) {
		try {
			return new Animation(true, new SpriteSheet(ImageIO.read(Settings.class.getResourceAsStream("graphics/animation/" + fileName)), 0, 0, width, height),
					time);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage loadImage(String name) {
		try {
			return ImageIO.read(Settings.class.getResource("graphics/" + name));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String toURL(File file) {
		try {
			return file.toURI().toURL().toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
