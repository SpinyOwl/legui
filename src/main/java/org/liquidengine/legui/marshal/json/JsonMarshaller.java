package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.binding.model.BindingUtilities.getFieldValue;
import static org.liquidengine.legui.marshal.json.JsonSerisalizeCreator.createAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.AbstractClassConverter;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.BindingUtilities;
import org.liquidengine.legui.binding.model.ClassBinding;

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
        return createGson().toJson(marshalToJson(object));
    }

    /**
     * Used to marshal object to JsonElement.
     *
     * @param object object to marshal.
     *
     * @return JsonElement.
     */
    protected static JsonElement marshalToJson(Object object) {
        JsonElement json;
        AbstractClassBinding<?> classBinding = BindingRegistry.getInstance().getBinding(object.getClass());
        if (classBinding != null) {
            json = marshalToJson(object, classBinding);
        } else {
            json = createGson().toJsonTree(object);
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
        Gson gson = createGson();
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
    protected static <T> JsonElement marshalToJson(T object, AbstractClassBinding<? extends T> classBinding) {
        JsonObject json = new JsonObject();
        json.addProperty("@type", object.getClass().getName());

        List<Binding> bindings = classBinding.getBindingList();

        for (Binding binding : bindings) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            Object fieldValue;
            if (binding.getFieldAccessor() != null) {
                fieldValue = binding.getFieldAccessor().getFieldValue(object);
            } else {
                fieldValue = getFieldValue(object, javaFieldName);
            }

            JsonElement value = null;
            if (binding.getLinkedClassBinding() != null) {
                value = marshalToJson(fieldValue, binding.getLinkedClassBinding());
            } else if (binding.getClassConverter() != null) {
                value = marshalToJson(fieldValue, binding.getClassConverter());
            } else {
                value = marshalToJson(fieldValue);
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
    protected static <T> JsonElement marshalToJson(T object, AbstractClassConverter<T> classConverter) {
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
    public static <T> T unmarshal(String json, Type clazz) {
        return unmarshal(new JsonParser().parse(json), clazz);
    }

    /**
     * Used to unmarshal json representation to Java object.
     *
     * @param json json to unmarshal.
     * @param type target class.
     * @param <T> class type.
     *
     * @return unmarshalled object or null.
     */
    protected static <T> T unmarshal(JsonElement json, Type type) {
        if (json.isJsonObject()) {
            JsonObject o = json.getAsJsonObject();
            JsonElement jsonElement = o.remove("@type");
            Type newType = null;
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                try {
                    newType = Class.forName(jsonElement.getAsString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (newType != null) {
                    type = newType;
                }
            }
        }
        if (type instanceof Class) {
            AbstractClassBinding<T> binding = BindingRegistry.getInstance().getBinding((Class<T>) type);
            if (binding != null) {
                return unmarshalFromJson(json, (Class<T>) type, binding);
            } else {
                createGson().fromJson(json, type);
            }
        }
        return createGson().fromJson(json, type);
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
    protected static <T> T unmarshalFromJson(JsonElement jsonElement, Class<T> clazz, AbstractClassBinding<T> classBinding) {
        List<Binding> bindings = classBinding.getBindingList();
//        List<BindedParameters>
        T instance = classBinding.createInstance(clazz);
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
                if (binding.getFieldAccessor() != null) {
                    fieldValue = unmarshal(element, binding.getFieldAccessor().getFieldType());
                } else {
                    fieldValue = unmarshal(element, fieldClass);
                }
            }

            if (binding.getFieldAccessor() != null) {
                binding.getFieldAccessor().setFieldValue(instance, fieldValue);
            } else {
                BindingUtilities.setFieldValue(instance, javaFieldName, fieldValue);
            }
        }

        classBinding.postConstruct(instance);
        return instance;
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
    protected static <T> T unmarshalFromJson(JsonElement element, AbstractClassConverter<T> classConverter) {
        return classConverter.convertToJava(element.getAsString());
    }

    protected static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Map<Class, ClassBinding> bindingMap = BindingRegistry.getInstance().getBindingMap();

        for (Entry<Class, ClassBinding> entry : bindingMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(entry.getKey(), createAdapter(entry.getKey(), entry.getValue()));
        }

        return gsonBuilder.create();
    }

}
