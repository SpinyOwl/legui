package org.liquidengine.legui.serialize.json.gson.component.optional;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.serialize.json.gson.AbstractGsonSerializer;
import org.liquidengine.legui.serialize.json.gson.GsonBuilder;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;

/**
 * Created by Alexander on 26.11.2016.
 */
public class GsonTextStateSerializer extends AbstractGsonSerializer<TextState> {
    @Override
    protected void jsonDeserialize(JsonObject json, TextState object) {
        object.setFont(json.get(FONT).getAsString());
        object.setText(json.get(TEXT).getAsString());
        object.setFontSize(json.get(FONT_SIZE).getAsFloat());
        object.setHorizontalAlign(HorizontalAlign.valueOf(json.get(HORIZONTAL_ALIGN).getAsString()));
        object.setVerticalAlign(VerticalAlign.valueOf(json.get(VERTICAL_ALIGN).getAsString()));

        JsonObject tc = json.getAsJsonObject(TEXT_COLOR);
        object.setTextColor(
                tc.get(R).getAsFloat(),
                tc.get(G).getAsFloat(),
                tc.get(B).getAsFloat(),
                tc.get(A).getAsFloat()
        );

        JsonObject hc = json.getAsJsonObject(HIGHLIGHT_COLOR);
        object.setHighlightColor(
                hc.get(R).getAsFloat(),
                hc.get(G).getAsFloat(),
                hc.get(B).getAsFloat(),
                hc.get(A).getAsFloat()
        );

        JsonObject p = json.getAsJsonObject(PADDING);
        object.setPadding(
                p.get(LEFT).getAsFloat(),
                p.get(TOP).getAsFloat(),
                p.get(RIGHT).getAsFloat(),
                p.get(BOTTOM).getAsFloat()
        );

    }

    @Override
    protected void jsonSerialize(TextState object, JsonObject json) {
        GsonBuilder.fill(json)
                .add(FONT, object.getFont())
                .add(TEXT, object.getText())
                .add(FONT_SIZE, object.getFontSize())
                .add(HORIZONTAL_ALIGN, object.getHorizontalAlign().name())
                .add(VERTICAL_ALIGN, object.getVerticalAlign().name())
                .add(TEXT_COLOR, GsonBuilder.create()
                        .add(R, object.getTextColor().x)
                        .add(G, object.getTextColor().y)
                        .add(B, object.getTextColor().z)
                        .add(A, object.getTextColor().w)
                        .get()
                )
                .add(HIGHLIGHT_COLOR, GsonBuilder.create()
                        .add(R, object.getHighlightColor().x)
                        .add(G, object.getHighlightColor().y)
                        .add(B, object.getHighlightColor().z)
                        .add(A, object.getHighlightColor().w)
                        .get()
                )
                .add(PADDING, GsonBuilder.create()
                        .add(LEFT, object.getPadding().x)
                        .add(TOP, object.getPadding().y)
                        .add(RIGHT, object.getPadding().z)
                        .add(BOTTOM, object.getPadding().w)
                        .get()
                )
        ;

    }
}
