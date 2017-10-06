package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.theme.Themes;

/**
 * White theme for controller instances.
 *
 * @param <T> {@link Controller} subclasses.
 */
public class WhiteControllerTheme<T extends Controller> extends WhiteComponentTheme<T> {

    @Override
    public void applyAll(T component) {
        applyAll(component);
        Tooltip tooltip = component.getTooltip();
        if (tooltip != null) {
            Themes.getDefaultTheme().applyAll(tooltip);
        }
    }
}
