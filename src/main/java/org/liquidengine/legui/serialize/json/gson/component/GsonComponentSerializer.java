package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.serialize.json.gson.AbstractGsonSerializer;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.createColor;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.readColor;

/**
 * Created by Alexander on 26.11.2016.
 */
public class GsonComponentSerializer<T extends Component> extends AbstractGsonSerializer<T> {

    @Override
    protected void jsonSerialize(T object, JsonObject json, GsonSerializeContext context) {
        GsonUtil.fill(json)
                .add(POSITION, GsonUtil.create()
                        .add(X, object.getPosition().x)
                        .add(Y, object.getPosition().y)
                        .get())
                .add(SIZE, GsonUtil.create()
                        .add(WIDTH, object.getSize().x)
                        .add(HEIGHT, object.getSize().y)
                        .get()
                )
                .add(BACKGROUND_COLOR, createColor(object.getBackgroundColor()))
                .add(ENABLED, object.isEnabled())
                .add(VISIBLE, object.isVisible())
                .add(CORNER_RADIUS, object.getCornerRadius())
        ;
    }

    @Override
    protected void jsonDeserialize(JsonObject json, T object, GsonSerializeContext context) {
        JsonObject position = json.getAsJsonObject(POSITION);
        JsonObject size = json.getAsJsonObject(SIZE);
        JsonObject bg = json.getAsJsonObject(BACKGROUND_COLOR);
        JsonElement enabled = json.get(ENABLED);
        JsonElement visible = json.get(VISIBLE);
        JsonElement cornerRadius = json.get(CORNER_RADIUS);

        if (isNotNull(position)) {
            JsonElement x = position.get(X);
            float xx = (!isNotNull(x)) ? object.getPosition().x : x.getAsFloat();
            JsonElement y = position.get(Y);
            float yy = (!isNotNull(y)) ? object.getPosition().y : y.getAsFloat();
            object.setPosition(xx, yy);
        }
        if (isNotNull(size)) {
            JsonElement width = size.get(WIDTH);
            float wid = (!isNotNull(width)) ? object.getSize().x : width.getAsFloat();
            JsonElement height = size.get(HEIGHT);
            float hei = (!isNotNull(height)) ? object.getSize().y : height.getAsFloat();
            object.setSize(wid, hei);
        }
        if (isNotNull(bg)) object.setBackgroundColor(readColor(bg));
        if (isNotNull(enabled)) object.setEnabled(enabled.getAsBoolean());
        if (isNotNull(visible)) object.setVisible(visible.getAsBoolean());
        if (isNotNull(cornerRadius)) object.setCornerRadius(cornerRadius.getAsFloat());
    }
}
