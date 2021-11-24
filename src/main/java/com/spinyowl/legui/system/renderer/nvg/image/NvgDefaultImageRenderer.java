package com.spinyowl.legui.system.renderer.nvg.image;


import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.image.Image;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.NvgImageRenderer;
import com.spinyowl.legui.system.renderer.nvg.util.NvgShapes;
import com.spinyowl.legui.system.renderer.nvg.util.NvgText;
import java.util.Map;
import org.joml.Vector2fc;
import org.joml.Vector4f;

/**
 * Used to render image rectangle if no other renderers implemented.
 */
public class NvgDefaultImageRenderer<I extends Image> extends NvgImageRenderer<I> {

  public static final String IMAGE = "Image";

  /**
   * Used to render specific Icon.
   *
   * @param image      image to render.
   * @param position   image position.
   * @param size       image size.
   * @param context    context.
   * @param nanovg     nanoVG context.
   * @param properties properties map.
   */
  @Override
  protected void renderImage(I image, Vector2fc position, Vector2fc size,
      Map<String, Object> properties, Context context, long nanovg) {

    float x = position.x();
    float y = position.y();
    float w = size.x();
    float h = size.y();

    NvgShapes.drawRect(nanovg, position, size, ColorConstants.red);
    NvgShapes.drawRectStroke(nanovg, position, size, ColorConstants.black, 1, 1);

    NvgText.drawTextLineToRect(nanovg, new Vector4f(x, y, w, h), true, HorizontalAlign.LEFT,
        VerticalAlign.MIDDLE,
        h / 3, FontRegistry.getDefaultFont(), IMAGE, ColorConstants.black());

  }
}
