package org.liquidengine.legui.system.renderer.nvg.image;

import org.joml.Vector2fc;
import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgLoadableImageReferenceManager;

import java.util.Map;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;

/**
 * Created by ShchAlexander on 3/31/2017.
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
     * @param properties properties map.
     */
    @Override
    protected void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {

        NvgLoadableImageReferenceManager manager = (NvgLoadableImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
        int imageRef = manager.getImageReference(image, nanovg);

        renderImage(imageRef, position, size, properties, nanovg);
    }
}
