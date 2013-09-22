package net.miscjunk.aamp.common;

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerClient {
	private List<Playlist> playlists;
    private List<MusicProvider> providers;
    private MusicQueue queue;
    private PlayableSong playing;
    private double volume;
	private OnSongFinished onSongFinished;
	
	private EventServer evtServer;
    
    private class OnSongFinished implements Runnable {
		public void run() {
			System.out.println("Song finished");
			sendEvent("next");
			Player.this.next();			
		}
    }
    

	private void stopCurrentPlaying() {
		if(playing != null) {
			playing.stop();
		}
	}	
    
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#next()
	 */
    public boolean next() {
    	if(queue.goToNext()) {
    	    sendEvent("next");
    		return play();
    	}
    	return false;
	}

	/* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#prev()
	 */
	public boolean prev() {
		double currentPos = playing.getPosition();
		if(playing != null &&  currentPos < 5) {
			if(queue.goToPrev()) {
			    sendEvent("prev");
				return play();
			}else {
			    sendEvent("rewind");
				return seek(0.0); //can't go any farther back..let's just rewind
			}
		}else if(playing != null && currentPos >= 5) {
		    sendEvent("rewind");
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

    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#play()
	 */
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
        sendEvent("play");
        System.out.println("Playing " + song.getId());
        return true;
    }
    private void initQueue() {
    	queue = new MusicQueue(getAllSongs());
	}

	/* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#pause()
	 */
	public boolean pause() {
        if (playing != null) {
            playing.pause();
            sendEvent("pause");
            return true;
        } else {
            return false;
        }
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#seek(double)
	 */
    public boolean seek(double position) {
        if (playing != null) {
            playing.seek(position);
            sendEvent("seek:"+position);
            return true;
        }
        return false;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#skipTo(java.lang.String)
	 */
    public boolean skipTo(String id) {
        if (queue.setCurrent(id)) {
            stopCurrentPlaying();
            Song song = queue.getCurrent();
            sendEvent("skipTo:"+id);
            playing = song.inflate();
            playing.setVolume(volume);
            playing.play();
            return true;
        } else {
            return false;
        }
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#setVolume(double)
	 */
    public boolean setVolume(double volume) {
        if (volume < 0.0 || volume > 1.0) return false;
        this.volume = volume;
        if (playing != null) {
            playing.setVolume(volume);
        }
        return true;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#setQueue(net.miscjunk.aamp.common.MusicQueue)
	 */
    public boolean setQueue(MusicQueue queue) {
        // TODO
        this.queue = queue;
        sendEvent("queue");
        return true;
    }
    
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getVolume()
	 */
    public double getVolume() {
        return volume;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getPosition()
	 */
    public double getPosition() {
        if (playing != null) {
            return playing.getPosition();
        } else {
            return 0.0;
        }
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getQueue()
	 */
    public MusicQueue getQueue() {
        return queue;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getCurrentSong()
	 */
    public Song getCurrentSong() {
        return queue.getCurrent();
    }
    
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getPlaylists()
	 */
    public List<Playlist> getPlaylists() {
        return playlists;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getPlaylist(java.lang.String)
	 */
    public Playlist getPlaylist(String id) {
        for (Playlist p : playlists) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#updatePlaylist(net.miscjunk.aamp.common.Playlist)
	 */
    public boolean updatePlaylist(Playlist list) {
        for (int i=0; i < playlists.size(); i++) {
            if (playlists.get(i).getId().equals(list.getId())) {
                playlists.set(i,list);
                return true;
            }
        }
        return false;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#addPlaylist(net.miscjunk.aamp.common.Playlist)
	 */
    public boolean addPlaylist(Playlist list) {
        for (Playlist p : playlists) {
            if (p.getId().equals(list.getId())) return false;
        }
        playlists.add(list);
        return true;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#removePlaylist(java.lang.String)
	 */
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
    
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getProviders()
	 */
    public List<MusicProvider> getProviders() {
        return providers;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getProvider(java.lang.String)
	 */
    public MusicProvider getProvider(String id) {
        for (MusicProvider p : providers) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#updateProvider(net.miscjunk.aamp.common.MusicProvider)
	 */
    public boolean updateProvider(MusicProvider list) {
        for (int i=0; i < providers.size(); i++) {
            if (providers.get(i).getId().equals(list.getId())) {
                providers.set(i,list);
                return true;
            }
        }
        return false;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#addProvider(net.miscjunk.aamp.common.MusicProvider)
	 */
    public boolean addProvider(MusicProvider provider) {
        for (MusicProvider p : providers) {
            if (p.getId().equals(provider.getId())) return false;
        }
        providers.add(provider);
        return true;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#removeProvider(java.lang.String)
	 */
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
    
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#getAllSongs()
	 */
    public Playlist getAllSongs() {
        Playlist p = new Playlist();
        for (MusicProvider pr : providers) {
        	Playlist providedSongs = pr.getAllSongs();
        	System.out.println(providedSongs.getSongs());
            p.append(providedSongs);
        }
        return p;
    }
    /* (non-Javadoc)
	 * @see net.miscjunk.aamp.common.PlayerClient#buildPlaylist(net.miscjunk.aamp.common.Query)
	 */
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

    public void setEventServer(EventServer evtServer) {
        this.evtServer = evtServer;
    }
    
    private void sendEvent(String message) {
        if (evtServer != null) {
            evtServer.sendMessage(message);
        }
    }
}
