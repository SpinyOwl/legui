package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.JsonConstants.CHECKED;
import static org.liquidengine.legui.marshal.JsonConstants.ICON_CHECKED;
import static org.liquidengine.legui.marshal.JsonConstants.ICON_UNCHECKED;
import static org.liquidengine.legui.marshal.JsonConstants.TEXT_STATE;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

/**
 * Marshaller for {@link CheckBox}.
 */
public class GsonCheckBoxMarshaller<T extends CheckBox> extends GsonControllerMarshaller<T> {

    /**
     * Reads data from object and puts it to json object.
     *
     * @param object object to read.
     * @param json json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);
        JsonObject textState = GsonMarshalUtil.marshalToJson(object.getTextState(), context);
        GsonUtil.fill(json)
            .add(TEXT_STATE, textState)
            .add(CHECKED, object.isChecked())
            .add(ICON_CHECKED, GsonMarshalUtil.marshalToJson(object.getIconChecked(), context))
            .add(ICON_UNCHECKED, GsonMarshalUtil.marshalToJson(object.getIconUnchecked(), context))
        ;
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json json object to read.
     * @param object object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement textState = json.get(TEXT_STATE);
        JsonElement checked = json.get(CHECKED);
        JsonElement iconChecked = json.get(ICON_CHECKED);
        JsonElement iconUnchecked = json.get(ICON_UNCHECKED);

        if (isNotNull(iconChecked)) {
            object.setIconChecked(GsonMarshalUtil.unmarshal(iconChecked.getAsJsonObject(), context));
        }
        if (isNotNull(iconUnchecked)) {
            object.setIconUnchecked(GsonMarshalUtil.unmarshal(iconUnchecked.getAsJsonObject(), context));
        }
        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState state = GsonMarshalUtil.unmarshal(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(checked)) {
            object.setChecked(checked.getAsBoolean());
        }
    }
}
