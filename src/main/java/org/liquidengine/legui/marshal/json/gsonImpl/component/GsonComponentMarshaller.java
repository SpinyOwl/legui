package org.liquidengine.legui.marshal.json.gsonImpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.marshal.json.gsonImpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.*;

/**
 * Created by ShchAlexander on 25.02.2017.
 */
public class GsonComponentMarshaller<T extends Component> extends AbstractGsonMarshaller<T> {


    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void jsonMarshal(T object, JsonObject json, GsonMarshalContext context) {
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
                .add(BORDER, GsonMarshalUtil.marshalToJson(object.getBorder(), context))
                .add(INTERSECTOR, GsonMarshalUtil.marshalToJson(object.getIntersector()))
        ;
    }

    /**
     * Reads data from json object and puts it to object
     *
     * @param json    json object to read
     * @param object  object to fill
     * @param context marshal context
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        JsonObject  position     = json.getAsJsonObject(POSITION);
        JsonObject  size         = json.getAsJsonObject(SIZE);
        JsonObject  bg           = json.getAsJsonObject(BACKGROUND_COLOR);
        JsonElement enabled      = json.get(ENABLED);
        JsonElement visible      = json.get(VISIBLE);
        JsonElement cornerRadius = json.get(CORNER_RADIUS);
        JsonElement border       = json.get(BORDER);
        JsonElement intersector  = json.get(INTERSECTOR);

        if (isNotNull(position)) {
            JsonElement x  = position.get(X);
            float       xx = (!isNotNull(x)) ? object.getPosition().x : x.getAsFloat();
            JsonElement y  = position.get(Y);
            float       yy = (!isNotNull(y)) ? object.getPosition().y : y.getAsFloat();
            object.setPosition(xx, yy);
        } else if (isJsonNull(position)) {
            object.setPosition(new Vector2f(0));
        }

        if (isNotNull(size)) {
            JsonElement width  = size.get(WIDTH);
            float       wid    = (!isNotNull(width)) ? object.getSize().x : width.getAsFloat();
            JsonElement height = size.get(HEIGHT);
            float       hei    = (!isNotNull(height)) ? object.getSize().y : height.getAsFloat();
            object.setSize(wid, hei);
        } else if (isJsonNull(size)) {
            object.setSize(new Vector2f(0));
        }

        if (isNotNull(bg)) object.setBackgroundColor(readColor(bg));
        else if (isJsonNull(bg)) object.setBackgroundColor(ColorConstants.transparent());

        if (isNotNull(enabled)) object.setEnabled(enabled.getAsBoolean());
        else if (isJsonNull(enabled)) object.setEnabled(true);

        if (isNotNull(visible)) object.setVisible(visible.getAsBoolean());
        else if (isJsonNull(visible)) object.setVisible(true);

        if (isNotNull(cornerRadius)) object.setCornerRadius(cornerRadius.getAsFloat());
        else if (isJsonNull(cornerRadius)) object.setCornerRadius(0);

        if (isNotNull(border)) object.setBorder(GsonMarshalUtil.unmarshal(border.getAsJsonObject(), context));
        else if (isJsonNull(border)) object.setBorder(null);
        else object.setBorder(null);

        if (isNotNull(intersector)) object.setIntersector(GsonMarshalUtil.unmarshal(intersector.getAsJsonObject(), context));
    }


}
