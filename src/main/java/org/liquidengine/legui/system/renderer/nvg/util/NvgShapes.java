package org.liquidengine.legui.system.renderer.nvg.util;

import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgStroke;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeColor;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeWidth;

import org.joml.Vector2fc;
import org.joml.Vector4fc;
import org.lwjgl.nanovg.NVGColor;

/**
 * NanoVG Shape utility. Used to draw shapes with NanoVG.
 * <p>
 * Created by ShchAlexander on 19.09.2017.
 */
public class NvgShapes {

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
        if (bgColor.w() == 0) {
            return;
        }
        NVGColor fillColor = NVGColor.calloc();
        NvgColorUtil.rgba(bgColor, fillColor);
        nvgBeginPath(nvg);
        nvgFillColor(nvg, fillColor);
        nvgRect(nvg, position.x(), position.y(), size.x(), size.y());
        nvgFill(nvg);
        fillColor.free();
    }

    /**
     * Used to draw rectangle.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param bgColor rectangle background color.
     */
    public static void drawRect(long nvg, Vector4fc rectangle, Vector4fc bgColor) {
        if (bgColor.w() == 0) {
            return;
        }
        NVGColor fillColor = NVGColor.calloc();
        NvgColorUtil.rgba(bgColor, fillColor);
        nvgBeginPath(nvg);
        nvgFillColor(nvg, fillColor);
        nvgRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w());
        nvgFill(nvg);
        fillColor.free();
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
        if (bgColor.w() == 0) {
            return;
        }
        NVGColor fillColor = NVGColor.calloc();
        NvgColorUtil.rgba(bgColor, fillColor);
        nvgBeginPath(nvg);
        nvgFillColor(nvg, fillColor);
        nvgRoundedRect(nvg, position.x(), position.y(), size.x(), size.y(), radius);
        nvgFill(nvg);
        fillColor.free();
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
        if (bgColor.w() == 0) {
            return;
        }
        NVGColor fillColor = NVGColor.calloc();
        NvgColorUtil.rgba(bgColor, fillColor);
        nvgBeginPath(nvg);
        nvgFillColor(nvg, fillColor);
        nvgRoundedRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w(), radius);
        nvgFill(nvg);
        fillColor.free();
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param rectStrokeColor rectangle color.
     */
    public static void drawRectStroke(long nvg, Vector2fc position, Vector2fc size, Vector4fc rectStrokeColor, float strokeWidth) {
        if (rectStrokeColor.w() == 0) {
            return;
        }
        NVGColor strokeColor = NVGColor.calloc();
        NvgColorUtil.rgba(rectStrokeColor, strokeColor);
        nvgBeginPath(nvg);
        nvgStrokeColor(nvg, strokeColor);
        nvgStrokeWidth(nvg, strokeWidth);
        nvgRect(nvg, position.x(), position.y(), size.x(), size.y());
        nvgStroke(nvg);
        strokeColor.free();
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param rectStrokeColor rectangle color.
     */
    public static void drawRectStroke(long nvg, Vector4fc rectangle, Vector4fc rectStrokeColor, float strokeWidth) {
        if (rectStrokeColor.w() == 0) {
            return;
        }
        NVGColor strokeColor = NVGColor.calloc();
        NvgColorUtil.rgba(rectStrokeColor, strokeColor);
        nvgBeginPath(nvg);
        nvgStrokeColor(nvg, strokeColor);
        nvgStrokeWidth(nvg, strokeWidth);
        nvgRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w());
        nvgStroke(nvg);
        strokeColor.free();
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context.
     * @param position rectangle position.
     * @param size rectangle size.
     * @param rectStrokeColor rectangle color.
     * @param radius cornder radius
     */
    public static void drawRectStroke(long nvg, Vector2fc position, Vector2fc size, Vector4fc rectStrokeColor, float strokeWidth, float radius) {
        if (rectStrokeColor.w() == 0) {
            return;
        }
        NVGColor strokeColor = NVGColor.calloc();
        NvgColorUtil.rgba(rectStrokeColor, strokeColor);
        nvgBeginPath(nvg);
        nvgStrokeColor(nvg, strokeColor);
        nvgStrokeWidth(nvg, strokeWidth);
        nvgRoundedRect(nvg, position.x(), position.y(), size.x(), size.y(), radius);
        nvgStroke(nvg);
        strokeColor.free();
    }

    /**
     * Used to draw rectangle stroke.
     *
     * @param nvg nanovg context
     * @param rectangle rectangle size and position.
     * @param rectStrokeColor rectangle color.
     * @param radius cornder radius
     */
    public static void drawRectStroke(long nvg, Vector4fc rectangle, Vector4fc rectStrokeColor, float strokeWidth, float radius) {
        if (rectStrokeColor.w() == 0) {
            return;
        }
        NVGColor strokeColor = NVGColor.calloc();
        NvgColorUtil.rgba(rectStrokeColor, strokeColor);
        nvgBeginPath(nvg);
        nvgStrokeColor(nvg, strokeColor);
        nvgStrokeWidth(nvg, strokeWidth);
        nvgRoundedRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w(), radius);
        nvgStroke(nvg);
        strokeColor.free();
    }

}
