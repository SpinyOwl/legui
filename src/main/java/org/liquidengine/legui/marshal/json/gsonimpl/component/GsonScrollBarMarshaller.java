package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.readColor;

/**
 * Marshaller for {@link ScrollBar}.
 */
public class GsonScrollBarMarshaller<T extends ScrollBar> extends GsonControllerMarshaller<T> {
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
                .add(ORIENTATION, object.getOrientation().name())
                .add(MIN_VALUE, object.getMinValue())
                .add(MAX_VALUE, object.getMaxValue())
                .add(CUR_VALUE, object.getCurValue())
                .add(VISIBLE_AMOUNT, object.getVisibleAmount())
                .add(ARROWS_ENABLED, object.isArrowsEnabled())
                .add(ARROW_SIZE, object.getArrowSize())
                .add(ARROW_COLOR, GsonUtil.createColor(object.getArrowColor()))
                .add(SCROLL_COLOR, GsonUtil.createColor(object.getScrollColor()))
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

        JsonElement orientation = json.get(ORIENTATION);
        JsonElement minValue = json.get(MIN_VALUE);
        JsonElement maxValue = json.get(MAX_VALUE);
        JsonElement curValue = json.get(CUR_VALUE);
        JsonElement visibleAmount = json.get(VISIBLE_AMOUNT);
        JsonElement arrowsEnabled = json.get(ARROWS_ENABLED);
        JsonElement arrowSize = json.get(ARROW_SIZE);
        JsonElement arrowColor = json.get(ARROW_COLOR);
        JsonElement scrollColor = json.get(SCROLL_COLOR);

        if (isNotNull(orientation)) object.setOrientation(Orientation.valueOf(orientation.getAsString()));
        if (isNotNull(minValue)) object.setMinValue(minValue.getAsFloat());
        if (isNotNull(maxValue)) object.setMaxValue(maxValue.getAsFloat());
        if (isNotNull(curValue)) object.setCurValue(curValue.getAsFloat());
        if (isNotNull(visibleAmount)) object.setVisibleAmount(visibleAmount.getAsFloat());
        if (isNotNull(arrowsEnabled)) object.setArrowsEnabled(arrowsEnabled.getAsBoolean());
        if (isNotNull(arrowSize)) object.setArrowSize(arrowSize.getAsFloat());
        if (isNotNull(arrowColor)) object.setArrowColor(readColor(arrowColor.getAsJsonObject()));
        if (isNotNull(scrollColor)) object.setScrollColor(readColor(scrollColor.getAsJsonObject()));
    }
}
