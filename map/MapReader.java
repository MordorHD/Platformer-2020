package platformer.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import platformer.SideScrollerMode;
import platformer.collision.Collision;
import platformer.collision.CollisionType;
import platformer.collision.Layer;

public class MapReader {

	private final MapProperties properties;

	public MapReader(URL url) {
		properties = new MapProperties();
		try {
			readXML(url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public MapReader(File file) {
		properties = new MapProperties();
		try {
			readXML(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public MapProperties getProperties() {
		return properties;
	}

	/**
	 * Reads a XML file and saves every collision node in rawData. Also saves the
	 * player position which it gets from Player node.
	 * 
	 * @param file File to read.
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws Exception
	 * @throws FileNotFoundException        File is non existent.
	 */
	private void readXML(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();

		NodeList list = doc.getElementsByTagName("Map").item(0).getChildNodes();

		// settings up variables for loop
		Element element = null;
		String[] values = null;
		int[] pos = null;
		CollisionType type = null;

		FOR: for (int i = 0; i < list.getLength(); i++) {

			if (!(list.item(i).getNodeType() == Node.ELEMENT_NODE))
				continue;

			element = (Element) list.item(i);

			switch (element.getTagName()) {
			case "SideScrollerMode":
				properties.sideScrollerMode = SideScrollerMode.valueOf(element.getTextContent());
				continue FOR;
			case "Config":
				if (!element.getTextContent().matches("-.*-"))
					properties.config.read(element.getTextContent());
				else {
					String content = "";
					String line = "";
					BufferedReader reader = new BufferedReader(
							new FileReader(file.getPath().split(file.getName())[0] + element.getTextContent().split("-")[1]));
					while ((line = reader.readLine()) != null) {
						content += line;
					}
					reader.close();
					properties.config.read(content);
				}
				continue FOR;
			}

			values = element.getTextContent().trim().split(",");
			pos = new int[values.length];
			for (int j = 0; j < values.length; j++)
				pos[j] = Double.valueOf(values[j]).intValue();

			switch (element.getTagName()) {
			case "Collision":
				type = CollisionType.valueOf(element.getAttribute("type"));
				Collision c = new Collision(pos[0], pos[1], 1, 1).setType(type);
				properties.add(c);
				break;
			case "Player":
				properties.playerPosition = new double[] { pos[0], pos[1] };
				break;
			case "Bounds":
				properties.lowestX = pos[0];
				properties.lowestY = pos[1];
				properties.largestX = pos[2];
				properties.largestY = pos[3];
				break;
			default:
				throw new IllegalArgumentException("Unknown type " + element.getTagName());
			}
		}
	}

	/**
	 * Reads a XML file and saves every collision node in rawData. Also saves the
	 * player position which it gets from Player node.
	 * 
	 * @param file File to read.
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws Exception
	 * @throws FileNotFoundException        File is non existent.
	 */
	private void readXML(URL url) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(url.openStream());
		doc.getDocumentElement().normalize();

		NodeList list = doc.getElementsByTagName("Map").item(0).getChildNodes();

		// settings up variables for loop
		Element element = null;
		String[] values = null;
		int[] pos = null;
		Collision construct = null;

		FOR: for (int i = 0; i < list.getLength(); i++) {

			if (list.item(i).getNodeType() != Node.ELEMENT_NODE)
				continue;

			element = (Element) list.item(i);

			switch (element.getTagName()) {
			case "SideScrollerMode":
				properties.sideScrollerMode = SideScrollerMode.valueOf(element.getTextContent());
				continue FOR;
			case "Config":
				if (!element.getTextContent().matches("-.*-"))
					properties.config.read(element.getTextContent());
				else {
					String mapPath = url.getPath();
					int index = 0;
					char[] cs = mapPath.toCharArray();
					for (int j = cs.length - 1; j >= 0; j--)
						if (cs[j] == '/') {
							index = j;
							break;
						}
					String filePath = mapPath.substring(0, index + 1).concat(element.getTextContent().split("-")[1]);
					System.out.println(filePath);
					String content = "";
					String line = "";
					BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("File:" + filePath).openStream()));
					while ((line = reader.readLine()) != null) {
						content += line;
					}
					reader.close();
					properties.config.read(content);
				}
				continue FOR;
			}

			values = element.getTextContent().trim().split(",");
			pos = new int[values.length];
			for (int j = 0; j < values.length; j++)
				pos[j] = Double.valueOf(values[j]).intValue();

			switch (element.getTagName()) {
			case "Collision":
				construct = new Collision(pos[0], pos[1], 1, 1);
				construct.setType(CollisionType.valueOf(element.getAttribute("type")));
				if (!element.getAttribute("layer").isEmpty())
					construct.setLayer(Layer.valueOf(element.getAttribute("layer")));
				properties.add(construct);
				break;
			case "Player":
				properties.playerPosition = new double[] { pos[0], pos[1] };
				break;
			case "Bounds":
				properties.lowestX = pos[0];
				properties.lowestY = pos[1];
				properties.largestX = pos[2];
				properties.largestY = pos[3];
				break;
			default:
				throw new IllegalArgumentException("Unknown type " + element.getTagName());
			}
		}
	}
}
