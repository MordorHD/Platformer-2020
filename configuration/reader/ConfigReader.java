package platformer.configuration.reader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import platformer.Settings;
import platformer.configuration.CallType;
import platformer.configuration.Caller;
import platformer.configuration.SuperType;
import platformer.configuration.VarType;
import platformer.configuration.Variable;
import platformer.entity.StatsProperties;

public class ConfigReader {

	private List<Caller> methodsToInvoke = new ArrayList<Caller>();
	private List<Variable<?>> variables = new ArrayList<Variable<?>>();

	public ConfigReader() {
	}

	/**
	 * Creates an instance of ConfigReader which reads and parse the given String.
	 * Parsed content will be accessible through getMethodsToInvoke() and
	 * getVariableMap().
	 * 
	 * @param config
	 */
	public ConfigReader(final String config) {
		read(config);

	}

	private String removeWhiteSpace(String str) {
		String copy = "";
		char c;
		for (int i = 0; i < str.length(); i++)
			if((c = str.charAt(i)) != ' ' && c != 0x09)
				copy += c;
		return copy;
	}
	
	/**
	 * Reads a given string and saves every component.
	 * 
	 * @param str String to be read.
	 */
	public void read(String str) {
		str = removeWhiteSpace(str);
		String builder = "";
		boolean ignoringSuperType = false; // syntax error or missing type error occurred. Ignoring until } is found
											// again.
		SuperType superType = null;
		boolean comment = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '#') {
				comment = !comment;
				continue;
			}
			if (comment)
				continue;
			if (!ignoringSuperType)
				if (c == ';') { // Two cases of semicolon 1. variable declaration, 2. end of two
								// parts function.
					if (superType == null) { // outside Boolean -> variable declaration
						readVar(builder);
					} else { // inside Boolean -> end of two parts function
						readCaller(superType, builder);
					}
				} else if (c == '{') { // start of any Boolean
					try {
						superType = SuperType.valueOf(builder.toUpperCase());
						System.out.println("Now working inside " + superType);
					} catch (IllegalArgumentException ex) {
						letTheUserKnow("Boolean type " + builder.toUpperCase() + " could not be found!");
						ignoringSuperType = true;
					}
				}
			if (c == '}') { // end of Boolean
				superType = null;
				ignoringSuperType = false;
			}

			if (c == ';' || c == '}' || c == '{') {
				builder = "";
			} else if (c != ' ' && c != '\n')
				builder += c;
		}
	}

	/**
	 * Reads a variable.
	 * 
	 * @param str String to be read.
	 */
	public void readVar(String str) {
		str = removeWhiteSpace(str);
		final String[] var_value = str.split(":");
		VarType varType = null;
		try {
			varType = VarType.valueOf(var_value[0].toUpperCase());
			System.out.println("Detected variable of type " + varType);
		} catch (IllegalArgumentException ex) {
			letTheUserKnow("Variable " + var_value[0] + " could not be found!");
		}
		Object[] values = null;

		try { // trying to remove surrounding quotes
			var_value[1] = var_value[1].split("\"")[1];
		} catch (Exception ex) {

		}
		Class<?> valueType = varType.getValueType();
		if (valueType == Double.class)
			values = new Object[] { Double.valueOf(var_value[0]) };
		if (valueType == String.class)
			values = new Object[] { var_value[1] };
		if (valueType == Double[].class) {
			String[] stringValues = var_value[1].split(",");
			values = new Double[stringValues.length];
			for (int j = 0; j < stringValues.length; j++)
				values[j] = Double.valueOf(stringValues[j]);
		}
		System.out.println(varType + " was assigned to " + Arrays.toString(values));
		addVar(varType, values);
	}

	/**
	 * Reads a two-split method.
	 * 
	 * @param superType SuperType the method is in.
	 * @param str       String to be read.
	 */
	public void readCaller(SuperType superType, String str) {
		str = removeWhiteSpace(str);
		final String[] parts = str.split(":");
		if (parts.length < 2)
			return;
		final String callPart = parts[0];
		final String mainPart = parts[1];

		System.out.println("Call part->" + callPart + "\nMain part->" + mainPart);

		final String[] callTypes = callPart.split("\\|");
		final CallType[] options = new CallType[callTypes.length];
		final Object[][] callParams = new Object[callTypes.length][];
		for (int j = 0; j < callTypes.length; j++)
			try {
				options[j] = CallType.valueOf(callTypes[j].split("\\(")[0].toUpperCase());
				System.out.println("____Detected call option____");
				System.out.println(
						options[j] + Arrays.toString(options[j].matcher(callTypes[j].split("\\(")[1].split(",|\\)"))));
				callParams[j] = options[j].matcher(callTypes[j].split("\\(")[1].split(",|\\)"));
			} catch (IllegalArgumentException ex) {
				letTheUserKnow("Call type " + callTypes[j].split("\\(")[0].toUpperCase() + " could not be found!");
			}

		final String[] mainTypes = mainPart.split("&");
		final Object[][] mainParams = new Object[mainTypes.length][];
		FOR: for (int j = 0; j < mainTypes.length; j++) {
			String methodString = mainTypes[j];
			String[] stringedParams = methodString.split("\\(")[1].split(",|\\)");
			for (final Method m : Caller.class.getMethods())
				if (m.getName().equals(mainTypes[j].split("\\(")[0])) {
					System.out.println("____Detected main method____");
					System.out.println(m.getName() + Arrays.toString(stringedParams));
					mainParams[j] = new Object[stringedParams.length];
					for (int l = 0; l < stringedParams.length; l++)
						try { // trying to either set value to an Integer or a String
							mainParams[j][l] = Double.valueOf(stringedParams[l]);
						} catch (NumberFormatException ex) {
							mainParams[j][l] = stringedParams[l];
						}

					continue FOR;
				}
			letTheUserKnow("Method " + mainTypes[j] + " could not be found!");
		}
		System.out.println("Successfully added method from super type " + superType);
		System.out.println(Arrays.deepToString(callTypes) + " -> " + Arrays.deepToString(callParams));
		System.out.println(Arrays.deepToString(mainTypes) + " -> " + Arrays.deepToString(mainParams));
		addCaller(new Caller(superType, options, callParams, mainTypes, mainParams));
	}

	public void addCaller(Caller caller) {
		methodsToInvoke.add(caller);
	}

	public void addVar(VarType type, Object[] values) {
		switch (type) {
		case BACKGROUNDIMAGE:
			variables.add(new Variable<BufferedImage>(type) {

				@Override
				public BufferedImage get() {
					return Settings.loadImage(((String) values[0]));
				}

			});
			break;
		case PLAYERSTATS:
			variables.add(new Variable<StatsProperties>(type) {

				@Override
				public StatsProperties get() {
					return new StatsProperties((Double) values[0], (Double) values[1], (Double) values[2],
							(Double) values[3], (Double) values[4], (Double) values[5]);
				}

			});
			break;
		}
	}

	/**
	 * Pops open a window with information the user should know. Like a syntax error
	 * or missing type error.
	 * 
	 * @param str Message for the user.
	 */
	private void letTheUserKnow(String str) {
		JOptionPane.showMessageDialog(null, str, "Compile Error!", JOptionPane.WARNING_MESSAGE);
	}

	public List<Caller> getMethodsToInvoke() {
		return methodsToInvoke;
	}

	public List<Variable<?>> getVariables() {
		return variables;
	}

}
