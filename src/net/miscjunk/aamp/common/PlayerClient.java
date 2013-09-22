package net.miscjunk.aamp.common;

import java.util.List;

public interface PlayerClient {

	boolean next();

	boolean prev();

	boolean play();

	boolean pause();

	boolean seek(double position);

	boolean skipTo(String id);

	boolean setVolume(double volume);

	boolean setQueue(MusicQueue queue);
	
	void shuffleQueue();

	double getVolume();

	double getPosition();

	MusicQueue getQueue();

	Song getCurrentSong();

	List<Playlist> getPlaylists();

	Playlist getPlaylist(String id);

	boolean updatePlaylist(Playlist list);

	boolean addPlaylist(Playlist list);

	boolean removePlaylist(String id);

	List<MusicProvider> getProviders();

	MusicProvider getProvider(String id);

	boolean updateProvider(MusicProvider list);

	boolean addProvider(MusicProvider provider);

	boolean removeProvider(String id);

	Playlist getAllSongs(); //Should be List<Song>

	Playlist buildPlaylist(Query query);

}