package org.liquidengine.legui.binding.accessor.frame;

import java.lang.reflect.Type;
import org.joml.Vector2f;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.component.Frame;

/**
 * Frame size accessor. Created cause there is no such field but you can set it during initialization.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class FrameSizeAccessor extends AbstractFieldAccessor<Frame, Vector2f> {

    /**
     * Used to get field value from object.
     *
     * @param object object to get field value.
     *
     * @return field value of object.
     */
    @Override
    public Vector2f getFieldValue(Frame object) {
        return object.getContainer().getSize();
    }

    /**
     * Used to set field value to object.
     *
     * @param object object to set field value.
     * @param value value to set.
     */
    @Override
    public void setFieldValue(Frame object, Vector2f value) {
        object.setSize(value);
    }

    /**
     * Used to obtain field type.
     *
     * @return field type.
     */
    @Override
    public Type getFieldType() {
        return Vector2f.class;
    }
}
