package org.liquidengine.legui.marshal.json.gsonImpl.Icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.PATH;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.isNotNull;

/**
 * Used to marshal/unmarshal from/to {@link ImageIcon} object.
 */
public class GsonImageIconMarshaller<I extends ImageIcon> extends GsonIconMarshaller<I> {
    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void marshal(I object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);
        GsonUtil.fill(json)
                .add(PATH, object.getImage().getPath());
    }

    /**
     * Reads data from json object and puts it to object
     *
     * @param json    json object to read
     * @param object  object to fill
     * @param context marshal context
     */
    @Override
    protected void unmarshal(JsonObject json, I object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement path = json.get(PATH);
        if (isNotNull(path)) object.setImage(ImageLoader.loadImage(path.getAsString()));
    }
}
