package org.liquidengine.legui.binding.accessor.selectbox;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.component.SelectBox;

/**
 * Accessor for selectbox elements.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class SelectBoxElementsAccessor extends AbstractFieldAccessor<SelectBox, List<String>> {

    /**
     * Used to get field value from object.
     *
     * @param object object to get field value.
     * @return field value of object.
     */
    @Override
    public List<String> getFieldValue(SelectBox object) {
        return object.getElements();
    }

    /**
     * Used to set field value to object.
     *
     * @param object object to set field value.
     * @param value value to set.
     */
    @Override
    public void setFieldValue(SelectBox object, List<String> value) {
        for (String s : value) {
            object.addElement(s);
        }
    }

    /**
     * Used to obtain field type.
     *
     * @return field type.
     */
    @Override
    public Type getFieldType() {
        return new TypeToken<List<String>>() {
        }.getType();
    }
}
