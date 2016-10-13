package org.liquidengine.legui.render.nvg.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NVGUtils;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.util.ColorUtil.blackOrWhite;
import static org.liquidengine.legui.util.ColorUtil.half;
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
    private long glyphAddress = memAddress(glyphs);

    @Override
    public void initialize() {

    }

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            TextInput agui = (TextInput) component;
            Vector2f pos = Util.calculatePosition(component);
            float x = pos.x;
            float y = pos.y;
            float w = component.getSize().x;
            float h = component.getSize().y;
            float br = agui.getCornerRadius();
            boolean enabled = agui.isEnabled();

            TextState textState = agui.getTextState();
            HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
            VerticalAlign verticalAlign = textState.getVerticalAlign();

            Vector4f bc = new Vector4f(agui.getBackgroundColor());

            float fontSize = textState.getFontSize();
            String font = textState.getFont() == null ? FontRegister.DEFAULT : textState.getFont();
            Vector4f textColor = textState.getTextColor();
            Vector4f pad = new Vector4f(textState.getPadding());
            float tx = x + pad.x;
            float ty = y + pad.y;
            float tw = w - pad.x - pad.z;
            float th = h - pad.y - pad.w;

            if (enabled && agui.isFocused()) {
                bc.w *= 1.1f;
            } else if (!enabled) {
                bc.w *= 0.5f;
            }
            if (!agui.isEditable()) {
                bc.w *= 0.3f;
            }

            drawBackground(context, x, y, w, h, br, bc);

            Border border = agui.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }

            String text;
            int caretPos = agui.getCaretPosition();
            nvgFontFace(context, font);
            nvgFontSize(context, fontSize);
            int renderBounds[] = new int[2];

            // get visible text to draw
            String textToDraw = null;
            try {
                text = updateRenderedText(agui, context, tw, renderBounds);
                textToDraw = text.substring(renderBounds[0], renderBounds[1]);
            } catch (Throwable e) {
                LOGGER.warn(e);
            }
            if (textToDraw == null) return;
            ByteBuffer byteText = MemoryUtil.memUTF8(textToDraw);

            // draw text
            NvgRenderUtils.alignTextInBox(context, horizontalAlign, verticalAlign);
            float bounds[] = NvgRenderUtils.calculateTextBoundsRect(context, tx, ty, tw, th, textToDraw, 0, horizontalAlign, verticalAlign);
            nvgBeginPath(context);
            NVGColor textColorN = textColor.w == 0 ? NVGUtils.rgba(0.0f, 0.0f, 0.0f, 1f, colorA) : NVGUtils.rgba(textColor, colorA);
            nvgFillColor(context, textColorN);
            nvgText(context, bounds[0], bounds[1], textToDraw, 0);

            int ng = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(byteText), 0, glyphAddress, maxGlyphCount);
            // draw caret
            if (agui.isFocused()) {
                calcMouseCaretPos(leguiContext, bounds, ng, agui, renderBounds, context);
                drawCaret(context, horizontalAlign, bc, tx, tw, bounds, byteText, caretPos - renderBounds[0], ng);
            }
        }
        resetScissor(context);
    }


    public String updateRenderedText(TextInput agui, long context, float targetTextWidth, int[] renderBounds) {
        String text = agui.getTextState().getText();
        ByteBuffer byteText = MemoryUtil.memUTF8(text);
        long glyphAddress = memAddress(glyphs);
        glyphs.clear();
        NvgRenderUtils.alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
        int nGlypths = nnvgTextGlyphPositions(context, 0, 0, memAddress(byteText), 0, glyphAddress, maxGlyphCount);
        int caretPos = agui.getCaretPosition();

        int leftPos = 0, rightPos = nGlypths > 0 ? nGlypths : -1;

        // get text to render depending on cursor position
        if (agui.isFocused()) {

            if (nGlypths == 0) {
                renderBounds[0] = 0;
                renderBounds[1] = 0;
                return text;
            }

            float width = 0;
            int leftBound = caretPos;
            int rightBound = caretPos;
            while (width <= targetTextWidth && !(leftBound == 0 && rightBound == nGlypths)) {
                if (leftBound > 0) leftBound--;
                if (rightBound < nGlypths) rightBound++;

                float left = glyphs.get(leftBound).x();
                float right = (rightBound == nGlypths ? glyphs.get(rightBound - 1).maxx() : glyphs.get(rightBound).x());
                width = right - left;
            }
            renderBounds[0] = leftBound;
            renderBounds[1] = rightBound;
        } else {
            renderBounds[0] = 0;
            renderBounds[1] = getR(targetTextWidth, nGlypths) + 1;
        }
        return text;
    }


    private void drawCaret(long context, HorizontalAlign horizontalAlign, Vector4f bc, float tx, float tw, float[] bounds, ByteBuffer byteText,
                           int currCaretPos, int ng) {
        float caretx;

        if (currCaretPos < ng) {
            caretx = glyphs.get(currCaretPos).x();
        } else {
            if (ng > 0)
                caretx = glyphs.get(ng - 1).maxx();
            else {
                caretx = tx + horizontalAlign.index * tw / 2f;
            }
        }
        blackOrWhite(bc, caretColor);
        caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);

        NvgRenderUtils.drawRectangle(context, caretColor, (int) caretx, bounds[5], 1f, bounds[7]);
    }


    private void calcMouseCaretPos(LeguiContext renderContext, float[] bounds, int ng, TextInput gui, int[] textBounds, long context) {
//        float mouseCaretx = 0;
        Vector2f cursorPosition = renderContext.getCursorPosition();

        double xpo = cursorPosition.x;
        float mx = (float) xpo;
        float fl = bounds[4];
        float fr = bounds[6] + bounds[4];
        int newCPos = 0;
        if (mx <= fl) {
            newCPos = 0;
            NvgRenderUtils.drawRectangle(context, half(caretColor), fl, bounds[5], 1f, bounds[7]);
        } else if (mx >= fr) {
            newCPos = ng;
            NvgRenderUtils.drawRectangle(context, half(caretColor), fr, bounds[5], 1f, bounds[7]);
        } else {
            int upper = ng - 1;
            for (int i = 0; i < upper; newCPos = i++) {
                float x = glyphs.get(i).x();
                float maxx = glyphs.get(i + 1).x();

                if (mx >= x && mx <= maxx) {
                    if (maxx - mx > mx - x) {
                        NvgRenderUtils.drawRectangle(context, half(caretColor), x, bounds[5], 1f, bounds[7]);
                        newCPos = i;
                    } else {
                        newCPos = i + 1;
                        NvgRenderUtils.drawRectangle(context, half(caretColor), maxx, bounds[5], 1f, bounds[7]);
                    }
                    break;
                }
            }

            // check also last symbol
            if (upper > 0) {
                float x = glyphs.get(upper).x();
                float maxx = glyphs.get(upper).maxx();

                if (mx >= x && mx <= maxx) {
                    if (maxx - mx > mx - x) {
                        newCPos = upper;
                    } else {
                        newCPos = upper + 1;
                    }
                }
            }
        }
        gui.setMouseCaretPosition(newCPos + textBounds[0]);
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

    @Override
    public void destroy() {

    }

    private int getR(float tw, int nGlypths) {
        int low = 0, high = nGlypths - 1, mid = 0;
        while (high - low > 1) {
            mid = (low + high) >>> 1;
            if (tw < glyphs.get(mid).maxx()) {
                high = mid;
            } else {
                low = mid;
            }
        }
        int r;

        if (nGlypths > 0 && glyphs.get(mid).maxx() > tw) r = low;
        else r = high;
        return r;
    }

}
