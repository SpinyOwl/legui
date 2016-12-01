package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.readColor;

/**
 * Created by Alexander on 28.11.2016.
 */
public class GsonScrollBarSerializer extends GsonComponentSerializer<ScrollBar> {
    @Override
    protected void jsonSerialize(ScrollBar object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        GsonUtil.fill(json)
                .add(ORIENTATION, object.getOrientation().name())
                .add(MIN_VALUE, object.getMinValue())
                .add(MAX_VALUE, object.getMaxValue())
                .add(CUR_VALUE, object.getCurValue())
                .add(VISIBLE_AMOUNT, object.getVisibleAmount())
                .add(ARROWS_ENABLED, object.isArrowsEnabled())
                .add(ARROW_SIZE, object.getArrowSize())
                .add(ARROW_COLOR, GsonUtil.create()
                        .add(R, object.getArrowColor().x)
                        .add(G, object.getArrowColor().y)
                        .add(B, object.getArrowColor().z)
                        .add(A, object.getArrowColor().w)
                        .get()
                )
                .add(SCROLL_COLOR, GsonUtil.create()
                        .add(R, object.getScrollColor().x)
                        .add(G, object.getScrollColor().y)
                        .add(B, object.getScrollColor().z)
                        .add(A, object.getScrollColor().w)
                        .get()
                )
        ;
    }

    @Override
    protected void jsonDeserialize(JsonObject json, ScrollBar object, GsonSerializeContext context) {
        super.jsonDeserialize(json, object, context);

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
        if (isNotNull(arrowColor)) object.setBackgroundColor(readColor(arrowColor.getAsJsonObject()));
        if (isNotNull(scrollColor)) object.setBackgroundColor(readColor(scrollColor.getAsJsonObject()));
    }


}
