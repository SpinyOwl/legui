package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.RadioButtonGroup;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;

import java.util.Map;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 28.11.2016.
 */
public class GsonRadioButtonSerializer extends GsonComponentSerializer<RadioButton> {

    @Override
    protected void jsonSerialize(RadioButton object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        JsonObject textState = GsonSerializeUtil.serializeToJson(object.getTextState(), context);
        RadioButtonGroup rbg = object.getRadioButtonGroup();
        Map<RadioButtonGroup, Integer> sgm = context.getSerializeRadioGroupMap();
        GsonUtil.fill(json)
                .add(TEXT_STATE, textState)
                .add(SELECTED, object.isEnabled())
                .add(GROUP, sgm.containsKey(rbg) ? sgm.get(rbg) : sgm.put(rbg, sgm.size()))
        ;
    }

    @Override
    protected void jsonDeserialize(JsonObject json, RadioButton object, GsonSerializeContext context) {
        super.jsonDeserialize(json, object, context);

        JsonElement textState = json.get(TEXT_STATE);
        JsonElement selected = json.get(SELECTED);
        JsonElement group = json.get(GROUP);

        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState state = (TextState) GsonSerializeUtil.deserializeFromJson(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(selected)) object.setSelected(selected.getAsBoolean());
        if (isNotNull(group)) {
            Map<Integer, RadioButtonGroup> drg = context.getDeserializeRadioGroupMap();
            int groupId = group.getAsInt();
            object.setRadioButtonGroup(drg.containsKey(groupId) ? drg.get(groupId) : drg.put(groupId, new RadioButtonGroup()));
        }
    }
}
