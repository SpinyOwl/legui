package org.liquidengine.legui.marshal.json.gsonImpl.component.optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.marshal.json.gsonImpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;
import org.liquidengine.legui.theme.Theme;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.isNotNull;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.readColor;

/**
 * Created by ShchAlexander on 26.02.2017.
 */
public class GsonTextStateMarshaller<T extends TextState> extends AbstractGsonMarshaller<T> {
    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
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

    /**
     * Reads data from json object and puts it to object
     *
     * @param json    json object to read
     * @param object  object to fill
     * @param context marshal context
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        JsonElement font            = json.get(FONT);
        JsonElement text            = json.get(TEXT);
        JsonElement fontSize        = json.get(FONT_SIZE);
        JsonElement horizontalAlign = json.get(HORIZONTAL_ALIGN);
        JsonElement verticalAlign   = json.get(VERTICAL_ALIGN);
        JsonElement textColor       = json.getAsJsonObject(TEXT_COLOR);
        JsonElement highlightColor  = json.getAsJsonObject(HIGHLIGHT_COLOR);
        JsonElement padding         = json.getAsJsonObject(PADDING);

        if (isNotNull(font)) object.setFont(font.getAsString());
        else if (font.isJsonNull()) object.setFont(Theme.DEFAULT_THEME.font());

        if (isNotNull(text)) object.setText(text.getAsString());
        else if (text.isJsonNull()) object.setText("");

        if (isNotNull(fontSize)) object.setFontSize(fontSize.getAsFloat());
        else if (fontSize.isJsonNull()) object.setFontSize(Theme.DEFAULT_THEME.fontSize());

        if (isNotNull(horizontalAlign)) object.setHorizontalAlign(HorizontalAlign.valueOf(horizontalAlign.getAsString()));
        else if (horizontalAlign.isJsonNull()) object.setHorizontalAlign(Theme.DEFAULT_THEME.horizontalAlign());

        if (isNotNull(verticalAlign)) object.setVerticalAlign(VerticalAlign.valueOf(verticalAlign.getAsString()));
        else if (verticalAlign.isJsonNull()) object.setVerticalAlign(Theme.DEFAULT_THEME.verticalAlign());

        if (isNotNull(textColor)) object.setTextColor(readColor(textColor.getAsJsonObject()));
        else if (textColor.isJsonNull()) object.setTextColor(Theme.DEFAULT_THEME.fontColor());

        if (isNotNull(highlightColor)) object.setHighlightColor(readColor(highlightColor.getAsJsonObject()));
        else if (highlightColor.isJsonNull()) object.setHighlightColor(Theme.DEFAULT_THEME.highlightColor());


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
            object.setPadding(Theme.DEFAULT_THEME.textPadding());
        }

    }
}
