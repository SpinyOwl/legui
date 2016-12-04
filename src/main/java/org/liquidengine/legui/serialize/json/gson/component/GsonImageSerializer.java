package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.PATH;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonImageSerializer extends GsonComponentSerializer<Image> {

    @Override
    protected void jsonSerialize(Image object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        GsonUtil.fill(json).add(PATH, object.getPath());
    }

    @Override
    protected void jsonDeserialize(JsonObject json, Image object, GsonSerializeContext context) {
        super.jsonDeserialize(json, object, context);

        JsonElement path = json.get(PATH);
        if (isNotNull(path)) object.setPath(path.getAsString());
    }
}
