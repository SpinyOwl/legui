package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.shadow.Shadow;

/**
 * Dark TextArea Theme for all text areas. Used to make text area dark.
 *
 * @param <T> {@link TextArea} subclasses.
 */
public class FlatTextAreaTheme<T extends TextArea> extends FlatComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f strokeColor;

    public FlatTextAreaTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, borderColor, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
        this.strokeColor = strokeColor;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, ColorUtil.oppositeBlackOrWhite(backgroundColor).mul(0.8f)));
        component.getFocusedStyle().getBackground().setColor(new Vector4f(backgroundColor).mul(3).add(ColorUtil.oppositeBlackOrWhite(backgroundColor)).div(4));
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor));
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHighlightColor(new Vector4f(strokeColor));
        component.getStyle().getBackground().setColor(new Vector4f(backgroundColor));
    }
}
