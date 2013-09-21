package net.miscjunk.aamp.common;

public interface PlayStrategy {
	void playSong(Song s);
	void pause(Song s);
	void seek(double time);
}
