package org.liquidengine.legui.util;

import org.joml.Vector4f;

import java.util.Random;

/**
 * Created by Shcherbin Alexander on 9/30/2016.
 */
public final class ColorUtil {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private ColorUtil() {
    }

    public static Vector4f negativeColor(Vector4f color) {
        Vector4f dest = new Vector4f(1).sub(color);
        dest.w = color.w;
        return dest;
    }

    public static void negativeColor(Vector4f color, Vector4f dest) {
        dest.zero().set(1).sub(color);
        dest.w = color.w;
    }

    public static Vector4f negativeColorRGB(Vector4f color) {
        Vector4f dest = new Vector4f(1).sub(color);
        dest.w = 1;
        return dest;
    }

    public static void negativeColorRGB(Vector4f color, Vector4f dest) {
        dest.zero().set(1).sub(color);
        dest.w = 1;
    }

    public static Vector4f half(Vector4f color) {
        return new Vector4f(color).div(2);
    }

    public static Vector4f oppositeBlackOrWhite(Vector4f color) {
        return oppositeBlackOrWhite(color, new Vector4f(1));
    }

    public static Vector4f oppositeBlackOrWhite(Vector4f color, Vector4f targetColor) {
        if ((color.x * 0.299f + color.y * 0.587f + color.z * 0.114f) > 170f / 255f) {
            targetColor.x = 0;
            targetColor.y = 0;
            targetColor.z = 0;
        } else {
            targetColor.x = 1;
            targetColor.y = 1;
            targetColor.z = 1;
        }
        return targetColor;
    }

    public static Vector4f randomColor() {
        return new Vector4f(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat(), 1);
    }
}
