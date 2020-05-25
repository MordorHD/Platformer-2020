package platformer.map;

import java.util.ArrayList;
import java.util.List;

import platformer.SideScrollerMode;
import platformer.collision.Collision;
import platformer.configuration.reader.ConfigReader;
import platformer.entity.GameConstants;

public class MapProperties {

	public int lowestX = -5, lowestY = -5, largestX = 5, largestY = 5;
	public SideScrollerMode sideScrollerMode = SideScrollerMode.BOTH;

	public final double[] blockSize = new double[] { GameConstants.SQS, GameConstants.SQS };

	public double[] playerPosition;
	
	public ConfigReader config = new ConfigReader();
	
	private List<Collision> collisions = new ArrayList<Collision>();

	public void add(Collision c) {
		collisions.add(c);
	}

	public Map getMap() {
		return new Map(collisions.toArray(new Collision[collisions.size()]), lowestX, lowestY, largestX, largestY);
	}

}
