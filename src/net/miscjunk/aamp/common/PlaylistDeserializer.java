package net.miscjunk.aamp.common;

import java.lang.reflect.Type;

import com.google.gson.*;

public class PlaylistDeserializer implements JsonDeserializer<Playlist> {
    Player player;
    public PlaylistDeserializer(Player player) {
        this.player = player;
    }

    @Override
    public Playlist deserialize(JsonElement src, Type typeOfSrc,
            JsonDeserializationContext context) throws JsonParseException {
        Playlist allSongs = player.getAllSongs();
        JsonObject jo = src.getAsJsonObject();
        Playlist p = new Playlist();
        p.id = jo.getAsJsonPrimitive("id").getAsString();
        JsonArray jsongs = jo.getAsJsonArray("songs");
        for (JsonElement el : jsongs) {
            JsonObject jsong = el.getAsJsonObject();
            String songId = jsong.getAsJsonPrimitive("id").getAsString();
            p.addSong(allSongs.getSong(songId));
        }
        return p;
    }

}
