package org.liquidengine.legui.serialize.json.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.serialize.json.JsonConstants;

/**
 * Created by Alexander on 26.11.2016.
 */
public final class GsonSerializeUtil {
    private GsonSerializeUtil() {
    }

    public static <T> String serialize(T component) {
        if (component == null) return null;
        AbstractGsonSerializer serializer = GsonSerializeRegistry.getRegistry().getSerializer(component.getClass());
        if (serializer == null) throw new LeguiException(LeguiExceptions.SERIALIZER_IS_NOT_EXIST.message(component.getClass().getName()));
        GsonSerializeContext context = new GsonSerializeContext();
        return serializer.serialize(component, context);
    }

    public static <T> JsonObject serializeToJson(T component) {
        return serializeToJson(component, new GsonSerializeContext());
    }

    public static <T> JsonObject serializeToJson(T component, GsonSerializeContext context) {
        if (component == null) return null;
        AbstractGsonSerializer serializer = GsonSerializeRegistry.getRegistry().getSerializer(component.getClass());
        if (serializer == null) throw new LeguiException(LeguiExceptions.SERIALIZER_IS_NOT_EXIST.message(component.getClass().getName()));
        return serializer.jsonSerialize(component, context);
    }

    public static <T> T deserialize(String json) {
        AbstractGsonSerializer<T> serializer = getGsonSerializer(getClassName(json), getShortTypeName(json));
        return serializer.deserialize(json, new GsonSerializeContext());
    }

    public static <T> T deserializeFromJson(JsonObject json) {
        return deserializeFromJson(json, new GsonSerializeContext());
    }

    public static <T> T deserializeFromJson(JsonObject json, GsonSerializeContext context) {
        AbstractGsonSerializer<T> serializer = getGsonSerializer(getClassName(json), getShortTypeName(json));
        return serializer.deserialize(json, context);
    }

    public static <T> AbstractGsonSerializer<T> getGsonSerializer(String className, String shortTypeName) {
        AbstractGsonSerializer<T> serializer = null;
        String                    type       = null;
        if (type == null) {
            type = className;
            if (type != null) {
                serializer = GsonSerializeRegistry.getRegistry().getSerializer(type);
            }
        }
        if (type == null) {
            type = shortTypeName;
            if (type != null) {
                serializer = GsonSerializeRegistry.getRegistry().getSerializerByShortType(type);
            }
        }
        if (type == null || serializer == null) throw new LeguiException(LeguiExceptions.DESERIALIZER_IS_NOT_EXIST.message(type));
        return serializer;
    }

    private static String getClassName(String json) {
        return getClassName(new JsonParser().parse(json).getAsJsonObject());
    }

    private static String getClassName(JsonObject jsonObject) {
        JsonElement claz = jsonObject.get(JsonConstants.CLASS_PARAMETER);
        if (claz == null || claz.isJsonNull()) {
            return null;
        } else {
            return claz.getAsString();
        }
    }

    private static String getShortTypeName(String json) {
        return getShortTypeName(new JsonParser().parse(json).getAsJsonObject());
    }

    private static String getShortTypeName(JsonObject jsonObject) {
        JsonElement typeName = jsonObject.get(JsonConstants.TYPE_PARAMETER);
        if (typeName == null || typeName.isJsonNull()) {
            return null;
        } else {
            return typeName.getAsString();
        }
    }

}
