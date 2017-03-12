package org.liquidengine.legui.marshal.json.gsonImpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.isNotNull;

/**
 * Created by Aliaksandr_Shcherbin on 3/3/2017.
 */
public class GsonToggleButtonMarshaller<T extends ToggleButton> extends GsonButtonMarshaller<T> {
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

        GsonUtil.fill(json)
                .add(TOGGLED, object.isToggled())
                .add(TOGGLEDED_BACKGROUND_ICON, GsonMarshalUtil.marshalToJson(object.getTogglededBackgroundIcon(), context))
                .add(TOGGLED_BACKGROUND_COLOR, GsonUtil.createColor(object.getToggledBackgroundColor()))
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

        JsonElement toggled = json.get(TOGGLED);
        JsonElement tbImage = json.get(TOGGLEDED_BACKGROUND_ICON);
        JsonElement tbColor = json.get(TOGGLED_BACKGROUND_COLOR);

        if (isNotNull(toggled)) object.setToggled(toggled.getAsBoolean());
        if (isNotNull(tbImage)) object.setTogglededBackgroundIcon(GsonMarshalUtil.unmarshal(tbImage.getAsJsonObject(), context));
        if (isNotNull(tbColor)) object.setToggledBackgroundColor(GsonUtil.readColor(tbColor.getAsJsonObject()));
    }
}
