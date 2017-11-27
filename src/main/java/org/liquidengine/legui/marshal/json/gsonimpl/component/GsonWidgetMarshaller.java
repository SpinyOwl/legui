package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.JsonConstants.BACKGROUND_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.BORDER;
import static org.liquidengine.legui.marshal.JsonConstants.CLOSEABLE;
import static org.liquidengine.legui.marshal.JsonConstants.CLOSE_BUTTON_BACKGROUND_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.CLOSE_BUTTON_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.CLOSE_ICON;
import static org.liquidengine.legui.marshal.JsonConstants.CONTAINER;
import static org.liquidengine.legui.marshal.JsonConstants.CORNER_RADIUS;
import static org.liquidengine.legui.marshal.JsonConstants.DRAGGABLE;
import static org.liquidengine.legui.marshal.JsonConstants.ENABLED;
import static org.liquidengine.legui.marshal.JsonConstants.HEIGHT;
import static org.liquidengine.legui.marshal.JsonConstants.INTERSECTOR;
import static org.liquidengine.legui.marshal.JsonConstants.MAXIMIZE_ICON;
import static org.liquidengine.legui.marshal.JsonConstants.MINIMIZABLE;
import static org.liquidengine.legui.marshal.JsonConstants.MINIMIZED;
import static org.liquidengine.legui.marshal.JsonConstants.MINIMIZE_ICON;
import static org.liquidengine.legui.marshal.JsonConstants.POSITION;
import static org.liquidengine.legui.marshal.JsonConstants.SIZE;
import static org.liquidengine.legui.marshal.JsonConstants.TAB_FOCUSABLE;
import static org.liquidengine.legui.marshal.JsonConstants.TAB_INDEX;
import static org.liquidengine.legui.marshal.JsonConstants.TITLE;
import static org.liquidengine.legui.marshal.JsonConstants.TITLE_BACKGROUND_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.TITLE_ENABLED;
import static org.liquidengine.legui.marshal.JsonConstants.TITLE_HEIGHT;
import static org.liquidengine.legui.marshal.JsonConstants.TOOLTIP;
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
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

/**
 * Marshaller for {@link Widget}.
 *
 * @param <T> type of component.
 */
public class GsonWidgetMarshaller<T extends Widget> extends AbstractGsonMarshaller<T> {

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
//            .add(BACKGROUND_COLOR, createColor(object.getBackgroundColor()))
            .add(ENABLED, object.isEnabled())
            .add(VISIBLE, object.isVisible())
//            .add(CORNER_RADIUS, object.getCornerRadius())
            .add(TAB_INDEX, object.getTabIndex())
            .add(TAB_FOCUSABLE, object.isTabFocusable())
//            .add(BORDER, GsonMarshalUtil.marshalToJson(object.getBorder(), context))
            .add(INTERSECTOR, GsonMarshalUtil.marshalToJson(object.getIntersector()))
            .add(TOOLTIP, GsonMarshalUtil.marshalToJson(object.getTooltip(), context))
        ;

        Component container = object.getContainer();
        JsonObject cont = GsonMarshalUtil.marshalToJson(container, context);
        fill(json)
            .add(CONTAINER, cont)
//                .add(RESIZABLE, object.isResizable())
            .add(DRAGGABLE, object.isDraggable())
            .add(MINIMIZABLE, object.isMinimizable())
            .add(MINIMIZED, object.isMinimized())
            .add(CLOSEABLE, object.isCloseable())
            .add(TITLE_ENABLED, object.isTitleEnabled())
            .add(TITLE_HEIGHT, object.getTitleHeight())
//            .add(TITLE_BACKGROUND_COLOR, createColor(object.getTitleBackgroundColor()))
            .add(CLOSE_BUTTON_COLOR, createColor(object.getCloseButtonColor()))
//            .add(CLOSE_BUTTON_BACKGROUND_COLOR, createColor(object.getCloseButtonBackgroundColor()))
            .add(TITLE, GsonMarshalUtil.marshalToJson(object.getTitleTextState(), context))
            .add(CLOSE_ICON, GsonMarshalUtil.marshalToJson(object.getCloseIcon(), context))
            .add(MINIMIZE_ICON, GsonMarshalUtil.marshalToJson(object.getMinimizeIcon(), context))
            .add(MAXIMIZE_ICON, GsonMarshalUtil.marshalToJson(object.getMaximizeIcon(), context))
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

        JsonElement tabIndex = json.get(TAB_INDEX);
        JsonElement tabFocusable = json.get(TAB_FOCUSABLE);

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
//        if (isNotNull(bg)) {
//            object.getBackground().setColor(readColor(bg));
//        }
        if (isNotNull(enabled)) {
            object.setEnabled(enabled.getAsBoolean());
        }
        if (isNotNull(visible)) {
            object.setVisible(visible.getAsBoolean());
        }
//        if (isNotNull(cornerRadius)) {
//            object.setCornerRadius(cornerRadius.getAsFloat());
//        }
        if (isNotNull(tabIndex)) {
            object.setTabIndex(tabIndex.getAsInt());
        }
        if (isNotNull(tabFocusable)) {
            object.setTabFocusable(tabFocusable.getAsBoolean());
        }

//        if (isNotNull(border)) {
//            object.setBorder(GsonMarshalUtil.unmarshal(border.getAsJsonObject(), context));
//        } else {
//            object.setBorder(null);
//        }

        if (isNotNull(intersector)) {
            object.setIntersector(GsonMarshalUtil.unmarshal(intersector.getAsJsonObject(), context));
        }
        if (isNotNull(tooltip) && tooltip.isJsonObject()) {
            object.setTooltip(GsonMarshalUtil.unmarshal((JsonObject) tooltip));
        }

        JsonElement container = json.get(CONTAINER);
//        JsonElement resizable = json.get(RESIZABLE);
        JsonElement draggable = json.get(DRAGGABLE);
        JsonElement minimized = json.get(MINIMIZED);
        JsonElement minimizable = json.get(MINIMIZABLE);
        JsonElement closeable = json.get(CLOSEABLE);
        JsonElement titleEnabled = json.get(TITLE_ENABLED);
        JsonElement titleHeight = json.get(TITLE_HEIGHT);
        JsonElement titleBackgroundColor = json.get(TITLE_BACKGROUND_COLOR);
        JsonElement closeButtonColor = json.get(CLOSE_BUTTON_COLOR);
        JsonElement closeButtonBackgroundColor = json.get(CLOSE_BUTTON_BACKGROUND_COLOR);
        JsonElement title = json.get(TITLE);
        JsonElement closeIcon = json.get(CLOSE_ICON);
        JsonElement minimizeIcon = json.get(MINIMIZE_ICON);
        JsonElement maximizeIcon = json.get(MAXIMIZE_ICON);

        if (isNotNull(container)) {
            object.setContainer(GsonMarshalUtil.unmarshal(container.getAsJsonObject(), context));
        }
//        if (isNotNull(resizable)) object.setResizable(resizable.getAsBoolean());
        if (isNotNull(title)) {
            object.getTitleTextState().copy(GsonMarshalUtil.unmarshal(title.getAsJsonObject(), context));
        }
        if (isNotNull(titleHeight)) {
            object.setTitleHeight(titleHeight.getAsFloat());
        }
//        if (isNotNull(titleBackgroundColor)) {
//            object.setTitleBackgroundColor(readColor(titleBackgroundColor.getAsJsonObject()));
//        }
        if (isNotNull(closeButtonColor)) {
            object.setCloseButtonColor(readColor(closeButtonColor.getAsJsonObject()));
        }
//        if (isNotNull(closeButtonBackgroundColor)) {
//            object.setCloseButtonBackgroundColor(readColor(closeButtonBackgroundColor.getAsJsonObject()));
//        }
        if (isNotNull(draggable)) {
            object.setDraggable(draggable.getAsBoolean());
        }
        if (isNotNull(closeable)) {
            object.setCloseable(closeable.getAsBoolean());
        }
        if (isNotNull(titleEnabled)) {
            object.setTitleEnabled(titleEnabled.getAsBoolean());
        }
        if (isNotNull(minimized)) {
            object.setMinimized(minimized.getAsBoolean());
        }
        if (isNotNull(minimizable)) {
            object.setMinimizable(minimizable.getAsBoolean());
        }
        if (isNotNull(closeIcon)) {
            object.setCloseIcon(GsonMarshalUtil.unmarshal(closeIcon.getAsJsonObject(), context));
        }
        if (isNotNull(minimizeIcon)) {
            object.setMinimizeIcon(GsonMarshalUtil.unmarshal(minimizeIcon.getAsJsonObject(), context));
        }
        if (isNotNull(maximizeIcon)) {
            object.setMaximizeIcon(GsonMarshalUtil.unmarshal(maximizeIcon.getAsJsonObject(), context));
        }

        object.resize();
    }
}
