package org.liquidengine.legui.theme.white.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.ProgressBar;

/**
 * White ProgressBar Theme for all progress bars. Used to make progress bar white.
 *
 * @param <T> {@link ProgressBar} subclasses.
 */
public class WhiteProgressBarTheme<T extends ProgressBar> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.setProgressColor(new Vector4f(0.6f, 0.8f, 0.7f, 1f));
    }
}
