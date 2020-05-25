package platformer.map.chunk;

import java.util.ArrayList;
import java.util.List;

import platformer.collision.Collision;
import platformer.collision.CollisionType;
import platformer.collision.Layer;
import platformer.entity.Entity;
import platformer.entity.GameConstants;
import platformer.map.Map;

public class MapChunker {
	/**
	 * Saves given map instance.
	 */
	private final Map map;

	private final Chunk[][][] chunks;
	/**
	 * Width and height of a single chunk.
	 */
	private final int chunkSize;

	/**
	 * Splitting a map into various chunks.
	 * 
	 * @param chunkSize Size of a chunk.
	 * @param map       Map to split into chunks.
	 */
	public MapChunker(final int chunkSize, Map map) {
		if (chunkSize < 1)
			throw new IllegalArgumentException("Chunksize must be larger than zero!");
		this.map = map;
		this.chunkSize = chunkSize;
		chunks = new Chunk[Layer.values().length][(int) Math.ceil((double) map.size[1] / (double) chunkSize)][(int) Math
				.ceil((double) map.size[0] / (double) chunkSize)];
		System.out.println("Parsing chunks of size " + chunkSize + "...");
		parseChunks();
	}

	public Map getMap() {
		return map;
	}

	/**
	 * Takes in an Entity and returns all chunks considering its velocity (not
	 * anymore, just surrounding chunks).
	 * 
	 * @param e Entity to calculate important chunks.
	 * @return Surrounding chunks.
	 */
	public List<Chunk> get(Entity e) {
		final int[] screenPosition = map.screenPosition((int) (e.getX() / GameConstants.SQS),
				(int) (e.getY() / GameConstants.SQS));
		final int[] chunkedPosition = { screenPosition[0] / chunkSize, screenPosition[1] / chunkSize };
		List<Chunk> chunks = new ArrayList<Chunk>();

		for (int j = -1; j <= 1; j++)
			for (int l = -1; l <= 1; l++)
				try {
					chunks.add(this.chunks[Layer.GROUND.getZ()][chunkedPosition[1] + j][chunkedPosition[0] + l]);
				} catch (ArrayIndexOutOfBoundsException ex) {
				}

		return chunks;
	}

	/**
	 * Changes the type of Collision at spot x, y. If the Collision at x, y is null
	 * then it creates a collision there.
	 * <p>
	 * It does that by calculating the "chunked" position (dividing indexed position
	 * by chunk size).
	 * </p>
	 * 
	 * @param x Position x.
	 * @param y Position y.
	 */
	public void set(double x, double y, Layer layer, CollisionType type) throws ArrayIndexOutOfBoundsException {
		final int[] screenPosition = map.screenPosition((int) (x / GameConstants.SQS),
				(int) (y / GameConstants.SQS));
		final int[] chunkedPosition = { screenPosition[0] / chunkSize, screenPosition[1] / chunkSize };

		chunks[0][chunkedPosition[1]][chunkedPosition[0]].set(
				screenPosition[0] % chunkSize,
				screenPosition[1] % chunkSize, layer, type);
	}

	/**
	 * Returns every chunk that is visible on the screen.
	 * 
	 * @param alignX
	 * @param alignY
	 * @param screenWidth  Width of the window.
	 * @param screenHeight Height of the window.
	 * @return All visible chunks as List.
	 */
	public List<Chunk> getVisibleChunks(int z, double alignX, double alignY, double screenWidth,
			double screenHeight) {
		int[] screenPosition = map.screenPosition((int) ((alignX - screenWidth / 2) / GameConstants.SQS),
				(int) ((alignY - screenHeight / 2) / GameConstants.SQS));
		int[] chunkedPosition = { screenPosition[0] / chunkSize, screenPosition[1] / chunkSize };
		int[] screenSize = map.screenPosition((int) (screenWidth / GameConstants.SQS),
				(int) (screenHeight / GameConstants.SQS));
		int[] chunkedScreenSize = { screenSize[0] / chunkSize, screenSize[1] / chunkSize };

		List<Chunk> chunks = new ArrayList<Chunk>();
		for (int y = chunkedPosition[1] - 1; y < chunkedPosition[1] + chunkedScreenSize[1]; y++)
			for (int x = chunkedPosition[0] - 1; x < chunkedPosition[0] + chunkedScreenSize[0]; x++) {
				try {
					chunks.add(this.chunks[z][y][x]);
				} catch (ArrayIndexOutOfBoundsException ex) {
				}
			}
		return chunks;
	}

	/**
	 * Splits the Map given when instantiating into various chunks.
	 * 
	 * This system is used because of high performance advantage. There's is no
	 * looping through every Chunk nor Collision necessary.
	 */
	@SuppressWarnings("unchecked")
	private void parseChunks() {
		ArrayList<Collision> chunk = new ArrayList<Collision>() {
			private static final long serialVersionUID = 7946994816091980873L;

			@Override
			public boolean add(Collision c) {
				if (c == null)
					return false;
				return super.add(c);
			}
		};
		for (int z = 0; z < Layer.values().length; z++)
			for (int y = 0; y < map.size[1]; y += chunkSize)
				for (int x = 0; x < map.size[0]; x += chunkSize) {
					for (int i = 0; i < chunkSize; i++)
						for (int j = 0; j < chunkSize; j++)
							try {
								chunk.add(map.get(x + i, y + j, Layer.valueOf(z)));
							} catch (ArrayIndexOutOfBoundsException e) {
							}
					chunks[z][y / chunkSize][x / chunkSize] = new Chunk(
							((List<Collision>) chunk.clone()).toArray(new Collision[chunk.size()]), x + map.lowestX,
							y + map.lowestY, x + map.lowestX + chunkSize, y + map.lowestY + chunkSize);
					chunk.clear();
				}
	}

}
