package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;

import java.util.ArrayList;
import java.util.List;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.COMPONENTS;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Shcherbin Alexander on 12/1/2016.
 */
public class GsonComponentContainerSerializer<T extends ComponentContainer> extends GsonComponentSerializer<T> {
    @Override
    protected void jsonSerialize(T object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        List<Component> components = object.getComponents();
        JsonArray comps = new JsonArray();
        for (Component component : components) {
            comps.add(GsonSerializeUtil.serializeToJson(component, context));
        }
        json.add(COMPONENTS, comps);
    }

    @Override
    protected void jsonDeserialize(JsonObject json, T object, GsonSerializeContext context) {
        super.jsonDeserialize(json, object, context);

        List<Component> componentList = new ArrayList<>();
        JsonElement components = json.get(COMPONENTS);
        if (isNotNull(components) && components.isJsonArray()) {
            JsonArray comps = components.getAsJsonArray();
            for (JsonElement comp : comps) {
                processComponent(context, componentList, comp);
            }
        }
        object.addAllComponents(componentList);
    }

    private void processComponent(GsonSerializeContext context, List<Component> componentList, JsonElement comp) {
        if (isNotNull(comp) && comp.isJsonObject()) {
            Object o = GsonSerializeUtil.deserializeFromJson(comp.getAsJsonObject(), context);
            if (o instanceof Component) {
                componentList.add((Component) o);
            }
        }
    }


}
