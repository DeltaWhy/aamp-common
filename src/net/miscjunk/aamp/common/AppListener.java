package net.miscjunk.aamp.common;

public abstract class AppListener {	
	protected Player handler;

	public AppListener(Player handler) {
		this.handler = handler;
	}
	
	public abstract void start();
	
}
