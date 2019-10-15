package org.liquidengine.legui.system.renderer.nvg;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;
import static org.lwjgl.nanovg.NanoVG.*;

import java.util.Map;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.ImageRenderer;
import org.lwjgl.nanovg.NVGPaint;

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

    /**
     * Default implementation for image rendering where imageRef is nanovg image reference
     * @param imageRef image reference
     * @param position position
     * @param size size
     * @param properties properties
     * @param nanovg nanovg
     */
    protected void renderImage(int imageRef, Vector2fc position, Vector2fc size, Map<String, Object> properties, long nanovg) {
        try (NVGPaint imagePaint = NVGPaint.calloc()) {
            float    x = position.x();
            float    y = position.y();
            float    w = size.x();
            float    h = size.y();
            Vector4f r = (Vector4f) properties.getOrDefault(C_RADIUS, 0);

            nvgBeginPath(nanovg);
            nvgImagePattern(nanovg, x, y, w, h, 0, imageRef, 1, imagePaint);
            nvgRoundedRectVarying(nanovg, x, y, w, h, r.x, r.y, r.z, r.w);
            nvgFillPaint(nanovg, imagePaint);
            nvgFill(nanovg);
        }
    }
}
