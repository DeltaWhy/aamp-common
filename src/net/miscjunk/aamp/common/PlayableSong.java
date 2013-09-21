package net.miscjunk.aamp.common;

public interface PlayableSong {
    boolean fetch();
    boolean play();

    boolean pause();
    /**
     * Sets current position in playback to seconds
     * 
     * @param position in seconds
     * @return
     */
    boolean seek(double position);
    /**
     * 
     * @return Current playback in seconds
     */
    double getPosition();
    
    void setVolume(double volume);
	void stop();
	
	Runnable getOnFinishedListener();
	void setOnFinishedListener(Runnable action);
}
