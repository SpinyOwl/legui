package org.liquidengine.legui.marshal.json.gsonimpl.image;

import static org.liquidengine.legui.marshal.JsonConstants.PATH;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

/**
 * Marshaller for {@link BufferedImage} instances.
 */
public class GsonLoadableImageMarshaller<I extends LoadableImage> extends GsonImageMarshaller<I> {

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
        GsonUtil.fill(json).add(PATH, object.getPath());
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
        JsonElement path = json.get(PATH);
        if (GsonUtil.isNotNull(path)) {
            object.setPath(path.getAsString());
            object.load();
        }
    }
}
