package org.liquidengine.legui.serialize.json.gson.component.optional;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.serialize.json.gson.AbstractGsonSerializer;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;

/**
 * Created by Alexander on 04.12.2016.
 */
public class GsonBorderSerializer extends AbstractGsonSerializer<Border> {
    @Override
    protected void deserialize(JsonObject json, Border object, GsonSerializeContext context) {
    }

    @Override
    protected void jsonSerialize(Border object, JsonObject json, GsonSerializeContext context) {
    }
}
