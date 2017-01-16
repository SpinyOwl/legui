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
import java.util.Map;

import static org.liquidengine.legui.util.ColorUtil.half;
import static org.liquidengine.legui.util.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Alexander on 09.10.2016.
 */
public class NvgTextAreaRenderer extends NvgLeguiComponentRenderer {
    public static final String PRATIO   = "pratio";
    public static final String POFFSETX = "poffsetx";
    public static final String PHALIGN  = "phalign";
    public static final String PVALIGN  = "pvalign";
    public static final String POFFSETY = "poffsety";

    private final Vector4f caretColor    = new Vector4f(0, 0, 0, 0.5f);
    private final int      maxGlyphCount = 2048;

    private NVGColor                colorA = NVGColor.create();
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        TextArea agui = (TextArea) component;
        createScissor(context, component);
        {
            Vector2f pos  = Util.calculatePosition(component);
            Vector2f size = component.getSize();
            float    br   = agui.getCornerRadius();
            Vector4f bc   = new Vector4f(agui.getBackgroundColor());

            drawBackground(context, pos.x, pos.y, size.x, size.y, br, bc);

            TextState textState     = agui.getTextState();
            Vector4f  p             = new Vector4f(textState.getPadding()).add(2, 2, 2, 2);
            Vector4f  intersectRect = new Vector4f(pos.x + p.x, pos.y + p.y, size.x - p.x - p.z, size.y - p.y - p.w);

            intersectScissor(context, new Vector4f(intersectRect).sub(1, 1, -2, -2));
            renderTextNew(leguiContext, context, agui, pos, size, intersectRect, bc);
        }
        resetScissor(context);

        createScissor(context, component);
        {
            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderTextNew(LeguiContext leguiContext, long context, TextArea gui, Vector2f pos, Vector2f size, Vector4f rect, Vector4f bc) {
        TextState           textState      = gui.getTextState();
        String              text           = textState.getText();
        String              font           = textState.getFont();
        float               fontSize       = textState.getFontSize();
        HorizontalAlign     halign         = textState.getHorizontalAlign();
        VerticalAlign       valign         = textState.getVerticalAlign();
        Vector4f            textColor      = textState.getTextColor();
        int                 caretPosition  = gui.getCaretPosition();
        boolean             focused        = gui.getState().isFocused();
        Vector4f            selectionColor = gui.getSelectionColor();
        int                 caretLine      = 0;
        Map<String, Object> metadata       = gui.getMetadata();

        String[] lines            = text.split("\n", -1);
        int      lineCount        = lines.length;
        int[]    lineStartIndeces = new int[lineCount];
        int      caretOffset      = 0;
        // calculate caret offset for every line
        for (int i = 0; i < lineCount - 1; i++) {
            lineStartIndeces[i + 1] = lineStartIndeces[i] + lines[i].length() + 1;
            if (caretPosition >= lineStartIndeces[i + 1]) {
                caretOffset = lineStartIndeces[i + 1];
                caretLine = i + 1;
            }
        }

        // calculate line caret position
        int lineCaretPosition = caretPosition - caretOffset;

        // if not focused set caret line and caret position in line to default
        if (!focused) {
            caretLine = (valign == VerticalAlign.TOP ? 0 : (valign == VerticalAlign.BOTTOM ? lineCount - 1 : lineCount / 2));
            lineCaretPosition = (halign == HorizontalAlign.LEFT ? 0 : (halign == HorizontalAlign.RIGHT ? lines[0].length() : lines[0].length() / 2));
        }

        int   vp             = valign == VerticalAlign.TOP ? 0 : valign == VerticalAlign.MIDDLE ? 1 : valign == VerticalAlign.BOTTOM ? 2 : 1;
        float voffset        = (lineCount - 1) * fontSize * vp * -0.5f + (valign == VerticalAlign.BASELINE ? fontSize / 4f : 0);
        float caretx         = 0;
        float mouseCaretX    = 0;
        int   mouseLineIndex = 0;

        int   mouseCaretPosition = 0;
        float mouseX             = leguiContext.getCursorPosition().x;
        float mouseY             = leguiContext.getCursorPosition().y;
        float rat                = size.y * size.x;

        preinitializeTextRendering(context, font, fontSize, halign, valign, textColor);

        // we need to calculate x and y offsets
        String  caretLineText   = lines[caretLine];
        float[] caretLineBounds = calculateTextBoundsRect(context, rect, caretLineText, halign, valign);
        float   carety          = caretLineBounds[5] + voffset + fontSize * caretLine;
        float   offsetY         = 0;
        if (carety + fontSize > rect.y + rect.w) {
            offsetY = carety - fontSize - (rect.y + rect.w);
        } else if (carety < rect.y) {
            offsetY = carety - rect.y;
        }

        // also we need to calculate offset x // caretLine
        float offsetX = 0;
        caretx = getCaretx(context, lineCaretPosition, caretLineText, caretLineBounds);
        if (caretx > rect.z + rect.x) {
            offsetX = caretx - rect.x - rect.z;
        } else if (caretx < rect.x) {
            offsetX = caretx - rect.x;
        }

        Float           poffsetx = /*offsetX;//*/(Float) metadata.getOrDefault(POFFSETX, 0f);
        Float           poffsety = /*offsetY;//*/(Float) metadata.getOrDefault(POFFSETY, 0f);
        Float           pratio   = (Float) metadata.getOrDefault(PRATIO, rat);
        HorizontalAlign phalign  = (HorizontalAlign) metadata.getOrDefault(PHALIGN, halign);
        VerticalAlign   pvalign  = (VerticalAlign) metadata.getOrDefault(PVALIGN, valign);

        // Check if we should recalculate offset y
        if (pratio != rat || pvalign != valign) {
            poffsety = offsetY;
        } else {
            // and if ratio is the same we should check if we need to update offset
            if (carety + fontSize - poffsety > rect.y + rect.w) {
                poffsety = poffsety + fontSize + (carety - poffsety - rect.y - rect.w);
            } else if (carety - poffsety < rect.y) {
                poffsety = poffsety + (carety - poffsety - rect.y);
            }
        }

        // Check if we should recalculate offset x
        if (pratio != rat || phalign != halign) {
            poffsetx = offsetX;
        } else {
            // and if ratio is the same we should check if we need to update offset
            if (caretx - poffsetx > rect.z + rect.x) {
                poffsetx = poffsetx + (caretx - poffsetx - rect.z - rect.x);
            } else if (caretx - poffsetx < rect.x) {
                poffsetx = poffsetx + (caretx - poffsetx - rect.x);
            }
        }

        preinitializeTextRendering(context, font, fontSize, halign, valign, textColor);

        float[][] bounds = new float[lineCount][8];
        for (int i = 0; i < lineCount; i++) {
            String  line       = lines[i];
            float[] lineBounds = calculateTextBoundsRect(context, rect, line, halign, valign);
            bounds[i] = lineBounds;
        }
        // calculate default mouse line index
        if (lineCount > 0) {
            float llineY = bounds[lineCount - 1][5] - poffsety + voffset + fontSize * (lineCount - 1);
            if (mouseY > llineY) mouseLineIndex = lineCount - 1;
        }

        // calculate caret color based on time
        if (focused) {
            oppositeBlackOrWhite(bc, caretColor);
            caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);
        }

        // render selection background
        if (focused) {
            int startSelectionIndex = gui.getStartSelectionIndex();
            int endSelectionIndex   = gui.getEndSelectionIndex();
            // swap
            if (startSelectionIndex > endSelectionIndex) {
                startSelectionIndex = startSelectionIndex + endSelectionIndex;
                endSelectionIndex = startSelectionIndex - endSelectionIndex;
                startSelectionIndex = startSelectionIndex - endSelectionIndex;
            }
            if (startSelectionIndex != endSelectionIndex) {
                int startSelectionLine        = 0;
                int startSelectionIndexInLine = 0;
                int endSelectionLine          = 0;
                int endSelectionIndexInLine   = 0;
                for (int i = 0; i < lineCount; i++) {
                    if (startSelectionIndex > lineStartIndeces[i]) {
                        startSelectionLine = i;
                    }
                    if (endSelectionIndex > lineStartIndeces[i]) {
                        endSelectionLine = i;
                    }
                }
                startSelectionIndexInLine = startSelectionIndex - lineStartIndeces[startSelectionLine];
                endSelectionIndexInLine = endSelectionIndex - lineStartIndeces[endSelectionLine];

                float startSelectionCaretX = getCaretx(context, startSelectionIndexInLine, lines[startSelectionLine], bounds[startSelectionLine]);
                float endSelectionCaretX   = getCaretx(context, endSelectionIndexInLine, lines[endSelectionLine], bounds[endSelectionLine]);

                for (int i = 0; i < lineCount; i++) {
                    if (i >= startSelectionLine && i <= endSelectionLine) {
                        float x1 = bounds[i][4];
                        float w  = bounds[i][6];
                        float x2 = x1 + w;
                        if (i == startSelectionLine) {
                            x1 = startSelectionCaretX;
                        }
                        if (i == endSelectionLine) {
                            x2 = endSelectionCaretX;
                        }
                        w = x2 - x1;
                        drawRectangle(context, selectionColor, x1 - poffsetx, bounds[i][5] - poffsety + voffset + fontSize * i, w, bounds[i][7]);
                    }
                }
            }
        }

        // render every line of text
        for (int i = 0; i < lineCount; i++) {
            ByteBuffer lineBytes = null;
            try {
                String line = lines[i];
                lineBytes = memUTF8(line);

                alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
                int ng = nnvgTextGlyphPositions(context, bounds[i][4], 0, memAddress(lineBytes), 0, memAddress(glyphs), maxGlyphCount);

                float lineX = bounds[i][4] - poffsetx;
                float lineY = bounds[i][5] - poffsety + voffset + fontSize * i;

                // calculate mouse caret position
                if (lineY <= mouseY && lineY + fontSize > mouseY) {
                    if (line.length() == 0) {
                        mouseCaretX = caretx;
                    } else {
                        float mx = mouseX + poffsetx;
                        if (mx <= glyphs.get(0).minx()) {
                            mouseCaretPosition = 0;
                            mouseCaretX = glyphs.get(0).minx();
                        } else if (mx >= glyphs.get(ng - 1).maxx()) {
                            mouseCaretPosition = ng;
                            mouseCaretX = glyphs.get(ng - 1).maxx();
                        } else if (!leguiContext.isIconified()) {
                            // binary search mouse caret position
                            int     upper = ng;
                            int     lower = 0;
                            boolean found = false;
                            do {
                                int   index = (upper + lower) / 2;
                                float left  = index == 0 ? glyphs.get(index).minx() : glyphs.get(index).x();
                                float right = index >= ng - 1 ? glyphs.get(ng - 1).maxx() : glyphs.get(index + 1).x();
                                float mid   = (left + right) / 2f;
                                if (mx >= left && mx < right) {
                                    found = true;
                                    if (mx > mid) {
                                        mouseCaretPosition = index + 1;
                                        mouseCaretX = right;
                                    } else {
                                        mouseCaretPosition = index;
                                        mouseCaretX = left;
                                    }
                                } else if (mx >= right) {
                                    if (index != ng) {
                                        lower = index + 1;
                                    } else {
                                        found = true;
                                        mouseCaretPosition = ng;
                                        mouseCaretX = right;
                                    }
                                } else if (mx < left) {
                                    if (index != 0) {
                                        upper = index;
                                    } else {
                                        found = true;
                                        mouseCaretPosition = 0;
                                        mouseCaretX = left;
                                    }
                                }
                            } while (!found);
                        }
                    }
                    mouseCaretX -= poffsetx;
                    mouseLineIndex = i;
                    // render mouse caret
                    if (leguiContext.isDebugEnabled()) {
                        drawRectStroke(context, mouseCaretX - 1, lineY, 1, bounds[i][7], new Vector4f(caretColor).div(2), 0, 1);
                    }
                }

                // render current line background
                if (i == caretLine && focused) {
                    Vector4f currentLineBgColor = oppositeBlackOrWhite(bc);
                    currentLineBgColor.w = 0.1f;
                    drawRectangle(context, currentLineBgColor, rect.x, lineY, rect.z, fontSize);
                }

                renderTextLineToBounds(context, lineX, lineY, bounds[i][6], bounds[i][7], fontSize, font, textColor, line, HorizontalAlign.LEFT, VerticalAlign.MIDDLE, false);
                if (i == caretLine && focused) {
                    // render caret
                    drawRectStroke(context, caretx - poffsetx - 1, lineY, 1, bounds[i][7], caretColor, 0, 1);
                }
            } finally {
                // free allocated memory
                if (lineBytes != null) {
                    memFree(lineBytes);
                }
            }
        }

        // put updated offsets and ratio to metadata
        metadata.put(PRATIO, rat);
        metadata.put(POFFSETY, poffsety);
        metadata.put(POFFSETX, poffsetx);
        metadata.put(PVALIGN, valign);
        metadata.put(PHALIGN, halign);
        gui.setMouseCaretPosition(lineStartIndeces[mouseLineIndex] + mouseCaretPosition);
    }

    private float getCaretx(long context, int lineCaretPosition, String caretLineText, float[] caretLineBounds) {
        float      caretx;
        ByteBuffer caretLineBytes = null;
        try {
            // allocate ofheap memory and fill it with text
            caretLineBytes = memUTF8(caretLineText);
            // align text for calculations
            alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
            int ng = nnvgTextGlyphPositions(context, caretLineBounds[4], 0, memAddress(caretLineBytes), 0, memAddress(glyphs), maxGlyphCount);
            caretx = calculateCaretPos(lineCaretPosition, caretLineBounds, ng);
        } finally {
            memFree(caretLineBytes);
        }
        return caretx;
    }

    private void preinitializeTextRendering(long context, String font, float fontSize, HorizontalAlign halign, VerticalAlign valign, Vector4f textColor) {
        alignTextInBox(context, halign, valign);
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);
        nvgFillColor(context, rgba(textColor, colorA));
    }

    private void renderText(long context, LeguiContext leguiContext, TextArea gui, Vector2f pos, Vector2f size, TextState textState, int caretPosition, boolean focused, Vector4f bc) {
        String   text    = textState.getText();
        String[] lines   = text.split("\n", -1);
        int[]    offsets = new int[lines.length];
        if (!focused) caretPosition = lines[0].length() * textState.getHorizontalAlign().index / 2;
        int caretLine   = 0;
        int caretOffset = 0;

        Vector4f p         = textState.getPadding();
        float    x         = pos.x + p.x;
        float    y         = pos.y + p.y;
        float    w         = size.x - p.x - p.z;
        float    h         = size.y - p.y - p.w;
        float    fontSize  = textState.getFontSize();
        int      lineCount = lines.length;

        // calculate offset and line caret position
        for (int i = 0; i < lineCount - 1; i++) {
            offsets[i + 1] = offsets[i] + lines[i].length() + 1;
            if (caretPosition >= offsets[i + 1]) {
                caretOffset = offsets[i + 1];
                caretLine = i + 1;
            }
        }
        int lineCaretPosition = caretPosition - caretOffset;

        // calculate which lines should be rendered
        int maxLines = (int) Math.floor(h / fontSize);
        int topIndex = caretLine;
        int botIndex = caretLine;
        int diff     = 0;

        while (diff < maxLines - 1 && !(topIndex == 0 && botIndex == lineCount - 1)) {
            if (topIndex > 0) topIndex--;
            if (botIndex < lineCount - 1) botIndex++;
            diff = botIndex - topIndex;
        }

        String          font            = textState.getFont();
        Vector4f        textColor       = textState.getTextColor();
        HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
        VerticalAlign   verticalAlign   = textState.getVerticalAlign();
        alignTextInBox(context, horizontalAlign, verticalAlign);
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);

        // calculate caret x position
        String line   = lines[caretLine];
        float  caretx = getCaretX(context, x, w, line, lineCaretPosition, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);

        // calculate text x offset
        float offsetX = 0;
        if (caretx - x > w) {
            offsetX = caretx - x - w;
        } else if (caretx < x) {
            offsetX = caretx - x;
        }

        oppositeBlackOrWhite(bc, caretColor);
        caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);
        if (focused) {
            drawSelectionBackground(context, gui, lines, x, y, w, h, fontSize, topIndex, botIndex, horizontalAlign, verticalAlign, offsetX);
        }
        Vector4f lineColor = oppositeBlackOrWhite(bc).mul(1, 1, 1, 0.1f);

        // calculate new caret position based on mouse position
        float my = leguiContext.getCursorPosition().y;
        for (int i = topIndex; i <= botIndex; i++) {
            line = lines[i];
            float y1 = y + (i - topIndex) * fontSize;
            if (i == topIndex && my < y1) {
                gui.setMouseCaretPosition(calculateMouseCaretPosition(leguiContext, context, gui, x, y1, w, fontSize, offsetX, line, offsets[i], horizontalAlign, verticalAlign));
            } else if (i == botIndex && my > y1 + fontSize) {
                gui.setMouseCaretPosition(calculateMouseCaretPosition(leguiContext, context, gui, x, y1, w, fontSize, offsetX, line, offsets[i], horizontalAlign, verticalAlign));
            } else if (my >= y1 && my <= y1 + fontSize) {
                gui.setMouseCaretPosition(calculateMouseCaretPosition(leguiContext, context, gui, x, y1, w, fontSize, offsetX, line, offsets[i], horizontalAlign, verticalAlign));
            }
        }

        //render text and caret
        for (int i = topIndex; i <= botIndex; i++) {
            line = lines[i];
            float y1 = y + (i - topIndex) * fontSize;
            if (focused) {
                if (caretLine == i) {
                    drawRectangle(context, lineColor, pos.x, y1, size.x, fontSize);
                    renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
                    drawRectangle(context, caretColor, caretx - offsetX, y1, 1, fontSize);
                } else {
                    renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
                }
            } else {
                renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
            }
        }

        // draw caret based on mouse position
        if (leguiContext.isDebugEnabled() && gui.getState().isFocused()) {
            LineData lineData = getStartLineIndexAndLineNumber(lines, gui.getMouseCaretPosition());
            for (int i = topIndex; i <= botIndex; i++) {
                float y1 = y + (i - topIndex) * fontSize;
                if (lineData.lineIndex == i) {
                    float mcx = getCaretX(context, x, w, lines[lineData.lineIndex], lineData.caretPositionInLine, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);
                    drawRectangle(context, half(caretColor), mcx - offsetX, y1, 1, fontSize);
                }
            }
        }
    }

    private int calculateMouseCaretPosition(LeguiContext leguiContext, long context, TextArea gui,
                                            float x, float y, float w, float h,
                                            float offsetX, String line, int lineOffset,
                                            HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        float      bounds[]  = NvgRenderUtils.calculateTextBoundsRect(context, x, y, w, h, line, horizontalAlign, verticalAlign);
        ByteBuffer textBytes = null;
        try {
            textBytes = memUTF8(line);

            int   ng      = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);
            float mx      = leguiContext.getCursorPosition().x;
            int   newCPos = 0;
            int   upper   = ng - 1;
            if (upper <= 0) {
            } else {
                float px  = glyphs.get(0).x() - offsetX;
                float mpx = glyphs.get(upper).maxx() - offsetX;
                if (mx <= px) {
                    newCPos = 0;
                } else if (mx >= mpx) {
                    newCPos = upper + 1;
                } else {
                    for (int i = 0; i < upper; newCPos = i++) {
                        px = glyphs.get(i).x() - offsetX;
                        mpx = glyphs.get(i + 1).x() - offsetX;
                        if (mx >= px && mx <= mpx) {
                            if (mx - px < mpx - mx) {
                                newCPos = i;
                            } else {
                                newCPos = i + 1;
                            }
                            break;
                        }
                    }
                    px = glyphs.get(upper).x() - offsetX;
                    mpx = glyphs.get(upper).maxx() - offsetX;
                    if (mx >= px && mx <= mpx) {
                        if (mpx - mx > mx - px) {
                            newCPos = upper;
                        } else {
                            newCPos = upper + 1;
                        }
                    }
                }
            }
            return newCPos + lineOffset;
        } finally {
            if (textBytes != null) {
                MemoryUtil.memFree(textBytes);
            }
        }
    }

    private void drawSelectionBackground(long context, TextArea gui, String[] lines,
                                         float x, float y, float w, float h,
                                         float fontSize, int topIndex, int botIndex, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float offsetX) {
        String line;
        int    start = gui.getStartSelectionIndex();
        int    end   = gui.getEndSelectionIndex();


        if (start >= 0 && end >= 0 && start != end) {
            if (start > end) {
                int swap = start;
                start = end;
                end = swap;
            }

            LineData startData = getStartLineIndexAndLineNumber(lines, start);
            LineData endData   = getStartLineIndexAndLineNumber(lines, end);

            float startSelCaretx = getCaretX(context, x, w, lines[startData.lineIndex], startData.caretPositionInLine, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);
            float endSelCaretx   = getCaretX(context, x, w, lines[endData.lineIndex], endData.caretPositionInLine, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);

            Vector4f selectionColor = gui.getSelectionColor();
            for (int i = topIndex; i <= botIndex; i++) {
                line = lines[i];
                float y1 = y + (i - topIndex) * fontSize;
                if (i == startData.lineIndex && i == endData.lineIndex) {
                    drawRectangle(context, selectionColor, startSelCaretx - offsetX, y1, endSelCaretx - startSelCaretx, fontSize);
                } else if (i == startData.lineIndex) {
                    float endX = getCaretX(context, x, w, line, line.length(), fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);
                    drawRectangle(context, selectionColor, startSelCaretx - offsetX, y1, endX - startSelCaretx, fontSize);
                } else if (i > startData.lineIndex && i < endData.lineIndex) {
                    float startX = getCaretX(context, x, w, line, 0, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);
                    float endX   = getCaretX(context, x, w, line, line.length(), fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);
                    drawRectangle(context, selectionColor, startX - offsetX, y1, endX - startX, fontSize);
                } else if (i == endData.lineIndex) {
                    float startX = getCaretX(context, x, w, line, 0, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);
                    drawRectangle(context, selectionColor, startX - offsetX, y1, endSelCaretx - startX, fontSize);
                }
            }

        }
    }

    private float calculateCaretPos(int caretPosition, float[] textBounds, int ng) {
        float caretx = 0;
        if (caretPosition < ng) {
            try {
                caretx = caretPosition == 0 ? glyphs.get(caretPosition).minx() : glyphs.get(caretPosition).x();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } else {
            if (ng > 0) {
                caretx = glyphs.get(ng - 1).maxx();
            } else {
                caretx = textBounds[4];
            }
        }
        return caretx;
    }

    private LineData getStartLineIndexAndLineNumber(String[] lines, int caretPosition) {
        int caretLine   = 0;
        int caretOffset = 0;
        for (String line : lines) {
            int newOffset = caretOffset + line.length();
            if (newOffset >= caretPosition) {
                break;
            }
            caretLine++;
            caretOffset = newOffset + 1;
        }
        return new LineData(caretPosition - caretOffset, caretLine);
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

    private static class LineData {
        private int caretPositionInLine;
        private int lineIndex;

        public LineData(int caretPositionInLine, int lineIndex) {
            this.caretPositionInLine = caretPositionInLine;
            this.lineIndex = lineIndex;
        }
    }
}
