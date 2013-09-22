package net.miscjunk.aamp.common;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SongAdapter implements JsonSerializer<Song>, JsonDeserializer<Song> {
    public JsonElement serialize(Song src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject el = new JsonObject();
        el.addProperty("id", src.getId());
        el.addProperty("album", src.getAlbum());
        el.addProperty("artist", src.getArtist());
        el.addProperty("title", src.getTitle());
        el.addProperty("track", src.getTrack());
        el.add("genres", context.serialize(src.getGenres()));
        el.addProperty("provider", src.getProvider().getId());
        return el;
    }
    
	public Song deserialize(JsonElement src, Type typeOfSrc,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = src.getAsJsonObject();
		String id = obj.get("id").getAsString();
		String album = getStringOrNull(obj, "album");
		String artist = getStringOrNull(obj, "artist");
		String title =  getStringOrNull(obj, "title");
		List<String> genres = null;
		if(obj.get("genres") != null && !obj.get("genres").isJsonNull()) {
			JsonArray genresArr = obj.get("genres").getAsJsonArray();
			genres = new ArrayList<String>();
			for(int i = 0; i < genresArr.size(); i++) {
				genres.add(genresArr.get(i).getAsString());
			}
		}
		int track = obj.get("track").getAsInt();

		Song unpacked = new Song(id, null);
		unpacked.setAlbum(album);
		unpacked.setArtist(artist);
		unpacked.setGenres(genres);
		unpacked.setTitle(title);
		unpacked.setTrack(track);
		return unpacked;
	}

	private String getStringOrNull(JsonObject obj, String memberName) {
		if(obj.get(memberName) == null) { 
			return null;
		}
		else if(obj.get(memberName).isJsonNull())
			return null;
		return obj.get(memberName).getAsString();
	}
}
