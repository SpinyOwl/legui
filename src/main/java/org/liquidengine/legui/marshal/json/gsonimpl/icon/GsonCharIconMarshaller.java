package org.liquidengine.legui.marshal.json.gsonimpl.icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Used to marshal/unmarshal from/to {@link ImageIcon} object.
 */
public class GsonCharIconMarshaller<I extends CharIcon> extends GsonIconMarshaller<I> {
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
                .add(FONT, object.getFont())
                .add(COLOR, GsonUtil.createColor(object.getColor()))
                .add(CHAR_CODE, object.getCharCode())
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
    protected void unmarshal(JsonObject json, I object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);

        JsonElement font     = json.get(FONT);
        JsonElement color    = json.get(COLOR);
        JsonElement charCode = json.get(CHAR_CODE);

        if (isNotNull(font)) object.setFont(font.getAsString());
        if (isNotNull(color)) object.setColor(GsonUtil.readColor(color.getAsJsonObject()));
        if (isNotNull(charCode)) object.setCharCode(charCode.getAsInt());

    }
}
