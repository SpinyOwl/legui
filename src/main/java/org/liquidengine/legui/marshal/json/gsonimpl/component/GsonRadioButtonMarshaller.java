package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.RadioButtonGroup;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import java.util.Map;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Created by ShchAlexander on 27.02.2017.
 */
public class GsonRadioButtonMarshaller<T extends RadioButton> extends GsonControllerMarshaller<T> {
    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);

        JsonObject                     textState = GsonMarshalUtil.marshalToJson(object.getTextState(), context);
        RadioButtonGroup               rbg       = object.getRadioButtonGroup();
        Map<RadioButtonGroup, Integer> sgm       = context.getSerializeRadioGroupMap();
        Integer                        group     = null;
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
                .add(ICON_CHECKED, GsonMarshalUtil.marshalToJson(object.getIconChecked(), context))
                .add(ICON_UNCHECKED, GsonMarshalUtil.marshalToJson(object.getIconUnchecked(), context))
        ;
    }

    /**
     * Reads data from json object and puts it to object
     *
     * @param json    json object to read
     * @param object  object to fill
     * @param context marshal context
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement textState     = json.get(TEXT_STATE);
        JsonElement selected      = json.get(SELECTED);
        JsonElement group         = json.get(GROUP);
        JsonElement iconChecked   = json.get(ICON_CHECKED);
        JsonElement iconUnchecked = json.get(ICON_UNCHECKED);

        if (isNotNull(iconChecked)) object.setIconChecked(GsonMarshalUtil.unmarshal(iconChecked.getAsJsonObject(), context));
        if (isNotNull(iconUnchecked)) object.setIconUnchecked(GsonMarshalUtil.unmarshal(iconUnchecked.getAsJsonObject(), context));
        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState  state        = GsonMarshalUtil.unmarshal(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(selected)) object.setChecked(selected.getAsBoolean());
        if (isNotNull(group)) {
            Map<Integer, RadioButtonGroup> drg     = context.getDeserializeRadioGroupMap();
            int                            groupId = group.getAsInt();
            RadioButtonGroup               g       = null;
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
