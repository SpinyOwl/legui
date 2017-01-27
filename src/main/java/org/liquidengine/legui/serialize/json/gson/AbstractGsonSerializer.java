package org.liquidengine.legui.serialize.json.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.serialize.json.JsonConstants;
import org.liquidengine.legui.serialize.json.JsonSerializeContext;
import org.liquidengine.legui.serialize.json.JsonSerializer;

import static org.liquidengine.legui.serialize.json.JsonConstants.CLASS_PARAMETER;
import static org.liquidengine.legui.serialize.json.JsonConstants.TYPE_PARAMETER;

/**
 * Created by Alexander on 26.11.2016.
 */
public abstract class AbstractGsonSerializer<T> implements JsonSerializer<T> {

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

    @Override
    public final T deserialize(String jsonString, JsonSerializeContext context) throws LeguiException {
        JsonParser parser    = new JsonParser();
        T          component = deserialize(parser.parse(jsonString), (GsonSerializeContext) context);
        return component;
    }

    public final T deserialize(JsonElement json, GsonSerializeContext context) {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            Class<?>   aClass     = findClass(jsonObject);
            T          component  = (T) aClass.newInstance();
            deserialize(jsonObject, component, context);
            return component;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new LeguiException(LeguiExceptions.DESERIALIZE_EXCEPTION.message(), e);
        }
    }

    private Class<?> findClass(JsonObject jsonObject) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String className = getClassName(jsonObject);
        if (className != null) {
            return Class.forName(className);
        }
        String type = getShortTypeName(jsonObject);
        if (type != null) {
            return GsonSerializeRegistry.getRegistry().getClassByShortType(type);
        }
        return null;
    }

    /**
     * Reads data from json object and puts it to object
     *
     * @param json   json object to read
     * @param object object to fill
     */
    protected abstract void deserialize(JsonObject json, T object, GsonSerializeContext context);

    @Override
    public final String serialize(T object, JsonSerializeContext context) {
        Gson       gson = new Gson();
        JsonObject json = jsonSerialize(object, (GsonSerializeContext) context);
        return gson.toJson(json);
    }

    public final JsonObject jsonSerialize(T object, GsonSerializeContext context) {
        try {
            JsonObject json             = new JsonObject();
            Class<?>   aClass           = object.getClass();
            String     shortTypeByClass = GsonSerializeRegistry.getRegistry().getShortTypeByClass(aClass);
            if (shortTypeByClass != null) {
                json.addProperty(TYPE_PARAMETER, shortTypeByClass);
            } else {
                json.addProperty(CLASS_PARAMETER, aClass.getName());
            }
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
