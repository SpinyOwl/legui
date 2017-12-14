package org.liquidengine.legui.binding.accessor.frame;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class FrameLayersAccessor extends AbstractFieldAccessor<Frame, List<Layer>> {

    /**
     * Used to get field value from object.
     *
     * @param object object to get field value.
     *
     * @return field value of object.
     */
    @Override
    public List<Layer> getFieldValue(Frame object) {
        return object.getLayers();
    }

    /**
     * Used to set field value to object.
     *
     * @param object object to set field value.
     * @param value value to set.
     */
    @Override
    public void setFieldValue(Frame object, List<Layer> value) {
        if (value != null) {
            for (Layer layer : value) {
                object.addLayer(layer);
            }
        }
    }

    /**
     * Used to obtain field type.
     *
     * @return field type.
     */
    @Override
    public Type getFieldType() {
        return new TypeToken<List<Layer>>() {
        }.getType();
    }
}
