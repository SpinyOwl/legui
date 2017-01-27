package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.JsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.readColor;

/**
 * Created by Alexander on 30.11.2016.
 */
public class GsonSliderSerializer extends GsonComponentSerializer<Slider> {
    @Override
    protected void jsonSerialize(Slider object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        GsonUtil.fill(json)
                .add(SLIDER_ACTIVE_COLOR, GsonUtil.create()
                        .add(R, object.getSliderActiveColor().x)
                        .add(G, object.getSliderActiveColor().y)
                        .add(B, object.getSliderActiveColor().z)
                        .add(A, object.getSliderActiveColor().w)
                        .get()
                )
                .add(SLIDER_COLOR, GsonUtil.create()
                        .add(R, object.getSliderColor().x)
                        .add(G, object.getSliderColor().y)
                        .add(B, object.getSliderColor().z)
                        .add(A, object.getSliderColor().w)
                        .get()
                )
                .add(ORIENTATION, object.getOrientation().name())
                .add(SLIDER_SIZE, object.getSliderSize())
                .add(VALUE, object.getValue())
        ;
    }

    @Override
    protected void deserialize(JsonObject json, Slider object, GsonSerializeContext context) {
        super.deserialize(json, object, context);

        JsonElement sliderActiveColor = json.get(SLIDER_ACTIVE_COLOR);
        JsonElement sliderColor       = json.get(SLIDER_COLOR);
        JsonElement orientation       = json.get(ORIENTATION);
        JsonElement sliderSize        = json.get(SLIDER_SIZE);
        JsonElement value             = json.get(VALUE);

        if (isNotNull(sliderActiveColor)) object.setSliderActiveColor(readColor(sliderActiveColor.getAsJsonObject()));
        if (isNotNull(sliderColor)) object.setSliderColor(readColor(sliderColor.getAsJsonObject()));
        if (isNotNull(orientation)) object.setOrientation(Orientation.valueOf(orientation.getAsString()));
        if (isNotNull(sliderSize)) object.setSliderSize(sliderSize.getAsFloat());
        if (isNotNull(value)) object.setValue(value.getAsFloat());

    }
}
