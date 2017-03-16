package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.*;

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
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        fill(json)
                .add(POSITION, create()
                        .add(X, object.getPosition().x)
                        .add(Y, object.getPosition().y)
                        .get())
                .add(SIZE, create()
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
            JsonElement x = position.get(X);
            JsonElement y = position.get(Y);
            if (isNotNull(x)) object.getPosition().x = x.getAsFloat();
            if (isNotNull(y)) object.getPosition().y = y.getAsFloat();
        }

        if (isNotNull(size)) {
            JsonElement x = size.get(WIDTH);
            JsonElement y = size.get(HEIGHT);
            if (isNotNull(x)) object.getSize().x = x.getAsFloat();
            if (isNotNull(y)) object.getSize().y = y.getAsFloat();
        }
        if (isNotNull(bg)) object.setBackgroundColor(readColor(bg));
        if (isNotNull(enabled)) object.setEnabled(enabled.getAsBoolean());
        if (isNotNull(visible)) object.setVisible(visible.getAsBoolean());
        if (isNotNull(cornerRadius)) object.setCornerRadius(cornerRadius.getAsFloat());

        if (isNotNull(border)) object.setBorder(GsonMarshalUtil.unmarshal(border.getAsJsonObject(), context));
        else object.setBorder(null);

        if (isNotNull(intersector)) object.setIntersector(GsonMarshalUtil.unmarshal(intersector.getAsJsonObject(), context));
    }


}
