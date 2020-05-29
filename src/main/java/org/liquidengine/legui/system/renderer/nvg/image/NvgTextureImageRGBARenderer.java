package org.liquidengine.legui.system.renderer.nvg.image;


import org.joml.Vector2fc;
import org.liquidengine.legui.image.TextureImageRGBA;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageReferenceManager;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.lwjgl.nanovg.NanoVG;

import java.util.Map;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;

/**
 * Used to render image rectangle if no other renderers implemented.
 */
public class NvgTextureImageRGBARenderer extends NvgImageRenderer<TextureImageRGBA> {

    /**
     * Used to render specific Icon.
     *
     * @param image    image to render.
     * @param position image position.
     * @param size     image size.
     * @param context  context.
     * @param nanovg   nanoVG context.
     */
    @Override
    protected void renderImage(TextureImageRGBA image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {

        NvgImageReferenceManager manager = (NvgImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
        int imageRef = manager.getImageReference(image, nanovg);
        if (image.needsToUpdate())
            NanoVG.nvgUpdateImage(nanovg, imageRef, image.getImageData());
        renderImage(imageRef, position, size, properties, nanovg);
    }
}
