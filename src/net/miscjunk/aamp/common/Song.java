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

    public boolean match(SimpleQuery query) {
        boolean matchAny = false;
        boolean matchGenre = false;
        boolean matchArtist = false;
        boolean matchAlbum = false;
        boolean matchTitle = false;
        
        if (query.getAny() != null) {
            String s = query.getAny().toLowerCase();
            if (title != null && title.toLowerCase().contains(s) ||
                    album != null && album.toLowerCase().contains(s) ||
                    artist != null && artist.toLowerCase().contains(s)) {
                matchAny = true;
            } else if (genres != null) {
                for (String g : genres) {
                    if (g.toLowerCase().contains(s)) {
                        matchAny = true;
                        break;
                    }
                }
            }
        } else matchAny = true;
        if (query.getGenre() != null) {
            if (genres != null) {
                String s = query.getGenre().toLowerCase();
                for (String g : genres) {
                    if (g.toLowerCase().contains(s)) {
                        matchGenre = true;
                        break;
                    }
                }
            }
        } else matchGenre = true;
        if (query.getArtist() != null) {
            if (artist != null) {
                String s = query.getArtist().toLowerCase();
                if (artist.toLowerCase().contains(s)) matchArtist = true;
            }
        } else matchArtist = true;
        if (query.getAlbum() != null) {
            if (album != null) {
                String s = query.getAlbum().toLowerCase();
                if (album.toLowerCase().contains(s)) matchAlbum = true;
            }
        } else matchAlbum = true;
        if (query.getTitle() != null) {
            if (title != null) {
                String s = query.getTitle().toLowerCase();
                if (title.toLowerCase().contains(s)) matchTitle = true;
            }
        } else matchTitle = true;
        
        return matchAny && matchGenre && matchArtist && matchAlbum && matchTitle;
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
