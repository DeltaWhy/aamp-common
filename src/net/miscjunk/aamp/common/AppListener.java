package net.miscjunk.aamp.common;

public abstract class AppListener {	
	protected PlayerHandler handler;

	public AppListener(PlayerHandler handler) {
		this.handler = handler;
	}
	
	public abstract void start();
	
}
