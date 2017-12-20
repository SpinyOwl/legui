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
import java.util.Map;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.BindingUtilities;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class BindingDeserializer<T> implements JsonDeserializer<T> {

    private AbstractClassBinding<T> classBinding;

    public BindingDeserializer(AbstractClassBinding<T> classBinding) {
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
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AbstractClassBinding<T> classBinding = this.classBinding;

        if (json.isJsonObject() && typeOfT instanceof Class) {
            ClassTypeHolder classTypeHolder = getClassFromJson(json.getAsJsonObject(), (Class) typeOfT);
            Class<T> classFromJson = classTypeHolder.clazz;
            if (typeOfT != classFromJson) {
                typeOfT = classFromJson;
                classBinding = getClassBinding(json.getAsJsonObject(), classFromJson, classBinding, classTypeHolder);
            }
            return unmarshal(json, typeOfT, classBinding, context, classTypeHolder);
        }

        return unmarshal(json, typeOfT, classBinding, context, null);
    }

    private T unmarshal(JsonElement json, Type typeOfT, AbstractClassBinding<T> classBinding, JsonDeserializationContext context, ClassTypeHolder holder) {
        if (json == null || json.isJsonNull()) {
            return null;
        }

        if (typeOfT instanceof Class) {
            return unmarshalClass(json, (Class<T>) typeOfT, classBinding, context, holder);
        }

        Type typeToUse = typeOfT;
        AbstractClassBinding<T> binding = null;
        if (json.isJsonObject()) {
            JsonObject o = json.getAsJsonObject();
            if (holder == null) {
                holder = getClassFromJson(o, null);
                typeToUse = holder.clazz == null ? typeToUse : holder.clazz;
                if (holder.alias != null) {
                    binding = BindingRegistry.getInstance().getBindingByTypeAlias(holder.alias);
                } else if (holder.className != null) {
                    binding = BindingRegistry.getInstance().getBinding(holder.clazz);
                }
            }
        }

        if (binding != null && typeToUse instanceof Class) {
            Class<T> aClass = (Class<T>) typeToUse;
            return unmarshal(json, aClass, binding, context, holder);
        }

        return context.deserialize(json, typeOfT);
    }

    private <T> T unmarshalClass(JsonElement jsonElement, Class<T> targetClass, AbstractClassBinding<T> classBinding, JsonDeserializationContext context,
        ClassTypeHolder holder) {
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
                if(element.isJsonObject()){
                    element.getAsJsonObject().remove(TYPE_PROPERTY.value());
                    element.getAsJsonObject().remove(CLASS_PROPERTY.value());
                }
                fieldValue = context.deserialize(element, BindingUtilities.classTreeGetFieldType(targetClass, javaFieldName));
            } else {

                if (binding.getLinkedClassBinding() != null) {
                    if (element.isJsonObject()) {
                        fieldValue = unmarshal(element, fieldClass, binding.getLinkedClassBinding(), context, holder);
                    } else {
                        System.err.println("NOT A JSON OBJECT");
                    }
                } else if (binding.getClassConverter() != null) {
                    fieldValue = binding.getClassConverter().convertToJava(element.getAsString());
                } else {
                    if (element.isJsonObject()) {
                        fieldValue = unmarshal(element.getAsJsonObject(), fieldClass, context, holder);
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

            if (fieldAccessor != null) {
                fieldAccessor.setFieldValue(instance, fieldValue);
            } else {
                BindingUtilities.setFieldValue(instance, javaFieldName, fieldValue);
            }
        }

        classBinding.postConstruct(instance);
        return instance;
    }

    private <T> AbstractClassBinding<T> getClassBinding(JsonObject json, Class<T> targetClass, AbstractClassBinding current,
        ClassTypeHolder classTypeHolder) {
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

    private <F> ClassTypeHolder<F> getClassFromJson(JsonObject json, Class<F> targetClass) {
        ClassTypeHolder holder = new ClassTypeHolder();
        holder.clazz = targetClass;

        // try to unmarshal by type alias.
        JsonElement typeProp = json.remove(TYPE_PROPERTY.value());
        if (typeProp != null && !typeProp.isJsonNull()) {
            holder.alias = typeProp.getAsString();
            AbstractClassBinding<T> binding = BindingRegistry.getInstance().<T>getBindingByTypeAlias(holder.alias);
            if (binding != null) {
                holder.clazz = binding.getBindingForType();
            }
        } else {
            // try unmarshal by class name.
            JsonElement classProp = json.remove(CLASS_PROPERTY.value());
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

    private <T> T unmarshal(JsonObject jsonObject, Class<T> fieldClass, JsonDeserializationContext context, ClassTypeHolder holder) {
        Class<T> typeToUse = fieldClass;
        AbstractClassBinding<T> binding = null;

        if (holder == null) {
            holder = getClassFromJson(jsonObject, typeToUse);
            typeToUse = holder.clazz;
            if (holder.alias != null) {
                binding = BindingRegistry.getInstance().getBindingByTypeAlias(holder.alias);
            } else if (holder.className != null) {
                binding = BindingRegistry.getInstance().getBinding(holder.clazz);
            }
        }

        if (binding != null) {
            return unmarshalClass(jsonObject, typeToUse, binding, context, holder);
        }
        return context.deserialize(jsonObject, typeToUse);
    }

    private static class ClassTypeHolder<T> {

        private String alias;
        private String className;

        private Class<T> clazz;
    }
}
