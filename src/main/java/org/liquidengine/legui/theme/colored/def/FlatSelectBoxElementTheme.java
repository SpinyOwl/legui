package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark SelectBox Theme for all select boxes. Used to make select box dark.
 *
 * @param <T> {@link SelectBox.SelectBoxElement} subclasses.
 */
public class FlatSelectBoxElementTheme<T extends SelectBox.SelectBoxElement> extends FlatButtonTheme<T> {

    private final Vector4f backgroundColor;

    public FlatSelectBoxElementTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor,
        Vector4f denyColor) {
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
        component.getStyle().setShadow(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor));
    }
}
