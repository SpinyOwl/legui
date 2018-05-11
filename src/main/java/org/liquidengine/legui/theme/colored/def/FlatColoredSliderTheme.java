package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Dark Slider Theme for all sliders. Used to make slider dark.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class FlatColoredSliderTheme<T extends Slider> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor2;
    private final Vector4f allowColor;

    public FlatColoredSliderTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor2 = backgroundColor2;
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
        component.setSliderColor(backgroundColor2);
        component.setSliderActiveColor(allowColor);
        component.getStyle().setBorder(null);
    }
}
