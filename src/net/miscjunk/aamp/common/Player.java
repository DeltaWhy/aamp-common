package net.miscjunk.aamp.common;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private List<Playlist> playlists;
    private List<MusicProvider> providers;
    private MusicQueue queue;
    private PlayableSong playing;
    private double volume;
	private OnSongFinished onSongFinished;
    
    private class OnSongFinished implements Runnable {
		public void run() {
			System.out.println("Song finished");
			Player.this.next();			
		}

    }
    

	private void stopCurrentPlaying() {
		if(playing != null) {
			playing.stop();
		}
	}	
    
    public boolean next() {
    	if(queue.goToNext()) {
    		return play();
    	}
    	return false;
	}

	public boolean prev() {
		double currentPos = playing.getPosition();
		if(playing != null &&  currentPos < 5) {
			if(queue.goToPrev()) {
				return play();
			}else {
				return seek(0.0); //can't go any farther back..let's just rewind
			}
		}else if(playing != null && currentPos >= 5) {
			return seek(0.0); //he wants to go to the start
		}
		return false;

	}
	
	public Player() {
        playlists = new ArrayList<Playlist>();
        providers = new ArrayList<MusicProvider>();
        queue = new MusicQueue(getAllSongs());
        volume = 1.0;
        onSongFinished = new OnSongFinished();
    }

    public boolean play() {
    	if(queue.isEmpty()) { //never intialized
    		initQueue();
    	}
    	
        Song song = queue.getCurrent();
        
        if (song == null) {
        	return false; //end of the list, we're done
        }
        stopCurrentPlaying();
        playing = song.inflate();
        playing.setOnFinishedListener(onSongFinished);

        playing.setVolume(volume);
        
        playing.play();
        System.out.println("Playing " + song.getId());
        return true;
    }
    private void initQueue() {
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
            stopCurrentPlaying();
            Song song = queue.getCurrent();
            playing = song.inflate();
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
    public boolean addProvider(MusicProvider provider) {
        for (MusicProvider p : providers) {
            if (p.getId().equals(provider.getId())) return false;
        }
        providers.add(provider);
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
