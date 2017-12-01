package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.marshal.MarshalUtils.getFieldValue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.BindingUtilities;
import org.liquidengine.legui.binding.model.ClassBinding;

/**
 * @author Aliaksandr_Shcherbin.
 */
public final class JsonMarshaller {

    private JsonMarshaller() {
    }

    public static String marshal(Object object) {
        return new Gson().toJson(marshalToJson(object));
    }

    private static JsonElement marshalToJson(Object object) {
        JsonElement json;
        AbstractClassBinding classBinding = BindingRegistry.getInstance().getBinding(object.getClass());
        if (classBinding != null) {
            json = marshalToJson(object, classBinding);
        } else {
            json = new Gson().toJsonTree(object);
        }
        return json;
    }

    public static <T> String marshal(T object, AbstractClassBinding classBinding) {
        Gson gson = new Gson();
        JsonElement e = marshalToJson(object, classBinding);
        return gson.toJson(e);
    }

    private static JsonElement marshalToJson(Object object, AbstractClassBinding classBinding) {
        JsonObject json = new JsonObject();
        List<Binding> bindings = classBinding.getBindingList();

        for (Binding binding : bindings) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }
//            binding.getFieldValueFromJavaObject(object);
//            binding.getJavaFieldFromStringRepresentation(field);
            Object field = getFieldValue(object, javaFieldName);
            JsonElement value = null;
            if (binding.getLinkedClassBinding() != null) {
                value = marshalToJson(field, binding.getLinkedClassBinding());
            } else {
                value = marshalToJson(field);
            }
            json.add(bindingFieldName, value);
        }
        return json;
    }

    public static <T> T unmarshal(String json, Class<T> clazz) {
        return unmarshal(new JsonParser().parse(json), clazz);
    }

    private static <T> T unmarshal(JsonElement json, Class<T> clazz) {
        AbstractClassBinding binding = BindingRegistry.getInstance().getBinding(clazz);
        if (binding == null) {
            return new Gson().fromJson(json, clazz);
        } else {
            try {
                return unmarshalFromJson(json, clazz, binding);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    private static <T> T unmarshalFromJson(JsonElement jsonElement, Class<T> clazz, AbstractClassBinding classBinding)
        throws IllegalAccessException, InstantiationException {
        T object = clazz.newInstance();
        classBinding.postConstruct(object);
        List<Binding> bindings = classBinding.getBindingList();
        for (Binding binding : bindings) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement element = jsonObject.get(bindingFieldName);

            Class fieldClass = BindingUtilities.classTreeGetFieldType(clazz, javaFieldName);

            Object fieldValue = null;
            if (binding.getLinkedClassBinding() != null) {
                fieldValue = unmarshalFromJson(element, fieldClass, binding.getLinkedClassBinding());
            } else {
                fieldValue = unmarshal(element, fieldClass);
            }
            BindingUtilities.setFieldValue(object, javaFieldName, fieldValue);
        }

        return object;
    }

    public static <T> T unmarshal(String json, Class<T> clazz, AbstractClassBinding binding) {
        T object = null;

        return object;
    }
}
