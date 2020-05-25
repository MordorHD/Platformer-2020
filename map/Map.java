package platformer.map;

import platformer.collision.Collision;
import platformer.collision.CollisionType;
import platformer.collision.Layer;

/**
 * This is a game map. It's purpose is to store collisions. It stores the
 * collision in one dimensional and three dimensional form unlike
 * OneDimensionalMap.
 * 
 * A Map may be used to calculate chunks. map.chunk.Chunk also extends Map.
 * 
 * @see platformer.map.chunk.MapChunker
 */
public class Map implements Cloneable {

	// two instances to store data
	/**
	 * Stores every layer in a two dimensional array.
	 */
	private Collision[][][] data;
	private Collision[] rawData;

	// rectangular bounds of the map
	public int lowestX, lowestY;
	public int[] size;

	/**
	 * Creates an Map Object with given three dimensional array.
	 * 
	 * @param Data three dimension Collision array.
	 */
	public Map(Collision[][][] data) {
		this.data = data;
		for (int i = 0; i < data.length; i++)
			for (int j = 0; j < data[i].length; j++)
				for (int l = 0; l < data[i][j].length; j++) {
					rawData[i * j * l + i + j] = data[i][j][l];
					if (data[i][j][l] != null) {
						lowestX = (int) Math.min(data[i][j][l].getX(), lowestX);
						lowestY = (int) Math.min(data[i][j][l].getY(), lowestY);
					}
				}
		size = new int[] { data[0].length, data.length };
	}

	/**
	 * Creates an Map Object with given one dimensional array. Also calculates every
	 * important variables like lowestX, lowestY, size[]. If these values can be
	 * obtained in any way before instantiating, consider using Map(data, lowestX,
	 * lowsestY, largestX, largestY).
	 * 
	 * @param data One dimension Collision array.
	 */
	@Deprecated
	public Map(Collision[] data) {
		boolean assigned = false;
		int largestX = 0, largestY = 0;
		for (Collision c : data) {
			if (c != null) {
				if (!assigned) {
					lowestX = (int) c.getX();
					lowestY = (int) c.getY();
					assigned = true;
				}
				lowestX = (int) Math.min(c.getX(), lowestX);
				lowestY = (int) Math.min(c.getY(), lowestY);
				largestX = (int) Math.max(c.getX(), largestX);
				largestY = (int) Math.max(c.getY(), largestY);
			}
		}
		size = new int[] { Math.abs(lowestX - largestX) + 1, Math.abs(lowestY - largestY) + 1 };
		System.out.println("Position of map " + lowestX + ", " + lowestY);
		System.out.println("Size of map " + size[0] + ", " + size[1]);
		rawData = data;
		parse(data);
	}

	/**
	 * Takes in a one dimensional array and parses it into a two dimensional.
	 * <p>
	 * Conditions are that lowestX < largestX and lowestY < largestY.
	 * </p>
	 * 
	 * @param data     One dimensional array of collisions.
	 * @param lowestX  Most left position of any collision in data.
	 * @param lowestY  Most upper position of any collision in data.
	 * @param largestX Most right position of any collision in data.
	 * @param largestY Most downer position of any collision in data.
	 */
	public Map(Collision[] data, final int lowestX, final int lowestY, final int largestX, final int largestY) {
		this.lowestX = lowestX;
		this.lowestY = lowestY;
		size = new int[] { largestX - lowestX + 1, largestY - lowestY + 1 };
		System.out.println("Position of map " + lowestX + ", " + lowestY);
		System.out.println("Size of map " + size[0] + ", " + size[1]);
		rawData = data;
		parse(data);
	}

	/**
	 * Calculates and returns the largest possible x value.
	 * 
	 * @return Largest x value.
	 */
	public int largestX() {
		return size[0] + lowestX;
	}

	/**
	 * Calculates and returns the largest possible y value.
	 * 
	 * @return Largest y value.
	 */
	public int largestY() {
		return size[1] + lowestY;
	}

	/**
	 * Parsing real position to screen position is necessary.
	 * <p>
	 * Let's say you have most left Collision at x = -4 and most up Collision at y =
	 * -3.<br>
	 * Of course it can't return a Collision at data[-3][-4].<br>
	 * That's why it moves every collision to the right and down until it matches 0,
	 * 0.<br>
	 * Always (x - lowestX, y - lowestY). So putting in (2, 4) would return (6, 7)
	 * in our example.
	 * </p>
	 * 
	 * @param x Position x.
	 * @param y Position y.
	 * @return [0] e N0 & [1] e N0.
	 */
	public int[] screenPosition(final int x, final int y) {
		return new int[] { x - lowestX, y - lowestY };
	}

	/**
	 * Parses given Collision array to a three dimensional array.<br>
	 * It overrides Collision[][][] data so it is no longer synchronized with
	 * Collision[] rawData.<br>
	 * That's why it should be used with caution.
	 * 
	 * @param data
	 */
	public void parse(Collision[] data) {
		this.data = new Collision[Layer.values().length][size[1]][size[0]];
		for (Collision c : data)
			if (c != null)
				if ((int) c.getX() - lowestX < 0
						|| (int) c.getY() - lowestY < 0
						|| (int) c.getX() - lowestX >= size[0]
						|| (int) c.getY() - lowestY >= size[1])
				continue;
				else
				this.data[c.getLayer().getZ()][(int) c.getY() - lowestY][(int) c.getX() - lowestX] = c;
	}

	/**
	 * Returns a deep copy of itself. Meaning that every Collision will be copied as
	 * well.
	 */
	@Override
	public Map clone() {
		Collision[][][] dataCopy = new Collision[data.length][size[1]][size[0]];
		for (int z = 0; z < dataCopy.length; z++)
			for (int y = 0; y < dataCopy[z].length; y++)
				for (int x = 0; x < dataCopy[z][y].length; x++)
					if (data[z][y][x] != null)
						dataCopy[z][y][x] = data[z][y][x].clone();
		return new Map(dataCopy);
	}

	public Collision[] toOneDimension() {
		return rawData;
	}

	/**
	 * Changes the type of the collision at given x, y coordinates which are
	 * indexed. When there's no collision at that spot, then it's going to create on
	 * with given type.
	 * 
	 * @param x    Indexed Position x.
	 * @param y    Indexed Position y.
	 * @param type Type of the Collision.
	 * @throws ArrayIndexOutOfBoundsException If the given coordinates are out of
	 *                                        bounds.
	 */
	public void set(int x, int y, Layer layer, CollisionType type) throws ArrayIndexOutOfBoundsException {
		data[layer.getZ()][y][x] = new Collision(x, y, 30, 30)
				.setType(type);
	}

	/**
	 * Returns collision at given indexed position.
	 * 
	 * @param x Indexed Position x.
	 * @param y Indexed Position y.
	 * @return Collision at x, y coordinate
	 */
	public Collision get(int x, int y, Layer layer) throws ArrayIndexOutOfBoundsException {
		return data[layer.getZ()][y][x];
	}

}
