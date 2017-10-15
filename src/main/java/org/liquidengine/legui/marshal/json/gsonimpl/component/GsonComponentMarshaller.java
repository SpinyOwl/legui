package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.JsonConstants.BACKGROUND_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.BORDER;
import static org.liquidengine.legui.marshal.JsonConstants.COMPONENTS;
import static org.liquidengine.legui.marshal.JsonConstants.CORNER_RADIUS;
import static org.liquidengine.legui.marshal.JsonConstants.ENABLED;
import static org.liquidengine.legui.marshal.JsonConstants.HEIGHT;
import static org.liquidengine.legui.marshal.JsonConstants.INTERSECTOR;
import static org.liquidengine.legui.marshal.JsonConstants.POSITION;
import static org.liquidengine.legui.marshal.JsonConstants.SIZE;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

/**
 * Marshaller for {@link Component}.
 */
public class GsonComponentMarshaller<T extends Component> extends AbstractGsonMarshaller<T> {


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

        if (!object.isEmpty()) {
            List<Component> components = object.getChilds();
            JsonArray comps = new JsonArray();
            for (Component component : components) {
                comps.add(GsonMarshalUtil.marshalToJson(component, context));
            }
            json.add(COMPONENTS, comps);
        }
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

        List<Component> componentList = new ArrayList<>();
        JsonElement components = json.get(COMPONENTS);
        if (isNotNull(components) && components.isJsonArray()) {
            JsonArray comps = components.getAsJsonArray();
            for (JsonElement comp : comps) {
                processComponent(context, componentList, comp);
            }
        }
        object.addAll(componentList);
    }

    private void processComponent(GsonMarshalContext context, List<Component> componentList, JsonElement comp) {
        if (isNotNull(comp) && comp.isJsonObject()) {
            Object o = GsonMarshalUtil.unmarshal(comp.getAsJsonObject(), context);
            if (o instanceof Component) {
                componentList.add((Component) o);
            }
        }
    }
}
