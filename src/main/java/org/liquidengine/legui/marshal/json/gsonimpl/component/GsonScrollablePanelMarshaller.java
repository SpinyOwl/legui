package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Marshaller for {@link ScrollablePanel}.
 */
public class GsonScrollablePanelMarshaller<T extends ScrollablePanel> extends GsonControllerMarshaller<T> {
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

        ScrollBar verticalScrollBar = object.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = object.getHorizontalScrollBar();
        Container container = object.getContainer();

        JsonObject vScrollBar = GsonMarshalUtil.marshalToJson(verticalScrollBar, context);
        JsonObject hScrollBar = GsonMarshalUtil.marshalToJson(horizontalScrollBar, context);
        JsonObject cont = GsonMarshalUtil.marshalToJson(container, context);

        GsonUtil.fill(json)
                .add(HORIZONTAL_SCROLL_BAR, hScrollBar)
                .add(VERTICAL_SCROLL_BAR, vScrollBar)
                .add(CONTAINER, cont)
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

        JsonElement hScrollBar = json.get(HORIZONTAL_SCROLL_BAR);
        JsonElement vScrollBar = json.get(VERTICAL_SCROLL_BAR);
        JsonElement container = json.get(CONTAINER);

        // this order is very important because container of Scrollable panel contains elements in such order.
        if (isNotNull(vScrollBar)) object.setVerticalScrollBar(GsonMarshalUtil.unmarshal(vScrollBar.getAsJsonObject(), context));
        if (isNotNull(hScrollBar)) object.setHorizontalScrollBar(GsonMarshalUtil.unmarshal(hScrollBar.getAsJsonObject(), context));
        if (isNotNull(container)) object.setContainer(GsonMarshalUtil.unmarshal(container.getAsJsonObject(), context));
        object.resize();
    }
}
