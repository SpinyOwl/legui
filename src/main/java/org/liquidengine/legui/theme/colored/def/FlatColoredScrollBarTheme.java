package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark ScrollBar Theme for all scroll bars. Used to make scroll bar dark.
 *
 * @param <T> {@link ScrollBar} subclasses.
 */
public class FlatColoredScrollBarTheme<T extends ScrollBar> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f backgroundColor2;

    public FlatColoredScrollBarTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
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
        component.getStyle().getBackground().setColor(backgroundColor2);
        component.setArrowColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
        component.setScrollColor(backgroundColor);
        component.setArrowsEnabled(false);
    }
}
