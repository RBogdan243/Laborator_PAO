package Service.impl;

import com.google.gson.*;

import java.lang.reflect.Type;

public class EntranceAdapter implements JsonDeserializer<Entrance> {
    @Override
    public Entrance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("Type").getAsString();

        switch (type) {
            case "FinalRoom":
                return context.deserialize(json, FinalRoom.class);
            case "SecretRoom":
                return context.deserialize(json, SecretRoom.class);
            case "PortraitRoom":
                return context.deserialize(json, PortraitRoom.class);
            case "TicketRoom":
                return context.deserialize(json, TicketRoom.class);
            case "SimpleRoom":
                return context.deserialize(json, SimpleRoom.class);
            default:
                return context.deserialize(json, Entrance.class);
        }
    }
}