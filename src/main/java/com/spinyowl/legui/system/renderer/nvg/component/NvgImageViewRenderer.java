package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.system.renderer.ImageRenderer.C_RADIUS;
import static com.spinyowl.legui.system.renderer.nvg.NvgRenderer.renderImage;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import com.spinyowl.legui.component.ImageView;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils;
import java.util.HashMap;
import org.joml.Vector2f;


public class NvgImageViewRenderer extends NvgDefaultComponentRenderer<ImageView> {

  @Override
  protected void renderSelf(ImageView imageView, Context context, long nanovg) {
    Vector2f size = imageView.getSize();
    Vector2f position = imageView.getAbsolutePosition();

    createScissor(nanovg, imageView);
    {
      HashMap<String, Object> p = new HashMap<>();
      p.put(C_RADIUS, NvgRenderUtils.getBorderRadius(imageView));

      renderBackground(imageView, context, nanovg);
      renderImage(imageView.getImage(), position, size, p, context);
    }
    resetScissor(nanovg);
  }
}
