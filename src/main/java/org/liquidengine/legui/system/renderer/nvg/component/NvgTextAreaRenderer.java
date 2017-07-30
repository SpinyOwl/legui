package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;

import java.nio.ByteBuffer;
import java.util.Map;

import static org.liquidengine.legui.color.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.util.NVGUtils.rgba;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * NanoVG Text area renderer.
 */
public class NvgTextAreaRenderer extends NvgComponentRenderer<TextArea> {
    private static final String PRATIO = "pratio";
    private static final String POFFSETX = "poffsetx";
    private static final String PHALIGN = "phalign";
    private static final String PVALIGN = "pvalign";
    private static final String POFFSETY = "poffsety";

    private final Vector4f caretColor = new Vector4f(0, 0, 0, 0.5f);
    private final int maxGlyphCount = 2048;

    private NVGColor colorA = NVGColor.create();
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);

    @Override
    public void renderComponent(TextArea component, Context leguiContext, long context) {
        createScissor(context, component);
        {
            Vector2f pos = component.getScreenPosition();
            Vector2f size = component.getSize();
            float br = component.getCornerRadius();
            Vector4f bc = new Vector4f(component.getBackgroundColor());

            drawBackground(context, pos.x, pos.y, size.x, size.y, br, bc);

            TextState textState = component.getTextState();
            Vector4f p = new Vector4f(textState.getPadding()).add(2, 2, 2, 2);
            Vector4f intersectRect = new Vector4f(pos.x + p.x, pos.y + p.y, size.x - p.x - p.z, size.y - p.y - p.w);

            intersectScissor(context, new Vector4f(intersectRect).sub(1, 1, -2, -2));
            renderText(leguiContext, context, component, size, intersectRect, bc);
        }
        resetScissor(context);

        createScissor(context, component);
        {
            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderText(Context leguiContext, long context, TextArea gui, Vector2f size, Vector4f rect, Vector4f bc) {
        TextState textState = gui.getTextState();
        String text = textState.getText();
        String font = textState.getFont();
        float fontSize = textState.getFontSize();
        HorizontalAlign halign = textState.getHorizontalAlign();
        VerticalAlign valign = textState.getVerticalAlign();
        Vector4f textColor = textState.getTextColor();
        int caretPosition = gui.getCaretPosition();
        boolean focused = gui.isFocused();
        Vector4f highlightColor = textState.getHighlightColor();
        int caretLine = 0;
        Map<String, Object> metadata = gui.getMetadata();

        String[] lines = text.split("\n", -1);
        int lineCount = lines.length;
        int[] lineStartIndeces = new int[lineCount];
        int caretOffset = 0;
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

        int vp = valign == VerticalAlign.TOP ? 0 : valign == VerticalAlign.MIDDLE ? 1 : valign == VerticalAlign.BOTTOM ? 2 : 1;
        float voffset = (lineCount - 1) * fontSize * vp * -0.5f + (valign == VerticalAlign.BASELINE ? fontSize / 4f : 0);
        float caretx;
        float mouseCaretX = 0;
        int mouseLineIndex = 0;

        int mouseCaretPositionInLine = 0;
        Vector2f cursorPosition = Mouse.getCursorPosition();
        float mouseX = cursorPosition.x;
        float mouseY = cursorPosition.y;
        float rat = size.y * size.x;

        preinitializeTextRendering(context, font, fontSize, halign, valign, textColor);

        // we need to calculate x and y offsets
        String caretLineText = lines[caretLine];
        float[] caretLineBounds = calculateTextBoundsRect(context, rect, caretLineText, halign, valign);
        float carety = caretLineBounds[5] + voffset + fontSize * caretLine;
        float offsetY = 0;
        offsetY = calculateOffsetY(rect, fontSize, carety, offsetY);

        // also we need to calculate offset x // caretLine
        float offsetX = 0;
        caretx = getCaretx(context, lineCaretPosition, caretLineText, caretLineBounds);
        offsetX = calculateOffsetX(rect, caretx, offsetX);

        Float poffsetx = /*offsetX;//*/(Float) metadata.getOrDefault(POFFSETX, 0f);
        Float poffsety = /*offsetY;//*/(Float) metadata.getOrDefault(POFFSETY, 0f);
        Float pratio = (Float) metadata.getOrDefault(PRATIO, rat);
        HorizontalAlign phalign = (HorizontalAlign) metadata.getOrDefault(PHALIGN, halign);
        VerticalAlign pvalign = (VerticalAlign) metadata.getOrDefault(PVALIGN, valign);

        // Check if we should recalculate offset y
        poffsety = recalculateOffsetY(rect, fontSize, valign, rat, carety, offsetY, poffsety, pratio, pvalign);

        // Check if we should recalculate offset x
        poffsetx = recalculateOffsetX(rect, halign, caretx, rat, offsetX, poffsetx, pratio, phalign);

        preinitializeTextRendering(context, font, fontSize, halign, valign, textColor);

        float[][] bounds = new float[lineCount][8];
        for (int i = 0; i < lineCount; i++) {
            String line = lines[i];
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
        renderSelectionBackground(context, gui, fontSize, focused, highlightColor, lines, lineCount, lineStartIndeces, voffset, poffsetx, poffsety, bounds);

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
                        if (mx <= glyphs.get(0).x()) {
                            mouseCaretPositionInLine = 0;
                            mouseCaretX = glyphs.get(0).x();
                        } else if (mx >= glyphs.get(ng - 1).maxx()) {
                            mouseCaretPositionInLine = ng;
                            mouseCaretX = glyphs.get(ng - 1).maxx();
                            // if window not minimized
                        } else if (!leguiContext.isIconified()) {
                            // binary search mouse caret position
                            int upper = ng;
                            int lower = 0;
                            boolean found = false;
                            do {
                                int index = (upper + lower) / 2;
                                float left = glyphs.get(index).x();
                                float right = index >= ng - 1 ? glyphs.get(ng - 1).maxx() : glyphs.get(index + 1).x();
                                float mid = (left + right) / 2f;
                                if (mx >= left && mx < right) {
                                    found = true;
                                    if (mx > mid) {
                                        mouseCaretPositionInLine = index + 1;
                                        mouseCaretX = right;
                                    } else {
                                        mouseCaretPositionInLine = index;
                                        mouseCaretX = left;
                                    }
                                } else if (mx >= right) {
                                    if (index != ng) {
                                        lower = index + 1;
                                    } else {
                                        found = true;
                                        mouseCaretPositionInLine = ng;
                                        mouseCaretX = right;
                                    }
                                } else if (mx < left) {
                                    if (index != 0) {
                                        upper = index;
                                    } else {
                                        found = true;
                                        mouseCaretPositionInLine = 0;
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
                renderCurrentLineBackground(context, rect, bc, fontSize, focused, caretLine, i, lineY);

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
        gui.setMouseCaretPosition(lineStartIndeces[mouseLineIndex] + mouseCaretPositionInLine);

        // put updated offsets and ratio to metadata
        metadata.put(PRATIO, rat);
        metadata.put(POFFSETY, poffsety);
        metadata.put(POFFSETX, poffsetx);
        metadata.put(PVALIGN, valign);
        metadata.put(PHALIGN, halign);
    }

    private void renderCurrentLineBackground(long context, Vector4f rect, Vector4f bc, float fontSize, boolean focused, int caretLine, int i, float lineY) {
        if (i == caretLine && focused) {
            Vector4f currentLineBgColor = oppositeBlackOrWhite(bc);
            currentLineBgColor.w = 0.1f;
            drawRectangle(context, currentLineBgColor, rect.x, lineY, rect.z, fontSize);
        }
    }

    private void renderSelectionBackground(long context, TextArea gui, float fontSize, boolean focused, Vector4f selectionColor, String[] lines, int lineCount, int[] lineStartIndeces, float voffset, Float poffsetx, Float poffsety, float[][] bounds) {
        if (focused) {
            int startSelectionIndex = gui.getStartSelectionIndex();
            int endSelectionIndex = gui.getEndSelectionIndex();
            // swap
            if (startSelectionIndex > endSelectionIndex) {
                startSelectionIndex = startSelectionIndex + endSelectionIndex;
                endSelectionIndex = startSelectionIndex - endSelectionIndex;
                startSelectionIndex = startSelectionIndex - endSelectionIndex;
            }
            if (startSelectionIndex != endSelectionIndex) {
                int startSelectionLine = 0;
                int startSelectionIndexInLine;
                int endSelectionLine = 0;
                int endSelectionIndexInLine;
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
                float endSelectionCaretX = getCaretx(context, endSelectionIndexInLine, lines[endSelectionLine], bounds[endSelectionLine]);

                for (int i = 0; i < lineCount; i++) {
                    if (i >= startSelectionLine && i <= endSelectionLine) {
                        float x1 = bounds[i][4];
                        float w = bounds[i][6];
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
    }

    private float calculateOffsetX(Vector4f rect, float caretx, float offsetX) {
        float newOffsetX = offsetX;
        if (caretx > rect.z + rect.x) {
            newOffsetX = caretx - rect.x - rect.z;
        } else if (caretx < rect.x) {
            newOffsetX = caretx - rect.x;
        }
        return newOffsetX;
    }

    private float calculateOffsetY(Vector4f rect, float fontSize, float carety, float offsetY) {
        float newOffsetY = offsetY;
        if (carety + fontSize > rect.y + rect.w) {
            newOffsetY = carety - fontSize - (rect.y + rect.w);
        } else if (carety < rect.y) {
            newOffsetY = carety - rect.y;
        }
        return newOffsetY;
    }

    private Float recalculateOffsetX(Vector4f rect, HorizontalAlign halign, float caretx, float rat, float offsetX, Float poffsetx, Float pratio, HorizontalAlign phalign) {
        float newPOffsetX = poffsetx;
        if (pratio != rat || phalign != halign) {
            newPOffsetX = offsetX;
        } else {
            // and if ratio is the same we should check if we need to update offset
            if (caretx - poffsetx > rect.z + rect.x) {
                newPOffsetX = poffsetx + (caretx - poffsetx - rect.z - rect.x);
            } else if (caretx - poffsetx < rect.x) {
                newPOffsetX = poffsetx + (caretx - poffsetx - rect.x);
            }
        }
        return newPOffsetX;
    }

    private Float recalculateOffsetY(Vector4f rect, float fontSize, VerticalAlign valign, float rat, float carety, float offsetY, Float poffsety, Float pratio, VerticalAlign pvalign) {
        float newPOffsetY = poffsety;
        if (pratio != rat || pvalign != valign) {
            newPOffsetY = offsetY;
        } else {
            // and if ratio is the same we should check if we need to update offset
            if (carety + fontSize - poffsety > rect.y + rect.w) {
                newPOffsetY = poffsety + fontSize + (carety - poffsety - rect.y - rect.w);
            } else if (carety - poffsety < rect.y) {
                newPOffsetY = poffsety + (carety - poffsety - rect.y);
            }
        }
        return newPOffsetY;
    }

    private float getCaretx(long context, int lineCaretPosition, String caretLineText, float[] caretLineBounds) {
        float caretx;
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

    private float calculateCaretPos(int caretPosition, float[] textBounds, int ng) {
        float caretx = 0;
        if (caretPosition < ng) {
            try {
                caretx = glyphs.get(caretPosition).x();
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
