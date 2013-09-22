package net.miscjunk.aamp.common;

public abstract class AppListener {	
	protected PlayerClient handler;

	public AppListener(PlayerClient handler) {
		this.handler = handler;
	}
	
	public abstract void start();
	
}
