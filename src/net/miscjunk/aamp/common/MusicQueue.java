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
		queue.get(0).play();
	}

	public void pause() {
		queue.get(0).pause();
	}
}
