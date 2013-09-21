package net.miscjunk.aamp.common;

import java.util.ArrayList;
import java.util.List;

public class MusicQueue {
	private List<SongAdapter> queue;
	
	public MusicQueue(SongAdapter... adapters) {
		queue = new ArrayList<>();
		for(SongAdapter song : adapters) {
			queue.add(song);
		}
	}
	
	public void play() {
		getCurrent().play();
	}

	public void pause() {
		getCurrent().pause();
	}

	public void setVolume(double volume) {
		getCurrent().setVolume(volume);
	}

	public void seek(double time) {
		getCurrent().seek(time);
	}
	
	private SongAdapter getCurrent() {
		return queue.get(0);
	}
}
