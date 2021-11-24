package com.spinyowl.legui.system.renderer.nvg.icon;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.icon.CharIcon;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.NvgIconRenderer;
import com.spinyowl.legui.system.renderer.nvg.util.NvgText;
import com.spinyowl.legui.util.TextUtil;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class NvgCharIconRenderer<I extends CharIcon> extends NvgIconRenderer<I> {

  @Override
  protected void renderIcon(I icon, Component component, Context context, long nanovg) {
    if (!component.isVisible() || icon == null || icon.getFont() == null) {
      return;
    }
    // render simple rectangle border
    Vector2f iconSize = icon.getSize();
    Vector2f p = calculateIconPosition(icon, component, iconSize);
    float w = iconSize.x;
    float h = iconSize.y;

    drawIcon(nanovg, p.x, p.y, w, h, icon, component);
  }


  private void drawIcon(long context, float x, float y, float w, float h, CharIcon icon,
      Component component) {
    if (component.isFocused()) {
      Vector4f focusedStrokeColor = component.getStyle().getFocusedStrokeColor();
      if (focusedStrokeColor != null) {
        NvgText.drawTextLineToRect(context, new Vector4f(x - 0.5f, y + 1, w, h), false,
            icon.getHorizontalAlign(), icon.getVerticalAlign(),
            icon.getSize().y, icon.getFont(), TextUtil.cpToStr(icon.getCharCode()),
            focusedStrokeColor);
      }
    }

    NvgText.drawTextLineToRect(context, new Vector4f(x + 0.5f, y, w, h), false,
        icon.getHorizontalAlign(), icon.getVerticalAlign(),
        icon.getSize().y, icon.getFont(), TextUtil.cpToStr(icon.getCharCode()), icon.getColor());
  }
}
