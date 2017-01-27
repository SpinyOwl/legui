package org.liquidengine.legui.serialize.json.gson.component.optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.serialize.json.gson.AbstractGsonSerializer;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;
import org.liquidengine.legui.util.ColorConstants;

import static org.liquidengine.legui.serialize.json.JsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.readColor;

/**
 * Created by Alexander on 26.11.2016.
 */
public class GsonTextStateSerializer extends AbstractGsonSerializer<TextState> {
    @Override
    protected void jsonSerialize(TextState object, JsonObject json, GsonSerializeContext context) {
        GsonUtil.fill(json)
                .add(FONT, object.getFont())
                .add(TEXT, object.getText())
                .add(FONT_SIZE, object.getFontSize())
                .add(HORIZONTAL_ALIGN, object.getHorizontalAlign().name())
                .add(VERTICAL_ALIGN, object.getVerticalAlign().name())
                .add(TEXT_COLOR, GsonUtil.create()
                        .add(R, object.getTextColor().x)
                        .add(G, object.getTextColor().y)
                        .add(B, object.getTextColor().z)
                        .add(A, object.getTextColor().w)
                        .get()
                )
                .add(HIGHLIGHT_COLOR, GsonUtil.create()
                        .add(R, object.getHighlightColor().x)
                        .add(G, object.getHighlightColor().y)
                        .add(B, object.getHighlightColor().z)
                        .add(A, object.getHighlightColor().w)
                        .get()
                )
                .add(PADDING, GsonUtil.create()
                        .add(LEFT, object.getPadding().x)
                        .add(TOP, object.getPadding().y)
                        .add(RIGHT, object.getPadding().z)
                        .add(BOTTOM, object.getPadding().w)
                        .get()
                )
        ;

    }

    @Override
    protected void deserialize(JsonObject json, TextState object, GsonSerializeContext context) {

        JsonElement font            = json.get(FONT);
        JsonElement text            = json.get(TEXT);
        JsonElement fontSize        = json.get(FONT_SIZE);
        JsonElement horizontalAlign = json.get(HORIZONTAL_ALIGN);
        JsonElement verticalAlign   = json.get(VERTICAL_ALIGN);
        JsonElement textColor       = json.getAsJsonObject(TEXT_COLOR);
        JsonElement highlightColor  = json.getAsJsonObject(HIGHLIGHT_COLOR);
        JsonElement padding         = json.getAsJsonObject(PADDING);

        if (isNotNull(font)) object.setFont(font.getAsString());
        else if (font.isJsonNull()) object.setFont(FontRegister.DEFAULT);

        if (isNotNull(text)) object.setText(text.getAsString());
        else if (text.isJsonNull()) object.setText("");

        if (isNotNull(fontSize)) object.setFontSize(fontSize.getAsFloat());
        else if (fontSize.isJsonNull()) object.setFontSize(16);

        if (isNotNull(horizontalAlign)) object.setHorizontalAlign(HorizontalAlign.valueOf(horizontalAlign.getAsString()));
        else if (horizontalAlign.isJsonNull()) object.setHorizontalAlign(HorizontalAlign.LEFT);

        if (isNotNull(verticalAlign)) object.setVerticalAlign(VerticalAlign.valueOf(verticalAlign.getAsString()));
        else if (verticalAlign.isJsonNull()) object.setVerticalAlign(VerticalAlign.MIDDLE);

        if (isNotNull(textColor)) object.setTextColor(readColor(textColor.getAsJsonObject()));
        else if (textColor.isJsonNull()) object.setTextColor(ColorConstants.black());

        if (isNotNull(highlightColor)) object.setHighlightColor(readColor(highlightColor.getAsJsonObject()));
        else if (highlightColor.isJsonNull()) object.setHighlightColor(ColorConstants.blue());


        if (isNotNull(padding)) {
            JsonObject  p      = padding.getAsJsonObject();
            JsonElement left   = p.get(LEFT);
            JsonElement top    = p.get(TOP);
            JsonElement right  = p.get(RIGHT);
            JsonElement bottom = p.get(BOTTOM);
            object.setPadding(
                    isNotNull(left) ? left.getAsFloat() : 0,
                    isNotNull(top) ? top.getAsFloat() : 0,
                    isNotNull(right) ? right.getAsFloat() : 0,
                    isNotNull(bottom) ? bottom.getAsFloat() : 0
            );
        } else if (padding.isJsonNull()) {
            object.setPadding(new Vector4f(0));
        }

    }
}
