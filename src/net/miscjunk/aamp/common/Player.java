package net.miscjunk.aamp.common;

import java.util.List;

public abstract class Player {
    public abstract void play();
    public abstract void pause();
    public abstract void seek(double position);
    public abstract void skipTo(String id);
    public abstract void setVolume(double volume);
    public abstract void setQueue(MusicQueue queue);
    
    public abstract double getVolume();
    public abstract double getPosition();
    public abstract MusicQueue getQueue();
    public abstract Song getCurrentSong();
    
    public abstract List<Playlist> getPlaylists();
    public abstract Playlist getPlaylist(String id);
    public abstract void updatePlaylist(Playlist list);
    public abstract void addPlaylist(Playlist list);
    public abstract void removePlaylist(String id);
    
    public abstract List<MusicProvider> getProviders();
    public abstract MusicProvider getProvider(String id);
    public abstract void updateProvider(MusicProvider provider);
    public abstract void addProvider(MusicProvider provider);
    public abstract void removeProvider(String id);
}
