package net.miscjunk.aamp.common;

public interface MusicProvider {
    Playlist getAllSongs();
    Playlist getSongs(SimpleQuery query);
    
    boolean update();
    
    PlayableSong inflate(Song song);
}
