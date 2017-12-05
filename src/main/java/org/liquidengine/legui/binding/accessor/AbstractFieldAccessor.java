package org.liquidengine.legui.binding.accessor;

import java.lang.reflect.Type;

/**
 * Created by ShchAlexander on 04.12.2017.
 */
public abstract class AbstractFieldAccessor<T> {

    public abstract Object getFieldValue(T object);

    public abstract void setFieldValue(T object, Object value);

    public abstract Type getFieldType();
}
