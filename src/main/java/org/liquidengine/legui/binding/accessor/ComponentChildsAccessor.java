package org.liquidengine.legui.binding.accessor;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 04.12.2017.
 */
public class ComponentChildsAccessor extends AbstractFieldAccessor<Component> {

    @Override
    public Object getFieldValue(Component object) {
        return object.getChilds();
    }

    @Override
    public void setFieldValue(Component object, Object value) {
    }
}
