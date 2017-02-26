package org.liquidengine.legui.marshal.json.gsonImpl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.marshal.JsonConstants;

/**
 * Created by Aliaksandr_Shcherbin on 2/24/2017.
 */
public final class GsonMarshalUtil {
    private GsonMarshalUtil() {
    }

    public static <T> String marshal(T component) {
        if (component == null) return null;
        AbstractGsonMarshaller marshaller = GsonMarshalRegistry.getRegistry().getMarshaller(component.getClass());
        if (marshaller == null) throw new LeguiException(LeguiExceptions.MARSHALLER_IS_NOT_EXIST.message(component.getClass().getName()));
        GsonMarshalContext context = new GsonMarshalContext();
        return marshaller.marshal(component, context);
    }

    public static <T> JsonObject marshalToJson(T component) {
        return marshalToJson(component, new GsonMarshalContext());
    }

    public static <T> JsonObject marshalToJson(T component, GsonMarshalContext context) {
        if (component == null) return null;
        AbstractGsonMarshaller marshaller = GsonMarshalRegistry.getRegistry().getMarshaller(component.getClass());
        if (marshaller == null) throw new LeguiException(LeguiExceptions.MARSHALLER_IS_NOT_EXIST.message(component.getClass().getName()));
        return marshaller.jsonMarshal(component, context);
    }

    public static <T> T unmarshal(String json) {
        AbstractGsonMarshaller<T> marshaller = getGsonMarshaller(getClassName(json), getShortTypeName(json));
        return marshaller.unmarshal(json, new GsonMarshalContext());
    }

    public static <T> T unmarshalFromJson(JsonObject json) {
        return unmarshalFromJson(json, new GsonMarshalContext());
    }

    public static <T> T unmarshalFromJson(JsonObject json, GsonMarshalContext context) {
        AbstractGsonMarshaller<T> marshaller = getGsonMarshaller(getClassName(json), getShortTypeName(json));
        return marshaller.unmarshal(json, context);
    }

    public static <T> AbstractGsonMarshaller<T> getGsonMarshaller(String className, String shortTypeName) {
        AbstractGsonMarshaller<T> marshaller = null;
        String                    type     = null;
        if (type == null) {
            type = className;
            if (type != null) {
                marshaller = GsonMarshalRegistry.getRegistry().getMarshaller(type);
            }
        }
        if (type == null) {
            type = shortTypeName;
            if (type != null) {
                marshaller = GsonMarshalRegistry.getRegistry().getMarshallerByShortType(type);
            }
        }
        if (type == null || marshaller == null) throw new LeguiException(LeguiExceptions.UNMARSHALLER_IS_NOT_EXIST.message(type));
        return marshaller;
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
