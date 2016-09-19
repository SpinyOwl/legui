package org.liquidengine.legui.util;

import org.joml.Vector4f;

/**
 * Created by Shcherbin Alexander on 7/27/2016.
 */
public final class ColorConstants {
    private ColorConstants() {
    }

    public static Vector4f red() {
        return new Vector4f(1, 0, 0, 1);
    }

    public static Vector4f green() {
        return new Vector4f(0, 1, 0, 1);
    }

    public static Vector4f blue() {
        return new Vector4f(0, 0, 1, 1);
    }

    public static Vector4f darkRed() {
        return new Vector4f(0.6f, 0, 0, 1);
    }

    public static Vector4f darkGreen() {
        return new Vector4f(0, 0.6f, 0, 1);
    }

    public static Vector4f darkBlue() {
        return new Vector4f(0, 0, 0.6f, 1);
    }

    public static Vector4f lightRed() {
        return new Vector4f(1, 0.3f, 0.3f, 1);
    }

    public static Vector4f lightGreen() {
        return new Vector4f(0.3f, 1, 0.3f, 1);
    }

    public static Vector4f lightBlue() {
        return new Vector4f(0.3f, 0.3f, 1, 1);
    }

    public static Vector4f black() {
        return new Vector4f(0, 0, 0, 1);
    }

    public static Vector4f white() {
        return new Vector4f(1);
    }

    public static Vector4f transparent() {
        return new Vector4f(0);
    }

    public static Vector4f lightGray() {
        return new Vector4f(0.8f, 0.8f, 0.8f, 1);
    }

    public static Vector4f darkGray() {
        return new Vector4f(0.2f, 0.2f, 0.2f, 1);
    }
}
