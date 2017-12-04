package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.binding.model.BindingUtilities.getFieldValue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.AbstractClassConverter;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.BindingUtilities;

/**
 * Json marshaller.
 *
 * @author ShchAlexander.
 */
public final class JsonMarshaller {

    /**
     * Private constructor.
     */
    private JsonMarshaller() {
    }

    /**
     * Used to marshal object to json representation.
     *
     * @param object object to marshal.
     *
     * @return json string.
     */
    public static String marshal(Object object) {
        return new Gson().toJson(marshalToJson(object));
    }

    /**
     * Used to marshal object to JsonElement.
     *
     * @param object object to marshal.
     *
     * @return JsonElement.
     */
    private static JsonElement marshalToJson(Object object) {
        JsonElement json;
        AbstractClassBinding<?> classBinding = BindingRegistry.getInstance().getBinding(object.getClass());
        if (classBinding != null) {
            json = marshalToJson(object, classBinding);
        } else {
            json = new Gson().toJsonTree(object);
        }
        return json;
    }

    /**
     * Used to marshal object to json representation.
     *
     * @param object object to marshal.
     * @param classBinding class binding to use till marshalling.
     * @param <T> type of object.
     *
     * @return json representation of object.
     */
    public static <T> String marshal(T object, AbstractClassBinding<? extends T> classBinding) {
        Gson gson = new Gson();
        JsonElement e = marshalToJson(object, classBinding);
        return gson.toJson(e);
    }

    /**
     * Used to marshal object to json using class binding.
     *
     * @param object object to marshal.
     * @param classBinding class binding.
     * @param <T> type of object.
     *
     * @return json representation og object.
     */
    private static <T> JsonElement marshalToJson(T object, AbstractClassBinding<? extends T> classBinding) {
        JsonObject json = new JsonObject();
        List<Binding> bindings = classBinding.getBindingList();

        for (Binding binding : bindings) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            Object field = getFieldValue(object, javaFieldName);
            JsonElement value = null;
            if (binding.getLinkedClassBinding() != null) {
                value = marshalToJson(field, binding.getLinkedClassBinding());
            } else if (binding.getClassConverter() != null) {
                value = marshalToJson(field, binding.getClassConverter());
            } else {
                value = marshalToJson(field);
            }
            json.add(bindingFieldName, value);
        }
        return json;
    }

    /**
     * Used to marshal object to json using class converter.
     *
     * @param object object to marshal.
     * @param classConverter class converter.
     * @param <T> type of object.
     *
     * @return json representation og object.
     */
    private static <T> JsonElement marshalToJson(T object, AbstractClassConverter<T> classConverter) {
        return new JsonPrimitive(classConverter.convertFromJava(object));
    }

    /**
     * Used to unmarshal json representation to Java object.
     *
     * @param json json to unmarshal.
     * @param clazz target class.
     * @param <T> class type.
     *
     * @return unmarshalled object or null.
     */
    public static <T> T unmarshal(String json, Class<T> clazz) {
        return unmarshal(new JsonParser().parse(json), clazz);
    }

    /**
     * Used to unmarshal json representation to Java object.
     *
     * @param json json to unmarshal.
     * @param clazz target class.
     * @param <T> class type.
     *
     * @return unmarshalled object or null.
     */
    private static <T> T unmarshal(JsonElement json, Class<T> clazz) {
        AbstractClassBinding<T> binding = BindingRegistry.getInstance().getBinding(clazz);
        if (binding == null) {
            return new Gson().fromJson(json, clazz);
        } else {
            return unmarshalFromJson(json, clazz, binding);
        }

    }

    /**
     * Used to unmarshal json representation to Java object using class binding.
     *
     * @param jsonElement json to unmarshal.
     * @param clazz target class.
     * @param classBinding class binding.
     * @param <T> class type.
     *
     * @return unmarshalled object or null.
     */
    private static <T> T unmarshalFromJson(JsonElement jsonElement, Class<T> clazz, AbstractClassBinding<T> classBinding) {
        List<Binding> bindings = classBinding.getBindingList();
        Map<String, Object> fieldValues = new HashMap<>();
//        List<BindedParameters>
        for (Binding binding : bindings) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement element = jsonObject.get(bindingFieldName);

            Class fieldClass = BindingUtilities.classTreeGetFieldType(clazz, javaFieldName);

            Object fieldValue;
            if (binding.getLinkedClassBinding() != null) {
                fieldValue = unmarshalFromJson(element, fieldClass, binding.getLinkedClassBinding());
            } else if (binding.getClassConverter() != null) {
                fieldValue = unmarshalFromJson(element, binding.getClassConverter());
            } else {
                fieldValue = unmarshal(element, fieldClass);
            }
            fieldValues.put(javaFieldName, fieldValue);
        }

        return classBinding.createInstance(clazz, fieldValues);
    }

    /**
     * Used to unmarshal json representation to Java object using class converter.
     *
     * @param element json to unmarshal.
     * @param classConverter class converter.
     * @param <T> class type.
     *
     * @return unmarshalled object or null.
     */
    private static <T> T unmarshalFromJson(JsonElement element, AbstractClassConverter<T> classConverter) {
        return classConverter.convertToJava(element.getAsString());
    }
}
