package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector4fc;
import org.lwjgl.nanovg.NVGColor;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class NVGUtils {
    public static NVGColor rgba(float r, float g, float b, float a, NVGColor color) {
        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);
        return color;
    }

    public static NVGColor rgba(float[] rgba, NVGColor color) {
        color.r(rgba[0]);
        color.g(rgba[1]);
        color.b(rgba[2]);
        color.a(rgba[3]);
        return color;
    }

    public static NVGColor rgba(Vector4fc rgba, NVGColor color) {
        color.r(rgba.x());
        color.g(rgba.y());
        color.b(rgba.z());
        color.a(rgba.w());
        return color;
    }
}
