package org.liquidengine.legui.marshal.json.gsonimpl.icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.IMAGE;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Used to marshal/unmarshal from/to {@link ImageIcon} object.
 */
public class GsonLoadableImageIconMarshaller<I extends ImageIcon> extends GsonIconMarshaller<I> {

    /**
     * Reads data from object and puts it to json object.
     *
     * @param object object to read.
     * @param json json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(I object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);
        GsonUtil.fill(json).add(IMAGE, GsonMarshalUtil.marshalToJson(object.getImage()));
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json json object to read.
     * @param object object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, I object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement image = json.get(IMAGE);
        if (isNotNull(image)) {
            object.setImage(GsonMarshalUtil.unmarshal(image.getAsJsonObject()));
        }
    }
}
