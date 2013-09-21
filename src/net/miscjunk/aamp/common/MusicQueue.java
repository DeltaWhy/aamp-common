package net.miscjunk.aamp.common;

public class MusicQueue {
	Playlist list;
	int currentSong;

	public MusicQueue(Playlist list) {
		System.out.println("Playlist: " + list.getSongs());
	    this.list = list;
	    this.currentSong = 0;
	}
	
	public boolean setCurrent(int index) {
	    if (index >=0 && index < list.size()) {
    	        this.currentSong = index;
    	        return true;
	    } else {
	        return false;
	    }
	}
	public boolean setCurrent(String id) {
	    for (int i=0; i < list.size(); i++) {
	        if (list.getSong(i).getId().equals(id)) {
	            currentSong = i;
	            return true;
	        }
	    }
	    return false;
	}
	public Song getCurrent() {
		if(currentSong >= list.size()) { return null; }
	    return list.getSong(currentSong);
	}

	public int getCurrentIndex() {
	    return currentSong;
	}

	public boolean isEmpty() {
		return this.list.size() == 0;
	}
}
