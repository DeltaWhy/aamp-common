package net.miscjunk.aamp.common;

import java.lang.reflect.Type;

import com.google.gson.*;

public class MusicProviderDeserializer implements JsonDeserializer<MusicProvider> {

    public MusicProvider deserialize(JsonElement src, Type typeOfSrc,
            JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = src.getAsJsonObject();
        String typeName = jo.getAsJsonPrimitive("type").getAsString();
        try {
            Type t = Class.forName(typeName);
            return context.deserialize(src, t);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
