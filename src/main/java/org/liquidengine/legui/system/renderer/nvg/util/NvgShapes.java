package org.liquidengine.legui.system.renderer.nvg.util;

import static org.lwjgl.nanovg.NanoVG.NVG_ROUND;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgLineCap;
import static org.lwjgl.nanovg.NanoVG.nvgLineJoin;
import static org.lwjgl.nanovg.NanoVG.nvgLineTo;
import static org.lwjgl.nanovg.NanoVG.nvgMoveTo;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRectVarying;
import static org.lwjgl.nanovg.NanoVG.nvgStroke;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeColor;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeWidth;

import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.lwjgl.nanovg.NVGColor;

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
        try (NVGColor fillColor = NvgColorUtil.create(bgColor)) {
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
        try (NVGColor fillColor = NvgColorUtil.create(bgColor)) {
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
        try (NVGColor fillColor = NvgColorUtil.create(bgColor)) {
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
            try (NVGColor fillColor = NvgColorUtil.create(bgColor)) {
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
        try (NVGColor fillColor = NvgColorUtil.create(bgColor)) {
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
            try (NVGColor fillColor = NvgColorUtil.create(bgColor)) {
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
        try (NVGColor strokeColor = NvgColorUtil.create(rectStrokeColor)) {
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
        try (NVGColor strokeColor = NvgColorUtil.create(rectStrokeColor)) {
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
            try (NVGColor strokeColor = NvgColorUtil.create(rectStrokeColor)) {
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
        try (NVGColor strokeColor = NvgColorUtil.create(rectStrokeColor)) {
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
            try (NVGColor strokeColor = NvgColorUtil.create(rectStrokeColor)) {
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
        try (NVGColor strokeColor = NvgColorUtil.create(rectStrokeColor)) {
            nvgBeginPath(nvg);
            nvgStrokeColor(nvg, strokeColor);
            nvgStrokeWidth(nvg, strokeWidth);
            nvgRoundedRect(nvg, rectangle.x(), rectangle.y(), rectangle.z(), rectangle.w(), radius);
            nvgStroke(nvg);
        }
    }


    /**
     * Used to render line.
     *
     * @param nvg nanoVG context.
     * @param color color to render
     * @param width line width (end of the line)
     * @param lineCap line cap. One of: {@link org.lwjgl.nanovg.NanoVG#NVG_BUTT BUTT}, {@link org.lwjgl.nanovg.NanoVG#NVG_ROUND ROUND}, {@link
     * org.lwjgl.nanovg.NanoVG#NVG_SQUARE SQUARE}, {@link org.lwjgl.nanovg.NanoVG#NVG_BEVEL BEVEL}, {@link org.lwjgl.nanovg.NanoVG#NVG_MITER MITER}
     * @param fromX x coordinate of start point of line
     * @param fromY y coordinate of start point of line
     * @param toX x coordinate of end point of line
     * @param toY y coordinate of end point of line
     */
    public static void drawLine(long nvg, float width, Vector4f color, int lineCap, float fromX, float fromY, float toX, float toY) {
        try (NVGColor colorA = NvgColorUtil.create(color)) {
            nvgLineCap(nvg, lineCap);
            nvgLineJoin(nvg, NVG_ROUND);
            nvgStrokeWidth(nvg, width);
            nvgStrokeColor(nvg, colorA);
            nvgBeginPath(nvg);
            nvgMoveTo(nvg, fromX, fromY);
            nvgLineTo(nvg, toX, toY);
            nvgStroke(nvg);
        }
    }


    /**
     * Used to render line.
     *
     * @param nvg nanoVG context.
     * @param color color to render
     * @param width line width (end of the line)
     * @param lineCap line cap. One of: {@link org.lwjgl.nanovg.NanoVG#NVG_BUTT BUTT}, {@link org.lwjgl.nanovg.NanoVG#NVG_ROUND ROUND}, {@link
     * org.lwjgl.nanovg.NanoVG#NVG_SQUARE SQUARE}, {@link org.lwjgl.nanovg.NanoVG#NVG_BEVEL BEVEL}, {@link org.lwjgl.nanovg.NanoVG#NVG_MITER MITER}
     * @param from start point of line
     * @param to end point of line
     */
    public static void drawLine(long nvg, float width, Vector4f color, int lineCap, Vector2fc from, Vector2fc to) {
        drawLine(nvg, width, color, lineCap, from.x(), from.y(), to.x(), to.y());
    }

}
