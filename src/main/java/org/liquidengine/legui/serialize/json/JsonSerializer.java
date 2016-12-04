package org.liquidengine.legui.serialize.json;

/**
 * Created by Alexander on 26.11.2016.
 */
public interface JsonSerializer<T> {
    T deserialize(String jsonString, JsonSerializeContext context);

    String serialize(T object, JsonSerializeContext context);
}
