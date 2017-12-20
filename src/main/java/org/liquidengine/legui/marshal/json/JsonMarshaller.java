package org.liquidengine.legui.marshal.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.model.AbstractClassBinding;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class JsonMarshaller {

    /**
     * Used to marshal object to json using bindings for special marshalling cases.
     *
     * @param o object to marshal.
     *
     * @return json in string representation.
     */
    public static String marshal(Object o) {
        return createGson().toJson(o);
    }

    /**
     * Used to create Gson instance with type adapters created from bindings.
     *
     * @return gson instance.
     */
    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        Map<Class, AbstractClassBinding> defaultBindings = BindingRegistry.getInstance().getDefaultBindingMap();
        for (Entry<Class, AbstractClassBinding> bindingEntry : defaultBindings.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(bindingEntry.getKey(), new BindingBasedJsonSerializer(bindingEntry.getValue()));
            gsonBuilder.registerTypeHierarchyAdapter(bindingEntry.getKey(), new BindingBasedJsonDeserializer(bindingEntry.getValue()));
        }

        return gsonBuilder.create();
    }

    /**
     * Used to unmarshal json to specified type using bindings for special marshalling cases.
     *
     * @param json json to unmarshal.
     * @param tClass target class.
     * @param <T> class type.
     *
     * @return unmarshalled instance from json.
     */
    public static <T> T unmarshal(String json, Class<T> tClass) {
        return createGson().fromJson(json, tClass);
    }
}
