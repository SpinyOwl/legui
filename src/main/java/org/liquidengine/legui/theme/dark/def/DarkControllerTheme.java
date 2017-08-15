package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark theme for controller instances.
 *
 * @param <T> {@link Controller} subclasses.
 */
public class DarkControllerTheme<T extends Controller> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        Tooltip tooltip = component.getTooltip();
        if (tooltip != null) {
            Themes.getDefaultTheme().applyAll(tooltip);
        }
    }
}
