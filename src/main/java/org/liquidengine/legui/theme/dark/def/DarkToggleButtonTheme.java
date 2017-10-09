package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.ToggleButton;

/**
 * Dark ToggleButton Theme for all toggle buttons. Used to make toggle button dark.
 *
 * @param <T> {@link ToggleButton} subclasses.
 */
public class DarkToggleButtonTheme<T extends ToggleButton> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBackgroundColor(ColorConstants.darkRed());
        component.setToggledBackgroundColor(ColorConstants.darkGreen());
    }
}
