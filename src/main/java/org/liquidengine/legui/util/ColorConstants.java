package org.liquidengine.legui.util;

import org.joml.Vector4f;
import org.joml.Vector4fc;

/**
 * Created by Shcherbin Alexander on 7/27/2016.
 */
public final class ColorConstants {

    //@formatter:off
    public static final Vector4fc RED = new Vector4f(1, 0, 0, 1);
    public static final Vector4fc GREEN = new Vector4f(0, 1, 0, 1);
    public static final Vector4fc BLUE = new Vector4f(0, 0, 1, 1);
    public static final Vector4fc DARK_RED = new Vector4f(0.6f, 0, 0, 1);
    public static final Vector4fc DARK_GREEN = new Vector4f(0, 0.6f, 0, 1);
    public static final Vector4fc DARK_BLUE = new Vector4f(0, 0, 0.6f, 1);
    public static final Vector4fc LIGHT_RED = new Vector4f(1, 0.3f, 0.3f, 1);
    public static final Vector4fc LIGHT_GREEN = new Vector4f(0.3f, 1, 0.3f, 1);
    public static final Vector4fc LIGHT_BLUE = new Vector4f(0.6f, 0.7f, 1, 1);
    public static final Vector4fc BLACK = new Vector4f(0, 0, 0, 1);
    public static final Vector4fc WHITE = new Vector4f(1);
    public static final Vector4fc TRANSPARENT = new Vector4f(0);
    public static final Vector4fc LIGHT_GRAY = new Vector4f(0.8f, 0.8f, 0.8f, 1);
    public static final Vector4fc DARK_GRAY = new Vector4f(0.2f, 0.2f, 0.2f, 1);
    public static final Vector4fc red = RED;
    public static final Vector4fc green = GREEN;
    public static final Vector4fc blue = BLUE;
    public static final Vector4fc darkRed = DARK_RED;
    public static final Vector4fc darkGreen = DARK_GREEN;
    public static final Vector4fc darkBlue = DARK_BLUE;
    public static final Vector4fc lightRed = LIGHT_RED;
    public static final Vector4fc lightGreen = LIGHT_GREEN;
    public static final Vector4fc lightBlue = LIGHT_BLUE;
    public static final Vector4fc black = BLACK;
    public static final Vector4fc white = WHITE;
    public static final Vector4fc transparent = TRANSPARENT;
    public static final Vector4fc lightGray = LIGHT_GRAY;
    public static final Vector4fc darkGray = DARK_GRAY;
    private ColorConstants() {
    }

    public static Vector4f red() { return new Vector4f(red); }
    public static Vector4f green() { return new Vector4f(green); }
    public static Vector4f blue() { return new Vector4f(blue); }
    public static Vector4f darkRed() { return new Vector4f(darkRed); }
    public static Vector4f darkGreen() { return new Vector4f(darkGreen); }
    public static Vector4f darkBlue() { return new Vector4f(darkBlue); }
    public static Vector4f lightRed() { return new Vector4f(lightRed); }
    public static Vector4f lightGreen() { return new Vector4f(lightGreen); }
    public static Vector4f lightBlue() { return new Vector4f(lightBlue); }
    public static Vector4f black() { return new Vector4f(black); }
    public static Vector4f white() { return new Vector4f(white); }
    public static Vector4f transparent() { return new Vector4f(transparent); }
    public static Vector4f lightGray() { return new Vector4f(lightGray); }
    public static Vector4f darkGray() { return new Vector4f(darkGray); }
    //@formatter:on
}
