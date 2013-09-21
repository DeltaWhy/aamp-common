package net.miscjunk.aamp.common;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Playlist> playlists;
    List<MusicProvider> providers;
    MusicQueue queue;
    PlayableSong playing;
    double volume;
    
    public Player() {
        playlists = new ArrayList<Playlist>();
        providers = new ArrayList<MusicProvider>();
        queue = new MusicQueue(new Playlist());
        volume = 1.0;
    }

    public boolean play() {
        Song song = queue.getCurrent();

        if (song == null) return false;
        if (playing != null) {
            playing.pause();
        }
        playing = song.getProvider().inflate(song);
        playing.setVolume(volume);
        playing.play();
        return true;
    }
    public boolean pause() {
        if (playing != null) {
            playing.pause();
            return true;
        } else {
            return false;
        }
    }
    public boolean seek(double position) {
        if (playing != null) {
            playing.seek(position);
            return true;
        }
        return false;
    }
    public boolean skipTo(String id) {
        if (queue.setCurrent(id)) {
            if (playing != null) {
                playing.pause();
            }
            Song song = queue.getCurrent();
            playing = song.getProvider().inflate(song);
            playing.setVolume(volume);
            playing.play();
            return true;
        } else {
            return false;
        }
    }
    public boolean setVolume(double volume) {
        if (volume < 0.0 || volume > 1.0) return false;
        this.volume = volume;
        if (playing != null) {
            playing.setVolume(volume);
        }
        return true;
    }
    public boolean setQueue(MusicQueue queue) {
        // TODO
        this.queue = queue;
        return true;
    }
    
    public double getVolume() {
        return volume;
    }
    public double getPosition() {
        if (playing != null) {
            return playing.getPosition();
        } else {
            return 0.0;
        }
    }
    public MusicQueue getQueue() {
        return queue;
    }
    public Song getCurrentSong() {
        return queue.getCurrent();
    }
    
    public List<Playlist> getPlaylists() {
        return playlists;
    }
    public Playlist getPlaylist(String id) {
        for (Playlist p : playlists) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
    public boolean updatePlaylist(Playlist list) {
        for (int i=0; i < playlists.size(); i++) {
            if (playlists.get(i).getId().equals(list.getId())) {
                playlists.set(i,list);
                return true;
            }
        }
        return false;
    }
    public boolean addPlaylist(Playlist list) {
        for (Playlist p : playlists) {
            if (p.getId().equals(list.getId())) return false;
        }
        playlists.add(list);
        return true;
    }
    public boolean removePlaylist(String id)
    {
        for (int i=0; i < playlists.size(); i++) {
            if (playlists.get(i).getId().equals(id)) {
                playlists.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public List<MusicProvider> getProviders() {
        return providers;
    }
    public MusicProvider getProvider(String id) {
        for (MusicProvider p : providers) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
    public boolean updateProvider(MusicProvider list) {
        for (int i=0; i < providers.size(); i++) {
            if (providers.get(i).getId().equals(list.getId())) {
                providers.set(i,list);
                return true;
            }
        }
        return false;
    }
    public boolean addProvider(MusicProvider list) {
        for (MusicProvider p : providers) {
            if (p.getId().equals(list.getId())) return false;
        }
        providers.add(list);
        return true;
    }
    public boolean removeProvider(String id)
    {
        for (int i=0; i < providers.size(); i++) {
            if (providers.get(i).getId().equals(id)) {
                providers.remove(i);
                return true;
            }
        }
        return false;
    }
}
