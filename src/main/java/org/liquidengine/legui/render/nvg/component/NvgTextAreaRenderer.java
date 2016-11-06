package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NVGUtils;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.util.ColorUtil.half;
import static org.liquidengine.legui.util.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Alexander on 09.10.2016.
 */
public class NvgTextAreaRenderer extends NvgLeguiComponentRenderer {
    private final Vector4f caretColor = new Vector4f(0, 0, 0, 0.5f);
    private final int maxGlyphCount = 2048;
    private NVGColor colorA = NVGColor.calloc();
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        TextArea agui = (TextArea) component;
        createScissor(context, component);
        {
            Vector2f pos = Util.calculatePosition(component);
            Vector2f size = component.getSize();
            float br = agui.getCornerRadius();
            Vector4f bc = new Vector4f(agui.getBackgroundColor());

            drawBackground(context, pos.x, pos.y, size.x, size.y, br, bc);

            TextState textState = agui.getTextState();
            Vector4f p = new Vector4f(textState.getPadding());
            p.x = p.x > 0 ? p.x - 1 : 0;
            p.y = p.y > 0 ? p.y - 1 : 0;
            p.z = p.z > 0 ? p.z - 1 : 0;
            p.w = p.w > 0 ? p.w - 1 : 0;

            renderText(context, leguiContext, agui, pos, size, textState, agui.getCaretPosition(), agui.isFocused(), bc);
        }
        resetScissor(context);

        createScissor(context, component);
        {
            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderText(long context, LeguiContext leguiContext, TextArea gui, Vector2f pos, Vector2f size, TextState textState, int caretPosition, boolean focused, Vector4f bc) {
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        int[] offsets = new int[lines.length];
        if (!focused) caretPosition = lines[0].length() /** textState.getHorizontalAlign().index*/ / 2;
        int caretLine = 0;
        int caretOffset = 0;

        Vector4f p = textState.getPadding();
        float x = pos.x + p.x;
        float y = pos.y + p.y;
        float w = size.x - p.x - p.z;
        float h = size.y - p.y - p.w;
        float fontSize = textState.getFontSize();
        int lineCount = lines.length;


        // calculate offset and line caret position
        for (int i = 0; i < lineCount - 1; i++) {
            offsets[i + 1] = offsets[i] + lines[i].length() + 1;
            if (caretPosition >= offsets[i + 1]) {
                caretOffset = offsets[i + 1];
                caretLine = i+1;
            }
        }
        int lineCaretPosition = caretPosition - caretOffset;

        // calculate which lines should be rendered
        int maxLines = (int) Math.floor(h / fontSize);
        int topIndex = caretLine;
        int botIndex = caretLine;
        int diff = 0;

        while (diff < maxLines - 1 && !(topIndex == 0 && botIndex == lineCount - 1)) {
            if (topIndex > 0) topIndex--;
            if (botIndex < lineCount - 1) botIndex++;
            diff = botIndex - topIndex;
        }


        String font = textState.getFont();
        Vector4f textColor = textState.getTextColor();
        HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
        VerticalAlign verticalAlign = textState.getVerticalAlign();
        alignTextInBox(context, horizontalAlign, verticalAlign);
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);

        // calculate caret x position
        String line = lines[caretLine];
        float caretx = getCaretX(context, x, w, line, lineCaretPosition, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);

        // calculate text x offset
        float offsetX = 0;
        if (caretx - x > w) {
            offsetX = caretx - x - w;
        } else if (caretx < x) {
            offsetX = caretx - x;
        }

        oppositeBlackOrWhite(bc, caretColor);
        caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);

        Vector4f selectionColor = oppositeBlackOrWhite(bc).mul(1, 1, 1, 0.1f);

        //render text and caret
        for (int i = topIndex; i <= botIndex; i++) {
            line = lines[i];
            float y1 = y + (i - topIndex) * fontSize;
            if (focused) {
                if (caretLine == i) {
                    drawRectangle(context, selectionColor, pos.x, y1, size.x, fontSize);
                    renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
                    drawRectangle(context, caretColor, caretx - offsetX, y1, 1, fontSize);
                } else {
                    renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
                }
                calculateMouseCaretPosition(leguiContext, context, gui, x, y1, w, fontSize, offsetX, line, offsets[i], horizontalAlign, verticalAlign);
            } else {
                renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
            }
        }

    }

    private void calculateMouseCaretPosition(LeguiContext leguiContext, long context, TextArea gui,
                                             float x, float y, float w, float h,
                                             float offsetX, String line, int lineOffset,
                                             HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        float bounds[] = NvgRenderUtils.calculateTextBoundsRect(context, x, y, w, h, line, 0, horizontalAlign, verticalAlign);
        ByteBuffer textBytes = MemoryUtil.memUTF8(line);
        int ng = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);
        float mx = leguiContext.getCursorPosition().x;
        float my = leguiContext.getCursorPosition().y;
        if (my < y || my >= y + h) return;
        float cx = 0;
        int newCPos = 0;
        int upper = ng - 1;
        if (upper <= 0) {
        } else {
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
        gui.setMouseCaretPosition(lineOffset + newCPos);
    }

    private void drawBackground(long context, float x, float y, float w, float h, float br, Vector4f bc) {
        if (bc.w != 0) {
            nvgSave(context);
            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, br);
            nvgFillColor(context, NVGUtils.rgba(bc, colorA));
            nvgFill(context);
        }
    }
}
