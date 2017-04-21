package org.liquidengine.legui.theme.dark.def;

import org.joml.Vector4f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Tooltip;

/**
 * Dark Tooltip Theme for all tooltips. Used to make tooltip dark.
 */
public class DarkTooltipTheme<T extends Tooltip> extends DarkComponentTheme<T> {
    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        component.setBorder(new SimpleLineBorder(new Vector4f(0, 0, 0, 0.9f), 1.2f));
        component.setBackgroundColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.9f));
        component.getTextState().setTextColor(ColorConstants.white());
    }
}
