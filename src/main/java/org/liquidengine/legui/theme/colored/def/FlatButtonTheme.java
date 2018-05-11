package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.shadow.Shadow;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 *
 * @param <T> {@link Button} subclasses.
 */
public class FlatButtonTheme<T extends Button> extends FlatComponentTheme<T> {

    private final Vector4f backgroundColor;

    public FlatButtonTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
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
        component.getStyle().getBackground().setColor(new Vector4f(backgroundColor));
        component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, ColorUtil.oppositeBlackOrWhite(backgroundColor).mul(0.8f)));

        component.getHoveredStyle().getBackground().setColor(new Vector4f(backgroundColor).mul(3).add(ColorUtil.oppositeBlackOrWhite(backgroundColor)).div(4));
        component.getPressedStyle().getBackground().setColor(new Vector4f(backgroundColor).mul(2).add(ColorUtil.oppositeBlackOrWhite(backgroundColor)).div(3));
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor));
    }
}
