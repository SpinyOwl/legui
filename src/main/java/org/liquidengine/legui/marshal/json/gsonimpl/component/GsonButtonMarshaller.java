package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Marshaller for {@link Button}.
 */
public class GsonButtonMarshaller<T extends Button> extends GsonControllerMarshaller<T> {
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
        json.add(TEXT_STATE, textState);
        json.add(BACKGROUND_ICON, GsonMarshalUtil.marshalToJson(object.getBackgroundIcon(), context));
        json.add(FOCUSED_BACKGROUND_ICON, GsonMarshalUtil.marshalToJson(object.getFocusedBackgroundIcon(), context));
        json.add(PRESSED_BACKGROUND_ICON, GsonMarshalUtil.marshalToJson(object.getPressedBackgroundIcon(), context));
        json.add(HOVERED_BACKGROUND_ICON, GsonMarshalUtil.marshalToJson(object.getHoveredBackgroundIcon(), context));
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

        JsonElement backgroundIcon        = json.get(BACKGROUND_ICON);
        JsonElement focusedBackgroundIcon = json.get(FOCUSED_BACKGROUND_ICON);
        JsonElement pressedBackgroundIcon = json.get(PRESSED_BACKGROUND_ICON);
        JsonElement hoveredBackgroundIcon = json.get(HOVERED_BACKGROUND_ICON);

        if (isNotNull(backgroundIcon)) object.setBackgroundIcon(GsonMarshalUtil.unmarshal(backgroundIcon.getAsJsonObject(), context));
        if (isNotNull(focusedBackgroundIcon)) object.setFocusedBackgroundIcon(GsonMarshalUtil.unmarshal(focusedBackgroundIcon.getAsJsonObject(), context));
        if (isNotNull(pressedBackgroundIcon)) object.setPressedBackgroundIcon(GsonMarshalUtil.unmarshal(pressedBackgroundIcon.getAsJsonObject(), context));
        if (isNotNull(hoveredBackgroundIcon)) object.setHoveredBackgroundIcon(GsonMarshalUtil.unmarshal(hoveredBackgroundIcon.getAsJsonObject(), context));

        JsonElement textState = json.get(TEXT_STATE);
        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState  state        = GsonMarshalUtil.unmarshal(asJsonObject, context);
            object.getTextState().copy(state);
        }

    }
}
