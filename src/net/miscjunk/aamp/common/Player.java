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
        queue = new MusicQueue(getAllSongs());
        volume = 1.0;
    }

    public boolean play() {
    	System.out.println("Playing");
    	if(queue.isEmpty()) { //never intialized
    		initQueue();
    	}
    	
        Song song = queue.getCurrent();

        if (song == null) {
        	System.out.println("DONE!");
        	return false; //end of the list
        }
    	System.out.println("About to play song " + song.getId());
        if (playing != null) {
            playing.stop();
        }
        System.out.println("About to inflate");
        playing = song.getProvider().inflate(song);
        System.out.println("About to set volume");

        playing.setVolume(volume);
        
        System.out.println("About to play");

        playing.play();
        System.out.println("Playing " + song.getId());
        return true;
    }
    private void initQueue() {
    	System.out.println("Init queue");
    	queue = new MusicQueue(getAllSongs());
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
                playing.stop();
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
    
    public Playlist getAllSongs() {
        Playlist p = new Playlist();
        for (MusicProvider pr : providers) {
        	Playlist providedSongs = pr.getAllSongs();
        	System.out.println(providedSongs.getSongs());
            p.append(providedSongs);
        }
        return p;
    }
    public Playlist buildPlaylist(Query query) {
        Playlist playlist = new Playlist();
        for (SimpleQuery part : query) {
            Playlist p = new Playlist();
            for (MusicProvider pr : providers) {
                p.append(pr.getSongs(part));
            }
            if (part.isExclude()) {
                playlist.subtract(p);
            } else {
                playlist.append(p);
            }
        }
        return playlist;
    }
}
