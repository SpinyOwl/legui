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
public class JsonMarshaller2 {

    public static String marshal(Object o) {
        return createGson().toJson(o);
    }

    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        Map<Class, AbstractClassBinding> defaultBindings = BindingRegistry.getInstance().getDefaultBindingMap();
        for (Entry<Class, AbstractClassBinding> bindingEntry : defaultBindings.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(bindingEntry.getKey(), new BindingSerializer(bindingEntry.getValue()));
            gsonBuilder.registerTypeHierarchyAdapter(bindingEntry.getKey(), new BindingDeserializer(bindingEntry.getValue()));
        }

        return gsonBuilder.create();
    }

    public static <T> T unmarshal(String json, Class<T> tClass) {
        return createGson().fromJson(json, tClass);
    }
}
