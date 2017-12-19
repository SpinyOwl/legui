//package org.liquidengine.legui.marshal.json;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.TypeAdapter;
//import com.google.gson.internal.Streams;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import org.liquidengine.legui.binding.BindingRegistry;
//import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
//import org.liquidengine.legui.binding.model.AbstractClassBinding;
//import org.liquidengine.legui.binding.model.Binding;
//import org.liquidengine.legui.binding.model.BindingUtilities;
//
///**
// * @author Aliaksandr_Shcherbin.
// */
//public class BindingTypeAdapter<T> extends TypeAdapter<T> {
//
//
//    private AbstractClassBinding<T> classBinding;
//    private TypeToken<T> type;
//    private Gson gson;
//
//    public BindingTypeAdapter(AbstractClassBinding<T> classBinding, TypeToken<T> type, Gson gson) {
//        this.classBinding = classBinding;
//        this.type = type;
//        this.gson = gson;
//    }
//
//    /**
//     * Writes one JSON value (an array, object, string, number, boolean or null)
//     * for {@code value}.
//     *
//     * @param out
//     * @param objectToMarshal the Java object to write. May be null.
//     */
//    @Override
//    public void write(JsonWriter out, T objectToMarshal) throws IOException {
//        Streams.write(marshalToJson(objectToMarshal, classBinding), out);
//    }
//
//
//
//    /**
//     * Reads one JSON value (an array, object, string, number, boolean or null)
//     * and converts it to a Java object. Returns the converted object.
//     *
//     * @param in
//     *
//     * @return the converted Java object. May be null.
//     */
//    @Override
//    public T read(JsonReader in) throws IOException {
//        JsonElement json = Streams.parse(in);
//        if (json == null || json.isJsonNull()) {
//            return null;
//        } else if (json.isJsonObject()) {
//            System.err.println("OBJECT");
//            return unmarshall(in, json.getAsJsonObject(), classBinding);
//        } else {
//            if (json.isJsonPrimitive()) {
//                System.err.println("PRIMITIVE NOT REGISTERED");
//            } else if (json.isJsonArray()) {
//                System.err.println("ARRAY NOT REGISTERED");
//            } else {
//                System.err.println("UNKNOWN");
//            }
//            TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(BindingTypeAdapterFactory.getInstance(), type);
//            if (delegateAdapter != null) {
//                return delegateAdapter.read(in);
//            }
//        }
//        System.err.println("NOTHING FOUND");
//        return null;
//    }
//
//    private T unmarshall(JsonReader in, JsonObject json, AbstractClassBinding<T> classBinding) throws IOException {
//        Class<T> clazz = null;
//        JsonElement typeProp = json.remove(TYPE_PROPERTY);
//        if (typeProp != null && !typeProp.isJsonNull() && typeProp.isJsonPrimitive()) {
//            String typeAlias = typeProp.getAsString();
//            AbstractClassBinding binding = BindingRegistry.getInstance().getBindingByTypeAlias(typeAlias);
//            if (binding != null) {
//                clazz = binding.getBindingForType();
//            }
//        }
//        if (clazz == null) {
//            JsonElement classProp = json.remove(CLASS_PROPERTY);
//            if (classProp != null && !classProp.isJsonNull() && classProp.isJsonPrimitive()) {
//                String className = classProp.getAsString();
//                try {
//                    clazz = (Class<T>) Class.forName(className);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (clazz == null) {
//            clazz = (Class<T>) classBinding.getBindingForType();
//        }
//        if (clazz == null) {
//            TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(BindingTypeAdapterFactory.getInstance(), type);
//            if (delegateAdapter != null) {
//                return delegateAdapter.read(in);
//            }
//            System.err.println("Can't find binding for type.");
//            return null;
//        }
//        return unmarshall(in, json, clazz, classBinding);
//    }
//
//    private T unmarshall(JsonReader in, JsonObject json, Class<T> clazz, AbstractClassBinding<T> classBinding) throws IOException {
//        if (json == null || json.isJsonNull()) {
//            return null;
//        }
//        T instance = classBinding.createInstance(clazz);
//        List<Binding> bindings = classBinding.getBindingList();
//
//        for (Binding binding : bindings) {
//            String javaFieldName = binding.getJavaFieldName();
//            String bindingFieldName = binding.getBindingFieldName();
//            if (bindingFieldName == null) {
//                bindingFieldName = javaFieldName;
//            }
//
//            JsonElement element = json.get(bindingFieldName);
//            if (element == null || element.isJsonNull()) {
//                continue;
//            }
//
//            // retrieving class for field value
//            Class fieldClass = BindingUtilities.classTreeGetFieldClass(clazz, javaFieldName);
//            AbstractFieldAccessor fieldAccessor = binding.getFieldAccessor();
//            if (fieldClass == null && fieldAccessor != null) {
//                Type fieldType = fieldAccessor.getFieldType();
//
//                if (fieldType instanceof Class) {
//                    fieldClass = (Class) fieldType;
//                } else {
//                    // for now skipping if can't get field class.
//                    continue;
//                }
//            }
//
//            Object fieldValue = null;
//            if (Collection.class.isAssignableFrom(fieldClass) || fieldClass.isArray() || Map.class.isAssignableFrom(fieldClass)) {
//                fieldValue = gson.fromJson(element, BindingUtilities.classTreeGetFieldType(clazz, javaFieldName));
//            } else {
//                if (binding.getLinkedClassBinding() != null) {
//                    if (element.isJsonObject()) {
//                        fieldValue = unmarshall(in, element.getAsJsonObject(), fieldClass, binding.getLinkedClassBinding());
//                    } else {
//                        System.err.println("NOT A JSON OBJECT");
//                    }
//                } else if (binding.getClassConverter() != null) {
//                    fieldValue = binding.getClassConverter().convertToJava(element.getAsString());
//                } else {
//                    if (element.isJsonObject() && element.getAsJsonObject().has(CLASS_PROPERTY)) {
//                        try {
//                            fieldClass = Class.forName(element.getAsJsonObject().get(CLASS_PROPERTY).getAsString());
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (!fieldClass.isInterface()) {
//                        fieldValue = gson.fromJson(element, fieldAccessor == null ? fieldClass : fieldAccessor.getFieldType());
//                    }
//                }
//            }
//
//            if (fieldAccessor != null) {
//                fieldAccessor.setFieldValue(instance, fieldValue);
//            } else {
//                BindingUtilities.setFieldValue(instance, javaFieldName, fieldValue);
//            }
//        }
//
//        classBinding.postConstruct(instance);
//        return instance;
//    }
//}
