package org.liquidengine.legui.theme.dark.def;

import org.joml.Vector4f;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Tooltip;

/**
 * Dark Tooltip Theme for all tooltips. Used to make tooltip dark.
 *
 * @param <T> tooltip subclasses.
 */
public class DarkTooltipTheme<T extends Tooltip> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(new SimpleLineBorder(new Vector4f(0, 0, 0, 0.9f), 1.2f));
        component.getStyle().getBackground().setColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.9f));
        component.getTextState().setTextColor(ColorConstants.white());
    }
}
