package platformer.configuration;

public enum VarType {
	BACKGROUNDIMAGE(String.class), PLAYERSTATS(Double[].class);

	private final Class<?> valueType;

	VarType(Class<?> valueType) {
		this.valueType = valueType;
	}

	public Class<?> getValueType() {
		return valueType;
	}
}
