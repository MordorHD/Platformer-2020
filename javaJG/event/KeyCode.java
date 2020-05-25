package javaJG.event;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import javaJG.assets.Arrays;
import javaJG.assets.Pair;

public interface KeyCode {
	public static final int BACKSPACE = 8;
	public static final int ENTER = 10;
	public static final int CONTROL = 17;
	public static final int ALT = 18;
	public static final int SHIFT = 16;
	public static final int CAPSLOCK = 20;
	public static final int TAB = 9;
	public static final int ESCAPE = 27;
	public static final int SPACE = 32;
	public static final int INSERT = 155;
	public static final int DELETE = 127;
	public static final int LEFT = 37;
	public static final int UP = 38;
	public static final int RIGHT = 39;
	public static final int DOWN = 40;

	public static final HashMap<Integer, String> parse = Arrays.createHashMap(new Pair<Integer, String>( 48, "0" ),
			new Pair<Integer, String>( 49, "1" ), new Pair<Integer, String>( 50, "2" ), new Pair<Integer, String>( 51, "3" ), new Pair<Integer, String>( 52, "4" ),
			new Pair<Integer, String>( 53, "5" ), new Pair<Integer, String>( 54, "6" ), new Pair<Integer, String>( 55, "7" ), new Pair<Integer, String>( 56, "8" ),
			new Pair<Integer, String>( 57, "9" ), new Pair<Integer, String>( 65, "a" ), new Pair<Integer, String>( 66, "b" ), new Pair<Integer, String>( 67, "c" ),
			new Pair<Integer, String>( 68, "d" ), new Pair<Integer, String>( 69, "e" ), new Pair<Integer, String>( 70, "f" ), new Pair<Integer, String>( 71, "g" ),
			new Pair<Integer, String>( 72, "h" ), new Pair<Integer, String>( 73, "i" ), new Pair<Integer, String>( 74, "j" ), new Pair<Integer, String>( 75, "k" ),
			new Pair<Integer, String>( 76, "l" ), new Pair<Integer, String>( 77, "m" ), new Pair<Integer, String>( 78, "n" ), new Pair<Integer, String>( 79, "o" ),
			new Pair<Integer, String>( 80, "p" ), new Pair<Integer, String>( 81, "q" ), new Pair<Integer, String>( 82, "r" ), new Pair<Integer, String>( 83, "s" ),
			new Pair<Integer, String>( 84, "t" ), new Pair<Integer, String>( 85, "u" ), new Pair<Integer, String>( 86, "v" ), new Pair<Integer, String>( 87, "w" ),
			new Pair<Integer, String>( 88, "x" ), new Pair<Integer, String>( 89, "y" ), new Pair<Integer, String>( 90, "z" ), new Pair<Integer, String>( 222, "ä" ),
			new Pair<Integer, String>( 192, "ö" ), new Pair<Integer, String>( 186, "ü" ), new Pair<Integer, String>( 219, "ß" ),
			new Pair<Integer, String>( SPACE, " " ));

	public static final HashMap<Integer, String> parseShift = Arrays.createHashMap(new Pair<Integer, String>( 48, "=" ),
			new Pair<Integer, String>( 49, "!" ), new Pair<Integer, String>( 50, "\"" ), new Pair<Integer, String>( 51, "§" ), new Pair<Integer, String>( 52, "$" ),
			new Pair<Integer, String>( 53, "%" ), new Pair<Integer, String>( 54, "&" ), new Pair<Integer, String>( 55, "/" ), new Pair<Integer, String>( 56, "(" ),
			new Pair<Integer, String>( 57, ")" ), new Pair<Integer, String>( 65, "A" ), new Pair<Integer, String>( 66, "B" ), new Pair<Integer, String>( 67, "C" ),
			new Pair<Integer, String>( 68, "D" ), new Pair<Integer, String>( 69, "E" ), new Pair<Integer, String>( 70, "F" ), new Pair<Integer, String>( 71, "G" ),
			new Pair<Integer, String>( 72, "H" ), new Pair<Integer, String>( 73, "I" ), new Pair<Integer, String>( 74, "J" ), new Pair<Integer, String>( 75, "K" ),
			new Pair<Integer, String>( 76, "L" ), new Pair<Integer, String>( 77, "M" ), new Pair<Integer, String>( 78, "N" ), new Pair<Integer, String>( 79, "O" ),
			new Pair<Integer, String>( 80, "P" ), new Pair<Integer, String>( 81, "Q" ), new Pair<Integer, String>( 82, "R" ), new Pair<Integer, String>( 83, "S" ),
			new Pair<Integer, String>( 84, "T" ), new Pair<Integer, String>( 85, "U" ), new Pair<Integer, String>( 86, "V" ), new Pair<Integer, String>( 87, "W" ),
			new Pair<Integer, String>( 88, "X" ), new Pair<Integer, String>( 89, "Y" ), new Pair<Integer, String>( 90, "Z" ), new Pair<Integer, String>( 222, "Ä" ),
			new Pair<Integer, String>( 192, "Ö" ), new Pair<Integer, String>( 186, "Ü" ), new Pair<Integer, String>( 219, "?" ),
			new Pair<Integer, String>( SPACE, " " ));

	public static final HashMap<Integer, String> parseControl = Arrays.createHashMap(new Pair<Integer, String>( 48, ")" ),
			new Pair<Integer, String>( 49, "1" ), new Pair<Integer, String>( 50, "2" ), new Pair<Integer, String>( 51, "3" ), new Pair<Integer, String>( 52, "4" ),
			new Pair<Integer, String>( 53, "5" ), new Pair<Integer, String>( 54, "6" ), new Pair<Integer, String>( 55, "{" ), new Pair<Integer, String>( 56, "[" ),
			new Pair<Integer, String>( 57, "]" ), new Pair<Integer, String>( 65, "a" ), new Pair<Integer, String>( 66, "b" ), new Pair<Integer, String>( 67, "c" ),
			new Pair<Integer, String>( 68, "d" ), new Pair<Integer, String>( 69, "€" ), new Pair<Integer, String>( 70, "f" ), new Pair<Integer, String>( 71, "g" ),
			new Pair<Integer, String>( 72, "h" ), new Pair<Integer, String>( 73, "i" ), new Pair<Integer, String>( 74, "j" ), new Pair<Integer, String>( 75, "k" ),
			new Pair<Integer, String>( 76, "l" ), new Pair<Integer, String>( 77, "m" ), new Pair<Integer, String>( 78, "n" ), new Pair<Integer, String>( 79, "o" ),
			new Pair<Integer, String>( 80, "p" ), new Pair<Integer, String>( 81, "@" ), new Pair<Integer, String>( 82, "r" ), new Pair<Integer, String>( 83, "s" ),
			new Pair<Integer, String>( 84, "t" ), new Pair<Integer, String>( 85, "u" ), new Pair<Integer, String>( 86, "v" ), new Pair<Integer, String>( 87, "w" ),
			new Pair<Integer, String>( 88, "x" ), new Pair<Integer, String>( 89, "y" ), new Pair<Integer, String>( 90, "z" ), new Pair<Integer, String>( 222, "ä" ),
			new Pair<Integer, String>( 192, "ö" ), new Pair<Integer, String>( 186, "ü" ), new Pair<Integer, String>( 219, "ß" ),
			new Pair<Integer, String>( SPACE, " " ));

	public static String toKeyString(KeyEvent e) {
		int keyCode = e.getKeyCode();
			if (e.isShiftDown())
				return parseShift.get(keyCode);
			if (e.isControlDown())
				return parseControl.get(keyCode);
			return parse.get(keyCode);		
	}
}
