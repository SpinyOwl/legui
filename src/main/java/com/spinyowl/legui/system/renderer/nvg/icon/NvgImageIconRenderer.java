package com.spinyowl.legui.system.renderer.nvg.icon;

import static com.spinyowl.legui.system.renderer.nvg.NvgRenderer.renderImage;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.icon.ImageIcon;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.ImageRenderer;
import com.spinyowl.legui.system.renderer.nvg.NvgIconRenderer;
import java.util.HashMap;
import org.joml.Vector2f;


public class NvgImageIconRenderer<I extends ImageIcon> extends NvgIconRenderer<I> {

  @Override
  protected void renderIcon(I icon, Component component, Context context, long nanovg) {
    if (!component.isVisible() || icon == null || icon.getImage() == null) {
      return;
    }
    // render simple rectangle border
    Vector2f iconSize = icon.getSize();
    Vector2f p = calculateIconPosition(icon, component, iconSize);

    HashMap<String, Object> prop = new HashMap<>();
    prop.put(ImageRenderer.C_RADIUS, getBorderRadius(component));
    renderImage(icon.getImage(), new Vector2f(p.x, p.y), iconSize, prop, context);
  }
}
