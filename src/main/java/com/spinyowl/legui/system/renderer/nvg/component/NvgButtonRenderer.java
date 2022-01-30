package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.style.util.StyleUtilities.getStyle;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.calculateTextBoundsRect;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgText.drawTextLineToRect;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.event.button.ButtonWidthChangeEvent;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class NvgButtonRenderer extends NvgDefaultComponentRenderer<Button> {

  @Override
  protected void renderSelf(Button component, Context context, long nanovg) {
    createScissor(nanovg, component);
    {
      Vector2f pos = component.getAbsolutePosition();
      Vector2f size = component.getSize();

      // Render text
      TextState textState = component.getTextState();
      Vector4f rect = new Vector4f(pos, size.x(), size.y());

      Float fontSize = getStyle(component, Style::getFontSize, 16F);

      HorizontalAlign hAlign = getStyle(component, Style::getHorizontalAlign, HorizontalAlign.LEFT);
      VerticalAlign vAlign = getStyle(component, Style::getVerticalAlign, VerticalAlign.MIDDLE);

      float[] textBounds =
          calculateTextBoundsRect(nanovg, rect, textState.getText(), hAlign, vAlign, fontSize);
      float textWidth = textState.getTextWidth();

      if (Math.abs(textWidth - textBounds[2]) > 0.001) {
        EventProcessorProvider.getInstance()
            .pushEvent(
                new ButtonWidthChangeEvent(
                    component, context, component.getFrame(), textBounds[2]));
      }

      pos = component.getAbsolutePosition();
      size = component.getSize();

      renderBackground(component, context, nanovg);

      // Render text
      nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
      textState = component.getTextState();
      rect = new Vector4f(pos, size.x(), size.y());
      fontSize = getStyle(component, Style::getFontSize, 16F);

      textState.setTextWidth(textBounds[2]);
      textState.setTextHeight(fontSize);
      textState.setCaretX(null);
      textState.setCaretY(null);

      drawTextLineToRect(
          nanovg,
          rect,
          true,
          getStyle(component, Style::getHorizontalAlign, HorizontalAlign.LEFT),
          getStyle(component, Style::getVerticalAlign, VerticalAlign.MIDDLE),
          fontSize,
          getStyle(component, Style::getFont, FontRegistry.getDefaultFont()),
          textState.getText(),
          getStyle(component, Style::getTextColor),
          component.getTextDirection());
    }
    resetScissor(nanovg);
  }
}
