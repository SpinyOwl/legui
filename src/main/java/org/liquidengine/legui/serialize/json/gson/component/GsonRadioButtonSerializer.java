package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.RadioButtonGroup;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

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
        Integer group = null;
        if (sgm.containsKey(rbg)) {
            group = sgm.get(rbg);
        } else {
            group = sgm.size();
            sgm.put(rbg, group);
        }
        GsonUtil.fill(json)
                .add(TEXT_STATE, textState)
                .add(SELECTED, object.isEnabled())
                .add(GROUP, group)
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
            TextState state = GsonSerializeUtil.deserializeFromJson(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(selected)) object.setSelected(selected.getAsBoolean());
        if (isNotNull(group)) {
            Map<Integer, RadioButtonGroup> drg = context.getDeserializeRadioGroupMap();
            int groupId = group.getAsInt();
            RadioButtonGroup g = null;
            if (drg.containsKey(groupId)) {
                g = drg.get(groupId);
            } else {
                g = new RadioButtonGroup();
                drg.put(groupId, g);
            }
            object.setRadioButtonGroup(g);
        }
    }
}
