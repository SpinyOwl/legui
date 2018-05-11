package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.shadow.Shadow;

/**
 * Dark Tooltip Theme for all tooltips. Used to make tooltip dark.
 *
 * @param <T> tooltip subclasses.
 */
public class FlatTooltipTheme<T extends Tooltip> extends FlatComponentTheme<T> {

    private final Vector4f backgroundColor;

    public FlatTooltipTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, borderColor, strokeColor, allowColor, denyColor);
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
        Vector4f bgc = ColorUtil.negativeColorRGB(backgroundColor);
        component.getStyle().getBackground().setColor(bgc);
        component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, ColorUtil.oppositeBlackOrWhite(bgc).mul(0.8f)));

        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(bgc));
    }
}
