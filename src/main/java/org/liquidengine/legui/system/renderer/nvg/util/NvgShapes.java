package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * NanoVG Shape utility. Used to draw shapes with NanoVG.
 * <p>
 * Created by ShchAlexander on 19.09.2017.
 */
public class NvgShapes {

    public static final Vector4fc ZERO_CORNDERS = new Vector4f(0);
    public static final float MIN_ALPHA = 0.001f;

    /**
     * Private constructor for utility class.
     */
    private NvgShapes() {
    }

    /**
     * Used to draw rectangle.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param bgColor rectangle background color.
     */
    public static void drawRect(long nvg, Vector2fc position, Vector2fc size, Vector4fc bgColor) {
        if (bgColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor fillColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(bgColor, fillColor);
            nvgBeginPath(nvg);
            nvgFillColor(nvg, fillColor);
            nvgRect(nvg, position.x(), position.y(), size.x(), size.y());
            nvgFill(nvg);
        }
    }

    /**
     * Used to draw rectangle.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param bgColor rectangle background color.
     */
    public static void drawRect(long nvg, Vector4fc rectangle, Vector4fc bgColor) {
        if (bgColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor fillColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(bgColor, fillColor);
            nvgBeginPath(nvg);
            nvgFillColor(nvg, fillColor);
            nvgRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w());
            nvgFill(nvg);
        }
    }

    /**
     * Used to draw rectangle.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param bgColor rectangle background color.
     * @param radius cornder radius
     */
    public static void drawRect(long nvg, Vector2fc position, Vector2fc size, Vector4fc bgColor, float radius) {
        if (bgColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor fillColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(bgColor, fillColor);
            nvgBeginPath(nvg);
            nvgFillColor(nvg, fillColor);
            nvgRoundedRect(nvg, position.x(), position.y(), size.x(), size.y(), radius);
            nvgFill(nvg);
        }
    }


    /**
     * Used to draw rectangle.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param bgColor rectangle background color.
     * @param radius cornder radius
     */
    public static void drawRect(long nvg, Vector2fc position, Vector2fc size, Vector4fc bgColor, Vector4f radius) {
        if (radius != null && !radius.equals(ZERO_CORNDERS)) {
            try (NVGColor fillColor = NVGColor.calloc()) {
                NvgColorUtil.fillNvgColorWithRGBA(bgColor, fillColor);
                nvgBeginPath(nvg);
                nvgFillColor(nvg, fillColor);
                if (radius.x == radius.y && radius.x == radius.z && radius.x == radius.w) {
                    nvgRoundedRect(nvg, position.x(), position.y(), size.x(), size.y(), radius.x);
                } else {
                    nvgRoundedRectVarying(nvg, position.x(), position.y(), size.x(), size.y(), radius.x, radius.y, radius.z, radius.w);
                }
                nvgFill(nvg);
            }
        } else {
            drawRect(nvg, position, size, bgColor);
        }
    }

    /**
     * Used to draw rectangle.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param bgColor rectangle background color.
     * @param radius cornder radius
     */
    public static void drawRect(long nvg, Vector4fc rectangle, Vector4fc bgColor, float radius) {
        if (bgColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor fillColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(bgColor, fillColor);
            nvgBeginPath(nvg);
            nvgFillColor(nvg, fillColor);
            nvgRoundedRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w(), radius);
            nvgFill(nvg);
        }
    }


    /**
     * Used to draw rectangle.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param bgColor rectangle background color.
     * @param radius cornder radius
     */
    public static void drawRect(long nvg, Vector4fc rectangle, Vector4fc bgColor, Vector4f radius) {
        if (radius != null && !radius.equals(ZERO_CORNDERS)) {
            try (NVGColor fillColor = NVGColor.calloc()) {
                NvgColorUtil.fillNvgColorWithRGBA(bgColor, fillColor);
                nvgBeginPath(nvg);
                nvgFillColor(nvg, fillColor);
                nvgRoundedRectVarying(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w(), radius.x, radius.y, radius.z, radius.w);
                nvgFill(nvg);
            }
        } else {
            drawRect(nvg, rectangle, bgColor, radius);
        }
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param rectStrokeColor rectangle color.
     * @param strokeWidth stroke width.
     */
    public static void drawRectStroke(long nvg, Vector2fc position, Vector2fc size, Vector4fc rectStrokeColor, float strokeWidth) {
        if (rectStrokeColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor strokeColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(rectStrokeColor, strokeColor);
            nvgBeginPath(nvg);
            nvgStrokeColor(nvg, strokeColor);
            nvgStrokeWidth(nvg, strokeWidth);
            nvgRect(nvg, position.x(), position.y(), size.x(), size.y());
            nvgStroke(nvg);
        }
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param rectStrokeColor rectangle color.
     * @param strokeWidth stroke width.
     */
    public static void drawRectStroke(long nvg, Vector4fc rectangle, Vector4fc rectStrokeColor, float strokeWidth) {
        if (rectStrokeColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor strokeColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(rectStrokeColor, strokeColor);
            nvgBeginPath(nvg);
            nvgStrokeColor(nvg, strokeColor);
            nvgStrokeWidth(nvg, strokeWidth);
            nvgRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w());
            nvgStroke(nvg);
        }
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param rectStrokeColor rectangle color.
     * @param strokeWidth stroke width.
     * @param radius radius vector.
     */
    public static void drawRectStroke(long nvg, Vector4fc rectangle, Vector4fc rectStrokeColor, float strokeWidth, Vector4f radius) {
        if (radius != null && !radius.equals(ZERO_CORNDERS)) {
            try (NVGColor strokeColor = NVGColor.calloc()) {
                NvgColorUtil.fillNvgColorWithRGBA(rectStrokeColor, strokeColor);
                nvgBeginPath(nvg);
                nvgStrokeColor(nvg, strokeColor);
                nvgStrokeWidth(nvg, strokeWidth);
                nvgRoundedRectVarying(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w(), radius.x, radius.y, radius.z, radius.w);
                nvgStroke(nvg);
            }
        } else {
            drawRectStroke(nvg, rectangle, rectStrokeColor, strokeWidth);
        }
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param rectStrokeColor rectangle color.
     * @param radius corner radius
     * @param strokeWidth stroke width.
     */
    public static void drawRectStroke(long nvg, Vector2fc position, Vector2fc size, Vector4fc rectStrokeColor, float strokeWidth, float radius) {
        if (rectStrokeColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor strokeColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(rectStrokeColor, strokeColor);
            nvgBeginPath(nvg);
            nvgStrokeColor(nvg, strokeColor);
            nvgStrokeWidth(nvg, strokeWidth);
            nvgRoundedRect(nvg, position.x(), position.y(), size.x(), size.y(), radius);
            nvgStroke(nvg);
        }
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param rectStrokeColor rectangle color.
     * @param strokeWidth stroke width.
     * @param radius radius vector.
     */
    public static void drawRectStroke(long nvg, Vector2fc position, Vector2fc size, Vector4fc rectStrokeColor, float strokeWidth, Vector4f radius) {
        if (radius != null && !radius.equals(ZERO_CORNDERS)) {
            try (NVGColor strokeColor = NVGColor.calloc()) {
                NvgColorUtil.fillNvgColorWithRGBA(rectStrokeColor, strokeColor);
                nvgBeginPath(nvg);
                nvgStrokeColor(nvg, strokeColor);
                nvgStrokeWidth(nvg, strokeWidth);
                nvgRoundedRectVarying(nvg, position.x(), position.y(), size.x(), size.y(), radius.x, radius.y, radius.z, radius.w);
                nvgStroke(nvg);
            }
        } else {
            drawRectStroke(nvg, position, size, rectStrokeColor, strokeWidth);
        }
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param rectStrokeColor rectangle color.
     * @param strokeWidth stroke width.
     * @param radius corner radius.
     */
    public static void drawRectStroke(long nvg, Vector4fc rectangle, Vector4fc rectStrokeColor, float strokeWidth, float radius) {
        if (rectStrokeColor.w() <= MIN_ALPHA) {
            return;
        }
        try (NVGColor strokeColor = NVGColor.calloc()) {
            NvgColorUtil.fillNvgColorWithRGBA(rectStrokeColor, strokeColor);
            nvgBeginPath(nvg);
            nvgStrokeColor(nvg, strokeColor);
            nvgStrokeWidth(nvg, strokeWidth);
            nvgRoundedRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w(), radius);
            nvgStroke(nvg);
        }
    }

}
