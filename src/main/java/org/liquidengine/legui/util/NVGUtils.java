package org.liquidengine.legui.util;

import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;

/**
 * Created by Shcherbin Alexander on 5/16/2016.
 */
public final class NVGUtils {

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

    public static NVGColor rgba(Vector4f rgba, NVGColor color) {
        color.r(rgba.x);
        color.g(rgba.y);
        color.b(rgba.z);
        color.a(rgba.w);
        return color;
    }

}
