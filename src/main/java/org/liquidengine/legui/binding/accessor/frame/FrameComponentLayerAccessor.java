package org.liquidengine.legui.binding.accessor.frame;

import java.lang.reflect.Type;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentLayer;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;

/**
 * Accessor for component layer in frame.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class FrameComponentLayerAccessor extends AbstractFieldAccessor<Frame, Layer<Component>> {

    /**
     * Used to get field value from object.
     *
     * @param object object to get field value.
     * @return field value of object.
     */
    @Override
    public Layer<Component> getFieldValue(Frame object) {
        return object.getComponentLayer();
    }

    /**
     * Used to set field value to object.
     *
     * @param object object to set field value.
     * @param value value to set.
     */
    @Override
    public void setFieldValue(Frame object, Layer<Component> value) {
        if (value != null) {
            object.getComponentLayer().setContainer(value.getContainer());
            object.getComponentLayer().setEventPassable(value.isEventPassable());
            object.getComponentLayer().setEventReceivable(value.isEventReceivable());
        }
    }

    /**
     * Used to obtain field type.
     *
     * @return field type.
     */
    @Override
    public Type getFieldType() {
        return ComponentLayer.class;
    }
}
