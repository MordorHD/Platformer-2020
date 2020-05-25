package javaJG.assets.attribute;

import javaJG.assets.ConnectedList;

public class Attribute {

	private ConnectedList<Variable, String> variableValue = new ConnectedList<>();

	public Attribute(String attr) {
		if (!isAttribute(attr))
			throw new IllegalArgumentException("Given attribute:" + attr + " Syntax of attribute: <var=...;var2=...;>");
		readAttribute(attr);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		for (Variable var : variableValue.getPrimaryList())
			builder.append(var.toString() + "=" + variableValue.getSecondaryValue(var) + ";");
		builder.append(">");
		return builder.toString();
	}

	public void appendAttribute(String attr) {
		if (!isAttribute(attr))
			throw new IllegalArgumentException(
					"Given attribute: " + attr + " Syntax of attribute: <var=...;var2=...;>");

		for (String str : attr.substring(1, attr.length() - 1).split(";"))
			if (variableValue.containsPrimaryValue(Variable.valueOf(str.split("=")[0])))
				variableValue.replaceSecondaryValue(
						variableValue.indexOfPrimaryValue(Variable.valueOf(str.split("=")[0])), str.split("=")[1]);
			else
				variableValue.put(Variable.valueOf(str.split("=")[0]), str.split("=")[1]);
	}

	public boolean isAttribute(String attribute) {
		if (attribute.matches(
				"<([a-zA-Z]+=\\([0-9]+(.[0-9]+)?(,[0-9]+(.[0-9]+)?)?(,[0-9]+(.[0-9]+)?,[0-9]+(.[0-9]+)?)?\\);)+>")) {
			return true;
		}
		return attribute.matches("<[a-zA-Z]+=(-)?[0-9]+(.[0-9]+)?>");
	}

	public Object getValue(Variable var) {
		if (!variableValue.containsPrimaryValue(var))
			throw new IllegalArgumentException("This attribute contains no " + var.toString());
		if (var.getType() == VariableType.COLOR)
			return Variable.toColor(variableValue.getSecondaryValue(var));
		else
			return variableValue.getSecondaryValue(var);
	}

	public void readAttribute(String attribute) {
		String[] attributes = attribute.substring(1, attribute.length() - 1).split(";");

		for (int i = 0; i < attributes.length; i++) {
			variableValue.put(Variable.valueOf(attributes[i].split("=")[0].toUpperCase()), attributes[i].split("=")[1]);
		}
	}

}
