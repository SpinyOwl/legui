package org.liquidengine.legui.binding.accessor;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
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
        List<Component> components = (List<Component>) value;
        for (Component component : components) {
            object.add(component);
        }
    }

    @Override
    public Type getFieldType() {
        return new TypeToken<List<Component>>(){}.getType();
    }
}
