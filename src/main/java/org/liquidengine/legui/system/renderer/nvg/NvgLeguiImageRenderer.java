package org.liquidengine.legui.system.renderer.nvg;

import org.joml.Vector2fc;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.LeguiImageRenderer;

import java.util.Map;

import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.NVG_CONTEXT;

/**
 * Image renderer.
 */
public abstract class NvgLeguiImageRenderer<I extends Image> extends LeguiImageRenderer<I> {

    /**
     * This method called by base abstract image renderer.
     *
     * @param image    image to render.
     * @param position image position.
     * @param size     image size.
     * @param context  context.
     */
    @Override
    public void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, LeguiContext context) {
        if (image == null) return;
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        renderImage(image, position, size, properties, context, nanovgContext);
    }

    /**
     * Used to render specific Icon.
     *
     * @param image    image to render.
     * @param position image position.
     * @param size     image size.
     * @param context  context.
     * @param nanovg   nanoVG context.
     */
    protected abstract void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, LeguiContext context, long nanovg);
}
