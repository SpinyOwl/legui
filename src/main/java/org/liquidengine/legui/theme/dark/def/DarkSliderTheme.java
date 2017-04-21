package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Slider;

/**
 * Dark Slider Theme for all sliders. Used to make slider dark.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class DarkSliderTheme<T extends Slider> extends DarkControllerTheme<T> {
    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        component.setBackgroundColor(ColorConstants.transparent());
        component.setSliderColor(ColorConstants.gray());
        component.setSliderActiveColor(ColorConstants.white());
        component.setBorder(null);
    }
}
