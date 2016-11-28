package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.serialize.json.gson.AbstractGsonSerializer;
import org.liquidengine.legui.serialize.json.gson.GsonBuilder;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;

/**
 * Created by Alexander on 26.11.2016.
 */
public class GsonComponentSerializer<T extends Component> extends AbstractGsonSerializer<T> {

    @Override
    protected void jsonSerialize(T object, JsonObject json) {
        GsonBuilder.fill(json)
                .add(POSITION, GsonBuilder.create()
                        .add(X, object.getPosition().x)
                        .add(Y, object.getPosition().y)
                        .get())
                .add(SIZE, GsonBuilder.create()
                        .add(WIDTH, object.getSize().x)
                        .add(HEIGHT, object.getSize().y)
                        .get()
                )
                .add(BACKGROUND_COLOR, GsonBuilder.create()
                        .add(R, object.getBackgroundColor().x)
                        .add(G, object.getBackgroundColor().y)
                        .add(B, object.getBackgroundColor().z)
                        .add(A, object.getBackgroundColor().w)
                        .get()
                )
                .add(ENABLED, object.isEnabled())
                .add(VISIBLE, object.isVisible())
                .add(CORNER_RADIUS, object.getCornerRadius())
        ;
    }

    @Override
    protected void jsonDeserialize(JsonObject json, T object) {
        JsonObject position = json.getAsJsonObject(POSITION);
        object.setPosition(position.get(X).getAsFloat(), position.get(Y).getAsFloat());

        JsonObject size = json.getAsJsonObject(SIZE);
        object.setSize(size.get(WIDTH).getAsFloat(), size.get(HEIGHT).getAsFloat());

        JsonObject bg = json.getAsJsonObject(BACKGROUND_COLOR);
        object.setBackgroundColor(bg.get(R).getAsFloat(), bg.get(G).getAsFloat(), bg.get(B).getAsFloat(), bg.get(A).getAsFloat());
        object.setEnabled(json.get(ENABLED).getAsBoolean());
        object.setEnabled(json.get(VISIBLE).getAsBoolean());
        object.setCornerRadius(json.get(CORNER_RADIUS).getAsFloat());
    }
}
