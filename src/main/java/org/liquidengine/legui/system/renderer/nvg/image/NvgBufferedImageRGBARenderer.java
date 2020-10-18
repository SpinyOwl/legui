package org.liquidengine.legui.system.renderer.nvg.image;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2fc;
import org.liquidengine.legui.image.BufferedImageRGBA;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageReferenceManager;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.lwjgl.nanovg.NanoVG;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static org.lwjgl.nanovg.NanoVG.nvgCreateImageRGBA;

/**
 * Used to render image rectangle if no other renderers implemented.
 */
public class NvgBufferedImageRGBARenderer extends NvgImageRenderer<BufferedImageRGBA> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void initialize() {
        NvgImageReferenceManager manager = NvgImageReferenceManager.getInstance();
        manager.putImageReferenceProvider(BufferedImageRGBA.class, (image, context) -> {
            int imageRef = 0;
            Function<BufferedImageRGBA, String> getPath = i -> "TI::RGBA::" + i.hashCode();

            if (image != null) {
                String path = getPath.apply(image);
                try {
                    imageRef = manager.getImageCache().get(path, () -> {
                        int reference = nvgCreateImageRGBA(context, image.getWidth(), image.getHeight(), 0, image.getImageData());
                        manager.getImageAssociationMap().put(getPath.apply(image), reference);
                        return reference;
                    });
                } catch (ExecutionException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            return imageRef;
        });
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
    @Override
    protected void renderImage(BufferedImageRGBA image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {

        NvgImageReferenceManager manager = NvgImageReferenceManager.getInstance();
        int imageRef = manager.getImageReference(image, nanovg);
        if (image.isUpdated())
            NanoVG.nvgUpdateImage(nanovg, imageRef, image.getImageData());
        renderImage(imageRef, position, size, properties, nanovg);
    }
}
