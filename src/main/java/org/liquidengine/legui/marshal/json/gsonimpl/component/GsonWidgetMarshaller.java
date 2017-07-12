package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.*;

/**
 * Marshaller for {@link Widget}.
 */
public class GsonWidgetMarshaller<T extends Widget> extends GsonControllerMarshaller<T> {
    /**
     * Reads data from object and puts it to json object.
     *
     * @param object  object to read.
     * @param json    json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);

        Container  container = object.getContainer();
        JsonObject cont      = GsonMarshalUtil.marshalToJson(container, context);
        fill(json)
                .add(CONTAINER, cont)
//                .add(RESIZABLE, object.isResizable())
                .add(DRAGGABLE, object.isDraggable())
                .add(MINIMIZABLE, object.isMinimizable())
                .add(MINIMIZED, object.isMinimized())
                .add(CLOSEABLE, object.isCloseable())
                .add(TITLE_ENABLED, object.isTitleEnabled())
                .add(TITLE_HEIGHT, object.getTitleHeight())
                .add(TITLE_BACKGROUND_COLOR, createColor(object.getTitleBackgroundColor()))
                .add(CLOSE_BUTTON_COLOR, createColor(object.getCloseButtonColor()))
                .add(CLOSE_BUTTON_BACKGROUND_COLOR, createColor(object.getCloseButtonBackgroundColor()))
                .add(TITLE, GsonMarshalUtil.marshalToJson(object.getTitleTextState(), context))
                .add(CLOSE_ICON, GsonMarshalUtil.marshalToJson(object.getCloseIcon(), context))
                .add(MINIMIZE_ICON, GsonMarshalUtil.marshalToJson(object.getMinimizeIcon(), context))
                .add(MAXIMIZE_ICON, GsonMarshalUtil.marshalToJson(object.getMaximizeIcon(), context))
        ;
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json    json object to read.
     * @param object  object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);


        JsonElement container = json.get(CONTAINER);
//        JsonElement resizable = json.get(RESIZABLE);
        JsonElement draggable                  = json.get(DRAGGABLE);
        JsonElement minimized                  = json.get(MINIMIZED);
        JsonElement minimizable                = json.get(MINIMIZABLE);
        JsonElement closeable                  = json.get(CLOSEABLE);
        JsonElement titleEnabled               = json.get(TITLE_ENABLED);
        JsonElement titleHeight                = json.get(TITLE_HEIGHT);
        JsonElement titleBackgroundColor       = json.get(TITLE_BACKGROUND_COLOR);
        JsonElement closeButtonColor           = json.get(CLOSE_BUTTON_COLOR);
        JsonElement closeButtonBackgroundColor = json.get(CLOSE_BUTTON_BACKGROUND_COLOR);
        JsonElement title                      = json.get(TITLE);
        JsonElement closeIcon                  = json.get(CLOSE_ICON);
        JsonElement minimizeIcon               = json.get(MINIMIZE_ICON);
        JsonElement maximizeIcon               = json.get(MAXIMIZE_ICON);


        if (isNotNull(container)) object.setContainer(GsonMarshalUtil.unmarshal(container.getAsJsonObject(), context));
//        if (isNotNull(resizable)) object.setResizable(resizable.getAsBoolean());
        if (isNotNull(title)) object.getTitleTextState().copy(GsonMarshalUtil.unmarshal(title.getAsJsonObject(), context));
        if (isNotNull(titleHeight)) object.setTitleHeight(titleHeight.getAsFloat());
        if (isNotNull(titleBackgroundColor)) object.setTitleBackgroundColor(readColor(titleBackgroundColor.getAsJsonObject()));
        if (isNotNull(closeButtonColor)) object.setCloseButtonColor(readColor(closeButtonColor.getAsJsonObject()));
        if (isNotNull(closeButtonBackgroundColor)) object.setCloseButtonBackgroundColor(readColor(closeButtonBackgroundColor.getAsJsonObject()));
        if (isNotNull(draggable)) object.setDraggable(draggable.getAsBoolean());
        if (isNotNull(closeable)) object.setCloseable(closeable.getAsBoolean());
        if (isNotNull(titleEnabled)) object.setTitleEnabled(titleEnabled.getAsBoolean());
        if (isNotNull(minimized)) object.setMinimized(minimized.getAsBoolean());
        if (isNotNull(minimizable)) object.setMinimizable(minimizable.getAsBoolean());
        if (isNotNull(closeIcon)) object.setCloseIcon(GsonMarshalUtil.unmarshal(closeIcon.getAsJsonObject(), context));
        if (isNotNull(minimizeIcon)) object.setMinimizeIcon(GsonMarshalUtil.unmarshal(minimizeIcon.getAsJsonObject(), context));
        if (isNotNull(maximizeIcon)) object.setMaximizeIcon(GsonMarshalUtil.unmarshal(maximizeIcon.getAsJsonObject(), context));

        object.resize();
    }
}
