package net.miscjunk.aamp.common;

public class SimpleQuery {
    String any;
    String genre;
    String artist;
    String album;
    String title;
    boolean exclude;
    
    public SimpleQuery(String any, String genre, String artist, String album, String title, boolean exclude) {
        this.any = any;
        this.genre = genre;
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.exclude = exclude;
    }

    /**
     * @return the any
     */
    public String getAny() {
        return any;
    }

    /**
     * @param any the any to set
     */
    public void setAny(String any) {
        this.any = any;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
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
     * @return the exclude
     */
    public boolean isExclude() {
        return exclude;
    }

    /**
     * @param exclude the exclude to set
     */
    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }
}
