package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.style.color.ColorConstants;

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
        component.getStyle().getBackground().setColor(ColorConstants.darkRed());
        component.getHoveredStyle().getBackground().setColor(ColorConstants.darkRed().mul(1.3f, 1.3f, 1.3f, 1f));
        component.getPressedStyle().getBackground().setColor(ColorConstants.darkRed().mul(1.6f, 1.6f, 1.6f, 1f));
        component.setToggledBackgroundColor(ColorConstants.darkGreen());
    }
}
