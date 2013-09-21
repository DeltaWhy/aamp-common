package net.miscjunk.aamp.common;

public class AppListener {
	
	private PlayerHandler handler;

	public AppListener(PlayerHandler handler) {
		this.handler = handler;
	}
	
	public void start() {
		this.handler.onEvent("play");
	}

}
