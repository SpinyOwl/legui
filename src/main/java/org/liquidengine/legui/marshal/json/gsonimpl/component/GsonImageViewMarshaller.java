package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

/**
 * Marshaller for {@link ImageView}.
 */
public class GsonImageViewMarshaller<T extends ImageView> extends GsonComponentMarshaller<T> {

    /**
     * Reads data from object and puts it to json object.
     *
     * @param object object to read.
     * @param json json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);
        GsonUtil.fill(json).add("image", GsonMarshalUtil.marshalToJson(object.getImage()));
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json json object to read.
     * @param object object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement path = json.get("image");
        if (isNotNull(path)) {
            object.setImage(GsonMarshalUtil.unmarshal(path.getAsJsonObject()));
        }
    }
}
