package net.miscjunk.aamp.common;

public class Song {
    String id;
    MusicProvider provider;
    
    public Song(String id, MusicProvider provider) {
       this.id = id; 
       this.provider = provider;
    }
    
    public String getId() {
        return id;
    }
    public MusicProvider getProvider() {
        return provider;
    }
}
