package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector4fc;
import org.lwjgl.nanovg.NVGColor;

/**
 * NanoVG utility. Used to convert some NanoVG elements to other. For example {@link NVGColor} to {@link org.joml.Vector4f} and back.
 */
public class NVGUtils {

    /**
     * Used to fill {@link NVGColor}.
     *
     * @param r     red.
     * @param g     green.
     * @param b     blue.
     * @param a     alpha.
     * @param color color to fill.
     * @return filled color.
     */
    public static NVGColor rgba(float r, float g, float b, float a, NVGColor color) {
        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);
        return color;
    }

    /**
     * Used to fill {@link NVGColor}.
     *
     * @param rgba  rgba array of floats.
     *              <ul>
     *              <li>rgba[0] - red.</li>
     *              <li>rgba[1] - green.</li>
     *              <li>rgba[2] - blue.</li>
     *              <li>rgba[3] - alpha.</li>
     *              </ul>
     * @param color color to fill.
     * @return filled color.
     */
    public static NVGColor rgba(float[] rgba, NVGColor color) {
        color.r(rgba[0]);
        color.g(rgba[1]);
        color.b(rgba[2]);
        color.a(rgba[3]);
        return color;
    }

    /**
     * Used to fill {@link NVGColor}.
     *
     * @param rgba  rgba color {@link Vector4fc} of floats.
     *              <ul>
     *              <li>rgba.x - red.</li>
     *              <li>rgba.y - green.</li>
     *              <li>rgba.z - blue.</li>
     *              <li>rgba.w - alpha.</li>
     *              </ul>
     * @param color color to fill.
     * @return filled color.
     */
    public static NVGColor rgba(Vector4fc rgba, NVGColor color) {
        color.r(rgba.x());
        color.g(rgba.y());
        color.b(rgba.z());
        color.a(rgba.w());
        return color;
    }
}
