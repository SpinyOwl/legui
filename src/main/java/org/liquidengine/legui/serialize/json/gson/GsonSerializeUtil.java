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
        GsonSerializeContext context = new GsonSerializeContext();
        return serializer.serialize(component, context);
    }

    public static <T> T deserialize(String json) {
        String type = getType(json);
        AbstractGsonSerializer<T> serializer = GsonSerializeRegistry.getRegistry().getSerializer(type);
        if (serializer == null) throw new LeguiException(LeguiExceptions.DESERIALIZER_IS_NOT_EXIST.message(type));
        GsonSerializeContext context = new GsonSerializeContext();
        return serializer.deserialize(json, context);
    }

    public static <T> JsonObject serializeToJson(T component) {
        return serializeToJson(component, new GsonSerializeContext());
    }

    public static <T> JsonObject serializeToJson(T component, GsonSerializeContext context) {
        AbstractGsonSerializer serializer = GsonSerializeRegistry.getRegistry().getSerializer(component.getClass());
        if (serializer == null) throw new LeguiException(LeguiExceptions.SERIALIZER_IS_NOT_EXIST.message(component.getClass().getName()));
        return serializer.jsonSerialize(component, context);
    }

    public static <T> T deserializeFromJson(JsonObject json) {
        return deserializeFromJson(json, new GsonSerializeContext());
    }

    public static <T> T deserializeFromJson(JsonObject json, GsonSerializeContext context) {
        String type = getType(json);
        AbstractGsonSerializer<T> serializer = GsonSerializeRegistry.getRegistry().getSerializer(type);
        if (serializer == null) throw new LeguiException(LeguiExceptions.DESERIALIZER_IS_NOT_EXIST.message(type));
        return serializer.jsonDeserialize(json, context);
    }

    private static String getType(String json) {
        return getType(new JsonParser().parse(json).getAsJsonObject());
    }

    private static String getType(JsonObject jsonObject) {
        return jsonObject.get("@class").getAsString();
    }

}
