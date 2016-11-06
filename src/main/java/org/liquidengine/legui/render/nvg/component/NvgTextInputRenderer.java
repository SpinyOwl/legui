package org.liquidengine.legui.render.nvg.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.util.ColorUtil.half;
import static org.liquidengine.legui.util.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Shcherbin Alexander on 5/20/2016.
 */
public class NvgTextInputRenderer extends NvgLeguiComponentRenderer {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Vector4f caretColor = new Vector4f(0, 0, 0, 0.5f);
    private final int maxGlyphCount = 1024;
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);
    private NVGColor colorA = NVGColor.create();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            TextInput agui = (TextInput) component;
            Vector2f pos = Util.calculatePosition(component);
            Vector2f size = component.getSize();
            boolean enabled = agui.isEnabled();
            Vector4f bc = new Vector4f(agui.getBackgroundColor());

            if (enabled && agui.isFocused()) {
                bc.w *= 1.1f;
            } else if (!enabled) {
                bc.w *= 0.5f;
            }
            if (!agui.isEditable()) {
                bc.w *= 0.3f;
            }
            drawBackground(context, pos.x, pos.y, size.x, size.y, agui.getCornerRadius(), bc);

            TextState textState = agui.getTextState();
            Vector4f p = new Vector4f(textState.getPadding());
            p.x = p.x > 0 ? p.x - 1 : 0;
            p.y = p.y > 0 ? p.y - 1 : 0;
            p.z = p.z > 0 ? p.z - 1 : 0;
            p.w = p.w > 0 ? p.w - 1 : 0;

            nvgIntersectScissor(context, pos.x, pos.y, size.x, size.y);
            renderText(leguiContext, context, agui, pos, size, textState, agui.getCaretPosition(), agui.isFocused(), bc);
        }
        resetScissor(context);

        createScissor(context, component);
        {
            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderText(LeguiContext leguiContext, long context, TextInput gui, Vector2f pos, Vector2f size, TextState textState, int caretPosition, boolean focused, Vector4f bc) {
        String text = textState.getText();
        if (!focused) caretPosition = text.length() * textState.getHorizontalAlign().index / 2;
        String font = textState.getFont();
        Vector4f textColor = textState.getTextColor();
        float fontSize = textState.getFontSize();
        HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
        VerticalAlign verticalAlign = textState.getVerticalAlign();
        alignTextInBox(context, horizontalAlign, verticalAlign);
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);
        nvgFillColor(context, rgba(textColor, colorA));

        Vector4f p = new Vector4f(textState.getPadding());
        float x = pos.x + p.x;
        float y = pos.y + p.y;
        float w = size.x - p.x - p.z;
        float h = size.y - p.y - p.w;

        float caretx = getCaretX(context, x, w, text, caretPosition, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);

        // calculate text x offset
        float offsetX = 0;
        if (caretx - x > w) {
            offsetX = caretx - x - w;
        } else if (caretx < x) {
            offsetX = caretx - x;
        }

        oppositeBlackOrWhite(bc, caretColor);
        caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);
        float bounds[] = calculateTextBoundsRect(context, x, y, w, h, text, 0, horizontalAlign, verticalAlign);
        if (focused) {
            renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y, w + offsetX * 2, h, fontSize, font, textColor, text, horizontalAlign, verticalAlign, false);
            drawRectangle(context, caretColor, caretx - offsetX, bounds[5], 1, fontSize);
            calculateMouseCaretPosition(leguiContext, context, gui, x, y, w, h, offsetX, text, horizontalAlign, verticalAlign);
        } else {
            renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y, w + offsetX * 2, h, fontSize, font, textColor, text, horizontalAlign, verticalAlign, false);
        }
    }

    private void calculateMouseCaretPosition(LeguiContext leguiContext, long context, TextInput gui, float x, float y, float w, float h, float offsetX, String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        float bounds[] = NvgRenderUtils.calculateTextBoundsRect(context, x, y, w, h, text, 0, horizontalAlign, verticalAlign);
        ByteBuffer textBytes = MemoryUtil.memUTF8(text);
        int ng = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);
        float mx = leguiContext.getCursorPosition().x;
        float cx = 0;
        int newCPos = 0;
        int upper = ng - 1;
        if (upper > 0) {
            float px = glyphs.get(0).x() - offsetX;
            float mpx = glyphs.get(upper).maxx() - offsetX;
            if (mx <= px) {
                cx = px;
                newCPos = 0;
            } else if (mx >= mpx) {
                cx = mpx;
                newCPos = upper + 1;
            } else {
                for (int i = 0; i < upper; newCPos = i++) {
                    px = glyphs.get(i).x() - offsetX;
                    mpx = glyphs.get(i + 1).x() - offsetX;
                    if (mx >= px && mx <= mpx) {
                        if (mx - px < mpx - mx) {
                            cx = px;
                            newCPos = i;
                        } else {
                            cx = mpx;
                            newCPos = i + 1;
                        }
                        break;
                    }
                }
                px = glyphs.get(upper).x() - offsetX;
                mpx = glyphs.get(upper).maxx() - offsetX;
                if (mx >= px && mx <= mpx) {
                    if (mpx - mx > mx - px) {
                        cx = px;
                        newCPos = upper;
                    } else {
                        cx = mpx;
                        newCPos = upper + 1;
                    }
                }
            }
        }
        drawRectangle(context, half(caretColor), cx, bounds[5], 1, bounds[7]);
        gui.setMouseCaretPosition(newCPos);
    }

    private void drawBackground(long context, float x, float y, float w, float h, float br, Vector4f bc) {
        if (bc.w != 0) {
            nvgSave(context);
            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, br);
            nvgFillColor(context, rgba(bc, colorA));
            nvgFill(context);
        }
    }

}
