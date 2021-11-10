package com.spinyowl.legui.system.renderer.nvg.border;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.NvgBorderRenderer;
import com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils;
import com.spinyowl.legui.system.renderer.nvg.util.NvgShapes;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class NvgSimpleLineBorderRenderer extends NvgBorderRenderer<SimpleLineBorder> {

  @Override
  protected void renderBorder(SimpleLineBorder border, Component component, Context context,
      long nanovg) {
    if (border.isEnabled()) {
      float thickness = border.getThickness();
      Vector4f borderColor = border.getColor();
      if (thickness <= 0 || borderColor.w == 0) {
        return;
      }

//            float cornerRadius = component.getBorderRadius();
      Vector2f size = component.getSize();
      Style style = component.getStyle();
      Vector2f absolutePosition = component.getAbsolutePosition();

      Vector2f bSize = new Vector2f(size);
      bSize.add(thickness, thickness);
      Vector2f bPos = new Vector2f(absolutePosition).sub(thickness / 2f, thickness / 2f);

      Vector4f borderRadius = NvgRenderUtils.getBorderRadius(component);

      if (component.isFocused() && style.getFocusedStrokeColor() != null) {
        Vector4f strokeColor = style.getFocusedStrokeColor();
        NvgShapes.drawRectStroke(nanovg, new Vector2f(bPos).add(-0.5f, +0.5f),
            new Vector2f(bSize).add(1f, 1f), strokeColor, 1f, borderRadius);
      }
      NvgShapes.drawRectStroke(nanovg, bPos, bSize, borderColor, thickness, borderRadius);

    }
  }
}
