package platformer.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.JOptionPane;

import platformer.collision.Collision;
import platformer.collision.CollisionType;
import platformer.collision.Layer;
import platformer.entity.Entity;
import platformer.entity.GameConstants;

public class Caller {

	private static final Map<String, Function<Entity, Object>> variableDeclaration = new HashMap<String, Function<Entity, Object>>();

	static {
		variableDeclaration.put("x", new Function<Entity, Object>() {

			@Override
			public Object apply(Entity t) {
				return t.getX();
			}

		});
		variableDeclaration.put("y", new Function<Entity, Object>() {

			@Override
			public Object apply(Entity t) {
				return t.getY();
			}

		});
	}

	private SuperType superType;
	private CallType[] options;
	private Object[][] paramsOption;
	private String[] callTypes;
	private Object[][] paramsCall;
	// extra variables for config methods
	private Counter counter = new Counter();
	private boolean shutDown = false;
	private boolean isWaiting;
	private List<CollisionType> ignoringTypes = new ArrayList<CollisionType>();

	public Caller(SuperType superType, CallType[] options, Object[][] paramsOption, String[] callTypes,
			Object[][] paramsCall) {
		this.superType = superType;
		this.options = options;
		this.paramsOption = paramsOption;
		this.callTypes = callTypes;
		this.paramsCall = paramsCall;
		for (String str : callTypes)
			if (str.split("\\(")[0].equals("wait"))
				isWaiting = true;
	}

	private void shutDown() {
		shutDown = true;
	}

	private Object[] checkForVariables(Entity e, Object[] input) {
		Object[] output = new Object[input.length];

		for (int i = 0; i < input.length; i++)
			if (input[i] instanceof String) {
				if (!variableDeclaration.containsKey(((String) input[i]).split("[^a-zA-Z0-9]")[0])) {
					output[i] = (String) input[i];
					continue;
				}
				char operator = 0;
				String s = (String) input[i];
				Double first = null;
				String collector = "";
				for (int j = 0; j < s.length(); j++) {
					char c = s.charAt(j);
					switch (c) {
					case '+':
						operator = '+';
						first = (Double) (variableDeclaration.get(collector).apply(e));
						collector = "";
						break;
					case '-':
						operator = '-';
						first = (Double) (variableDeclaration.get(collector).apply(e));
						collector = "";
						break;
					case '*':
						operator = '*';
						first = (Double) (variableDeclaration.get(collector).apply(e));
						collector = "";
						break;
					case '/':
						operator = '/';
						first = (Double) (variableDeclaration.get(collector).apply(e));
						collector = "";
						break;
					case '%':
						operator = '%';
						first = (Double) (variableDeclaration.get(collector).apply(e));
						collector = "";
						break;
					default:
						collector += c;
						break;
					}
				}
				if (operator == 0)
					if (variableDeclaration.get(collector) != null)
						output[i] = Math.floor(((Double) variableDeclaration.get(collector).apply(e)) / GameConstants.SQS);
					else
						output[i] = collector;
				else
					switch (operator) {
					case '+':
						output[i] = Math.floor(first / GameConstants.SQS) + Double.valueOf(collector);
						break;
					case '-':
						output[i] = Math.floor(first / GameConstants.SQS) - Double.valueOf(collector);
						break;
					case '*':
						output[i] = Math.floor(first / GameConstants.SQS) * Double.valueOf(collector);
						break;
					case '/':
						output[i] = Math.floor(first / GameConstants.SQS) / Double.valueOf(collector);
						break;
					case '%':
						output[i] = Math.floor(first / GameConstants.SQS) % Double.valueOf(collector);
						break;
					}
			} else {
				output[i] = (Double) input[i];
			}

		return output;
	}

	public boolean isCallable(SuperType superType, CallType callType, Object[] paramsOption) {
		if (shutDown) return false;

		if (!superType.equals(this.superType)) return false;

		int optionIndex = -1;
		for (int i = 0; i < options.length; i++)
			if (callType.equals(options[i])) optionIndex = i;
		if (optionIndex == -1) return false;

		if (!callType.paramsMatch(paramsOption)) return false;

		return callType.isCallable(this.paramsOption[optionIndex], paramsOption);
	}

	public void call(Entity e) {
		System.out.println("CALL");
		for (int i = 0; i < callTypes.length; i++) {
			if ((isWaiting && callTypes[i].split("\\(")[0].equals("wait")) || !isWaiting)
				try {
					Class<?>[] paramTypes = new Class<?>[paramsCall[i].length + 1];
					paramTypes[0] = Entity.class;
					Object[] params = new Object[paramsCall[i].length + 1];
					params[0] = e;
					Object[] realInstanceParams = checkForVariables(e, paramsCall[i]);
					for (int j = 0; j < realInstanceParams.length; j++) {
						params[j + 1] = realInstanceParams[j];
						paramTypes[j + 1] = realInstanceParams[j].getClass();
					}

					final Method m = Caller.class.getDeclaredMethod(callTypes[i].split("\\(")[0], paramTypes);
					m.invoke(this, params);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException ex) {
							System.out.println("Failed to invoke " + callTypes[i]);
							shutDown();
							ex.printStackTrace();
						}
		}
		counter.countUp();
	}

	private class Counter {
		int count = 0;

		int countUp() {
			return count += 1;
		}

		int getCount() {
			return count;
		}
	}

	/************************************
	 * Possible methods in .config file *
	 ***********************************/

	public void message(Entity e, String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void ignore(Entity e, String type) {
		if (CollisionType.valueOf(type) != null)
			ignoringTypes.add(CollisionType.valueOf(type));
	}

	public void wait(Entity e, Double value) {
		if (counter.getCount() >= Math.floor(value)) isWaiting = false;
	}

	public void closeEvent(Entity e, Double value) {
		if (counter.getCount() >= Math.floor(value)) shutDown();
	}

	public void heal(Entity e, Double value) {
		e.stats.HP += value;
	}

	public void hurt(Entity e, Double dmg) {
		e.applyDamage(dmg);
	}

	public void create(Entity e, Double x, Double y, String layer, String type) {
		try {
			final int[] screenPosition = e.chunkerInstance().getMap().screenPosition((int) Math.floor(x), (int) Math.floor(y));
			e.chunkerInstance().getMap().set(screenPosition[0], screenPosition[1], Layer.valueOf(layer),
					CollisionType.valueOf(type));
		} catch (ArrayIndexOutOfBoundsException ex) {
		}
	}

	public void destroy(Entity e, Double x, Double y, String layer) {
		try {
			final int[] screenPosition = e.chunkerInstance().getMap().screenPosition((int) Math.floor(x), (int) Math.floor(y));
			Collision c = e.chunkerInstance().getMap().get(screenPosition[0], screenPosition[1], Layer.valueOf(layer));
			if (c != null && !ignoringTypes.contains(c.getType())) {
				c.closeActivity();
				c.setVisibility(false);
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
		}
	}

	public void morph(Entity e, Double x, Double y, String layer, String newType) {
		final int[] screenPosition = e.chunkerInstance().getMap().screenPosition((int) Math.ceil(x), (int) Math.ceil(y));
		try {
			Collision c = e.chunkerInstance().getMap().get(screenPosition[0], screenPosition[1], Layer.valueOf(layer));
			if (!ignoringTypes.contains(c.getType()))
				c.setType(CollisionType.valueOf(newType));
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
		}
	}

	public void changeVelocityBy(Entity e, Double vX, Double vY) {
		e.velocityX += vX;
		e.velocityY += vY;
	}

	public void changeVelocityTo(Entity e, Double vX, Double vY) {
		e.velocityX = vY;
		e.velocityY = vY;
	}

	public void reposeBy(Entity e, Double indexNewX, Double indexNewY) {
		e.add(indexNewX * GameConstants.SQS, indexNewY * GameConstants.SQS);
	}

	public void reposeTo(Entity e, Double indexNewX, Double indexNewY) {
		e.setX(indexNewX * GameConstants.SQS);
		e.setY(indexNewY * GameConstants.SQS);
	}

}
