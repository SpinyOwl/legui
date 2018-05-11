package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.style.border.SimpleLineBorder;

/**
 * Dark ProgressBar Theme for all progress bars. Used to make progress bar dark.
 *
 * @param <T> {@link ProgressBar} subclasses.
 */
public class FlatProgressBarTheme<T extends ProgressBar> extends FlatComponentTheme<T> {

    private final Vector4f borderColor;
    private final Vector4f allowColor;

    public FlatProgressBarTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
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
        component.getStyle().setBorder(new SimpleLineBorder(new Vector4f(borderColor), 1));
        component.setProgressColor(allowColor);
    }
}
