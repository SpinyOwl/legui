package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark ScrollBar Theme for all scroll bars. Used to make scroll bar dark.
 *
 * @param <T> {@link ScrollBar} subclasses.
 */
public class FlatScrollBarTheme<T extends ScrollBar> extends FlatComponentTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatScrollBarTheme() {
    }

    public FlatScrollBarTheme(FlatColoredThemeSettings settings) {
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
        component.getStyle().setShadow(null);
        component.getStyle().getBackground().setColor(settings.sliderColor());
        component.setArrowColor(ColorUtil.oppositeBlackOrWhite(settings.borderColor()));
        component.setScrollColor(settings.backgroundColor());
        component.setArrowsEnabled(false);
    }
}
