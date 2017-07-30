package org.liquidengine.legui.marshal.json.gsonimpl.image;

import com.google.gson.JsonObject;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;

/**
 * Marshaller for Image instances.
 */
public class GsonImageMarshaller<I extends Image> extends AbstractGsonMarshaller<I> {
    /**
     * Reads data from object and puts it to json object.
     *
     * @param object  object to read.
     * @param json    json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(I object, JsonObject json, GsonMarshalContext context) {
        // this is default image marshaller. no need to implement this method.
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
        // this is default image marshaller. no need to implement this method.
    }
}
