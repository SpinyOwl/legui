package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 *
 * @param <T> {@link Button} subclasses.
 */
public class FlatColoredButtonTheme<T extends Button> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f backgroundColor2;

    public FlatColoredButtonTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
        this.backgroundColor2 = backgroundColor2;
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
        component.getStyle().getBackground().setColor(backgroundColor2);
        component.getHoveredStyle().getBackground().setColor(new Vector4f(backgroundColor2).mul(3).add(ColorUtil.oppositeBlackOrWhite(backgroundColor2)).div(4));
        component.getPressedStyle().getBackground().setColor(new Vector4f(backgroundColor2).mul(2).add(ColorUtil.oppositeBlackOrWhite(backgroundColor2)).div(3));
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
    }
}
