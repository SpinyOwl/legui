package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;

import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;
import static org.lwjgl.nanovg.NanoVG.nvgResetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgScissor;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public final class NvgRenderUtil {
    private NvgRenderUtil() {
    }

    /**
     * Creates scissor for provided component by it's parent components
     *
     * @param context nanovg context
     * @param gui     {@link Component}
     */
    public static void createScissor(long context, Component gui) {
        Component parent = gui.getParent();
        createScissorByParent(context, parent);
    }

    /**
     * Creates scissor for provided bounds
     *
     * @param context nanovg context
     * @param bounds  bounds
     */
    public static void createScissor(long context, Vector4f bounds) {
        nvgScissor(context, bounds.x, bounds.y, bounds.z, bounds.w);
    }

    /**
     * Intersects scissor for provided bounds
     *
     * @param context nanovg context
     * @param bounds  bounds
     */
    public static void intersectScissor(long context, Vector4f bounds) {
        nvgIntersectScissor(context, bounds.x, bounds.y, bounds.z, bounds.w);
    }

    /**
     * Creates scissor by provided component and it's parent components
     *
     * @param context nanovg context
     * @param parent  parent component
     */
    public static void createScissorByParent(long context, Component parent) {
        if (parent != null) {
            Vector2f p = parent.getScreenPosition();
            Vector2f s = parent.getSize();
            nvgScissor(context, p.x, p.y, s.x, s.y);

            while ((parent = parent.getParent()) != null) {
                p = parent.getScreenPosition();
                s = parent.getSize();
                nvgIntersectScissor(context, p.x, p.y, s.x, s.y);
            }
        }
    }

    /**
     * Resets scissor
     *
     * @param context nanovg context pointer
     */
    public static void resetScissor(long context) {
        nvgResetScissor(context);
    }
}
