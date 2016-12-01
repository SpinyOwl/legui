package org.liquidengine.legui.serialize.json.gson.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.serialize.json.gson.GsonUtil;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeContext;

import java.util.List;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;
import static org.liquidengine.legui.serialize.json.gson.GsonUtil.isNotNull;

/**
 * Created by Alexander on 30.11.2016.
 */
public class GsonSelectBoxSerializer extends GsonComponentSerializer<SelectBox> {

    @Override
    protected void jsonSerialize(SelectBox object, JsonObject json, GsonSerializeContext context) {
        super.jsonSerialize(object, json, context);

        GsonUtil.fill(json)
                .add(SELECTED_ELEMENT, object.getSelection())
                .add(ELEMENT_HEIGHT, object.getElementHeight())
                .add(BUTTON_WIDTH, object.getButtonWidth())
                .add(VISIBLE_COUNT, object.getVisibleCount())
        ;
        List<String> elements = object.getElements();
        JsonArray jsonElements = new JsonArray();
        for (String element : elements) {
            jsonElements.add(element);
        }
        json.add(ELEMENTS, jsonElements);

    }

    @Override
    protected void jsonDeserialize(JsonObject json, SelectBox object, GsonSerializeContext context) {
        super.jsonDeserialize(json, object, context);

        JsonElement elements = json.get(ELEMENTS);
        JsonElement selectedElement = json.get(SELECTED_ELEMENT);
        JsonElement elementHeight = json.get(ELEMENT_HEIGHT);
        JsonElement buttonWidth = json.get(BUTTON_WIDTH);
        JsonElement visibleCount = json.get(VISIBLE_COUNT);

        if (isNotNull(elements) && elements.isJsonArray()) {
            JsonArray el = elements.getAsJsonArray();
            for (JsonElement jsonElement : el) {
                object.addElement(jsonElement.getAsString());
            }
        }

        if (isNotNull(selectedElement)) object.setSelected(selectedElement.getAsString(), true);
        if (isNotNull(elementHeight)) object.setElementHeight(elementHeight.getAsFloat());
        if (isNotNull(buttonWidth)) object.setButtonWidth(buttonWidth.getAsFloat());
        if (isNotNull(visibleCount)) object.setVisibleCount(visibleCount.getAsInt());
    }
}
