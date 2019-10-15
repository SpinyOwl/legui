package org.liquidengine.legui.system.renderer.nvg.image;


import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgLoadableImageReferenceManager;
import org.lwjgl.nanovg.NVGPaint;

import java.util.Map;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Used to render image rectangle if no other renderers implemented.
 */
public class NvgFBOImageRenderer extends NvgImageRenderer<FBOImage> {

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
    protected void renderImage(FBOImage image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {

        NvgLoadableImageReferenceManager manager = (NvgLoadableImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
        int imageRef = manager.getImageReference(image, nanovg);

        renderImage(imageRef, position, size, properties, nanovg);
    }
}
