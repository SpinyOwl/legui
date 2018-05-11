package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark ScrollBar Theme for all scroll bars. Used to make scroll bar dark.
 *
 * @param <T> {@link ScrollBar} subclasses.
 */
public class FlatScrollBarTheme<T extends ScrollBar> extends FlatComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f borderColor;

    public FlatScrollBarTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, borderColor, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(new Vector4f(borderColor));
        component.setArrowColor(ColorUtil.oppositeBlackOrWhite(borderColor));
        component.setScrollColor(new Vector4f(backgroundColor));
        component.setArrowsEnabled(false);
    }
}
