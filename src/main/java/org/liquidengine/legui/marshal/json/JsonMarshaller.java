package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.binding.model.BindingUtilities.getFieldValue;
import static org.liquidengine.legui.marshal.json.JsonSerisalizeCreator.createAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
        if (object == null) {
            return JsonNull.INSTANCE;
        }
        JsonElement json;
        AbstractClassBinding<?> classBinding = BindingRegistry.getInstance().getBinding(object.getClass());
        if (classBinding != null) {
            json = marshalToJson(object, classBinding);
        } else {
            json = createGson().toJsonTree(object);
            if (json.isJsonObject()) {
                json.getAsJsonObject().addProperty("@type", object.getClass().getName());
            }
        }
        return json;
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

        JsonElement jsonE;
        if (object == null) {
            jsonE = JsonNull.INSTANCE;
        } else {
            JsonObject json = new JsonObject();
            jsonE = json;
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
        }
        return jsonE;
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
        if (json == null || json.isJsonNull()) {
            return null;
        }
        Type typeToUse = type;
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
                    typeToUse = newType;
                }
            }
        }
        if (typeToUse instanceof Class) {
            AbstractClassBinding<T> binding = BindingRegistry.getInstance().getBinding((Class<T>) typeToUse);
            if (binding != null) {
                return unmarshalFromJson(json, (Class<T>) typeToUse, binding);
            }
        }
        return createGson().fromJson(json, typeToUse);
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
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
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

            // retrieving class for field value
            Class fieldClass = BindingUtilities.classTreeGetFieldType(clazz, javaFieldName);
            if (fieldClass == null && binding.getFieldAccessor() != null) {
                Type fieldType = binding.getFieldAccessor().getFieldType();
                if (fieldType instanceof Class) {
                    fieldClass = (Class) fieldType;
                } else {
                    // for now skipping if can't get field class.
                    continue;
                }
            }

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
        if (element == null || element.isJsonNull()) {
            return null;
        }
        return classConverter.convertToJava(element.getAsString());
    }

    /**
     * Used to create Gson instance with type adapters for class bindings in {@link BindingRegistry}.
     *
     * @return Gson instance.
     */
    protected static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Map<Class, AbstractClassBinding> bindingMap = BindingRegistry.getInstance().getBindingMap();

        for (Entry<Class, AbstractClassBinding> entry : bindingMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(entry.getKey(), createAdapter(entry.getKey(), entry.getValue()));
        }

        return gsonBuilder.create();
    }

}
