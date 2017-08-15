package org.liquidengine.legui.system.renderer.nvg.image;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.drawRectStroke;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.renderTextLineToBounds;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRect;

import java.util.Map;
import org.joml.Vector2fc;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegistry;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.lwjgl.nanovg.NVGColor;

/**
 * Used to render image rectangle if no other renderers implemented.
 */
public class NvgDefaultImageRenderer<I extends Image> extends NvgImageRenderer<I> {

    public static final String IMAGE = "Image";
    private NVGColor def;

    @Override
    public void initialize() {
        def = NVGColor.calloc();
    }

    /**
     * Used to render specific Icon.
     *
     * @param image image to render.
     * @param position image position.
     * @param size image size.
     * @param context context.
     * @param nanovg nanoVG context.
     */
    @Override
    protected void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {

        float x = position.x();
        float y = position.y();
        float w = size.x();
        float h = size.y();

        nvgBeginPath(nanovg);
        nvgFillColor(nanovg, def);
        nvgRect(nanovg, x, y, w, h);
        nvgFill(nanovg);

        drawRectStroke(nanovg, x, y, w, h, ColorConstants.black, 1, 1);

        renderTextLineToBounds(nanovg, x, y, w, h, h / 3, FontRegistry.DEFAULT, ColorConstants.red(), IMAGE, HorizontalAlign.CENTER, VerticalAlign.MIDDLE,
            true);

    }

    @Override
    public void destroy() {
        def.free();
    }
}
