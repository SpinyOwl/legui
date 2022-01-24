package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.style.util.StyleUtilities.getInnerContentRectangle;
import static com.spinyowl.legui.style.util.StyleUtilities.getPadding;
import static com.spinyowl.legui.style.util.StyleUtilities.getStyle;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.calculateTextBoundsRect;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.event.label.LabelWidthChangeEvent;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.util.NvgText;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class NvgLabelRenderer extends NvgDefaultComponentRenderer<Label> {

  @Override
  public void renderSelf(Label label, Context context, long nanovg) {
    createScissor(nanovg, label);
    {
      Style style = label.getStyle();
      Vector2f pos = label.getAbsolutePosition();
      Vector2f size = label.getSize();

      TextState textState = label.getTextState();
      Vector4f padding = getPadding(label, style);
      Vector4f rect = getInnerContentRectangle(pos, size, padding);

      Float fontSize = getStyle(label, Style::getFontSize, 16F);

      HorizontalAlign hAlign = getStyle(label, Style::getHorizontalAlign, HorizontalAlign.LEFT);
      VerticalAlign vAlign = getStyle(label, Style::getVerticalAlign, VerticalAlign.MIDDLE);

      float[] textBounds =
          calculateTextBoundsRect(nanovg, rect, textState.getText(), hAlign, vAlign, fontSize);
      float textWidth = textState.getTextWidth();

      // Calculation of the text size and sending of the sizing event
      if (Math.abs(textWidth - textBounds[2]) > 0.001) {
        EventProcessorProvider.getInstance()
            .pushEvent(new LabelWidthChangeEvent(label, context, label.getFrame(), textBounds[2]));
      }

      // The variables are recovered again because they could have been modified by an event.
      style = label.getStyle();
      pos = label.getAbsolutePosition();
      size = label.getSize();

      textState = label.getTextState();
      padding = getPadding(label, style);
      rect = getInnerContentRectangle(pos, size, padding);
      fontSize = getStyle(label, Style::getFontSize, 16F);
      vAlign = getStyle(label, Style::getVerticalAlign, VerticalAlign.MIDDLE);
      hAlign = getStyle(label, Style::getHorizontalAlign, HorizontalAlign.LEFT);

      // Applying the new settings to the text
      textState.setTextWidth(textBounds[2]);
      textState.setTextHeight(fontSize);
      textState.setCaretX(null);
      textState.setCaretY(null);

      renderBackground(label, context, nanovg);

      // At the end we draw the text
      NvgText.drawTextLineToRect(
          nanovg,
          rect,
          false,
          hAlign,
          vAlign,
          fontSize,
          getStyle(label, Style::getFont, FontRegistry.getDefaultFont()),
          textState.getText(),
          getStyle(label, Style::getTextColor),
          label.getTextDirection());
    }
    resetScissor(nanovg);
  }
}
