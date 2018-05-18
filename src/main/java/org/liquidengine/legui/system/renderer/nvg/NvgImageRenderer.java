package org.liquidengine.legui.system.renderer.nvg;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

import java.util.Map;
import org.joml.Vector2fc;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.ImageRenderer;

/**
 * Image renderer.
 */
public abstract class NvgImageRenderer<I extends Image> extends ImageRenderer<I> {

    /**
     * This method called by base abstract image renderer.
     *
     * @param image image to render.
     * @param position image position.
     * @param size image size.
     * @param context context.
     */
    @Override
    public void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context) {
        if (image == null) {
            return;
        }
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        renderImage(image, position, size, properties, context, nanovgContext);
    }

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
    protected abstract void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg);
}
