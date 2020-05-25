package platformer.configuration.reader;

public abstract class Parser {

	public Parser() {
		
	}

	public abstract boolean matches(String s);
	public abstract boolean checkSyntax(String s);
}
