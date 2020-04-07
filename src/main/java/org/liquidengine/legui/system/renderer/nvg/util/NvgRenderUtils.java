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
import static org.lwjgl.nanovg.NanoVG.nvgResetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRectVarying;
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
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.shadow.Shadow;
import org.liquidengine.legui.style.util.StyleUtilities;
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


    public static float[] calculateTextBoundsRect(
            long context, Vector4f rect, String text,
            HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float fontSize) {
        return calculateTextBoundsRect(context, rect.x, rect.y, rect.z, rect.w,
                text, horizontalAlign, verticalAlign, fontSize);
    }

    public static float[] calculateTextBoundsRect(
            long context, float x, float y, float w, float h,
            String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float fontSize) {
        ByteBuffer byteText = null;
        try {
            byteText = memUTF8(text, false);
            return calculateTextBoundsRect(context, x, y, w, h, byteText, horizontalAlign, verticalAlign, fontSize);
        } finally {
            if (byteText != null) {
                memFree(byteText);
            }
        }
    }

    public static float[] calculateTextBoundsRect(
            long context, float x, float y, float w, float h,
            ByteBuffer text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float fontSize) {
        float[] bounds = new float[4];
        if (text != null && text.limit() != 0) {
            nvgTextBounds(context, x, y, text, bounds);
            return createBounds(x, y, w, h, horizontalAlign, verticalAlign, bounds);
        }
        return createBounds(x, y, w, h, horizontalAlign, verticalAlign, 0, fontSize);
    }

    public static float[] createBounds(float x, float y, float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float[] bounds) {
        float ww = bounds[2] - bounds[0];
        float hh = bounds[3] - bounds[1];
        return createBounds(x, y, w, h, horizontalAlign, verticalAlign, ww, hh);
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

    /**
     * Creates scissor for provided component by it's parent components.
     *
     * @param context nanovg context.
     * @param gui     {@link Component}.
     */
    public static void createScissor(long context, Component gui) {
        Component parent = gui.getParent();
        createScissorByParent(context, parent);
    }

    /**
     * Creates scissor for provided bounds.
     *
     * @param context nanovg context.
     * @param bounds  bounds.
     */
    public static void createScissor(long context, Vector4f bounds) {
        nvgScissor(context, bounds.x, bounds.y, bounds.z, bounds.w);
    }

    /**
     * Intersects scissor for provided bounds.
     *
     * @param context nanovg context.
     * @param bounds  bounds.
     */
    public static void intersectScissor(long context, Vector4f bounds) {
        nvgIntersectScissor(context, bounds.x, bounds.y, bounds.z, bounds.w);
    }

    /**
     * Creates scissor by provided component and it's parent components.
     *
     * @param context nanovg context.
     * @param parent  parent component.
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

    public static Vector4f getBorderRadius(Component component) {
        Style style = component.getStyle();
        Vector4f r = StyleUtilities.getBorderRadius(component, style);

        if (component.isFocused()) {
            applyCurrentRadius(r, component, component.getFocusedStyle());
        }
        if (component.isHovered()) {
            applyCurrentRadius(r, component, component.getHoveredStyle());
        }
        if (component.isPressed()) {
            applyCurrentRadius(r, component, component.getPressedStyle());
        }

        return r;
    }

    private static void applyCurrentRadius(Vector4f r, Component component, Style curr) {
        if (curr.getBorderTopLeftRadius() != null) {
            r.x = StyleUtilities.getFloatLengthNullSafe(curr.getBorderTopLeftRadius(), component.getSize().x);
        }
        if (curr.getBorderTopRightRadius() != null) {
            r.x = StyleUtilities.getFloatLengthNullSafe(curr.getBorderTopRightRadius(), component.getSize().x);
        }
        if (curr.getBorderBottomRightRadius() != null) {
            r.x = StyleUtilities.getFloatLengthNullSafe(curr.getBorderBottomRightRadius(), component.getSize().x);
        }
        if (curr.getBorderBottomLeftRadius() != null) {
            r.x = StyleUtilities.getFloatLengthNullSafe(curr.getBorderBottomLeftRadius(), component.getSize().x);
        }
    }

    public static void renderShadow(long context, Component component) {
        Shadow shadow = component.getStyle().getShadow();
        if (shadow != null && shadow.getColor() != null && shadow.getColor().w > 0.01f) {
            float hOffset = shadow.gethOffset();
            float vOffset = shadow.getvOffset();
            float blur = shadow.getBlur();
            float spread = shadow.getSpread();
            Vector2f absolutePosition = component.getAbsolutePosition();
            Vector2f size = component.getSize();

            float x = absolutePosition.x;
            float y = absolutePosition.y;
            float w = size.x;
            float h = size.y;
            Vector4f borderRadius = getBorderRadius(component);
            float cornerRadius = (borderRadius.x + borderRadius.y + borderRadius.z + borderRadius.w) / 4;

            try (
                    NVGPaint shadowPaint = NVGPaint.calloc();
                    NVGColor firstColor = NvgColorUtil.create(shadow.getColor());
                    NVGColor secondColor = NvgColorUtil.create(0,0,0,0)
            ) {
                // creating gradient and put it to shadowPaint
                nvgBoxGradient(context,
                        x + hOffset - spread,
                        y + vOffset - spread,
                        w + 2 * spread,
                        h + 2 * spread,
                        cornerRadius + spread,
                        blur,
                        firstColor,
                        secondColor,
                        shadowPaint);
                nvgBeginPath(context);
                nvgRoundedRectVarying(context,
                        x + hOffset - spread - blur,
                        y + vOffset - spread - blur,
                        w + 2 * spread + 2 * blur,
                        h + 2 * spread + 2 * blur,
                        borderRadius.x + spread,
                        borderRadius.y + spread,
                        borderRadius.z + spread,
                        borderRadius.w + spread
                );
                nvgRoundedRectVarying(context, x, y, w, h, borderRadius.x, borderRadius.y, borderRadius.z, borderRadius.w);
                nvgPathWinding(context, NVG_HOLE);
                nvgFillPaint(context, shadowPaint);
                nvgFill(context);
            }
        }
        nvgRestore(context);
    }
}
