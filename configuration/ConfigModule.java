package platformer.configuration;

import java.util.List;
import platformer.configuration.reader.ConfigReader;
import platformer.entity.Entity;

public class ConfigModule {

	private final List<Caller> methods;
	private final List<Variable<?>> variables;

	public ConfigModule(ConfigReader reader) {
		methods = reader.getMethodsToInvoke();
		variables = reader.getVariables();
	}
	
	public List<Caller> getMethods() {
		return methods;
	}

	public Object getVar(VarType type) {
		for (Variable<?> var : variables)
			if (var.getType().equals(type))
				return var.get();
		return null;
	}

	public void invoke(Entity e, SuperType type, CallType source, Object... params) {
		for (Caller caller : methods)
			if (caller.isCallable(type, source, params))
				caller.call(e);
	}
}
