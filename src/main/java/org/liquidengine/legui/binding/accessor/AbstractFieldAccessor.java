package org.liquidengine.legui.binding.accessor;

import java.lang.reflect.Type;

/**
 * Field accessor. Used to access field (get/set) and retrieve field type.
 *
 * @param <T> type of class binding.
 * @param <V> type of field value.
 * <p>
 * Created by ShchAlexander on 04.12.2017.
 */
public abstract class AbstractFieldAccessor<T, V> {

    /**
     * Used to get field value from object.
     *
     * @param object object to get field value.
     *
     * @return field value of object.
     */
    public abstract V getFieldValue(T object);

    /**
     * Used to set field value to object.
     *
     * @param object object to set field value.
     * @param value value to set.
     */
    public abstract void setFieldValue(T object, V value);

    /**
     * Used to obtain field type.
     *
     * @return field type.
     */
    public abstract Type getFieldType();
}
