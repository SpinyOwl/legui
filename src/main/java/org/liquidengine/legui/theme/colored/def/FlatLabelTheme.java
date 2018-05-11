package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark Label Theme for all labels. Used to make label dark.
 *
 * @param <T> {@link Label} subclasses.
 */
public class FlatLabelTheme<T extends Label> extends FlatComponentTheme<T> {

    private final Vector4f backgroundColor;

    public FlatLabelTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
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
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor));
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}
