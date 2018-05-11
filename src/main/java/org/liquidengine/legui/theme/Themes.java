package org.liquidengine.legui.theme;

import org.joml.Vector4f;
import org.liquidengine.legui.theme.colored.FlatColoredTheme;
import org.liquidengine.legui.theme.dark.DarkTheme;
import org.liquidengine.legui.theme.white.WhiteTheme;

/**
 * Enum of existing themes.
 */
public final class Themes {

    public static final Theme WHITE_THEME = new WhiteTheme();
    public static final Theme DARK_THEME = new DarkTheme();
    public static final Theme FLAT_PETERRIVER = new FlatColoredTheme(
        new Vector4f(41 / 256f, 128 / 256f, 185 / 256f, 1f),
        new Vector4f(52 / 255f, 152 / 255f, 219 / 255f, 1),
        new Vector4f(236 / 255f, 240 / 255f, 241 / 255f, 1),
        new Vector4f(46 / 255f, 204 / 255f, 113 / 255f, 1),
        new Vector4f(231 / 255f, 76 / 255f, 60 / 255f, 1)
    );
    public static final Theme FLAT_DARK = new FlatColoredTheme(
        rgba(44,62,80 ,1),
        rgba(189,195,199 ,1),
        rgba(236,240,241 ,1),
        rgba(39,174,96 ,1),
        rgba(192,57,43 ,1)
    );
    private static Theme DEFAULT_THEME = WHITE_THEME;

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
