package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Dark Slider Theme for all sliders. Used to make slider dark.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class FlatSliderTheme<T extends Slider> extends FlatComponentTheme<T> {

    private final Vector4f borderColor;
    private final Vector4f allowColor;

    public FlatSliderTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, borderColor, strokeColor, allowColor, denyColor);
        this.borderColor = borderColor;
        this.allowColor = allowColor;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.setSliderColor(new Vector4f(borderColor));
        component.setSliderActiveColor(new Vector4f(allowColor));
        component.getStyle().setBorder(null);
    }
}
