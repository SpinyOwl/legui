package org.liquidengine.legui.theme.dark.def;

import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.theme.AbstractTheme;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark Component Theme for all components. Used to make component dark.
 *
 * @param <T> {@link Component} subclasses.
 */
public class DarkComponentTheme<T extends Component> extends AbstractTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(new SimpleLineBorder(ColorConstants.lightGray(), 1.2f));
        component.getStyle().setBorderRadius(2f);
        component.getStyle().getBackground().setColor(ColorConstants.darkGray());
        Tooltip tooltip = component.getTooltip();
        if (tooltip != null) {
            Themes.getDefaultTheme().applyAll(tooltip);
        }
        List<? extends Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            Themes.getDefaultTheme().applyAll(child);
        }
    }
}