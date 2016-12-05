package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.JsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Shcherbin Alexander on 12/1/2016.
 */
public class GsonScrollablePanelSerializer extends GsonComponentSerializer<ScrollablePanel> {
    @Override
    protected void jsonSerialize(ScrollablePanel object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        ScrollBar verticalScrollBar = object.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = object.getHorizontalScrollBar();
        ComponentContainer container = object.getContainer();

        JsonObject vScrollBar = GsonSerializeUtil.serializeToJson(verticalScrollBar, context);
        JsonObject hScrollBar = GsonSerializeUtil.serializeToJson(horizontalScrollBar, context);
        JsonObject cont = GsonSerializeUtil.serializeToJson(container, context);

        GsonUtil.fill(json)
                .add(HORIZONTAL_SCROLL_BAR, hScrollBar)
                .add(VERTICAL_SCROLL_BAR, vScrollBar)
                .add(CONTAINER, cont)
        ;
    }

    @Override
    protected void deserialize(JsonObject json, ScrollablePanel object, GsonSerializeContext context) {
        super.deserialize(json, object, context);

        JsonElement hScrollBar = json.get(HORIZONTAL_SCROLL_BAR);
        JsonElement vScrollBar = json.get(VERTICAL_SCROLL_BAR);
        JsonElement container = json.get(CONTAINER);

        if (isNotNull(hScrollBar)) object.setHorizontalScrollBar(GsonSerializeUtil.deserializeFromJson(hScrollBar.getAsJsonObject(), context));
        if (isNotNull(vScrollBar)) object.setVerticalScrollBar(GsonSerializeUtil.deserializeFromJson(vScrollBar.getAsJsonObject(), context));
        if (isNotNull(container)) object.setContainer(GsonSerializeUtil.deserializeFromJson(container.getAsJsonObject(), context));
    }
}
