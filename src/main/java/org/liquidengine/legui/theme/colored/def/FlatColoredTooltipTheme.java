package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark Tooltip Theme for all tooltips. Used to make tooltip dark.
 *
 * @param <T> tooltip subclasses.
 */
public class FlatColoredTooltipTheme<T extends Tooltip> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor;

    public FlatColoredTooltipTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(null);
        component.getStyle().getBackground().setColor(ColorUtil.negativeColorRGB(backgroundColor));
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(ColorUtil.negativeColorRGB(backgroundColor)));
    }
}
