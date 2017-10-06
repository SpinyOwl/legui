package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.ScrollBar;

/**
 * White ScrollBar Theme for all scroll bars. Used to make scroll bar white.
 *
 * @param <T> {@link ScrollBar} subclasses.
 */
public class WhiteScrollBarTheme<T extends ScrollBar> extends WhiteControllerTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.setArrowColor(ColorConstants.gray());
        component.setScrollColor(ColorConstants.gray());
        component.setArrowsEnabled(true);
    }
}
