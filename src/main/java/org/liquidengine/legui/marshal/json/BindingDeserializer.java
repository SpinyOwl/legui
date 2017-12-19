package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.marshal.json.BindingProperties.CLASS_PROPERTY;
import static org.liquidengine.legui.marshal.json.BindingProperties.TYPE_PROPERTY;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.BindingUtilities;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class BindingDeserializer implements JsonDeserializer {

    private AbstractClassBinding classBinding;

    public BindingDeserializer(AbstractClassBinding classBinding) {
        this.classBinding = classBinding;
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
        if (json == null || json.isJsonNull()) {
            return null;
        } else if (json.isJsonObject()) {
            return unmarshall(json.getAsJsonObject(), typeOfT, classBinding, context);
        } else {
            if (json.isJsonPrimitive()) {
                System.err.println("PRIMITIVE NOT REGISTERED");
            } else if (json.isJsonArray()) {
                System.err.println("ARRAY NOT REGISTERED");
            } else {
                System.err.println("UNKNOWN");
            }
            return context.deserialize(json, typeOfT);
        }
    }

    private <T> T unmarshall(JsonObject json, Type typeOfT, JsonDeserializationContext context) {
        Class<T> clazz = gettClass(json, typeOfT);
        if (clazz != null) {
            AbstractClassBinding<T> binding = BindingRegistry.getInstance().getBinding(clazz);
            if (binding != null) {
                return unmarshall(json, clazz, binding, context);
            } else {
                return context.deserialize(json, clazz);
            }
        }
        return context.deserialize(json, typeOfT);
    }

    private <T> Class<T> gettClass(JsonObject json, Type typeOfT) {
        Class<T> clazz = null;
        Class<T> tClass = null;
        if (typeOfT instanceof Class) {
            try {
                Class<T> temp = (Class<T>) typeOfT;
                if (!temp.isInterface() && !Modifier.isAbstract(temp.getModifiers())) {
                    tClass = temp;
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        JsonElement typeProp = json.remove(TYPE_PROPERTY.value());
        if (typeProp != null && !typeProp.isJsonNull() && typeProp.isJsonPrimitive()) {
            String typeAlias = typeProp.getAsString();
            AbstractClassBinding binding = BindingRegistry.getInstance().getBindingByTypeAlias(typeAlias);
            if (binding != null) {
                clazz = binding.getBindingForType();
            }
        }

        if (clazz == null) {
            JsonElement classProp = json.remove(CLASS_PROPERTY.value());
            if (classProp != null && !classProp.isJsonNull() && classProp.isJsonPrimitive()) {
                String className = classProp.getAsString();
                try {
                    clazz = (Class<T>) Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (clazz == null) {
            clazz = tClass;
        }
        return clazz;
    }

    private <T> T unmarshall(JsonObject json, Type typeOfT, AbstractClassBinding<T> classBinding, JsonDeserializationContext context) {
        Class<T> clazz = gettClass(json, typeOfT);
        if (clazz == null) {
            clazz = (Class<T>) classBinding.getBindingForType();
        }
        if (clazz == null) {
            context.deserialize(json, typeOfT);
        }
        return unmarshall(json, clazz, classBinding, context);
    }

    private <T> T unmarshall(JsonObject json, Class<T> clazz, AbstractClassBinding<T> classBinding, JsonDeserializationContext context) {
        if (json == null || json.isJsonNull()) {
            return null;
        }
        T instance = classBinding.createInstance(clazz);
        List<Binding> bindings = classBinding.getBindingList();

        for (Binding binding : bindings) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            JsonElement element = json.get(bindingFieldName);
            if (element == null || element.isJsonNull()) {
                continue;
            }

            // retrieving class for field value
            Class fieldClass = BindingUtilities.classTreeGetFieldClass(clazz, javaFieldName);
            AbstractFieldAccessor fieldAccessor = binding.getFieldAccessor();
            if (fieldClass == null && fieldAccessor != null) {
                Type fieldType = fieldAccessor.getFieldType();

                if (fieldType instanceof Class) {
                    fieldClass = (Class) fieldType;
                } else {
                    // for now skipping if can't get field class.
                    continue;
                }
            }

            Object fieldValue = null;
            if (Collection.class.isAssignableFrom(fieldClass) || fieldClass.isArray() || Map.class.isAssignableFrom(fieldClass)) {
                fieldValue = context.deserialize(element, BindingUtilities.classTreeGetFieldType(clazz, javaFieldName));
            } else {
                if (binding.getLinkedClassBinding() != null) {
                    if (element.isJsonObject()) {
                        fieldValue = unmarshall(element.getAsJsonObject(), fieldClass, binding.getLinkedClassBinding(), context);
                    } else {
                        System.err.println("NOT A JSON OBJECT");
                    }
                } else if (binding.getClassConverter() != null) {
                    fieldValue = binding.getClassConverter().convertToJava(element.getAsString());
                } else {

                    if (element.isJsonObject()) {
                        fieldValue = unmarshall(element.getAsJsonObject(), (Type) fieldClass, context);
                    } else {
                        if (fieldClass.isPrimitive() || (!fieldClass.isInterface() && !Modifier.isAbstract(fieldClass.getModifiers()))) {
                            fieldValue = context.deserialize(element, fieldAccessor == null ? fieldClass : fieldAccessor.getFieldType());
                        }
                    }
                }
            }

            if (fieldAccessor != null) {
                fieldAccessor.setFieldValue(instance, fieldValue);
            } else {
                BindingUtilities.setFieldValue(instance, javaFieldName, fieldValue);
            }
        }

        classBinding.postConstruct(instance);
        return instance;
    }
}
