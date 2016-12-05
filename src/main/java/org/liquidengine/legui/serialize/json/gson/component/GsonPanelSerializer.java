package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;

/**
 * Created by Shcherbin Alexander on 12/1/2016.
 */
public class GsonPanelSerializer extends GsonComponentContainerSerializer<Panel> {
    @Override
    protected void jsonSerialize(Panel object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);
    }

    @Override
    protected void deserialize(JsonObject json, Panel object, GsonSerializeContext context) {
        super.deserialize(json, object, context);
    }
}
