package platformer.collision;

public enum Layer {
	BACKGROUND(0), DECORATIVE(1), GROUND(2), FOREGROUND(3);

	public static Layer valueOf(int z) {
		return values()[z];
	}
	
	private final int z;

	Layer(int z) {
		this.z = z;
	}

	public int getZ() {
		return z;
	}
}
