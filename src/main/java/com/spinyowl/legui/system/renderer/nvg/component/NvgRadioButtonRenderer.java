package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.style.util.StyleUtilities.getPadding;
import static com.spinyowl.legui.style.util.StyleUtilities.getStyle;
import static com.spinyowl.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgText.drawTextLineToRect;

import com.spinyowl.legui.component.RadioButton;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.icon.Icon;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class NvgRadioButtonRenderer extends NvgDefaultComponentRenderer<RadioButton> {

  @Override
  public void renderSelf(RadioButton radioButton, Context context, long nanovg) {
    createScissor(nanovg, radioButton);
    {
      // default renderer used
      Style style = radioButton.getStyle();
      Vector2f pos = radioButton.getAbsolutePosition();
      Vector2f size = radioButton.getSize();

      // Draw background rectangle
      renderBackground(radioButton, context, nanovg);

      TextState textState = radioButton.getTextState();
      Icon icon =
          radioButton.isChecked() ? radioButton.getIconChecked() : radioButton.getIconUnchecked();
      Vector4f padding = getPadding(radioButton, style);
      Vector4f pad = new Vector4f(padding.w, padding.x, padding.y, padding.z);

      // renderNvg text
      float iconWidthForUse;
      if (icon != null) {
        iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * icon.getSize().x;
      } else {
        iconWidthForUse = 0F;
      }

      Vector2f textRectPos = new Vector2f(pos).add(iconWidthForUse, pad.y);
      Vector2f textRectSize = new Vector2f(size).sub(iconWidthForUse + pad.z, pad.y + pad.w);

      Vector4f rect = new Vector4f(textRectPos, textRectSize.x(), textRectSize.y());
      drawTextLineToRect(
          nanovg,
          rect,
          true,
          getStyle(radioButton, Style::getHorizontalAlign, HorizontalAlign.LEFT),
          getStyle(radioButton, Style::getVerticalAlign, VerticalAlign.MIDDLE),
          getStyle(radioButton, Style::getFontSize, 16F),
          getStyle(radioButton, Style::getFont, FontRegistry.getDefaultFont()),
          textState.getText(),
          getStyle(radioButton, Style::getTextColor));
      renderIcon(icon, radioButton, context);
    }
    resetScissor(nanovg);
  }
}
