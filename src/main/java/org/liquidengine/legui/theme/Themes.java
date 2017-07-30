package org.liquidengine.legui.theme;

import org.liquidengine.legui.theme.dark.DarkTheme;
import org.liquidengine.legui.theme.white.def.WhiteTheme;

/**
 * Enum of existing themes.
 */
public final class Themes {
    public static final Theme WHITE_THEME = new WhiteTheme();
    public static final Theme DARK_THEME = new DarkTheme();
    private static Theme DEFAULT_THEME = WHITE_THEME;

    private Themes() {
    }


    public static Theme getDefaultTheme() {
        return DEFAULT_THEME;
    }

    public static void setDefaultTheme(Theme defaultTheme) {
        if (defaultTheme != null) {
            DEFAULT_THEME = defaultTheme;
        }
    }

}
