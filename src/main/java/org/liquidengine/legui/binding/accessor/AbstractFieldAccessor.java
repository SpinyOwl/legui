package org.liquidengine.legui.binding.accessor;

import java.lang.reflect.Type;

/**
 * Created by ShchAlexander on 04.12.2017.
 */
public abstract class AbstractFieldAccessor<T> {

    /**
     * Used to get field value from object.
     *
     * @param object object to get field value.
     *
     * @return field value of object.
     */
    public abstract Object getFieldValue(T object);

    /**
     * Used to set field value to object.
     *
     * @param object object to set field value.
     * @param value value to set.
     */
    public abstract void setFieldValue(T object, Object value);

    /**
     * Used to obtain field type.
     *
     * @return field type.
     */
    public abstract Type getFieldType();
}
