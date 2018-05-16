package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Slider Theme for all sliders. Used to make slider dark.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class FlatSliderTheme<T extends Slider> extends FlatComponentTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatSliderTheme(FlatColoredThemeSettings settings) {
        super(settings);
        this.settings = settings;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.setSliderColor(settings.borderColor());
        component.setSliderActiveColor(settings.allowColor());
        component.getStyle().setBorder(null);
    }
}
