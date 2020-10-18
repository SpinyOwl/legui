package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector4fc;
import org.lwjgl.nanovg.NVGColor;

/**
 * NanoVG utility. Used to convert some NanoVG elements to other. For example {@link NVGColor} to {@link org.joml.Vector4f} and back.
 */
public final class NvgColorUtil {

    private NvgColorUtil() {
    }

    /**
     * Used to fill {@link NVGColor}.
     *
     * @param r red.
     * @param g green.
     * @param b blue.
     * @param a alpha.
     * @param color color to fill.
     */
    public static void fillNvgColorWithRGBA(float r, float g, float b, float a, NVGColor color) {
        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);
    }

    /**
     * Used to fill {@link NVGColor}.
     *
     * @param rgba rgba color {@link Vector4fc} of floats. <ul> <li>rgba.x - red.</li> <li>rgba.y - green.</li> <li>rgba.z - blue.</li> <li>rgba.w -
     * alpha.</li>
     * </ul>
     * @param color color to fill.
     */
    public static void fillNvgColorWithRGBA(Vector4fc rgba, NVGColor color) {
        color.r(rgba.x());
        color.g(rgba.y());
        color.b(rgba.z());
        color.a(rgba.w());
    }

    /**
     * Used to allocate and fill instance of {@link NVGColor}. Should be used in try-with-resources to avoid memory leaks.
     *
     * @param r red.
     * @param g green.
     * @param b blue.
     * @param a alpha.
     *
     * @return allocated and filled color.
     */
    public static NVGColor create(float r, float g, float b, float a) {
        NVGColor color = NVGColor.calloc();
        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);
        return color;
    }

    /**
     * Used to allocate and fill instance of {@link NVGColor}. Should be used in try-with-resources to avoid memory leaks.
     *
     * @param rgba rgba color {@link Vector4fc} of floats. <ul> <li>rgba.x - red.</li> <li>rgba.y - green.</li> <li>rgba.z - blue.</li> <li>rgba.w -
     * alpha.</li>
     * </ul>
     * @return allocated and filled color.
     */
    public static NVGColor create(Vector4fc rgba) {
        NVGColor color = NVGColor.calloc();
        color.r(rgba.x());
        color.g(rgba.y());
        color.b(rgba.z());
        color.a(rgba.w());
        return color;
    }
}
