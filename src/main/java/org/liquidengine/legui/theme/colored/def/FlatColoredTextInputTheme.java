package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark TextInput Theme for all text inputs. Used to make text input dark.
 *
 * @param <T> {@link TextInput} subclasses.
 */
public class FlatColoredTextInputTheme<T extends TextInput> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor2;

    public FlatColoredTextInputTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
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
        Vector4f bgc = ColorUtil.oppositeBlackOrWhite(backgroundColor2);
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(bgc));
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHighlightColor(new Vector4f(bgc).add(ColorUtil.oppositeBlackOrWhite(bgc)).div(2));
        component.getStyle().getBackground().setColor(bgc);
    }
}
