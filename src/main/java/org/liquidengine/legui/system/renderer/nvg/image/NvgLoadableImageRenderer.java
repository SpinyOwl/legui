package org.liquidengine.legui.system.renderer.nvg.image;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRectVarying;

import java.util.Map;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgLoadableImageReferenceManager;
import org.lwjgl.nanovg.NVGPaint;

/**
 * Created by Aliaksandr_Shcherbin on 3/31/2017.
 */
public class NvgLoadableImageRenderer<I extends LoadableImage> extends NvgImageRenderer<I> {


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

        NVGPaint imagePaint = NVGPaint.calloc();
        NvgLoadableImageReferenceManager manager = (NvgLoadableImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);

        int imageRef = manager.getImageReference(image, nanovg);
        float x = position.x();
        float y = position.y();
        float w = size.x();
        float h = size.y();
        Vector4f r = (Vector4f) properties.getOrDefault(C_RADIUS, 0);

        nvgBeginPath(nanovg);
        nvgImagePattern(nanovg, x, y, w, h, 0, imageRef, 1, imagePaint);
        nvgRoundedRectVarying(nanovg, x, y, w, h, r.x, r.y, r.z, r.w);
        nvgFillPaint(nanovg, imagePaint);
        nvgFill(nanovg);
        imagePaint.free();
    }
}
