package org.liquidengine.legui.marshal.json.gsonimpl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.JsonMarshaller;

import static org.liquidengine.legui.marshal.JsonConstants.CLASS_PARAMETER;
import static org.liquidengine.legui.marshal.JsonConstants.TYPE_PARAMETER;

/**
 * Created by Aliaksandr_Shcherbin on 2/24/2017.
 */
public abstract class AbstractGsonMarshaller<T> implements JsonMarshaller<T> {

    private static String getClassName(String json) {
        return getClassName(new JsonParser().parse(json).getAsJsonObject());
    }

    private static String getClassName(JsonObject jsonObject) {
        JsonElement claz = jsonObject.get(CLASS_PARAMETER);
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
        JsonElement typeName = jsonObject.get(TYPE_PARAMETER);
        if (typeName == null || typeName.isJsonNull()) {
            return null;
        } else {
            return typeName.getAsString();
        }
    }

    @Override
    public final T unmarshal(String jsonString, JsonMarshalContext context) throws LeguiException {
        JsonParser parser    = new JsonParser();
        T          component = unmarshal(parser.parse(jsonString), (GsonMarshalContext) context);
        return component;
    }

    public final T unmarshal(JsonElement json, GsonMarshalContext context) {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            Class<?>   aClass     = findClass(jsonObject);
            T          component  = (T) aClass.newInstance();
            unmarshal(jsonObject, component, context);
            return component;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new LeguiException(LeguiExceptions.UNMARSHALLING_EXCEPTION.message(), e);
        }
    }

    private Class<?> findClass(JsonObject jsonObject) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String className = getClassName(jsonObject);
        if (className != null) {
            return Class.forName(className);
        }
        String type = getShortTypeName(jsonObject);
        if (type != null) {
            return GsonMarshalRegistry.getRegistry().getClassByShortType(type);
        }
        return null;
    }

    @Override
    public final String marshal(T object, JsonMarshalContext context) {
        Gson       gson = new Gson();
        JsonObject json = jsonMarshal(object, (GsonMarshalContext) context);
        return gson.toJson(json);
    }

    public final JsonObject jsonMarshal(T object, GsonMarshalContext context) {
        try {
            JsonObject json             = new JsonObject();
            Class<?>   aClass           = object.getClass();
            String     shortTypeByClass = GsonMarshalRegistry.getRegistry().getShortTypeByClass(aClass);
            if (shortTypeByClass != null) {
                json.addProperty(TYPE_PARAMETER, shortTypeByClass);
            } else {
                json.addProperty(CLASS_PARAMETER, aClass.getName());
            }
            marshal(object, json, context);
            return json;
        } catch (Throwable e) {
            throw new LeguiException(LeguiExceptions.MARSHALLING_EXCEPTION.message(), e);
        }
    }

    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    protected abstract void marshal(T object, JsonObject json, GsonMarshalContext context);

    /**
     * Reads data from json object and puts it to object
     *
     * @param json    json object to read
     * @param object  object to fill
     * @param context marshal context
     */
    protected abstract void unmarshal(JsonObject json, T object, GsonMarshalContext context);


}
