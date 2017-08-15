package org.liquidengine.legui.marshal.json.gsonimpl.image;

import com.google.gson.JsonObject;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;

/**
 * Marshaller for {@link BufferedImage} instances.
 */
public class GsonBufferedImageMarshaller<I extends BufferedImage> extends GsonLoadableImageMarshaller<I> {

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
    }

}
