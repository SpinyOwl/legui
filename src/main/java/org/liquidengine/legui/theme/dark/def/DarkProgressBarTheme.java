package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.ProgressBar;

/**
 * Dark ProgressBar Theme for all progress bars. Used to make progress bar dark.
 */
public class DarkProgressBarTheme<T extends ProgressBar> extends DarkControllerTheme<T> {
    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        component.setProgressColor(ColorConstants.gray());
    }
}
