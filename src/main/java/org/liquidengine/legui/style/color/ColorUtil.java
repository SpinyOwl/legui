package org.liquidengine.legui.style.color;

import org.joml.Vector4f;

import java.util.Random;

/**
 * Used to perform some operations with color vectors.
 */
public final class ColorUtil {

    /**
     * Used to create random color.
     */
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    /**
     * Private constructor to avoid creation instances of utility class.
     */
    private ColorUtil() {
    }

    /**
     * Used to create negative color for provided color. Alpha value will be the same as in source color.
     *
     * @param color color to negotiate.
     *
     * @return new negative color.
     */
    public static Vector4f negativeColor(Vector4f color) {
        Vector4f dest = new Vector4f(1).sub(color);
        dest.w = color.w;
        return dest;
    }

    /**
     * Used to create negative color and set it to dest. Alpha value will be the same as in source color.
     *
     * @param color color to negotiate.
     * @param dest target color object to set.
     */
    public static void negativeColor(Vector4f color, Vector4f dest) {
        dest.zero().set(1).sub(color);
        dest.w = color.w;
    }

    /**
     * Used to negotiate only rgb part. Alpha value will be setted to 1.
     *
     * @param color color to negotiate.
     *
     * @return new negative color.
     */
    public static Vector4f negativeColorRGB(Vector4f color) {
        Vector4f dest = new Vector4f(1).sub(color);
        dest.w = 1;
        return dest;
    }

    /**
     * Used to create negative color and set it to dest. Alpha value will be setted to 1.
     *
     * @param color color to negotiate.
     * @param dest target color object to set.
     *
     * @return destination color filled with negative color.
     */
    public static Vector4f negativeColorRGB(Vector4f color, Vector4f dest) {
        dest.zero().set(1).sub(color);
        dest.w = 1;
        return dest;
    }

    /**
     * Used to obtain half color from source.
     *
     * @param color source color.
     *
     * @return new color.
     */
    public static Vector4f half(Vector4f color) {
        return new Vector4f(color).div(2);
    }

    /**
     * Used to obtain half color from source.
     *
     * @param color source color.
     *
     * @return new color.
     */
    public static Vector4f halfRGB(Vector4f color) {
        return new Vector4f(color.x / 2f, color.y / 2f, color.z / 2f, color.w);
    }

    /**
     * Used to return black or white color based on source color. for example for white source color it should return black.
     *
     * @param color color to find opposite black or white color.
     *
     * @return opposite black or white color.
     */
    public static Vector4f oppositeBlackOrWhite(Vector4f color) {
        return oppositeBlackOrWhite(color, new Vector4f(1,1,1,1));
    }

    /**
     * Used to return black or white color based on source color. For example for white source color it should return black.
     *
     * @param color color to find opposite black or white color.
     * @param targetColor color to set result.
     *
     * @return opposite black or white color.
     */
    public static Vector4f oppositeBlackOrWhite(Vector4f color, Vector4f targetColor) {
        if (color.w == 0) {
            return ColorConstants.gray();
        }
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

    public static Vector4f fromInt(int r, int g, int b, float a) {
        return new Vector4f(r/255f, g/255f, b/255f, a);
    }

    /**
     * Used to generate random color.
     *
     * @return random color.
     */
    public static Vector4f randomColor() {
        return new Vector4f(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat(), 1);
    }
}
