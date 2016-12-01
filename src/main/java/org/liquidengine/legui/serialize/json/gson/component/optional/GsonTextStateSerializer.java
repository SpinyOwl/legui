package org.liquidengine.legui.serialize.json.gson.component.optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.serialize.json.gson.AbstractGsonSerializer;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonConstants.FONT;
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
    protected void jsonDeserialize(JsonObject json, TextState object, GsonSerializeContext context) {

        JsonElement font = json.get(FONT);
        JsonElement text = json.get(TEXT);
        JsonElement fontSize = json.get(FONT_SIZE);
        JsonElement horizontalAlign = json.get(HORIZONTAL_ALIGN);
        JsonElement verticalAlign = json.get(VERTICAL_ALIGN);
        JsonElement textColor = json.getAsJsonObject(TEXT_COLOR);
        JsonElement highlightColor = json.getAsJsonObject(HIGHLIGHT_COLOR);
        JsonElement padding = json.getAsJsonObject(PADDING);

        if (isNotNull(font)) object.setFont(font.getAsString());
        if (isNotNull(text)) object.setText(text.getAsString());
        if (isNotNull(fontSize)) object.setFontSize(fontSize.getAsFloat());
        if (isNotNull(horizontalAlign)) object.setHorizontalAlign(HorizontalAlign.valueOf(horizontalAlign.getAsString()));
        if (isNotNull(verticalAlign)) object.setVerticalAlign(VerticalAlign.valueOf(verticalAlign.getAsString()));

        if (isNotNull(textColor)) object.setTextColor(readColor(textColor.getAsJsonObject()));
        if (isNotNull(highlightColor)) object.setHighlightColor(readColor(highlightColor.getAsJsonObject()));

        if (isNotNull(padding)) {
            JsonObject p = padding.getAsJsonObject();
            JsonElement left = p.get(LEFT);
            JsonElement top = p.get(TOP);
            JsonElement right = p.get(RIGHT);
            JsonElement bottom = p.get(BOTTOM);
            object.setPadding(
                    isNotNull(left) ? left.getAsFloat() : 0,
                    isNotNull(top) ? top.getAsFloat() : 0,
                    isNotNull(right) ? right.getAsFloat() : 0,
                    isNotNull(bottom) ? bottom.getAsFloat() : 0
            );
        }

    }
}
