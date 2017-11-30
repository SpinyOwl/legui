package org.liquidengine.legui.marshal.j;

import static org.liquidengine.legui.marshal.MarshalUtils.getFieldValue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.BindingRegistry;
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

    public static JsonElement marshalToJson(Object object) {
        JsonElement json;
        ClassBinding classBinding = BindingRegistry.getInstance().getBinding(object.getClass());
        if (classBinding != null) {
            json = marshalToJson(object, classBinding);
        } else {
            json = new Gson().toJsonTree(object);
        }
        return json;
    }

    public static String marshal(Object object, ClassBinding classBinding) {
        Gson gson = new Gson();
        JsonElement e = marshalToJson(object, classBinding);
        return gson.toJson(e);
    }

    public static JsonElement marshalToJson(Object object, ClassBinding classBinding) {
        JsonObject json = new JsonObject();
        Map<String, Binding> bindings = classBinding.getBindings();
        for (Entry<String, Binding> b : bindings.entrySet()) {
            Binding binding = b.getValue();
            String bindingField = binding.getBindingField();
            String fieldName = binding.getJavaField();
            Object field = getFieldValue(object, fieldName);
            if (binding.getLinkedClassBinding() != null) {
                json.add(bindingField, marshalToJson(field, binding.getLinkedClassBinding()));
            } else {
                json.add(bindingField, marshalToJson(field));
            }
        }
        return json;
    }
}
