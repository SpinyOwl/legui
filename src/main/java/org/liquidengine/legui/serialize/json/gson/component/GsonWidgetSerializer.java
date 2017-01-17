package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;

import static org.liquidengine.legui.serialize.json.JsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.*;

/**
 * Created by Alexander on 02.12.2016.
 */
public class GsonWidgetSerializer extends GsonComponentSerializer<Widget> {
    @Override
    protected void jsonSerialize(Widget object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        ComponentContainer container = object.getContainer();
        JsonObject cont = GsonSerializeUtil.serializeToJson(container, context);
        GsonUtil.fill(json)
                .add(CONTAINER, cont)
//                .add(RESIZABLE, object.isResizable())
                .add(DRAGGABLE, object.isDraggable())
                .add(MINIMIZED, object.isMinimized())
                .add(CLOSEABLE, object.isCloseable())
                .add(TITLE_ENABLED, object.isTitleEnabled())
                .add(TITLE_HEIGHT, object.getTitleHeight())
                .add(TITLE_BACKGROUND_COLOR, createColor(object.getTitleBackgroundColor()))
                .add(CLOSE_BUTTON_COLOR, createColor(object.getCloseButtonColor()))
                .add(TITLE, GsonSerializeUtil.serializeToJson(object.getTitleTextState(), context))
        ;
    }

    @Override
    protected void deserialize(JsonObject json, Widget object, GsonSerializeContext context) {
        super.deserialize(json, object, context);

        JsonElement container = json.get(CONTAINER);
//        JsonElement resizable = json.get(RESIZABLE);
        JsonElement draggable = json.get(DRAGGABLE);
        JsonElement minimized = json.get(MINIMIZED);
        JsonElement closeable = json.get(CLOSEABLE);
        JsonElement titleEnabled = json.get(TITLE_ENABLED);
        JsonElement titleHeight = json.get(TITLE_HEIGHT);
        JsonElement titleBackgroundColor = json.get(TITLE_BACKGROUND_COLOR);
        JsonElement closeButtonColor = json.get(CLOSE_BUTTON_COLOR);
        JsonElement title = json.get(TITLE);

        if (isNotNull(container)) object.setContainer(GsonSerializeUtil.deserializeFromJson(container.getAsJsonObject(), context));
//        if (isNotNull(resizable)) object.setResizable(resizable.getAsBoolean());
        if (isNotNull(title)) object.getTitleTextState().copy(GsonSerializeUtil.deserializeFromJson(title.getAsJsonObject(), context));
        if (isNotNull(titleHeight)) object.setTitleHeight(titleHeight.getAsFloat());
        if (isNotNull(titleBackgroundColor)) object.setTitleBackgroundColor(readColor(titleBackgroundColor.getAsJsonObject()));
        if (isNotNull(closeButtonColor)) object.setCloseButtonColor(readColor(closeButtonColor.getAsJsonObject()));
        if (isNotNull(draggable)) object.setDraggable(draggable.getAsBoolean());
        if (isNotNull(closeable)) object.setCloseable(closeable.getAsBoolean());
        if (isNotNull(titleEnabled)) object.setTitleEnabled(titleEnabled.getAsBoolean());
        if (isNotNull(minimized)) object.setDraggable(minimized.getAsBoolean());

        object.resize();
    }
}
