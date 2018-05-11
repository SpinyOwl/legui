package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark ToggleButton Theme for all toggle buttons. Used to make toggle button dark.
 *
 * @param <T> {@link ToggleButton} subclasses.
 */
public class FlatColoredToggleButtonTheme<T extends ToggleButton> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor2;
    private final Vector4f allowColor;
    private final Vector4f denyColor;

    public FlatColoredToggleButtonTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor2 = backgroundColor2;
        this.allowColor = allowColor;
        this.denyColor = denyColor;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(denyColor);
        component.getHoveredStyle().getBackground().setColor(new Vector4f(denyColor).mul(1.3f, 1.3f, 1.3f, 1f));
        component.getPressedStyle().getBackground().setColor(new Vector4f(denyColor).mul(1.6f, 1.6f, 1.6f, 1f));
        component.setToggledBackgroundColor(allowColor);
    }
}
