package org.liquidengine.legui.binding.accessor;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 04.12.2017.
 */
public class ComponentChildsAccessor extends AbstractFieldAccessor<Component> {

    /**
     * Used to get childs of {@link Component}.
     *
     * @param object {@link Component} instance.
     *
     * @return childs of component.
     */
    @Override
    public Object getFieldValue(Component object) {
        return object.getChilds();
    }

    @Override
    public void setFieldValue(Component object, Object value) {
        if (value instanceof List) {
            List vList = (List) value;
            if (!vList.isEmpty()) {
                for (Object listEntry : vList) {
                    if (listEntry instanceof Component) {
                        object.add((Component) listEntry);
                    }
                }
            }
        }
    }

    @Override
    public Type getFieldType() {
        return new TypeToken<List<Component>>() {}.getType();
    }
}
