package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.style.util.StyleUtilities.getPadding;
import static com.spinyowl.legui.style.util.StyleUtilities.getStyle;
import static com.spinyowl.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import com.spinyowl.legui.component.CheckBox;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.icon.Icon;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.util.NvgText;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector4f;


public class NvgCheckBoxRenderer extends NvgDefaultComponentRenderer<CheckBox> {

  @Override
  public void renderSelf(CheckBox checkBox, Context context, long nanovg) {
    createScissor(nanovg, checkBox);
    {
      Style style = checkBox.getStyle();
      Vector2f pos = checkBox.getAbsolutePosition();
      Vector2f size = checkBox.getSize();

      /*Draw background rectangle*/
      renderBackground(checkBox, context, nanovg);

      TextState textState = checkBox.getTextState();
      Icon icon = checkBox.isChecked() ? checkBox.getIconChecked() : checkBox.getIconUnchecked();
      float iconWid = icon.getSize().x;

      Vector4f padding = getPadding(checkBox, style);

      float iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * iconWid;

      float h = size.y - (padding.y + padding.w);
      float y = pos.y + padding.y;
      float x = pos.x + iconWidthForUse + padding.x;
      float w = size.x - iconWidthForUse - padding.z - padding.x;

      Vector2fc size1 = new Vector2f(w, h);
      Vector4f rect = new Vector4f(new Vector2f(x, y), size1.x(), size1.y());
      NvgText.drawTextLineToRect(nanovg, rect, true,
          getStyle(checkBox, Style::getHorizontalAlign, HorizontalAlign.LEFT),
          getStyle(checkBox, Style::getVerticalAlign, VerticalAlign.MIDDLE),
          getStyle(checkBox, Style::getFontSize, 16F),
          getStyle(checkBox, Style::getFont, FontRegistry.getDefaultFont()),
          textState.getText(),
          getStyle(checkBox, Style::getTextColor));
      renderIcon(icon, checkBox, context);
    }
    resetScissor(nanovg);
  }
}
