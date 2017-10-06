package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.ToggleButton;

/**
 * White ToggleButton Theme for all toggle buttons. Used to make toggle button white.
 *
 * @param <T> {@link ToggleButton} subclasses.
 */
public class WhiteToggleButtonTheme<T extends ToggleButton> extends WhiteControllerTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBackgroundColor(ColorConstants.red());
        component.setToggledBackgroundColor(ColorConstants.green());
    }
}
