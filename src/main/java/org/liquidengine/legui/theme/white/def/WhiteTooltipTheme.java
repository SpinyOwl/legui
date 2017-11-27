package org.liquidengine.legui.theme.white.def;

import org.joml.Vector4f;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Tooltip;

/**
 * White Tooltip Theme for all tooltips. Used to make tooltip white.
 *
 * @param <T> tooltip subclasses.
 */
public class WhiteTooltipTheme<T extends Tooltip> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBorder(new SimpleLineBorder(new Vector4f(0, 0, 0, 0.9f), 1.2f));
        component.setBackgroundColor(new Vector4f(1f, 1f, 1f, 0.9f));
        component.getTextState().setTextColor(ColorConstants.black());
    }
}
