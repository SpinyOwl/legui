package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.JsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 01.12.2016.
 */
public class GsonTextAreaSerializer extends GsonComponentSerializer<TextArea> {
    @Override
    protected void jsonSerialize(TextArea object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);
        JsonObject textState = GsonSerializeUtil.serializeToJson(object.getTextState(), context);
        GsonUtil.fill(json)
                .add(EDITABLE, object.isEditable())
                .add(SELECTION_COLOR, GsonUtil.createColor(object.getSelectionColor()))
                .add(TEXT_STATE, textState)
        ;
    }

    @Override
    protected void deserialize(JsonObject json, TextArea object, GsonSerializeContext context) {
        super.deserialize(json, object, context);

        JsonElement editable       = json.get(EDITABLE);
        JsonElement selectionColor = json.get(SELECTION_COLOR);
        JsonElement textState      = json.get(TEXT_STATE);

        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState  state        = GsonSerializeUtil.deserializeFromJson(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(editable)) object.setEditable(editable.getAsBoolean());
        if (isNotNull(selectionColor)) object.setSelectionColor(GsonUtil.readColor(selectionColor.getAsJsonObject()));
    }
}
