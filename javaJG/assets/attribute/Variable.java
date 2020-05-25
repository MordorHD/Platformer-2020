package javaJG.assets.attribute;

import java.awt.Color;

/**
 * Implementation: 18.03.
 */
public enum Variable implements VariableType {
	TEXTCOLOR(COLOR), BACKGROUNDCOLOR(COLOR), BACKGROUNDSTROKE(COLOR), VALUE(OTHER);
	
	private final int type;
	
	Variable(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public static Color toColor(String str) {
		if (!str.matches("\\([0-9]+(.[0-9]+)?(,[0-9]+(.[0-9]+)?)?(,[0-9]+(.[0-9]+)?,[0-9]+(.[0-9]+)?)?\\)"))
			throw new IllegalArgumentException("Given color:" + str +" Syntax of color: (r,g,b,a) or (r,g,b) or (gray,a) or (gray)");
		
		Color color = null;
		
		String[] vs = str.substring(1, str.length() - 1).split(",");
		switch (vs.length) {
		case 1:
			color = new Color(Float.valueOf(vs[0]), Float.valueOf(vs[0]), Float.valueOf(vs[0]));
			break;
		case 2:
			color = new Color(Float.valueOf(vs[0]), Float.valueOf(vs[0]), Float.valueOf(vs[0]), Float.valueOf(vs[1]));
			break;
		case 3:
			color = new Color(Float.valueOf(vs[0]), Float.valueOf(vs[1]), Float.valueOf(vs[2]));
			break;
		case 4:
			color = new Color(Float.valueOf(vs[0]), Float.valueOf(vs[1]), Float.valueOf(vs[2]),
					Float.valueOf(vs[3]));
			break;
		}
		return color;
	}
}
