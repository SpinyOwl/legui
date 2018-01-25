package org.liquidengine.legui.system.renderer.nvg.util;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BASELINE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BOTTOM;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_RIGHT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.NVG_HOLE;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgBoxGradient;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;
import static org.lwjgl.nanovg.NanoVG.nvgPathWinding;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgResetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgScissor;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextBounds;
import static org.lwjgl.system.MemoryUtil.memFree;
import static org.lwjgl.system.MemoryUtil.memUTF8;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

/**
 * Created by ShchAlexander on 2/2/2017.
 */
public final class NvgRenderUtils {

    /**
     * Private constructor.
     */
    private NvgRenderUtils() {
    }


    public static float[] calculateTextBoundsRect(long context, Vector4f rect, String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        return calculateTextBoundsRect(context, rect.x, rect.y, rect.z, rect.w, text, horizontalAlign, verticalAlign);
    }

    public static float[] calculateTextBoundsRect(long context, float x, float y, float w, float h, String text, HorizontalAlign horizontalAlign,
        VerticalAlign verticalAlign) {
        ByteBuffer byteText = null;
        try {
            byteText = memUTF8(text, false);
            return calculateTextBoundsRect(context, x, y, w, h, byteText, horizontalAlign, verticalAlign);
        } finally {
            if (byteText != null) {
                memFree(byteText);
            }
        }
    }

    public static float[] calculateTextBoundsRect(long context, float x, float y, float w, float h, ByteBuffer text, HorizontalAlign horizontalAlign,
        VerticalAlign verticalAlign) {
        float bounds[] = new float[4];
        nvgTextBounds(context, x, y, text, bounds);
        return createBounds(x, y, w, h, horizontalAlign, verticalAlign, bounds);
    }


    public static float[] createBounds(float x, float y, float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float[] bounds) {
        float ww = bounds[2] - bounds[0];
        float hh = bounds[3] - bounds[1];
        return createBounds(x, y, w, h, horizontalAlign, verticalAlign, /*bounds, */ww, hh);
    }

    public static float[] createBounds(float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float[] bounds, float ww, float hh) {
        int hp = horizontalAlign == HorizontalAlign.LEFT ? 0 : horizontalAlign == HorizontalAlign.CENTER ? 1 : 2;
        int vp = verticalAlign == VerticalAlign.TOP ? 0 : verticalAlign == VerticalAlign.MIDDLE ? 1 : verticalAlign == VerticalAlign.BOTTOM ? 2 : 3;
        float x1 = bounds[0] + (w + ww) * 0.5f * hp;

        float baseline = (vp > 2 ? hh / 4.0f : 0);
        float vv = (vp == 3 ? 1 : vp);
        float y1 = bounds[1] + (h + hh) * 0.5f * vv + (vp > 2 ? (+baseline) : 0);
        return new float[]{
            x1, y1, ww, hh,
            x1 - (ww * 0.5f * hp), y1 - (hh * 0.5f * vv) - baseline, ww, hh
        };
    }

    public static float[] createBounds(float x, float y, float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float tw, float th) {
        int hp = horizontalAlign.index;
        int vp = verticalAlign.index;

        float x1 = x + w * 0.5f * hp;

        float baseline = (vp > 2 ? th / 4.0f : 0);
        float vv = (vp == 3 ? 1 : vp);
        float y1 = y + h * 0.5f * vv + (vp > 2 ? (+baseline) : 0);
        return new float[]{
            x1, y1, tw, th,
            x1 - (tw * 0.5f * hp), y1 - (th * 0.5f * vv) - baseline, tw, th
        };
    }


    public static void alignTextInBox(long context, HorizontalAlign hAlig, VerticalAlign vAlig) {
        int hAlign = hAlig == HorizontalAlign.CENTER ? NVG_ALIGN_CENTER : hAlig == HorizontalAlign.LEFT ? NVG_ALIGN_LEFT : NVG_ALIGN_RIGHT;
        int vAlign = vAlig == VerticalAlign.TOP ? NVG_ALIGN_TOP : vAlig == VerticalAlign.BOTTOM ?
            NVG_ALIGN_BOTTOM : vAlig == VerticalAlign.MIDDLE ? NVG_ALIGN_MIDDLE : NVG_ALIGN_BASELINE;
        nvgTextAlign(context, hAlign | vAlign);
    }

    public static void dropShadow(long context, float x, float y, float w, float h, float cornerRadius, Vector4f shadowColor) {
        NVGPaint shadowPaint = NVGPaint.calloc();
        NVGColor colorA = NVGColor.calloc();
        NVGColor colorB = NVGColor.calloc();

        nvgBoxGradient(context, x, y + 2, w, h, cornerRadius * 2, 10, NvgColorUtil.rgba(shadowColor, colorA), NvgColorUtil.rgba(0, 0, 0, 0, colorB),
            shadowPaint);
        nvgBeginPath(context);
        nvgRect(context, x - 10, y - 10, w + 20, h + 30);
        nvgRoundedRect(context, x, y, w, h, cornerRadius);
        nvgPathWinding(context, NVG_HOLE);
        nvgFillPaint(context, shadowPaint);
        nvgFill(context);

        shadowPaint.free();
        colorA.free();
        colorB.free();
    }

    /**
     * Creates scissor for provided component by it's parent components.
     *
     * @param context nanovg context.
     * @param gui {@link Component}.
     */
    public static void createScissor(long context, Component gui) {
        Component parent = gui.getParent();
        createScissorByParent(context, parent);
    }

    /**
     * Creates scissor for provided bounds.
     *
     * @param context nanovg context.
     * @param bounds bounds.
     */
    public static void createScissor(long context, Vector4f bounds) {
        nvgScissor(context, bounds.x, bounds.y, bounds.z, bounds.w);
    }

    /**
     * Intersects scissor for provided bounds.
     *
     * @param context nanovg context.
     * @param bounds bounds.
     */
    public static void intersectScissor(long context, Vector4f bounds) {
        nvgIntersectScissor(context, bounds.x, bounds.y, bounds.z, bounds.w);
    }

    /**
     * Creates scissor by provided component and it's parent components.
     *
     * @param context nanovg context.
     * @param parent parent component.
     */
    public static void createScissorByParent(long context, Component parent) {
        List<Component> parents = new ArrayList<>();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParent();
        }
        Vector2f pos = new Vector2f();
        int size = parents.size();
        if (size > 0) {
            parent = parents.get(size - 1);
            pos.add(parent.getPosition());
            Vector2f s = parent.getSize();
            createScissor(context, new Vector4f(pos, s.x, s.y));
            if (size > 1) {
                for (int i = size - 2; i >= 0; i--) {
                    parent = parents.get(i);
                    s = parent.getSize();
                    pos.add(parent.getPosition());
                    nvgIntersectScissor(context, pos.x, pos.y, s.x, s.y);
                }
            }
        }
    }

    /**
     * Used to reset scissor.
     *
     * @param context nanovg context pointer.
     */
    public static void resetScissor(long context) {
        nvgResetScissor(context);
    }

}
