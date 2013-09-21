package net.miscjunk.aamp.common;

import java.util.List;
import java.util.ArrayList;

public class Playlist {
    List<Song> songs;
    String id;
    
    public Playlist() {
    	songs = new ArrayList<Song>();
    	id = "playlist" + songs.hashCode();
    }

    public String getId() {
        return id;
    }
    public List<Song> getSongs() {
        return songs;
    }
    public Song getSong(int index) {
        return songs.get(index);
    }
    /**
     * This fucker can return NULLLLLL
     * @param id
     * @return
     */
    public Song getSong(String id) {
    	for(Song song : songs) {
    		if(id.equals(song.getId())) {
    			return song;
    		}
    	}
        return null;
    }
    public int size() {
        return songs.size();
    }
    
    public void append(Playlist other) {//hack--shouldn't have dups?
        songs.addAll(other.songs);
    }
    public void subtract(Playlist other) {
        songs.removeAll(other.songs);
    }
    public void insertSong(Song song, int index) {
        
    }
    public void addSong(Song song) {
        songs.add(song);
    }
    
    public void removeSong(String id) {
        int index = this.indexOf(id);
        if(index > -1)
        	songs.remove(index);
    }

	private int indexOf(String id) {
        int index = -1, i = 0;
        for(Song song : songs) {
    		if(id.equals(song.getId())) {
    			index = i;
    			break;
    		}
    		i++;
        }
        return index;
	}
}
