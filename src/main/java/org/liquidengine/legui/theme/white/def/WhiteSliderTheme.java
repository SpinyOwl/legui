package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * White Slider Theme for all sliders. Used to make slider white.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class WhiteSliderTheme<T extends Slider> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.setSliderColor(ColorConstants.gray());
        component.setSliderActiveColor(ColorConstants.blue());
    }
}
