package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Dark ProgressBar Theme for all progress bars. Used to make progress bar dark.
 *
 * @param <T> {@link ProgressBar} subclasses.
 */
public class FlatColoredProgressBarTheme<T extends ProgressBar> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor2;
    private final Vector4f allowColor;

    public FlatColoredProgressBarTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
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
        component.getStyle().setBorder(new SimpleLineBorder(new Vector4f(backgroundColor2), 1));
        component.setProgressColor(allowColor);
    }
}
