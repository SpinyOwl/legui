package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.JsonConstants.PATH;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 27.11.2016.
 */
public class GsonImageViewSerializer extends GsonComponentSerializer<ImageView> {

    @Override
    protected void jsonSerialize(ImageView object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        GsonUtil.fill(json).add(PATH, object.getImage().getPath());
    }

    @Override
    protected void deserialize(JsonObject json, ImageView object, GsonSerializeContext context) {
        super.deserialize(json, object, context);

        JsonElement path = json.get(PATH);
        if (isNotNull(path)) object.setImage(new Image(path.getAsString()));
    }
}
