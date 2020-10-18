package org.liquidengine.legui.system.renderer.nvg.image;


import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

import java.util.Map;

/**
 * Used to render image rectangle if no other renderers implemented.
 */
public class NvgDefaultImageRenderer<I extends Image> extends NvgImageRenderer<I> {

    public static final String IMAGE = "Image";

    /**
     * Used to render specific Icon.
     *
     * @param image image to render.
     * @param position image position.
     * @param size image size.
     * @param context context.
     * @param nanovg nanoVG context.
     * @param properties properties map.
     */
    @Override
    protected void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {

        float x = position.x();
        float y = position.y();
        float w = size.x();
        float h = size.y();

        NvgShapes.drawRect(nanovg, position, size, ColorConstants.red);
        NvgShapes.drawRectStroke(nanovg, position, size, ColorConstants.black, 1, 1);

        NvgText.drawTextLineToRect(nanovg, new Vector4f(x, y, w, h), true, HorizontalAlign.LEFT, VerticalAlign.MIDDLE,
                                   h / 3, FontRegistry.getDefaultFont(), IMAGE, ColorConstants.black());

    }
}
