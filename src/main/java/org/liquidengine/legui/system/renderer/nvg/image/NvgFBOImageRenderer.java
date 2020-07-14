package org.liquidengine.legui.system.renderer.nvg.image;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2fc;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgImageReferenceManager;
import org.liquidengine.legui.system.renderer.nvg.NvgImageRenderer;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.nanovg.NanoVGGL3;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.IntFunction;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.GL_MAJOR_VERSION;
import static org.lwjgl.opengl.GL30.GL_MINOR_VERSION;

/**
 * Used to render image rectangle if no other renderers implemented.
 */
public class NvgFBOImageRenderer extends NvgImageRenderer<FBOImage> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void initialize() {
        NvgImageReferenceManager manager = NvgImageReferenceManager.getInstance();
        manager.putImageReferenceProvider(FBOImage.class, (i, c) -> {
            IntFunction<String> getFboPath = textureId -> "::FBO::" + textureId;
            int textureId = i.getTextureId();
            Integer imageRef = 0;
            if (textureId != 0) {
                String path = getFboPath.apply(textureId);
                try {
                    imageRef = manager.getImageCache().get(path, createReference(manager, i, c, getFboPath));
                } catch (ExecutionException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } else {
                return 0;
            }
            return imageRef;
        });
    }

    private Callable<Integer> createReference(NvgImageReferenceManager manager, FBOImage i, Long c, IntFunction<String> getFboPath) {
        return () -> {
            int handle = i.getTextureId();
            int width = i.getWidth();
            int height = i.getHeight();
            int reference = 0;
            boolean isVersionNew = (glGetInteger(GL_MAJOR_VERSION) > 3) || (glGetInteger(GL_MAJOR_VERSION) == 3 && glGetInteger(GL_MINOR_VERSION) >= 2);
            if (isVersionNew) {
                reference = NanoVGGL3.nvglCreateImageFromHandle(c, handle, width, height, 0);
            } else {
                reference = NanoVGGL2.nvglCreateImageFromHandle(c, handle, width, height, 0);
            }
            manager.getImageAssociationMap().put(getFboPath.apply(handle), reference);
            return reference;
        };
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
    protected void renderImage(FBOImage image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context, long nanovg) {

        NvgImageReferenceManager manager = NvgImageReferenceManager.getInstance();
        int imageRef = manager.getImageReference(image, nanovg);

        renderImage(imageRef, position, size, properties, nanovg);
    }
}
