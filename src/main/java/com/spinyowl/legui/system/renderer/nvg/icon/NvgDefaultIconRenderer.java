package com.spinyowl.legui.system.renderer.nvg.icon;

import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.icon.Icon;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.NvgIconRenderer;
import com.spinyowl.legui.system.renderer.nvg.util.NvgShapes;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class NvgDefaultIconRenderer extends NvgIconRenderer {

  @Override
  protected void renderIcon(Icon icon, Component component, Context context, long nanovg) {
    if (!component.isVisible()) {
      return;
    }
    // render simple rectangle border
    Vector2f iconSize = icon.getSize();
    Vector2f p = calculateIconPosition(icon, component, iconSize);
    float w = iconSize.x;
    float h = iconSize.y;

    NvgShapes.drawRect(nanovg, new Vector4f(p.x, p.y, w, h), ColorConstants.red,
        getBorderRadius(component));
    NvgShapes.drawRectStroke(nanovg, new Vector4f(p.x, p.y, w, h), ColorConstants.black, 1,
        getBorderRadius(component));
  }
}
