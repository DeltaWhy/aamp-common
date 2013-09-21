package net.miscjunk.aamp.common;

public interface PlayableSong {
    boolean fetch();
    boolean play();

    boolean pause();
    boolean seek(double position);
    double getPosition();
    
    void setVolume(double volume);
}
