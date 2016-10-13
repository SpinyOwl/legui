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
import org.liquidengine.legui.util.ColorConstants;
import org.liquidengine.legui.util.NVGUtils;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Alexander on 09.10.2016.
 */
public class NvgTextAreaRenderer extends NvgLeguiComponentRenderer {
    public static final Vector4f BLUE = ColorConstants.lightBlue();
    public static final Vector4f BLACK = ColorConstants.black();
    public static final Vector4f RED = ColorConstants.red();
    private final int maxGlyphCount = 2048;
    private NVGColor colorA = NVGColor.calloc();
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);
    private NVGColor color = NVGColor.calloc();

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

            nvgIntersectScissor(context, pos.x, pos.y, size.x, size.y);
            renderText(context, pos, size, textState, agui.getCaretPosition());
        }
        resetScissor(context);

        createScissor(context, component);
        {
            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderText(long context, Vector2f pos, Vector2f size, TextState textState, int caretPosition) {
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        int caretLine = 0;
        int caretOffset = 0;
        for (String line : lines) {
            int newOffset = caretOffset + line.length();
            if (newOffset >= caretPosition) {
                break;
            }
            caretLine++;
            caretOffset = newOffset + 1;
        }

        int lineCaretPosition = caretPosition - caretOffset;

        Vector4f p = textState.getPadding();
        float x = pos.x + p.x;
        float y = pos.y + p.y;
        float w = size.x - p.x - p.z;
        float h = size.y - p.y - p.w;
        float fontSize = textState.getFontSize();
        int lineCount = lines.length;

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


        String line = lines[caretLine];
        float bounds[] = NvgRenderUtils.calculateTextBoundsRect(context, x, 0, w, fontSize, line, 0, horizontalAlign, verticalAlign);
        ByteBuffer textBytes = MemoryUtil.memUTF8(line);
        int ng = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);
        float caretx = 0;
        if (lineCaretPosition < ng) {
            try {
                caretx = glyphs.get(lineCaretPosition).x();
            } catch (IndexOutOfBoundsException e) {
                System.out.println(lineCaretPosition);
                e.printStackTrace();
            }
        } else {
            if (ng > 0) {
                caretx = glyphs.get(ng - 1).maxx();
            } else {
                caretx = x + horizontalAlign.index * w / 2f;
            }
        }

        float offsetX = 0;
        if (caretx - x > w) {
            offsetX = caretx - x - w;
        } else if (caretx < x) {
            offsetX = caretx - x;
        }

        for (int i = topIndex; i <= botIndex; i++) {
            line = lines[i];
            float y1 = y + (i - topIndex) * fontSize;
            if (i == caretLine) {
                drawRectangle(context, BLUE, pos.x, y1, size.x, fontSize);
                renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
                drawRectangle(context, RED, caretx - offsetX, y1, 1, fontSize);
            } else {
                renderTextLineToBounds(context, x - offsetX * (1 + horizontalAlign.index), y1, w + offsetX * 2, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
            }
        }

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
