package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonProgressBarSerializer extends GsonComponentSerializer<ProgressBar> {

    @Override
    protected void jsonSerialize(ProgressBar object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        GsonUtil.fill(json)
                .add(VALUE, object.getValue())
                .add(PROGRESS_COLOR, GsonUtil.create()
                        .add(R, object.getBackgroundColor().x)
                        .add(G, object.getBackgroundColor().y)
                        .add(B, object.getBackgroundColor().z)
                        .add(A, object.getBackgroundColor().w)
                        .get()
                )
        ;
    }

    @Override
    protected void jsonDeserialize(JsonObject json, ProgressBar object, GsonSerializeContext context) {
        super.jsonDeserialize(json, object, context);

        JsonElement value = json.get(VALUE);
        JsonObject pc = json.getAsJsonObject(PROGRESS_COLOR);

        if (isNotNull(value)) object.setValue(value.getAsFloat());
        if (isNotNull(pc)) object.setBackgroundColor(GsonUtil.readColor(pc));
    }
}
