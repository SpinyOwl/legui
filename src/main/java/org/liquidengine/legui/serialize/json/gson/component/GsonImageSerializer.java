package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.serialize.json.gson.GsonBuilder;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.PATH;
import static org.liquidengine.legui.serialize.json.gson.GsonConstants.VISIBLE;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonImageSerializer extends GsonComponentSerializer<Image> {

    @Override
    protected void jsonSerialize(Image object, JsonObject json) {
        super.jsonSerialize(object, json);

        GsonBuilder.fill(json).add(PATH, object.getPath());
    }

    @Override
    protected void jsonDeserialize(JsonObject json, Image object) {
        super.jsonDeserialize(json, object);

        object.setPath(json.get(PATH).getAsString());
    }
}
