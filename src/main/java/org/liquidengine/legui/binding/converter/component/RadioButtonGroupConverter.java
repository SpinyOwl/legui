package org.liquidengine.legui.binding.converter.component;

import org.liquidengine.legui.binding.converter.AbstractClassConverter;
import org.liquidengine.legui.component.RadioButtonGroup;

/**
 * Converter for radiobutton group which used in radiobutton.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class RadioButtonGroupConverter extends AbstractClassConverter<RadioButtonGroup> {

    /**
     * Used to convert java object to string representation.
     *
     * @param o object to convert.
     * @return string representation of object.
     */
    @Override
    public String convertFromJava(RadioButtonGroup o) {
        return Integer.toString(o.getIndex());
    }

    /**
     * Used to convert string representation to java object.
     *
     * @param o string representation of object to convert.
     * @return object of string representation.
     */
    @Override
    public RadioButtonGroup convertToJava(String o) {
        int index = 0;
        try {
            index = Integer.parseInt(o);
        } catch (NumberFormatException ignored) {
            // do nothing
        }
        if (index == 0) {
            return new RadioButtonGroup();
        } else {
            return RadioButtonGroup.getGroupByIndex(index);
        }
    }
}
