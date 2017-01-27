package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.JsonConstants.CHECKED;
import static org.liquidengine.legui.serialize.json.JsonConstants.TEXT_STATE;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonCheckboxSerializer extends GsonComponentSerializer<CheckBox> {

    @Override
    protected void jsonSerialize(CheckBox object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        JsonObject textState = GsonSerializeUtil.serializeToJson(object.getTextState(), context);
        GsonUtil.fill(json)
                .add(TEXT_STATE, textState)
                .add(CHECKED, object.isChecked())
        ;
    }

    @Override
    protected void deserialize(JsonObject json, CheckBox object, GsonSerializeContext context) {
        super.deserialize(json, object, context);

        JsonElement textState = json.get(TEXT_STATE);
        JsonElement checked   = json.get(CHECKED);

        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState  state        = GsonSerializeUtil.deserializeFromJson(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(checked)) object.setChecked(checked.getAsBoolean());
    }
}
