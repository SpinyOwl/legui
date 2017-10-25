package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Slider;

/**
 * White Slider Theme for all sliders. Used to make slider white.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class WhiteSliderTheme<T extends Slider> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBorder(null);
        component.setBackgroundColor(ColorConstants.transparent());
        component.setSliderColor(ColorConstants.gray());
        component.setSliderActiveColor(ColorConstants.blue());
    }
}
