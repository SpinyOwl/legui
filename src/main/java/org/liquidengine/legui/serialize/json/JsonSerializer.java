package org.liquidengine.legui.serialize.json;

/**
 * Created by Alexander on 26.11.2016.
 */
public interface JsonSerializer<T> {
    T deserialize(String json);

    String serialize(T object);
}
