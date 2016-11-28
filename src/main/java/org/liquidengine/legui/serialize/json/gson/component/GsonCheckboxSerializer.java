package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonBuilder;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonCheckboxSerializer extends GsonComponentSerializer<CheckBox> {

    @Override
    protected void jsonSerialize(CheckBox object, JsonObject json) {
        super.jsonSerialize(object, json);

        JsonObject textState = GsonSerializeUtil.serializeToJson(object.getTextState());
        GsonBuilder.fill(json)
                .add(TEXT_STATE, textState)
                .add(CHECKED, object.isChecked())
        ;
    }

    @Override
    protected void jsonDeserialize(JsonObject json, CheckBox object) {
        super.jsonDeserialize(json, object);

        TextState state = (TextState) GsonSerializeUtil.deserializeFromJson(json.get(TEXT_STATE).getAsJsonObject());
        object.getTextState().copy(state);
        object.setChecked(json.get(CHECKED).getAsBoolean());
    }
}
