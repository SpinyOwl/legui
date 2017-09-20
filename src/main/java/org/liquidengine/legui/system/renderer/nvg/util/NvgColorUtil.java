package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector4fc;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

/**
 * NanoVG utility. Used to convert some NanoVG elements to other. For example {@link NVGColor} to {@link org.joml.Vector4f} and back.
 */
public class NvgColorUtil {

    /**
     * Used to fill {@link NVGColor}.
     *
     * @param r red.
     * @param g green.
     * @param b blue.
     * @param a alpha.
     * @param color color to fill.
     * @return filled color.
     */
    public static NVGColor rgba(float r, float g, float b, float a, NVGColor color) {
        return NanoVG.nvgRGBAf(r, g, b, a, color);
    }

    /**
     * Used to fill {@link NVGColor}.
     *
     * @param rgba rgba color {@link Vector4fc} of floats. <ul> <li>rgba.x - red.</li> <li>rgba.y - green.</li> <li>rgba.z - blue.</li> <li>rgba.w - alpha.</li>
     * </ul>
     * @param color color to fill.
     * @return filled color.
     */
    public static NVGColor rgba(Vector4fc rgba, NVGColor color) {
        return NanoVG.nvgRGBAf(rgba.x(), rgba.y(), rgba.z(), rgba.w(), color);
    }
}
