package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.readColor;

/**
 * Marshaller for {@link Slider}.
 */
public class GsonSliderMarshaller<T extends Slider> extends GsonControllerMarshaller<T> {
    /**
     * Reads data from object and puts it to json object.
     *
     * @param object  object to read.
     * @param json    json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);

        GsonUtil.fill(json)
                .add(SLIDER_ACTIVE_COLOR, GsonUtil.createColor(object.getSliderActiveColor()))
                .add(SLIDER_COLOR, GsonUtil.createColor(object.getSliderColor()))
                .add(ORIENTATION, object.getOrientation().name())
                .add(SLIDER_SIZE, object.getSliderSize())
                .add(VALUE, object.getValue())
        ;
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json    json object to read.
     * @param object  object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement sliderActiveColor = json.get(SLIDER_ACTIVE_COLOR);
        JsonElement sliderColor = json.get(SLIDER_COLOR);
        JsonElement orientation = json.get(ORIENTATION);
        JsonElement sliderSize = json.get(SLIDER_SIZE);
        JsonElement value = json.get(VALUE);

        if (isNotNull(sliderActiveColor)) object.setSliderActiveColor(readColor(sliderActiveColor.getAsJsonObject()));
        if (isNotNull(sliderColor)) object.setSliderColor(readColor(sliderColor.getAsJsonObject()));
        if (isNotNull(orientation)) object.setOrientation(Orientation.valueOf(orientation.getAsString()));
        if (isNotNull(sliderSize)) object.setSliderSize(sliderSize.getAsFloat());
        if (isNotNull(value)) object.setValue(value.getAsFloat());
    }
}
