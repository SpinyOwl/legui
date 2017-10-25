package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.JsonConstants.BACKGROUND_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.BORDER;
import static org.liquidengine.legui.marshal.JsonConstants.CONTAINER;
import static org.liquidengine.legui.marshal.JsonConstants.CORNER_RADIUS;
import static org.liquidengine.legui.marshal.JsonConstants.ENABLED;
import static org.liquidengine.legui.marshal.JsonConstants.HEIGHT;
import static org.liquidengine.legui.marshal.JsonConstants.HORIZONTAL_SCROLL_BAR;
import static org.liquidengine.legui.marshal.JsonConstants.INTERSECTOR;
import static org.liquidengine.legui.marshal.JsonConstants.POSITION;
import static org.liquidengine.legui.marshal.JsonConstants.SIZE;
import static org.liquidengine.legui.marshal.JsonConstants.TOOLTIP;
import static org.liquidengine.legui.marshal.JsonConstants.VERTICAL_SCROLL_BAR;
import static org.liquidengine.legui.marshal.JsonConstants.VISIBLE;
import static org.liquidengine.legui.marshal.JsonConstants.WIDTH;
import static org.liquidengine.legui.marshal.JsonConstants.X;
import static org.liquidengine.legui.marshal.JsonConstants.Y;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.create;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.createColor;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.fill;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.readColor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

/**
 * Marshaller for {@link ScrollablePanel}.
 *
 * @param <T> type of component.
 */
public class GsonScrollablePanelMarshaller<T extends ScrollablePanel> extends AbstractGsonMarshaller<T> {

    /**
     * Reads data from object and puts it to json object.
     *
     * @param object object to read.
     * @param json json object to fill.
     * @param context marshal context.
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
            .add(TOOLTIP, GsonMarshalUtil.marshalToJson(object.getTooltip(), context))
        ;

        ScrollBar verticalScrollBar = object.getVerticalScrollBar();
        ScrollBar horizontalScrollBar = object.getHorizontalScrollBar();
        Component container = object.getContainer();

        JsonObject vScrollBar = GsonMarshalUtil.marshalToJson(verticalScrollBar, context);
        JsonObject hScrollBar = GsonMarshalUtil.marshalToJson(horizontalScrollBar, context);
        JsonObject cont = GsonMarshalUtil.marshalToJson(container, context);

        // remove base container
        json.remove(CONTAINER);

        GsonUtil.fill(json)
            .add(HORIZONTAL_SCROLL_BAR, hScrollBar)
            .add(VERTICAL_SCROLL_BAR, vScrollBar)
            .add(CONTAINER, cont)
        ;
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json json object to read.
     * @param object object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {

        JsonObject position = json.getAsJsonObject(POSITION);
        JsonObject size = json.getAsJsonObject(SIZE);
        JsonObject bg = json.getAsJsonObject(BACKGROUND_COLOR);
        JsonElement enabled = json.get(ENABLED);
        JsonElement visible = json.get(VISIBLE);
        JsonElement cornerRadius = json.get(CORNER_RADIUS);
        JsonElement border = json.get(BORDER);
        JsonElement intersector = json.get(INTERSECTOR);
        JsonElement tooltip = json.get(TOOLTIP);

        if (isNotNull(position)) {
            JsonElement x = position.get(X);
            JsonElement y = position.get(Y);
            if (isNotNull(x)) {
                object.getPosition().x = x.getAsFloat();
            }
            if (isNotNull(y)) {
                object.getPosition().y = y.getAsFloat();
            }
        }

        if (isNotNull(size)) {
            JsonElement x = size.get(WIDTH);
            JsonElement y = size.get(HEIGHT);
            if (isNotNull(x)) {
                object.getSize().x = x.getAsFloat();
            }
            if (isNotNull(y)) {
                object.getSize().y = y.getAsFloat();
            }
        }
        if (isNotNull(bg)) {
            object.setBackgroundColor(readColor(bg));
        }
        if (isNotNull(enabled)) {
            object.setEnabled(enabled.getAsBoolean());
        }
        if (isNotNull(visible)) {
            object.setVisible(visible.getAsBoolean());
        }
        if (isNotNull(cornerRadius)) {
            object.setCornerRadius(cornerRadius.getAsFloat());
        }

        if (isNotNull(border)) {
            object.setBorder(GsonMarshalUtil.unmarshal(border.getAsJsonObject(), context));
        } else {
            object.setBorder(null);
        }

        if (isNotNull(intersector)) {
            object.setIntersector(GsonMarshalUtil.unmarshal(intersector.getAsJsonObject(), context));
        }
        if (isNotNull(tooltip) && tooltip.isJsonObject()) {
            object.setTooltip(GsonMarshalUtil.unmarshal((JsonObject) tooltip));
        }

        JsonElement hScrollBar = json.get(HORIZONTAL_SCROLL_BAR);
        JsonElement vScrollBar = json.get(VERTICAL_SCROLL_BAR);
        JsonElement container = json.get(CONTAINER);

        // this order is very important because container of Scrollable panel contains elements in such order.
        if (isNotNull(vScrollBar)) {
            object.setVerticalScrollBar(GsonMarshalUtil.unmarshal(vScrollBar.getAsJsonObject(), context));
        }
        if (isNotNull(hScrollBar)) {
            object.setHorizontalScrollBar(GsonMarshalUtil.unmarshal(hScrollBar.getAsJsonObject(), context));
        }
        if (isNotNull(container)) {
            object.setContainer(GsonMarshalUtil.unmarshal(container.getAsJsonObject(), context));
        }
        object.resize();
    }
}
