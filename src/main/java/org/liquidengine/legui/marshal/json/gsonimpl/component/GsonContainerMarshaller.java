package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

import java.util.ArrayList;
import java.util.List;

import static org.liquidengine.legui.marshal.JsonConstants.COMPONENTS;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Marshaller for {@link Container}.
 */
public class GsonContainerMarshaller<T extends Container> extends GsonControllerMarshaller<T> {
    /**
     * Reads data from object and puts it to json object.
     *
     * @param object  object to read.
     * @param json    json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);
        List<Component> components = object.getChilds();
        JsonArray       comps      = new JsonArray();
        for (Component component : components) {
            comps.add(GsonMarshalUtil.marshalToJson(component, context));
        }
        json.add(COMPONENTS, comps);
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json    json object to read.
     * @param object  object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        List<Component> componentList = new ArrayList<>();
        JsonElement     components    = json.get(COMPONENTS);
        if (isNotNull(components) && components.isJsonArray()) {
            JsonArray comps = components.getAsJsonArray();
            for (JsonElement comp : comps) {
                processComponent(context, componentList, comp);
            }
        }
        object.addAll(componentList);
    }

    private void processComponent(GsonMarshalContext context, List<Component> componentList, JsonElement comp) {
        if (isNotNull(comp) && comp.isJsonObject()) {
            Object o = GsonMarshalUtil.unmarshal(comp.getAsJsonObject(), context);
            if (o instanceof Component) {
                componentList.add((Component) o);
            }
        }
    }
}
