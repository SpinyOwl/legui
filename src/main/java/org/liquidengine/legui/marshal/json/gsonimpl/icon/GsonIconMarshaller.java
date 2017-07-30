package org.liquidengine.legui.marshal.json.gsonimpl.icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Created by ShchAlexander on 13.03.2017.
 */
public class GsonIconMarshaller<I extends Icon> extends AbstractGsonMarshaller<I> {
    /**
     * Reads data from object and puts it to json object.
     *
     * @param object  object to read.
     * @param json    json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(I object, JsonObject json, GsonMarshalContext context) {
        GsonUtil.fill(json)
                .add(SIZE, GsonUtil.create().add(WIDTH, object.getSize().x).add(HEIGHT, object.getSize().y).get())
                .add(HORIZONTAL_ALIGN, object.getHorizontalAlign().name())
                .add(VERTICAL_ALIGN, object.getVerticalAlign().name())
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
    protected void unmarshal(JsonObject json, I object, GsonMarshalContext context) {
        JsonObject size = json.getAsJsonObject(SIZE);
        JsonElement horizontalAlign = json.get(HORIZONTAL_ALIGN);
        JsonElement verticalAlign = json.get(VERTICAL_ALIGN);
        if (isNotNull(horizontalAlign)) object.setHorizontalAlign(HorizontalAlign.valueOf(horizontalAlign.getAsString()));

        if (isNotNull(verticalAlign)) object.setVerticalAlign(VerticalAlign.valueOf(verticalAlign.getAsString()));

        if (isNotNull(size)) {
            JsonElement x = size.get(WIDTH);
            JsonElement y = size.get(HEIGHT);
            if (isNotNull(x)) object.getSize().x = x.getAsFloat();
            if (isNotNull(y)) object.getSize().y = y.getAsFloat();
        }

    }
}
