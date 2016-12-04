package org.liquidengine.legui.serialize.json.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.serialize.json.JsonSerializeContext;
import org.liquidengine.legui.serialize.json.JsonSerializer;

import static org.liquidengine.legui.serialize.json.JsonConstants.CLASS_PARAMETER;

/**
 * Created by Alexander on 26.11.2016.
 */
public abstract class AbstractGsonSerializer<T> implements JsonSerializer<T> {

    @Override
    public final T deserialize(String jsonString, JsonSerializeContext context) throws LeguiException {
        JsonParser parser = new JsonParser();
        T component = jsonDeserialize(parser.parse(jsonString), (GsonSerializeContext) context);
        return component;
    }

    public final T jsonDeserialize(JsonElement json, GsonSerializeContext context) {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            String className = jsonObject.get(CLASS_PARAMETER.getValue()).getAsString();
            T component = (T) Class.forName(className).newInstance();
            jsonDeserialize(jsonObject, component, context);
            return component;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new LeguiException(LeguiExceptions.DESERIALIZE_EXCEPTION.message(), e);
        }
    }

    /**
     * Reads data from json object and puts it to object
     *
     * @param json   json object to read
     * @param object object to fill
     */
    protected abstract void jsonDeserialize(JsonObject json, T object, GsonSerializeContext context);


    @Override
    public final String serialize(T object, JsonSerializeContext context) {
        Gson gson = new Gson();
        JsonObject json = jsonSerialize(object, (GsonSerializeContext) context);
        return gson.toJson(json);
    }

    public final JsonObject jsonSerialize(T object, GsonSerializeContext context) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty(CLASS_PARAMETER.getValue(), object.getClass().getName());
            jsonSerialize(object, json, context);
            return json;
        } catch (Throwable e) {
            throw new LeguiException(LeguiExceptions.SERIALIZE_EXCEPTION.message(), e);
        }
    }

    /**
     * Reads data from object and puts it to json object
     *
     * @param object object to read
     * @param json   json object to fill
     */
    protected abstract void jsonSerialize(T object, JsonObject json, GsonSerializeContext context);

}
