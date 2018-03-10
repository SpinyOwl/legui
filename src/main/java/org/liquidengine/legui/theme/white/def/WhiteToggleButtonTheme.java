package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * White ToggleButton Theme for all toggle buttons. Used to make toggle button white.
 *
 * @param <T> {@link ToggleButton} subclasses.
 */
public class WhiteToggleButtonTheme<T extends ToggleButton> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(ColorConstants.red());
        component.setToggledBackgroundColor(ColorConstants.green());
    }
}
