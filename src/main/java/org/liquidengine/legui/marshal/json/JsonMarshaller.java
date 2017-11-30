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
        ClassBinding classBinding = BindingRegistry.getInstance().getBinding(object.getClass());
        if (classBinding != null) {
            json = marshalToJson(object, classBinding);
        } else {
            json = new Gson().toJsonTree(object);
        }
        return json;
    }

    public static <T> String marshal(T object, ClassBinding classBinding) {
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
            if (binding.getLinkedClassBinding() != null) {
                json.add(bindingFieldName, marshalToJson(field, binding.getLinkedClassBinding()));
            } else {
                json.add(bindingFieldName, marshalToJson(field));
            }
        }
        return json;
    }

    public static <T> T unmarshal(String json, Class<T> clazz) {
        ClassBinding binding = BindingRegistry.getInstance().getBinding(clazz);
        if (binding == null) {
            return new Gson().fromJson(json, clazz);
        } else {
            try {
                JsonElement jsonElement = new JsonParser().parse(json);
                return unmarshalFromJson(jsonElement, clazz, binding);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static <T> T unmarshalFromJson(JsonElement jsonElement, Class<T> clazz, ClassBinding classBinding)
        throws IllegalAccessException, InstantiationException {
        T object = clazz.newInstance();
        List<Binding> bindings = classBinding.getBindingList();
        for (Binding binding : bindings) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            JsonObject jsonO = jsonElement.getAsJsonObject();
            JsonElement element = jsonO.get(bindingFieldName);

        }

        return object;
    }

    public static <T> T unmarshal(String json, Class<T> clazz, ClassBinding binding) {
        T object = null;

        return object;
    }
}
