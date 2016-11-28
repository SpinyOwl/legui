package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.serialize.json.gson.GsonBuilder;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonConstants.A;
import static org.liquidengine.legui.serialize.json.gson.GsonConstants.B;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonProgressBarSerializer extends GsonComponentSerializer<ProgressBar> {

    @Override
    protected void jsonSerialize(ProgressBar object, JsonObject json) {
        super.jsonSerialize(object, json);

        GsonBuilder.fill(json)
                .add(VALUE, object.getValue())
                .add(PROGRESS_COLOR, GsonBuilder.create()
                        .add(R, object.getBackgroundColor().x)
                        .add(G, object.getBackgroundColor().y)
                        .add(B, object.getBackgroundColor().z)
                        .add(A, object.getBackgroundColor().w)
                        .get()
                )
        ;
    }

    @Override
    protected void jsonDeserialize(JsonObject json, ProgressBar object) {
        super.jsonDeserialize(json, object);

        object.setValue(json.get(VALUE).getAsFloat());
        JsonObject pc = json.getAsJsonObject(PROGRESS_COLOR);
        object.setBackgroundColor(pc.get(R).getAsFloat(), pc.get(G).getAsFloat(), pc.get(B).getAsFloat(), pc.get(A).getAsFloat());
    }
}
