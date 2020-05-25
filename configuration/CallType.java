package platformer.configuration;

import javaJG.geometry.Rectangle2D;

public enum CallType {
	DO(new Matcher() {

		@Override
		public Class<?>[] get() {
			return new Class<?>[0];
		}

		@Override
		public Class<?>[] getCall() {
			return new Class<?>[0];
		}

		@Override
		public boolean isCallable(Object[] prior, Object[] paramsOption) {
			return prior.length == 0 && paramsOption.length == 0;
		}

	}), POS(new Matcher() {

		@Override
		public Class<?>[] get() {
			return new Class<?>[] { Double.class, Double.class };
		}

		@Override
		public Class<?>[] getCall() {
			return get();
		}

		@Override
		public boolean isCallable(Object[] prior, Object[] curr) {
			for (int i = 0; i < prior.length; i++)
				if (!prior[i].equals(curr[i]))
					return false;
			return true;
		}

	}), TYPE(new Matcher() {

		@Override
		public Class<?>[] get() {
			return new Class<?>[] { String.class };
		}

		@Override
		public Class<?>[] getCall() {
			return get();
		}

		@Override
		public boolean isCallable(Object[] prior, Object[] curr) {
			for (int i = 0; i < prior.length; i++)
				if (!prior[i].equals(curr[i]))
					return false;
			return true;
		}

	}), ZONE(new Matcher() {

		@Override
		public Class<?>[] get() {
			return new Class<?>[] { Double.class, Double.class, Double.class, Double.class };
		}

		@Override
		public Class<?>[] getCall() {
			return new Class<?>[] { Double.class, Double.class };
		}

		@Override
		public boolean isCallable(Object[] prior, Object[] curr) {
			return new Rectangle2D((Double) prior[0], (Double) prior[1], (Double) prior[2], (Double) prior[3])
					.contains((Double) curr[0], (Double) curr[1]);
		}

	});

	private Matcher matcher;

	CallType(final Matcher matcher) {
		this.matcher = matcher;
	}

	public boolean paramsMatch(Object[] paramsOption) {
		if (matcher.getCall().length != paramsOption.length)
			return false;
		for (int i = 0; i < paramsOption.length; i++)
			if (!paramsOption[i].getClass().equals(matcher.getCall()[i]))
				return false;
		return true;
	}

	boolean isCallable(Object[] prior, Object[] paramsOption) {
		return matcher.isCallable(prior, paramsOption);
	}

	public Object[] matcher(String[] params) {
		return matcher.match(params);
	}

	private interface Matcher {
		abstract Class<?>[] get();

		abstract Class<?>[] getCall();

		abstract boolean isCallable(Object[] prior, Object[] paramsOption);

		default Object[] match(String[] params) {
			if (params.length != get().length)
				return null;
			Object[] match = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				try {
					match[i] = Double.valueOf(params[i]);
				} catch (NumberFormatException e) {
					match[i] = params[i];
				}
				if (!match[i].getClass().equals(get()[i]))
					return null;
			}
			return match;
		}
	}
}
