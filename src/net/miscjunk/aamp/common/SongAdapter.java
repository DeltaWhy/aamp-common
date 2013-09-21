package net.miscjunk.aamp.common;

public class SongAdapter {
	private Song song;
	private PlayStrategy playStrategy;
	
	public SongAdapter(Song song, PlayStrategy playStrategy) {
		this.song = song;
		this.playStrategy = playStrategy;
	}
	
	public void play() {
		this.playStrategy.playSong(this.song);
	}

	public void pause() {
		this.playStrategy.pause(this.song);
	}

	public void setVolume(double volume) {
		this.playStrategy.setVolume(volume);
	}

}
