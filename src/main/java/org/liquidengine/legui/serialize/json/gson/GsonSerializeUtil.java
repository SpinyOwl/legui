package org.liquidengine.legui.serialize.json.gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;

/**
 * Created by Alexander on 26.11.2016.
 */
public final class GsonSerializeUtil {
    private GsonSerializeUtil() {
    }

    public static <T> String serialize(T component) {
        AbstractGsonSerializer serializer = GsonSerializeRegistry.getRegistry().getSerializer(component.getClass());
        if (serializer == null) throw new LeguiException(LeguiExceptions.SERIALIZER_IS_NOT_EXIST.message(component.getClass().getName()));
        return serializer.serialize(component);
    }

    public static <T>T deserialize(String json) {
        String type = getType(json);
        AbstractGsonSerializer<T> serializer = GsonSerializeRegistry.getRegistry().getSerializer(type);
        if (serializer == null) throw new LeguiException(LeguiExceptions.DESERIALIZER_IS_NOT_EXIST.message(type));
        return serializer.deserialize(json);
    }

    public static <T> JsonObject serializeToJson(T component) {
        AbstractGsonSerializer serializer = GsonSerializeRegistry.getRegistry().getSerializer(component.getClass());
        if (serializer == null) throw new LeguiException(LeguiExceptions.SERIALIZER_IS_NOT_EXIST.message(component.getClass().getName()));
        return serializer.jsonSerialize(component);
    }

    public static Object deserializeFromJson(JsonObject json) {
        String type = getType(json);
        AbstractGsonSerializer serializer = GsonSerializeRegistry.getRegistry().getSerializer(type);
        if (serializer == null) throw new LeguiException(LeguiExceptions.DESERIALIZER_IS_NOT_EXIST.message(type));
        return serializer.jsonDeserialize(json);
    }

    private static String getType(String json) {
        return getType(new JsonParser().parse(json).getAsJsonObject());
    }

    private static String getType(JsonObject jsonObject) {
        return jsonObject.get("@class").getAsString();
    }

}
