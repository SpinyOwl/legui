package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark ToggleButton Theme for all toggle buttons. Used to make toggle button dark.
 *
 * @param <T> {@link ToggleButton} subclasses.
 */
public class FlatToggleButtonTheme<T extends ToggleButton> extends FlatComponentTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatToggleButtonTheme() {
    }

    public FlatToggleButtonTheme(FlatColoredThemeSettings settings) {
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
        component.getStyle().getBackground().setColor(settings.denyColor());

        component.getHoveredStyle().getBackground().setColor(settings.denyColor().mul(1.3f, 1.3f, 1.3f, 1f));
        component.getPressedStyle().getBackground().setColor(settings.denyColor().mul(1.6f, 1.6f, 1.6f, 1f));
        component.setToggledBackgroundColor(settings.allowColor());
    }
}
