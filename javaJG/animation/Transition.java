package javaJG.animation;

import javaJG.assets.Function;

public abstract class Transition {

	private long duration;

	public long getDuration() {
		return duration;
	}

	public void duration(long duration) {
		this.duration = duration;
	}

	private long delay = 1;

	public long getDelay() {
		return delay;
	}

	public void delay(long delay) {
		if (delay < 1)
			throw new IllegalArgumentException("Delay can't be shorter than 1!");
		this.delay = delay;
	}

	protected Function onStart;

	public Function getOnStart() {
		return onStart;
	}
	
	public void setOnStart(Function onStart) {
		this.onStart = onStart;
	}
	
	protected Function onEnd;

	public Function getOnEnd() {
		return onEnd;
	}
	
	public void setOnEnd(Function onEnd) {
		this.onEnd = onEnd;
	}

	public Transition() {

	}

	public Transition(long duration) {
		this.duration = duration;
	}

	public Transition(long duration, long delay) {
		this.duration = duration;
	}

	protected void next(Object[] o) {
	}

	protected void end(Object[] o) {
	}

	protected final void play(Object[] args) {
		if (onStart != null)
			onStart.call();
		new Thread() {
			@Override
			public void run() {
				long count = 0;
				while (true) {
					try {
						Thread.sleep(getDelay());
					} catch (InterruptedException e) {
					} finally {
						if (count++ < getDuration()) {
							next(args);
						} else {
							if (onEnd != null)
								onEnd.call();
							end(args);
							return;
						}
					}
				}
			}
		}.start();
	}
}
