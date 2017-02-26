package org.liquidengine.legui.marshal.json.gsonImpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.CHECKED;
import static org.liquidengine.legui.marshal.JsonConstants.TEXT_STATE;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.isNotNull;

/**
 * Created by ShchAlexander on 27.02.2017.
 */
public class GsonCheckBoxMarshaller<T extends CheckBox> extends GsonControllerMarshaller<T> {
    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void jsonMarshal(T object, JsonObject json, GsonMarshalContext context) {
        super.jsonMarshal(object, json, context);
        JsonObject textState = GsonMarshalUtil.marshalToJson(object.getTextState(), context);
        GsonUtil.fill(json)
                .add(TEXT_STATE, textState)
                .add(CHECKED, object.isChecked())
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

        JsonElement textState = json.get(TEXT_STATE);
        JsonElement checked   = json.get(CHECKED);

        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState  state        = GsonMarshalUtil.unmarshal(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(checked)) object.setChecked(checked.getAsBoolean());
    }
}
