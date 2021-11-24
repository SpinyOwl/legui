package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.style.util.StyleUtilities.getPadding;
import static com.spinyowl.legui.style.util.StyleUtilities.getStyle;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.alignTextInBox;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createBounds;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.intersectScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nnvgText;
import static org.lwjgl.nanovg.NanoVG.nnvgTextBreakLines;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memFree;
import static org.lwjgl.system.MemoryUtil.memUTF8;

import com.spinyowl.legui.component.Tooltip;
import com.spinyowl.legui.component.event.tooltip.TooltipTextSizeChangeEvent;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.util.NvgColorUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGTextRow;


public class NvgTooltipRenderer extends NvgDefaultComponentRenderer<Tooltip> {


  @Override
  public void renderSelf(Tooltip component, Context context, long nanovg) {
    createScissor(nanovg, component);
    {
      Style style = component.getStyle();
      TextState textState = component.getTextState();
      Vector2f pos = component.getAbsolutePosition();
      Vector2f size = component.getSize();
      float fontSize = getStyle(component, Style::getFontSize, 16F);
      String font = getStyle(component, Style::getFont, FontRegistry.getDefaultFont());
      String text = textState.getText();
      HorizontalAlign horizontalAlign = getStyle(component, Style::getHorizontalAlign,
          HorizontalAlign.LEFT);
      VerticalAlign verticalAlign = getStyle(component, Style::getVerticalAlign,
          VerticalAlign.MIDDLE);
      Vector4f textColor = getStyle(component, Style::getTextColor);
      Vector4f padding = getPadding(component, style);

      renderBackground(component, context, nanovg);

      nvgFontSize(nanovg, fontSize);
      nvgFontFace(nanovg, font);

      ByteBuffer byteText = null;
      try {

        byteText = memUTF8(text, false);
        long start = memAddress(byteText);
        long end = start + byteText.remaining();

        float x = pos.x + padding.x;
        float y = pos.y + padding.y;
        float w = size.x - padding.x - padding.z;
        float h = size.y - padding.y - padding.w;

        intersectScissor(nanovg, new Vector4f(x, y, w, h));

        List<float[]> boundList = new ArrayList<>();
        List<long[]> indicesList = new ArrayList<>();

        try (
            NVGColor colorA = NvgColorUtil.create(textColor);
            NVGTextRow.Buffer buffer = NVGTextRow.calloc(1)
        ) {
          alignTextInBox(nanovg, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
          nvgFontSize(nanovg, fontSize);
          nvgFontFace(nanovg, font);
          nvgFillColor(nanovg, colorA);

          // calculate text bounds for every line and start/end indices
          float newWidth = 0;
          float newHeight = 0;
          int rows = 0;
          while (nnvgTextBreakLines(nanovg, start, end, size.x, memAddress(buffer), 1) != 0) {
            NVGTextRow row = buffer.get(0);
            float[] bounds = createBounds(x, y + rows * fontSize, w, h, horizontalAlign,
                verticalAlign, row.width(), fontSize);
            boundList.add(bounds);
            newWidth = Math.max(row.width(), newWidth);
            indicesList.add(new long[]{row.start(), row.end()});
            start = row.next();
            rows++;
          }
          newHeight = rows * fontSize;

          float textWidth = textState.getTextWidth();
          float textHeight = textState.getTextHeight();
          textState.setTextWidth(newWidth);
          textState.setTextHeight(newHeight);

          if (Math.abs(textWidth - newWidth) > 0.001 || Math.abs(textHeight - newHeight) > 0.001) {
            EventProcessorProvider.getInstance().pushEvent(
                new TooltipTextSizeChangeEvent(component, context, component.getFrame(), newWidth,
                    newHeight));
          }

          // calculate offset for all lines
          float offsetY = 0.5f * fontSize * ((rows - 1) * verticalAlign.index - 1);

          // render text lines
          for (int i = 0; i < rows; i++) {
            float[] bounds = boundList.get(i);
            long[] indices = indicesList.get(i);

            nvgBeginPath(nanovg);
            nnvgText(nanovg, bounds[4], bounds[5] - offsetY, indices[0], indices[1]);
          }
        }
      } finally {
        memFree(byteText);
      }
    }
    resetScissor(nanovg);
  }
}
