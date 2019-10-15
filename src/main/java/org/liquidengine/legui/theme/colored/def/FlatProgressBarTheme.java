package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark ProgressBar Theme for all progress bars. Used to make progress bar dark.
 *
 * @param <T> {@link ProgressBar} subclasses.
 */
public class FlatProgressBarTheme<T extends ProgressBar> extends FlatComponentTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatProgressBarTheme() {
    }

    public FlatProgressBarTheme(FlatColoredThemeSettings settings) {
        super(settings);
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.setProgressColor(settings.allowColor());
    }
}
