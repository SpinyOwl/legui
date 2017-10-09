package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.JsonConstants.BUTTON_WIDTH;
import static org.liquidengine.legui.marshal.JsonConstants.COLLAPSE_ICON;
import static org.liquidengine.legui.marshal.JsonConstants.ELEMENTS;
import static org.liquidengine.legui.marshal.JsonConstants.ELEMENT_HEIGHT;
import static org.liquidengine.legui.marshal.JsonConstants.EXPAND_ICON;
import static org.liquidengine.legui.marshal.JsonConstants.SELECTED_ELEMENT;
import static org.liquidengine.legui.marshal.JsonConstants.VISIBLE_COUNT;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

/**
 * Marshaller for {@link SelectBox}.
 */
public class GsonSelectBoxMarshaller<T extends SelectBox> extends GsonComponentMarshaller<T> {

    /**
     * Reads data from object and puts it to json object.
     *
     * @param object object to read.
     * @param json json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);

        GsonUtil.fill(json)
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
        super.unmarshal(json, object, context);

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
