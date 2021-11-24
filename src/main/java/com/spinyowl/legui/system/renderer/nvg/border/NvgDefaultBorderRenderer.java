package com.spinyowl.legui.system.renderer.nvg.border;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.style.Border;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.NvgBorderRenderer;
import com.spinyowl.legui.system.renderer.nvg.util.NvgShapes;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class NvgDefaultBorderRenderer extends NvgBorderRenderer {

  @Override
  protected void renderBorder(Border border, Component component, Context context, long nanovg) {
    if (!component.isVisible()) {
      return;
    }
    // render simple rectangle border
    Vector2f position = component.getAbsolutePosition();
    Vector2f size = component.getSize();

    float x = position.x;
    float y = position.y;
    float w = size.x;
    float h = size.y;

    NvgShapes.drawRectStroke(nanovg, new Vector4f(x, y, w, h), ColorConstants.black, 1);

    if (component.isFocused()) {
      NvgShapes.drawRectStroke(nanovg, new Vector4f(x - 1, y - 1, w + 2, h + 2), ColorConstants.red,
          2);
    }
  }
}
