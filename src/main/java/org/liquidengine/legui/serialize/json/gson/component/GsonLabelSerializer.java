package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.TEXT_STATE;

/**
 * Created by Alexander on 26.11.2016.
 */
public class GsonLabelSerializer extends GsonComponentSerializer<Label> {

    @Override
    protected void jsonSerialize(Label object, JsonObject json) {
        super.jsonSerialize(object, json);

        JsonObject textState = GsonSerializeUtil.serializeToJson(object.getTextState());
        json.add(TEXT_STATE, textState);
    }

    @Override
    protected void jsonDeserialize(JsonObject json, Label object) {
        super.jsonDeserialize(json, object);

        TextState state = (TextState) GsonSerializeUtil.deserializeFromJson(json.get(TEXT_STATE).getAsJsonObject());
        object.getTextState().copy(state);
    }
}
