package org.liquidengine.legui.marshal.json.gsonImpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.isNotNull;

/**
 * Created by ShchAlexander on 26.02.2017.
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
        json.add(BACKGROUND_IMAGE, GsonMarshalUtil.marshalToJson(object.getBackgroundImage(), context));
        json.add(FOCUSED_BACKGROUND_IMAGE, GsonMarshalUtil.marshalToJson(object.getFocusedBackgroundImage(), context));
        json.add(PRESSED_BACKGROUND_IMAGE, GsonMarshalUtil.marshalToJson(object.getPressedBackgroundImage(), context));
        json.add(HOVERED_BACKGROUND_IMAGE, GsonMarshalUtil.marshalToJson(object.getHoveredBackgroundImage(), context));
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

        JsonElement backgroundImage        = json.get(BACKGROUND_IMAGE);
        JsonElement focusedBackgroundImage = json.get(FOCUSED_BACKGROUND_IMAGE);
        JsonElement pressedBackgroundImage = json.get(PRESSED_BACKGROUND_IMAGE);
        JsonElement hoveredBackgroundImage = json.get(HOVERED_BACKGROUND_IMAGE);

        if (isNotNull(backgroundImage)) object.setBackgroundImage(GsonMarshalUtil.unmarshal(backgroundImage.getAsJsonObject(), context));
        if (isNotNull(focusedBackgroundImage)) object.setFocusedBackgroundImage(GsonMarshalUtil.unmarshal(focusedBackgroundImage.getAsJsonObject(), context));
        if (isNotNull(pressedBackgroundImage)) object.setPressedBackgroundImage(GsonMarshalUtil.unmarshal(pressedBackgroundImage.getAsJsonObject(), context));
        if (isNotNull(hoveredBackgroundImage)) object.setHoveredBackgroundImage(GsonMarshalUtil.unmarshal(hoveredBackgroundImage.getAsJsonObject(), context));

        JsonElement textState = json.get(TEXT_STATE);
        if (isNotNull(textState)) {
            JsonObject asJsonObject = textState.getAsJsonObject();
            TextState  state        = GsonMarshalUtil.unmarshal(asJsonObject, context);
            object.getTextState().copy(state);
        }

    }
}
