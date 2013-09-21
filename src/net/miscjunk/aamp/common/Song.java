package net.miscjunk.aamp.common;

import java.util.List;

public class Song {
    String id;
    int trackno;
    String title;
    String album;
    String artist;
    List<String> genres;
    MusicProvider provider;
    
    public Song(String id, MusicProvider provider) {
       this.id = id; 
       this.provider = provider;
    }
    
    public String getId() {
        return id;
    }

    /**
     * @return the trackno
     */
    public int getTrack() {
        return trackno;
    }

    /**
     * @param trackno the trackno to set
     */
    public void setTrack(int track) {
        this.trackno = track;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * @param album the album to set
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @return the genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres the genres to set
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

	public PlayableSong inflate() {
		return provider.inflate(this);
	}
	
	@Override
	public String toString() {
		return id;
	}

	public MusicProvider getProvider() {
		return provider;
	};
}
