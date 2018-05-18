package org.liquidengine.legui.binding.accessor.component;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 04.12.2017.
 */
public class ComponentChildComponentsAccessor extends AbstractFieldAccessor<Component, List<Component>> {

    /**
     * Used to get childComponents of {@link Component}.
     *
     * @param object {@link Component} instance.
     *
     * @return childComponents of component.
     */
    @Override
    public List<Component> getFieldValue(Component object) {
        return object.getChildComponents();
    }

    @Override
    public void setFieldValue(Component object, List<Component> value) {
        if (value != null && !value.isEmpty()) {
            for (Component listEntry : value) {
                if (listEntry != null) {
                    object.add(listEntry);
                }
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
        return new TypeToken<List<Component>>() {
        }.getType();
    }
}
