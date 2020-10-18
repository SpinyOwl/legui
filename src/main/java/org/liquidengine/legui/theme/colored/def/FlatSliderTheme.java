package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Slider Theme for all sliders. Used to make slider dark.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class FlatSliderTheme<T extends Slider> extends FlatBorderlessTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatSliderTheme() {
    }

    public FlatSliderTheme(FlatColoredThemeSettings settings) {
        super(settings);
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.setSliderColor(settings.sliderColor());
        component.setSliderActiveColor(settings.allowColor());
    }
}
