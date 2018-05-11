package org.liquidengine.legui.theme;

import org.joml.Vector4f;
import org.liquidengine.legui.theme.colored.FlatColoredTheme;

/**
 * Enum of existing themes.
 */
public final class Themes {

    public static final Theme FLAT_PETERRIVER = new FlatColoredTheme(
        rgba(41, 128, 185, 1f),
        rgba(52, 73, 94, 1),
        rgba(100, 181, 246, 1),
        rgba(46, 204, 113, 1),
        rgba(231, 76, 60, 1)
    );
    public static final Theme FLAT_PETERRIVER_DARK = new FlatColoredTheme(
        rgba(44, 62, 80, 1),
        rgba(127, 140, 141, 1),
        rgba(100, 181, 246, 1),
        rgba(39, 174, 96, 1),
        rgba(192, 57, 43, 1)
    );
    public static final Theme FLAT_WHITE = new FlatColoredTheme(
        rgba(245, 245, 245, 1),
        rgba(176,190,197 ,1),
        rgba(100, 181, 246, 1),
        rgba(165, 214, 167, 1),
        rgba(239, 154, 154, 1)
    );
    public static final Theme FLAT_DARK = new FlatColoredTheme(
        rgba(33,33,33 ,1),
        rgba(97,97,97 ,1),
        rgba(100, 181, 246, 1),
        rgba(27,94,32 ,1),
        rgba(183,28,28 ,1)
    );
    private static Theme DEFAULT_THEME = FLAT_WHITE;

    private Themes() {
    }

    private static Vector4f rgba(int r, int g, int b, float a) {
        return new Vector4f(r / 255f, g / 255f, b / 255f, a);
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
