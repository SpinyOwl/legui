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
import java.util.Map;

import static org.liquidengine.legui.util.ColorUtil.half;
import static org.liquidengine.legui.util.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Shcherbin Alexander on 5/20/2016.
 */
public class NvgTextInputRenderer extends NvgLeguiComponentRenderer {
    public static final  String                  PRATIO        = "pratio";
    public static final  String                  POFFSET       = "poffset";
    private static final Logger                  LOGGER        = LogManager.getLogger();
    private final        Vector4f                caretColor    = new Vector4f(0, 0, 0, 0.5f);
    private final        int                     maxGlyphCount = 1024;
    private              NVGGlyphPosition.Buffer glyphs        = NVGGlyphPosition.create(maxGlyphCount);
    private              NVGColor                colorA        = NVGColor.create();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            TextInput agui    = (TextInput) component;
            Vector2f  pos     = Util.calculatePosition(component);
            Vector2f  size    = component.getSize();
            boolean   enabled = agui.isEnabled();
            Vector4f  bc      = new Vector4f(agui.getBackgroundColor());

            if (enabled && agui.getState().isFocused()) {
                bc.w *= 1.1f;
            } else if (!enabled) {
                bc.w *= 0.5f;
            }
            if (!agui.isEditable()) {
                bc.w *= 0.3f;
            }
            drawBackground(context, pos.x, pos.y, size.x, size.y, agui.getCornerRadius(), bc);

            TextState textState = agui.getTextState();
            Vector4f  p         = new Vector4f(textState.getPadding());
            p.x = p.x > 0 ? p.x - 1 : 0;
            p.y = p.y > 0 ? p.y - 1 : 0;
            p.z = p.z > 0 ? p.z - 1 : 0;
            p.w = p.w > 0 ? p.w - 1 : 0;

            Vector4f rect = new Vector4f(pos.x + p.x, pos.y + p.y, size.x - p.x - p.z, size.y - p.y - p.w);

//            intersectScissor(context, rect);
//            renderText(leguiContext, context, agui, pos, size, textState, agui.getCaretPosition(), agui.getState().isFocused(), bc);
            renderTextNew(leguiContext, context, agui, pos, size, p, rect, bc);
        }
        resetScissor(context);

        createScissor(context, component);
        {
            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderTextNew(LeguiContext leguiContext, long context, TextInput gui, Vector2f pos, Vector2f size, Vector4f p, Vector4f rect, Vector4f bc) {
        TextState           textState      = gui.getTextState();
        String              text           = textState.getText();
        String              font           = textState.getFont();
        float               fontSize       = textState.getFontSize();
        Vector4f            highlightColor = gui.getSelectionColor();
        HorizontalAlign     halign         = textState.getHorizontalAlign();
        VerticalAlign       valign         = textState.getVerticalAlign();
        Vector4f            textColor      = textState.getTextColor();
        int                 caretPosition  = gui.getCaretPosition();
        Map<String, Object> metadata       = gui.getMetadata();


        // initially configure text rendering
        alignTextInBox(context, halign, valign);
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);
        nvgFillColor(context, rgba(textColor, colorA));

        float[] textBounds = calculateTextBoundsRect(context, rect.x, rect.y, rect.z, rect.w, text, halign, valign);

        // calculate caret coordinate and mouse caret coordinate
        float      caretx;
        float      mouseCaretX        = 0;
        int        mouseCaretPosition = 0;
        ByteBuffer textBytes          = null;
        try {
            // allocate ofheap memory and fill it with text
            textBytes = memUTF8(text);
            // align text for calculations
            alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
            int ng = nnvgTextGlyphPositions(context, textBounds[4], 0, memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);

            caretx = 0;
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


            float offsetX = 0;
            if (caretx > rect.z + rect.x) {
                offsetX = caretx - rect.x - rect.z;
            } else if (caretx < rect.x) {
                offsetX = caretx - rect.x;
            }

            // get previous offset
            Float poffset = offsetX;
            if (metadata.containsKey(POFFSET)) poffset = (Float) metadata.get(POFFSET);
            else metadata.put(POFFSET, poffset);

            // get previous ratio
            float rat    = size.y * size.x;
            Float pratio = rat;
            if (metadata.containsKey(PRATIO)) pratio = (Float) metadata.get(PRATIO);
            else metadata.put(PRATIO, pratio);

            // we should recalculate offsets if ratio is changed
            if (pratio != rat) {
                poffset = offsetX;
            } else {
                // and if ratio is the same we should check if we need to update offset
                if (caretx - poffset > rect.z + rect.x) {
                    poffset = poffset + (caretx - poffset - rect.z - rect.x);
                } else if (caretx - poffset < rect.x) {
                    poffset = poffset + (caretx - poffset - rect.x);
                }
            }
            metadata.put(POFFSET, poffset);
            metadata.put(PRATIO, rat);

            // calculate mouse caret position
            float mx = leguiContext.getCursorPosition().x + poffset;
            if (mx < glyphs.get(0).x()) {
                mouseCaretPosition = 0;
                mouseCaretX = glyphs.get(0).x();
            } else if (mx > glyphs.get(ng - 1).maxx()) {
                mouseCaretPosition = ng;
                mouseCaretX = glyphs.get(ng - 1).maxx();
            } else {
                // binary search mouse caret position
                int     upper = ng;
                int     lower = 0;
                boolean found = false;
                do {
                    int              index = (upper + lower) / 2;
                    NVGGlyphPosition gp    = glyphs.get(index);
                    float            left  = gp.x();
                    float            right = gp.maxx();
                    float            mid   = (left + right) / 2f;
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

            mouseCaretX -= poffset;
            float startX  = textBounds[4] - poffset;
            float nCaretX = caretx - poffset;

            oppositeBlackOrWhite(bc, caretColor);
            caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);

            drawRectangle(context, highlightColor, mouseCaretX, rect.y, nCaretX-mouseCaretX, rect.w);

            // render text
            renderTextLineToBounds(context, startX, textBounds[5], textBounds[6], textBounds[7], fontSize, font, textColor, text, HorizontalAlign.LEFT, VerticalAlign.MIDDLE, false);

            // render caret
            drawRectStroke(context, nCaretX, rect.y, 1, rect.w, caretColor, 0, 1);

            // render mouse caret
            Vector4f cc = new Vector4f(this.caretColor);
            cc.x = 1;
            drawRectStroke(context, mouseCaretX, rect.y, 1, rect.w, cc, 0, 1);


        } finally {
            // free allocated memory
            if (textBytes != null) {
                memFree(textBytes);
            }
        }
        gui.setMouseCaretPosition(mouseCaretPosition);
    }

    private void renderText(LeguiContext leguiContext, long context, TextInput gui, Vector2f pos, Vector2f size, TextState textState, int caretPosition, boolean focused, Vector4f bc) {
        String text = textState.getText();
        if (!focused) caretPosition = text.length() * textState.getHorizontalAlign().index / 2;
        String          font            = textState.getFont();
        Vector4f        textColor       = textState.getTextColor();
        float           fontSize        = textState.getFontSize();
        HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
        VerticalAlign   verticalAlign   = textState.getVerticalAlign();
        alignTextInBox(context, horizontalAlign, verticalAlign);
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);
        nvgFillColor(context, rgba(textColor, colorA));

        Vector4f p = new Vector4f(textState.getPadding());
        float    x = pos.x + p.x;
        float    y = pos.y + p.y;
        float    w = size.x - p.x - p.z;
        float    h = size.y - p.y - p.w;

        float caretx = getCaretX(context, x, w, text, caretPosition, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);

        gui.getMetadata().put("caretx", caretx);
        Float lastcx = (Float) gui.getMetadata().get("lastcx");
        if (lastcx != null) {
            if (horizontalAlign == HorizontalAlign.LEFT) {
                float diff = caretx - lastcx;
                if (diff <= 0) {
                    lastcx = caretx;
                    gui.getMetadata().put("lastcx", lastcx);
                } else {
                    float delta = diff - w;
                    if (delta >= 0) {
                        lastcx -= delta;
                        gui.getMetadata().put("lastcx", lastcx);
                    }
                }
            } else if (horizontalAlign == HorizontalAlign.RIGHT) {
                float diff = lastcx - caretx;
                if (diff <= 0) {
                    lastcx = caretx;
                    gui.getMetadata().put("lastcx", lastcx);
                } else {
                    float delta = diff - w;
                    if (delta >= 0) {
                        lastcx -= delta;
                        gui.getMetadata().put("lastcx", lastcx);
                    }
                }
            }
        } else {
            lastcx = caretx;
            gui.getMetadata().put("lastcx", lastcx);
        }

        // calculate text x offset
        float offsetX1 = 0;
        if (caretx - x > w) {
            offsetX1 = caretx - x - w;
        } else if (caretx < x) {
            offsetX1 = caretx - x;
        }
        float offsetX = offsetX1;
        gui.getMetadata().put("coffsetx", offsetX);
        Float loffsetx = (Float) gui.getMetadata().get("loffsetx");
        if (loffsetx != null) {
            if (horizontalAlign == HorizontalAlign.LEFT) {
                float diff = loffsetx - offsetX;
                if (diff <= 0) {
                    loffsetx = offsetX;
                    gui.getMetadata().put("loffsetx", loffsetx);
                } else {
                    float delta = diff - w;
                    if (delta >= 0) {
                        loffsetx -= delta;
                        gui.getMetadata().put("loffsetx", loffsetx);
                    }
                }
            } else if (horizontalAlign == HorizontalAlign.RIGHT) {
                float diff = offsetX - loffsetx;
                if (diff < 0) {
                    loffsetx = offsetX;
                    gui.getMetadata().put("loffsetx", loffsetx);
                } else {
                    float delta = diff - w;
                    if (delta > 0) {
                        loffsetx += delta;
                        gui.getMetadata().put("loffsetx", loffsetx);
                    } else if (offsetX >= 0) {
                        loffsetx = offsetX;
                        gui.getMetadata().put("loffsetx", loffsetx);
                    }
                }
            }
        } else {
            loffsetx = offsetX;
            gui.getMetadata().put("loffsetx", loffsetx);
        }


        oppositeBlackOrWhite(bc, caretColor);
        caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);
        float bounds[] = calculateTextBoundsRect(context, x, y, w, h, text, horizontalAlign, verticalAlign);
        float offset   = loffsetx * (1 + horizontalAlign.index);

        // calculate mouse cursor position
        int newCPos = calculateMouseCaretPosition(leguiContext, context, gui, x, y, w, h, loffsetx, text, horizontalAlign, verticalAlign);
        gui.setMouseCaretPosition(newCPos);
        if (focused) {
            drawSelectionBackground(context, gui, text, fontSize, horizontalAlign, verticalAlign, x, w, loffsetx, bounds);
            // draw text
            renderTextLineToBounds(context, x - offset, y, w + loffsetx * 2, h, fontSize, font, textColor, text, horizontalAlign, verticalAlign, false);
            // draw mouse caret
            if (leguiContext.isDebugEnabled()) {
                drawRectangle(context, half(caretColor), getCaretX(context, x, w, text, newCPos, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount) - loffsetx, bounds[5], 1, bounds[7]);
            }
            // draw current caret
            drawRectangle(context, caretColor, caretx - loffsetx, bounds[5], 1, fontSize);
        } else {
            renderTextLineToBounds(context, x - offset, y, w + loffsetx * 2, h, fontSize, font, textColor, text, horizontalAlign, verticalAlign, false);
        }
    }

    private void drawSelectionBackground(long context, TextInput gui, String text, float fontSize, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float x, float w, float offsetX, float[] bounds) {
        int start = gui.getStartSelectionIndex();
        int end   = gui.getEndSelectionIndex();
        if (start >= 0 && end >= 0 && start <= text.length() && end <= text.length()) {
            float startSelCaretx = getCaretX(context, x, w, text, start, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);
            float endSelCaretx   = getCaretX(context, x, w, text, end, fontSize, horizontalAlign, verticalAlign, glyphs, maxGlyphCount);

            if (startSelCaretx > endSelCaretx) {
                float swap = startSelCaretx;
                startSelCaretx = endSelCaretx;
                endSelCaretx = swap;
            }
            if (start != end)
                drawRectangle(context, gui.getSelectionColor(), startSelCaretx - offsetX, bounds[5], endSelCaretx - startSelCaretx, fontSize);
        }
    }

    private int calculateMouseCaretPosition(LeguiContext leguiContext, long context, TextInput gui, float x, float y, float w, float h, float offsetX, String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        float      bounds[]  = NvgRenderUtils.calculateTextBoundsRect(context, x, y, w, h, text, horizontalAlign, verticalAlign);
        ByteBuffer textBytes = null;
        try {
            textBytes = MemoryUtil.memUTF8(text);
            int   ng      = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);
            float mx      = leguiContext.getCursorPosition().x;
            int   newCPos = 0;
            int   upper   = ng - 1;
            if (upper > 0) {
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
            return newCPos;
        } finally {
            if (textBytes != null) {
                MemoryUtil.memFree(textBytes);
            }
        }
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
