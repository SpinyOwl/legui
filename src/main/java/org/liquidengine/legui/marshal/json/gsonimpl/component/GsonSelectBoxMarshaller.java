package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.JsonConstants.BACKGROUND_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.BORDER;
import static org.liquidengine.legui.marshal.JsonConstants.BUTTON_WIDTH;
import static org.liquidengine.legui.marshal.JsonConstants.COLLAPSE_ICON;
import static org.liquidengine.legui.marshal.JsonConstants.CORNER_RADIUS;
import static org.liquidengine.legui.marshal.JsonConstants.ELEMENTS;
import static org.liquidengine.legui.marshal.JsonConstants.ELEMENT_HEIGHT;
import static org.liquidengine.legui.marshal.JsonConstants.ENABLED;
import static org.liquidengine.legui.marshal.JsonConstants.EXPAND_ICON;
import static org.liquidengine.legui.marshal.JsonConstants.HEIGHT;
import static org.liquidengine.legui.marshal.JsonConstants.INTERSECTOR;
import static org.liquidengine.legui.marshal.JsonConstants.POSITION;
import static org.liquidengine.legui.marshal.JsonConstants.SELECTED_ELEMENT;
import static org.liquidengine.legui.marshal.JsonConstants.SIZE;
import static org.liquidengine.legui.marshal.JsonConstants.TAB_FOCUSABLE;
import static org.liquidengine.legui.marshal.JsonConstants.TAB_INDEX;
import static org.liquidengine.legui.marshal.JsonConstants.TOOLTIP;
import static org.liquidengine.legui.marshal.JsonConstants.VISIBLE;
import static org.liquidengine.legui.marshal.JsonConstants.VISIBLE_COUNT;
import static org.liquidengine.legui.marshal.JsonConstants.WIDTH;
import static org.liquidengine.legui.marshal.JsonConstants.X;
import static org.liquidengine.legui.marshal.JsonConstants.Y;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.create;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.createColor;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.fill;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.readColor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

/**
 * Marshaller for {@link SelectBox}.
 *
 * @param <T> type of component.
 */
public class GsonSelectBoxMarshaller<T extends SelectBox> extends AbstractGsonMarshaller<T> {

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
            .add(TAB_INDEX, object.getTabIndex())
            .add(TAB_FOCUSABLE, object.isTabFocusable())
            .add(BORDER, GsonMarshalUtil.marshalToJson(object.getBorder(), context))
            .add(INTERSECTOR, GsonMarshalUtil.marshalToJson(object.getIntersector()))
            .add(TOOLTIP, GsonMarshalUtil.marshalToJson(object.getTooltip(), context))
        ;

        fill(json)
            .add(SELECTED_ELEMENT, object.getSelection())
            .add(ELEMENT_HEIGHT, object.getElementHeight())
            .add(BUTTON_WIDTH, object.getButtonWidth())
            .add(VISIBLE_COUNT, object.getVisibleCount())
            .add(COLLAPSE_ICON, GsonMarshalUtil.marshalToJson(object.getCollapseIcon(), context))
            .add(EXPAND_ICON, GsonMarshalUtil.marshalToJson(object.getExpandIcon(), context))
        ;
        List<String> elements = object.getElements();
        JsonArray jsonElements = new JsonArray();
        for (String element : elements) {
            jsonElements.add(element);
        }
        json.add(ELEMENTS, jsonElements);
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
        if(isNotNull(tabIndex)) {
            object.setTabIndex(tabIndex.getAsInt());
        }
        if(isNotNull(tabFocusable)) {
            object.setTabFocusable(tabFocusable.getAsBoolean());
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

        JsonElement elements = json.get(ELEMENTS);
        JsonElement selectedElement = json.get(SELECTED_ELEMENT);
        JsonElement elementHeight = json.get(ELEMENT_HEIGHT);
        JsonElement buttonWidth = json.get(BUTTON_WIDTH);
        JsonElement visibleCount = json.get(VISIBLE_COUNT);
        JsonElement collapseIcon = json.get(COLLAPSE_ICON);
        JsonElement expandIcon = json.get(EXPAND_ICON);

        if (isNotNull(elements) && elements.isJsonArray()) {
            JsonArray el = elements.getAsJsonArray();
            for (JsonElement jsonElement : el) {
                object.addElement(jsonElement.getAsString());
            }
        }

        if (isNotNull(selectedElement)) {
            object.setSelected(selectedElement.getAsString(), true);
        }
        if (isNotNull(elementHeight)) {
            object.setElementHeight(elementHeight.getAsFloat());
        }
        if (isNotNull(buttonWidth)) {
            object.setButtonWidth(buttonWidth.getAsFloat());
        }
        if (isNotNull(visibleCount)) {
            object.setVisibleCount(visibleCount.getAsInt());
        }
        if (isNotNull(collapseIcon)) {
            object.setCollapseIcon(GsonMarshalUtil.unmarshal(collapseIcon.getAsJsonObject(), context));
        }
        if (isNotNull(expandIcon)) {
            object.setExpandIcon(GsonMarshalUtil.unmarshal(expandIcon.getAsJsonObject(), context));
        }

    }
}
