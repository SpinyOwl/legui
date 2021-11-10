package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.style.color.ColorUtil.oppositeBlackOrWhite;
import static com.spinyowl.legui.style.util.StyleUtilities.getInnerContentRectangle;
import static com.spinyowl.legui.style.util.StyleUtilities.getPadding;
import static com.spinyowl.legui.style.util.StyleUtilities.getStyle;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.alignTextInBox;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.calculateTextBoundsRect;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.intersectScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.runWithScissor;
import static org.lwjgl.nanovg.NanoVG.NVG_ROUND;
import static org.lwjgl.nanovg.NanoVG.nnvgTextGlyphPositions;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFindFont;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memFree;
import static org.lwjgl.system.MemoryUtil.memUTF8;

import com.spinyowl.legui.component.TextInput;
import com.spinyowl.legui.component.event.textinput.TextInputWidthChangeEvent;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.util.NvgColorUtil;
import com.spinyowl.legui.system.renderer.nvg.util.NvgShapes;
import com.spinyowl.legui.system.renderer.nvg.util.NvgText;
import java.nio.ByteBuffer;
import java.util.Map;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;


public class NvgTextInputRenderer extends NvgDefaultComponentRenderer<TextInput> {

  public static final String PRATIO = "pratio";
  public static final String PALIGN = "palign";
  public static final String POFFSET = "poffset";
  private static final int MAX_GLYPH_COUNT = 1024;
  private final Vector4f caretColor = new Vector4f(0, 0, 0, 0.5f);

  /**
   * Used to render textInput.
   *
   * @param component textInput to render.
   * @param context   legui context.
   * @param nanovg    nanovg context pointer.
   */
  @Override
  protected void renderSelf(TextInput component, Context context, long nanovg) {
    runWithScissor(nanovg, component, () -> {
      Vector2f pos = component.getAbsolutePosition();
      Vector2f size = component.getSize();
      boolean enabled = component.isEnabled();
      Style style = component.getStyle();
      Vector4f bc = new Vector4f(style.getBackground().getColor());

      if (enabled && component.isFocused()) {
        bc.w *= 1.1f;
      } else if (!enabled) {
        bc.w *= 0.5f;
      }
      if (!component.isEditable()) {
        bc.w *= 0.3f;
      }
      renderBackground(component, context, nanovg);

      Vector4f padding = getPadding(component, style);
      Vector4f intersectRect = getInnerContentRectangle(pos, size, padding);
      intersectScissor(nanovg, new Vector4f(intersectRect).sub(1, 1, -2, -2));
      renderText(context, nanovg, component, size, intersectRect, bc);
    });
  }

  private void renderText(Context leguiContext, long context, TextInput gui, Vector2f size,
      Vector4f rect, Vector4f bc) {

    String font = getStyle(gui, Style::getFont, FontRegistry.getDefaultFont());
    // switch to default font if font not found in nanovg.
    if (nvgFindFont(context, font) == -1) {
      font = FontRegistry.getDefaultFont();
    }

    Vector4f textColor = getStyle(gui, Style::getTextColor);
    try (
        NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.calloc(MAX_GLYPH_COUNT);
        NVGColor colorA = NvgColorUtil.create(textColor)
    ) {
      TextState textState = gui.getTextState();
      String text = textState.getText();
      float fontSize = getStyle(gui, Style::getFontSize, 16F);
      Vector4f highlightColor = getStyle(gui, Style::getHighlightColor);
      HorizontalAlign halign = getStyle(gui, Style::getHorizontalAlign, HorizontalAlign.LEFT);
      VerticalAlign valign = getStyle(gui, Style::getVerticalAlign, VerticalAlign.MIDDLE);
      int caretPosition = gui.getCaretPosition();
      Map<String, Object> metadata = gui.getMetadata();
      int startSelectionIndex = gui.getStartSelectionIndex();
      int endSelectionIndex = gui.getEndSelectionIndex();
      boolean focused = gui.isFocused();
      if (focused) {
        updateCaret(bc);
      }
      if (text.isEmpty()) {

        if (focused) {
          // render caret
          float nCaretX = rect.x + halign.index * rect.z / 2f;
          renderCaret(context, rect, nCaretX, caretColor);
        }
        textState.setTextHeight(fontSize);
        gui.setMouseCaretPosition(0);
      } else {

        // initially configure text rendering
        alignTextInBox(context, halign, valign);
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);
        nvgFillColor(context, colorA);

        int textLength = text.length();
        if (!focused) {
          switch (halign) {
            case LEFT:
              caretPosition = (0);
              break;
            case RIGHT:
              caretPosition = textLength;
              break;
            default:
              caretPosition = textLength / 2;
              break;
          }
        }

        float[] textBounds = calculateTextBoundsRect(context, rect, text, halign, valign, fontSize);
        // calculate caret coordinate and mouse caret coordinate
        float caretx;
        float startSelectionX;
        float endSelectionX;
        float mouseCaretX = 0;
        int mouseCaretPosition = 0;
        float ratio = size.y * size.x;
        ByteBuffer textBytes = null;
        try {
          // allocate ofheap memory and fill it with text
          textBytes = memUTF8(text);

          // align text for calculations
          alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
          int ng = nnvgTextGlyphPositions(context, textBounds[4], 0, memAddress(textBytes), 0,
              memAddress(glyphs), MAX_GLYPH_COUNT);

          // get caret position on screen based on caret position in text
          // and get x position of first and last selection
          caretx = calculateCaretPos(caretPosition, textBounds, ng, glyphs);
          startSelectionX = calculateCaretPos(startSelectionIndex, textBounds, ng, glyphs);
          endSelectionX = calculateCaretPos(endSelectionIndex, textBounds, ng, glyphs);

          // calculate text offset in text field based on caret position on screen
          // (caret always should be inside text field bounds)
          float offsetX = getOffsetX(rect, caretx);

          // get previous offset
          Float poffset = (Float) metadata.getOrDefault(POFFSET, offsetX);

          // get previous ratio
          Float pratio = (Float) metadata.getOrDefault(PRATIO, ratio);

          // get previous align to know if we need to recalculate offset
          HorizontalAlign palign = (HorizontalAlign) metadata.getOrDefault(PALIGN, halign);

          // we should recalculate offsets if ratio is changed
          poffset = recalculateOffsetX(rect, halign, caretx, ratio, offsetX, poffset, pratio,
              palign);

          // calculate mouse caret position
          float mx = Mouse.getCursorPosition().x + poffset;
          if (mx <= glyphs.get(0).x()) {
            mouseCaretX = glyphs.get(0).x();
          } else if (mx >= glyphs.get(ng - 1).maxx()) {
            mouseCaretPosition = ng;
            mouseCaretX = glyphs.get(ng - 1).maxx();
            // if window not minimized
          } else if (!leguiContext.isIconified()) {
            // binary search mouse caret position
            int upper = ng;
            int lower = 0;
            boolean found = false;
            do {
              int index = (upper + lower) / 2;
              float left = index == 0 ? glyphs.get(index).minx() : glyphs.get(index).x();
              float right = index >= ng - 1 ? glyphs.get(ng - 1).maxx() : glyphs.get(index + 1).x();
              float mid = (left + right) / 2f;
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
                  mouseCaretX = left;
                }
              }
            } while (!found);
          }
          mouseCaretX -= poffset;
          float nCaretX = caretx - poffset;

          float textWidth = textState.getTextWidth();

          textState.setTextWidth(textBounds[2]);
          textState.setTextHeight(fontSize);
          textState.setCaretX(nCaretX);
          textState.setCaretY(textBounds[5]);

          if (Math.abs(textWidth - textBounds[2]) > 0.001) {
            EventProcessorProvider.getInstance().pushEvent(
                new TextInputWidthChangeEvent(gui, leguiContext, gui.getFrame(), textBounds[2]));
          }

          drawSelection(context, rect, highlightColor,
              startSelectionIndex, endSelectionIndex,
              focused, startSelectionX, endSelectionX, poffset);
          // render text

          Vector4f bounds = new Vector4f(textBounds[4] - poffset, textBounds[5], textBounds[6],
              textBounds[7]);
          NvgText.drawTextLineToRect(context, bounds, false,
              HorizontalAlign.LEFT, VerticalAlign.MIDDLE, fontSize, font, text, textColor);

          if (focused) {
            // render caret
            renderCaret(context, rect, nCaretX, caretColor);
          }
          // render mouse caret
          if (leguiContext.isDebugEnabled()) {
            Vector4f cc = new Vector4f(this.caretColor);
            cc.x = 1;
            renderCaret(context, rect, mouseCaretX, caretColor);
          }

          // put last offset and ration to metadata
          updateMetadata(halign, metadata, ratio, poffset);
        } finally {
          // free allocated memory
          memFree(textBytes);
        }
        gui.setMouseCaretPosition(mouseCaretPosition);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void updateMetadata(HorizontalAlign halign, Map<String, Object> metadata, float ratio,
      Float poffset) {
    metadata.put(POFFSET, poffset);
    metadata.put(PALIGN, halign);
    metadata.put(PRATIO, ratio);
  }

  private void drawSelection(long context, Vector4f rect, Vector4f highlightColor,
      int startSelectionIndex, int endSelectionIndex, boolean focused,
      float startSelectionX, float endSelectionX, Float poffset) {
    if (focused && startSelectionIndex != endSelectionIndex) {
      Vector2f position = new Vector2f(startSelectionX - poffset, rect.y);
      Vector2f size = new Vector2f(endSelectionX - startSelectionX, rect.w);
      NvgShapes.drawRect(context, position, size, highlightColor);
    }
  }

  private void updateCaret(Vector4f bc) {
    oppositeBlackOrWhite(bc, caretColor);
    caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);
  }

  private Float recalculateOffsetX(Vector4f rect, HorizontalAlign halign,
      float caretx, float ratio, float offsetX,
      Float poffset, Float pratio, HorizontalAlign palign) {
    float newpoffset = poffset;
    if (pratio != ratio || palign != halign) {
      newpoffset = offsetX;
    } else {
      // and if ratio is the same we should check if we need to update offset
      if (caretx - poffset > rect.z + rect.x) {
        newpoffset = poffset + (caretx - poffset - rect.z - rect.x);
      } else if (caretx - poffset < rect.x) {
        newpoffset = poffset + (caretx - poffset - rect.x);
      }
    }
    return newpoffset;
  }

  private float getOffsetX(Vector4f rect, float caretx) {
    float offsetX = 0;
    if (caretx > rect.z + rect.x) {
      offsetX = caretx - rect.x - rect.z;
    } else if (caretx < rect.x) {
      offsetX = caretx - rect.x;
    }
    return offsetX;
  }

  private void renderCaret(long context, Vector4f rect, float nCaretX, Vector4f color) {
    NvgShapes.drawLine(context, 1, color, NVG_ROUND, nCaretX, rect.y, nCaretX, rect.y + rect.w);
  }

  private float calculateCaretPos(int caretPosition, float[] textBounds, int ng,
      NVGGlyphPosition.Buffer glyphs) {
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
}
