package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.shadow.Shadow;

/**
 * Dark ToggleButton Theme for all toggle buttons. Used to make toggle button dark.
 *
 * @param <T> {@link ToggleButton} subclasses.
 */
public class FlatToggleButtonTheme<T extends ToggleButton> extends FlatComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f allowColor;
    private final Vector4f denyColor;

    public FlatToggleButtonTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, borderColor, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
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
        component.getStyle().getBackground().setColor(new Vector4f(denyColor));
        component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, ColorUtil.oppositeBlackOrWhite(backgroundColor).mul(0.8f)));
        component.getHoveredStyle().getBackground().setColor(new Vector4f(denyColor).mul(1.3f, 1.3f, 1.3f, 1f));
        component.getPressedStyle().getBackground().setColor(new Vector4f(denyColor).mul(1.6f, 1.6f, 1.6f, 1f));
        component.setToggledBackgroundColor(new Vector4f(allowColor));
    }
}
