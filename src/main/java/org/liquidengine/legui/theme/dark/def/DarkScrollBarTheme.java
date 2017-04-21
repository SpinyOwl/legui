package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.ScrollBar;

/**
 * Dark ScrollBar Theme for all scroll bars. Used to make scroll bar dark.
 */
public class DarkScrollBarTheme<T extends ScrollBar> extends DarkControllerTheme<T> {
    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        component.setArrowColor(ColorConstants.white());
        component.setScrollColor(ColorConstants.white());
        component.setArrowsEnabled(true);
    }
}
