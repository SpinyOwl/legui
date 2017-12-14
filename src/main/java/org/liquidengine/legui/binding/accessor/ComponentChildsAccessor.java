package org.liquidengine.legui.binding.accessor;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 04.12.2017.
 */
public class ComponentChildsAccessor extends AbstractFieldAccessor<Component, List<Component>> {

    /**
     * Used to get childs of {@link Component}.
     *
     * @param object {@link Component} instance.
     *
     * @return childs of component.
     */
    @Override
    public List<Component> getFieldValue(Component object) {
        return object.getChilds();
    }

    @Override
    public void setFieldValue(Component object, List<Component> value) {
        if (value != null) {
            if (!value.isEmpty()) {
                for (Component listEntry : value) {
                    if (listEntry != null) {
                        object.add(listEntry);
                    }
                }
            }
        }
    }

    @Override
    public Type getFieldType() {
        return new TypeToken<List<Component>>(){}.getType();
    }
}
