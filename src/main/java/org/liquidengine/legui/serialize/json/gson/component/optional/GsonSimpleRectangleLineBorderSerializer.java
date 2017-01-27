package org.liquidengine.legui.serialize.json.gson.component.optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.serialize.json.gson.AbstractGsonSerializer;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.JsonConstants.BORDER_COLOR;
import static org.liquidengine.legui.serialize.json.JsonConstants.THICKNESS;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 04.12.2016.
 */
public class GsonSimpleRectangleLineBorderSerializer extends AbstractGsonSerializer<SimpleRectangleLineBorder> {

    @Override
    protected void jsonSerialize(SimpleRectangleLineBorder object, JsonObject json, GsonSerializeContext context) {
        GsonUtil.fill(json)
                .add(THICKNESS, object.getThickness())
                .add(BORDER_COLOR, GsonUtil.createColor(object.getBorderColor()))
        ;
    }

    @Override
    protected void deserialize(JsonObject json, SimpleRectangleLineBorder object, GsonSerializeContext context) {
        JsonElement thickness   = json.get(THICKNESS);
        JsonElement borderColor = json.get(BORDER_COLOR);
        if (isNotNull(thickness)) object.setThickness(thickness.getAsFloat());
        if (isNotNull(borderColor)) object.setBorderColor(GsonUtil.readColor(borderColor.getAsJsonObject()));
    }
}
