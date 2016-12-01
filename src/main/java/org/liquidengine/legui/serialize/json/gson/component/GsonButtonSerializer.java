package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.TEXT_STATE;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonButtonSerializer extends GsonComponentSerializer<Button> {

    @Override
    protected void jsonSerialize(Button object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        JsonObject textState = GsonSerializeUtil.serializeToJson(object.getTextState(), context);
        json.add(TEXT_STATE, textState);
    }

    @Override
    protected void jsonDeserialize(JsonObject json, Button object, GsonSerializeContext context) {
        super.jsonDeserialize(json, object, context);

        JsonElement textState = json.get(TEXT_STATE);
        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState state = (TextState) GsonSerializeUtil.deserializeFromJson(asJsonObject, context);
            object.getTextState().copy(state);
        }
    }
}
