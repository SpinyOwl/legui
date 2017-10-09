package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.ScrollBar;

/**
 * Dark ScrollBar Theme for all scroll bars. Used to make scroll bar dark.
 *
 * @param <T> {@link ScrollBar} subclasses.
 */
public class DarkScrollBarTheme<T extends ScrollBar> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.setArrowColor(ColorConstants.white());
        component.setScrollColor(ColorConstants.white());
        component.setArrowsEnabled(true);
    }
}
