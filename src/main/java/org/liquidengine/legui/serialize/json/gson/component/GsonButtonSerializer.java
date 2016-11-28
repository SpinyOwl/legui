package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.TEXT_STATE;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonButtonSerializer extends GsonComponentSerializer<Button> {

    @Override
    protected void jsonSerialize(Button object, JsonObject json) {
        super.jsonSerialize(object, json);

        JsonObject textState = GsonSerializeUtil.serializeToJson(object.getTextState());
        json.add(TEXT_STATE, textState);
    }

    @Override
    protected void jsonDeserialize(JsonObject json, Button object) {
        super.jsonDeserialize(json, object);

        TextState state = (TextState) GsonSerializeUtil.deserializeFromJson(json.get(TEXT_STATE).getAsJsonObject());
        object.getTextState().copy(state);
    }
}
