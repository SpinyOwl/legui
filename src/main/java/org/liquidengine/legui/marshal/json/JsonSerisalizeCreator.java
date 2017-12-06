package org.liquidengine.legui.marshal.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.liquidengine.legui.binding.model.ClassBinding;

/**
 * Utility class.
 * Created by ShchAlexander on 05.12.2017.
 */
public final class JsonSerisalizeCreator {

    /**
     * Private constructor.
     */
    private JsonSerisalizeCreator() {
        // private empty constructor.
    }

    /**
     * Used to create adapter for class with class binding.
     *
     * @param c class.
     * @param b class binding.
     *
     * @return Gson adapter.
     */
    protected static CustomSerializerDeserialiser createAdapter(Class c, ClassBinding b) {
        return new CustomSerializerDeserialiser();
    }

    /**
     * Gson serializer for class bindings.
     */
    public static class CustomSerializerDeserialiser implements JsonSerializer, JsonDeserializer {

        /**
         * Gson invokes this call-back method during serialization when it encounters a field of the
         * specified type.
         * <p>
         * <p>In the implementation of this call-back method, you should consider invoking
         * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
         * non-trivial field of the {@code src} object. However, you should never invoke it on the
         * {@code src} object itself since that will cause an infinite loop (Gson will call your
         * call-back method again).</p>
         *
         * @param src the object that needs to be converted to Json.
         * @param typeOfSrc the actual type (fully genericized version) of the source object.
         * @param context
         *
         * @return a JsonElement corresponding to the specified object.
         */
        @Override
        public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
            return JsonMarshaller.marshalToJson(src);
        }

        /**
         * Gson invokes this call-back method during deserialization when it encounters a field of the
         * specified type.
         * <p>In the implementation of this call-back method, you should consider invoking
         * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
         * for any non-trivial field of the returned object. However, you should never invoke it on the
         * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
         * call-back method again).
         *
         * @param json The Json data being deserialized
         * @param typeOfT The type of the Object to deserialize to
         * @param context
         *
         * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
         *
         * @throws JsonParseException if json is not in the expected format of {@code typeofT}
         */
        @Override
        public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return JsonMarshaller.unmarshal(json, typeOfT);
        }
    }
}
