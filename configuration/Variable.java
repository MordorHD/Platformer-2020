package platformer.configuration;

public abstract class Variable<T> {

	private final VarType type;

	public Variable(VarType type) {
		this.type = type;
	}
	
	public abstract T get();

	public boolean equals(Variable<?> other) {
		return other.type == type;
	}

	public VarType getType() {
		return type;
	}
	
}
