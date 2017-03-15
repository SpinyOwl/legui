package org.liquidengine.legui.marshal.json.gsonimpl.border;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.BORDER_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.THICKNESS;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Created by Aliaksandr_Shcherbin on 2/24/2017.
 */
public class GsonSimpleLineBorderMarshaller extends GsonBorderMarshaller<SimpleLineBorder> {

    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void marshal(SimpleLineBorder object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);
        GsonUtil.fill(json)
                .add(THICKNESS, object.getThickness())
                .add(BORDER_COLOR, GsonUtil.createColor(object.getColor()));
    }

    /**
     * Reads data from json object and puts it to object
     *
     * @param json    json object to read
     * @param object  object to fill
     * @param context marshal context
     */
    @Override
    protected void unmarshal(JsonObject json, SimpleLineBorder object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement thickness   = json.get(THICKNESS);
        JsonElement borderColor = json.get(BORDER_COLOR);
        if (isNotNull(thickness)) object.setThickness(thickness.getAsFloat());
        if (isNotNull(borderColor)) object.setColor(GsonUtil.readColor(borderColor.getAsJsonObject()));
    }
}
