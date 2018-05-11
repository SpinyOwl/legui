package org.liquidengine.legui.theme.colored.def;

import java.util.List;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.theme.AbstractTheme;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark Component Theme for all components. Used to make component dark.
 *
 * @param <T> {@link Component} subclasses.
 */
public class FlatComponentTheme<T extends Component> extends AbstractTheme<T> {

    private final Vector4f borderColor;
    private final Vector4f strokeColor;
    private final Vector4f backgroundColor;

    public FlatComponentTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
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
        component.getStyle().setBorder(new SimpleLineBorder(new Vector4f(borderColor), 1));
        component.getStyle().setBorderRadius(2f);
        component.getStyle().getBackground().setColor(new Vector4f(backgroundColor));
        component.getStyle().setFocusedStrokeColor(new Vector4f(strokeColor));

        Tooltip tooltip = component.getTooltip();
        if (tooltip != null) {
            Themes.getDefaultTheme().applyAll(tooltip);
        }
        List<? extends Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            Themes.getDefaultTheme().applyAll(child);
        }
    }
}