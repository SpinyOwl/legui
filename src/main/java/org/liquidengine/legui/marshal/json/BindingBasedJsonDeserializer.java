package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.marshal.json.JsonMarshalProperties.CLASS_PROPERTY;
import static org.liquidengine.legui.marshal.json.JsonMarshalProperties.TYPE_PROPERTY;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.BindingUtilities;

/**
 * Json deserializer based on bindings.
 *
 * @param <T> type of class.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class BindingBasedJsonDeserializer<T> implements JsonDeserializer<T> {

    /**
     * Default class binding to use with specified type.
     */
    private AbstractClassBinding<T> classBinding;

    /**
     * Deserializer Constructor.
     *
     * @param classBinding default class binding to use.
     */
    public BindingBasedJsonDeserializer(AbstractClassBinding<T> classBinding) {
        this.classBinding = classBinding;
    }

    /**
     * Used to get class binding for specified class and json.
     *
     * @param targetClass current target class.
     * @param current current default binding.
     * @param classTypeHolder holder which keeps type or class name and class instance.
     * @param <T> type of class.
     *
     * @return deserialized instance.
     */
    private static <T> AbstractClassBinding<T> getClassBinding(Class<T> targetClass, AbstractClassBinding current, ClassTypeHolder classTypeHolder) {
        AbstractClassBinding newBinding = null;
        if (classTypeHolder.alias != null) {
            newBinding = BindingRegistry.getInstance().<T>getBindingByTypeAlias(classTypeHolder.alias);
        } else {
            newBinding = BindingRegistry.getInstance().getBinding(targetClass);
        }

        if (newBinding != null) {
            return newBinding;
        }
        return current;
    }

    /**
     * Used to obtain class from json object.
     *
     * @param json json to read.
     * @param targetClass default type to use during unmarshal.
     * @param <T> type of class.
     *
     * @return deserialized instance.
     */
    private static <T> ClassTypeHolder<T> getClassFromJson(JsonObject json, Class<T> targetClass) {
        ClassTypeHolder holder = new ClassTypeHolder();
        holder.clazz = targetClass;

        // try to unmarshal by type alias.
        JsonElement typeProp = json.remove(TYPE_PROPERTY);
        if (typeProp != null && !typeProp.isJsonNull()) {
            holder.alias = typeProp.getAsString();
            AbstractClassBinding<T> binding = BindingRegistry.getInstance().<T>getBindingByTypeAlias(holder.alias);
            if (binding != null) {
                holder.clazz = binding.getBindingForType();
            }
        } else {
            // try unmarshal by class name.
            JsonElement classProp = json.remove(CLASS_PROPERTY);
            if (classProp != null && !classProp.isJsonNull()) {
                try {
                    Class<T> newType = (Class<T>) Class.forName(classProp.getAsString());
                    holder.className = classProp.getAsString();
                    holder.alias = null;
                    holder.clazz = newType;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return holder;
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
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Type typeToUse = typeOfT;
        AbstractClassBinding<T> classBinding = this.classBinding;

        if (json.isJsonObject() && typeToUse instanceof Class) {
            ClassTypeHolder classTypeHolder = getClassFromJson(json.getAsJsonObject(), (Class) typeToUse);
            Class<T> classFromJson = classTypeHolder.clazz;
            if (typeToUse != classFromJson) {
                typeToUse = classFromJson;
                classBinding = getClassBinding(classFromJson, classBinding, classTypeHolder);
            }
            return unmarshal(json, typeToUse, classBinding, context, classTypeHolder);
        }

        return unmarshal(json, typeToUse, classBinding, context, null);
    }

    /**
     * Used to unmarshal json using specified type and class binding.
     * In case if json have @type or @class attribute classBinding and type will be replaced with more suitable for this case.
     *
     * @param json json to unmarshal.
     * @param typeOfT type to use during unmarshal.
     * @param classBinding class binding to use.
     * @param context context.
     * @param holder holder which keeps type or class name and class instance.
     *
     * @return deserialized instance.
     */
    private T unmarshal(JsonElement json, Type typeOfT, AbstractClassBinding<T> classBinding, JsonDeserializationContext context, ClassTypeHolder holder) {
        ClassTypeHolder holderToUse = holder;
        if (json == null || json.isJsonNull()) {
            return null;
        }

        if (typeOfT instanceof Class) {
            return unmarshalClass(json, (Class<T>) typeOfT, classBinding, context);
        }

        Type typeToUse = typeOfT;
        AbstractClassBinding<T> binding = null;
        if (json.isJsonObject()) {
            JsonObject o = json.getAsJsonObject();
            if (holderToUse == null) {
                holderToUse = getClassFromJson(o, null);
                typeToUse = holderToUse.clazz == null ? typeToUse : holderToUse.clazz;
                if (holderToUse.alias != null) {
                    binding = BindingRegistry.getInstance().getBindingByTypeAlias(holderToUse.alias);
                } else if (holderToUse.className != null) {
                    binding = BindingRegistry.getInstance().getBinding(holderToUse.clazz);
                }
            }
        }

        if (binding != null && typeToUse instanceof Class) {
            Class<T> aClass = (Class<T>) typeToUse;
            return unmarshal(json, aClass, binding, context, holderToUse);
        }

        return context.deserialize(json, typeOfT);
    }

    /**
     * Used to unmarshal json using specified type and class binding.
     *
     * @param jsonElement json to unmarshal.
     * @param targetClass type to use during unmarshal.
     * @param classBinding class binding to use.
     * @param context context.
     * @param <T> type of class.
     *
     * @return deserialized instance.
     */
    private <T> T unmarshalClass(JsonElement jsonElement, Class<T> targetClass, AbstractClassBinding<T> classBinding, JsonDeserializationContext context) {
        if (jsonElement == null || targetClass == null) {
            return null;
        }
        if (classBinding == null) {
            return context.deserialize(jsonElement, targetClass);
        }

        if (!jsonElement.isJsonObject()) {
            return context.deserialize(jsonElement, targetClass);
        }

        JsonObject json = jsonElement.getAsJsonObject();

        T instance = classBinding.createInstance(targetClass);

        Map<String, Object> fieldValues = new HashMap<>();

        for (Binding binding : classBinding.getBindingList()) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            JsonElement element = json.get(bindingFieldName);
            if (element == null || element.isJsonNull() || (element.isJsonArray() && element.getAsJsonArray().size() == 0)) {
                continue;
            }

            // retrieving class for field value
            Class fieldClass = BindingUtilities.classTreeGetFieldClass(targetClass, javaFieldName);

            AbstractFieldAccessor fieldAccessor = binding.getFieldAccessor();
            if (fieldClass == null && fieldAccessor != null) {
                Type fieldType = fieldAccessor.getFieldType();
                if (fieldType instanceof Class) {
                    fieldClass = (Class) fieldType;
                } else {
                    fieldAccessor.setFieldValue(instance, context.deserialize(element, fieldType));
                    continue;
                }
            }
            Object fieldValue = null;

            // default deserialization.
            if (Collection.class.isAssignableFrom(fieldClass) ||
                Map.class.isAssignableFrom(fieldClass) ||
                fieldClass.isArray()) {
                if (element.isJsonObject()) {
                    element.getAsJsonObject().remove(TYPE_PROPERTY);
                    element.getAsJsonObject().remove(CLASS_PROPERTY);
                }
                fieldValue = context.deserialize(element, BindingUtilities.classTreeGetFieldType(targetClass, javaFieldName));
            } else {

                if (binding.getLinkedClassBinding() != null) {
                    if (element.isJsonObject()) {
                        fieldValue = unmarshal(element, fieldClass, binding.getLinkedClassBinding(), context, null);
                    }
                } else if (binding.getClassConverter() != null) {
                    fieldValue = binding.getClassConverter().convertToJava(element.getAsString());
                } else {
                    if (element.isJsonObject()) {
                        fieldValue = unmarshal(element.getAsJsonObject(), fieldClass, context, null);
                    } else {
                        if (fieldClass.isPrimitive()) {
                            fieldValue = context.deserialize(element, BindingUtilities.classTreeGetFieldType(targetClass, javaFieldName));
                        } else {
                            if (!fieldClass.isInterface() && !Modifier.isAbstract(fieldClass.getModifiers())) {
                                fieldValue = context.deserialize(element, BindingUtilities.classTreeGetFieldType(targetClass, javaFieldName));
                            } else {
                                System.err.println("NON E");
                            }
                        }
                    }
                }
            }

            fieldValues.put(javaFieldName, fieldValue);

            if (fieldAccessor != null) {
                fieldAccessor.setFieldValue(instance, fieldValue);
            } else {
                BindingUtilities.setFieldValue(instance, javaFieldName, fieldValue);
            }
        }

        classBinding.postConstruct(instance);
        return instance;
    }

    /**
     * Used to unmarshal json using specified type and class binding.
     * In case if json have @type or @class attribute classBinding and type will be replaced with more suitable for this case.
     *
     * @param jsonObject json to unmarshal.
     * @param fieldClass type to use during unmarshal.
     * @param context context.
     * @param holder holder which keeps type or class name and class instance.
     * @param <T> type of class.
     *
     * @return deserialized instance.
     */
    private <T> T unmarshal(JsonObject jsonObject, Class<T> fieldClass, JsonDeserializationContext context, ClassTypeHolder holder) {
        ClassTypeHolder holderToUse = holder;
        Class<T> typeToUse = fieldClass;
        AbstractClassBinding<T> binding = null;

        if (holderToUse == null) {
            holderToUse = getClassFromJson(jsonObject, typeToUse);
            typeToUse = holderToUse.clazz;
            if (holderToUse.alias != null) {
                binding = BindingRegistry.getInstance().getBindingByTypeAlias(holderToUse.alias);
            } else if (holderToUse.className != null) {
                binding = BindingRegistry.getInstance().getBinding(holderToUse.clazz);
            }
        }

        if (binding != null) {
            return unmarshalClass(jsonObject, typeToUse, binding, context);
        }
        return context.deserialize(jsonObject, typeToUse);
    }

    /**
     * Class type holder. Used to hold retrieved from json object target type.
     *
     * @param <T> type.
     */
    private static class ClassTypeHolder<T> {

        /**
         * Alias (if json object contains alias).
         */
        private String alias;
        /**
         * Class name (if json object contains alias).
         */
        private String className;
        /**
         * Class instance created from class name or obtained from binding (by alias).
         */
        private Class<T> clazz;
    }
}
