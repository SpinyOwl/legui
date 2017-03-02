package org.liquidengine.legui.marshal.json.gsonImpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.isNotNull;

/**
 * Created by ShchAlexander on 27.02.2017.
 */
public class GsonTextAreaMarshaller<T extends TextArea> extends GsonControllerMarshaller<T> {
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

        JsonObject textState = GsonMarshalUtil.marshalToJson(object.getTextState(), context);
        GsonUtil.fill(json)
                .add(EDITABLE, object.isEditable())
                .add(SELECTION_COLOR, GsonUtil.createColor(object.getSelectionColor()))
                .add(TEXT_STATE, textState)
                .add(CARET_POSITION, object.getCaretPosition())
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

        JsonElement editable       = json.get(EDITABLE);
        JsonElement selectionColor = json.get(SELECTION_COLOR);
        JsonElement textState      = json.get(TEXT_STATE);
        JsonElement caretPosition  = json.get(CARET_POSITION);

        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState  state        = GsonMarshalUtil.unmarshal(asJsonObject, context);
            object.getTextState().copy(state);
        }
        if (isNotNull(editable)) object.setEditable(editable.getAsBoolean());
        if (isNotNull(selectionColor)) object.setSelectionColor(GsonUtil.readColor(selectionColor.getAsJsonObject()));
        if (isNotNull(caretPosition)) object.setCaretPosition(caretPosition.getAsInt());
    }
}
