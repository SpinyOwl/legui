package org.liquidengine.legui.system.renderer.nvg;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.image.ImageChannels;
import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.image.TextureImageRGBA;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.nanovg.NanoVGGL3;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

import static org.lwjgl.nanovg.NanoVG.nvgCreateImageRGBA;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.GL_MAJOR_VERSION;
import static org.lwjgl.opengl.GL30.GL_MINOR_VERSION;

/**
 * Created by ShchAlexander on 1/26/2017.
 */
public class NvgImageReferenceManager {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * BufferedImage queue to remove.
     */
    private final Queue<String> imagesToRemove = new ConcurrentLinkedQueue<>();

    /**
     * Removal listener.
     */
    private final RemovalListener<String, Integer> removalListener = removal -> imagesToRemove.add(removal.getKey());

    /**
     * Cache of loaded images. If image is reached only by soft reference it will be deleted.
     */
    private final Cache<String, Integer> imageCache;

    /**
     * Cleanup scheduler.
     */
    private final ScheduledExecutorService cleanup = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, Integer> imageAssociationMap = new ConcurrentHashMap<>();

    /**
     * Used to create image reference manager.
     */
    protected NvgImageReferenceManager() {
        imageCache = CacheBuilder.newBuilder().initialCapacity(200)
            .expireAfterAccess(3000, TimeUnit.SECONDS).removalListener(removalListener).build();
        cleanup.scheduleAtFixedRate(() -> imageCache.cleanUp(), 1, 1, TimeUnit.SECONDS);
    }

    /**
     * Used to create image reference manager.
     *
     * @param expireCleanupEnabled the expire cleanup enabled.
     */
    protected NvgImageReferenceManager(boolean expireCleanupEnabled) {
        CacheBuilder<String, Integer> cacheBuilder = CacheBuilder.newBuilder().initialCapacity(200).removalListener(removalListener);
        if (expireCleanupEnabled) {
            cacheBuilder.expireAfterAccess(300, TimeUnit.SECONDS);
        }
        imageCache = cacheBuilder.build();
        cleanup.scheduleAtFixedRate(() -> imageCache.cleanUp(), 1, 1, TimeUnit.SECONDS);
    }

    /**
     * Used to create image reference manager.
     *
     * @param duration the duration after which cache should clean unused references.
     */
    protected NvgImageReferenceManager(int duration) {
        imageCache = CacheBuilder.newBuilder().initialCapacity(200)
            .removalListener(removalListener).expireAfterAccess(duration, TimeUnit.SECONDS).build();
        cleanup.scheduleAtFixedRate(() -> imageCache.cleanUp(), 1, 1, TimeUnit.SECONDS);
    }


    /**
     * Used to remove old images.
     *
     * @param context nanovg context.
     */
    protected void removeOldImages(long context) {
        String path = imagesToRemove.poll();
        if (path == null) {
            return;
        }
        Integer imageRef = imageAssociationMap.remove(path);
        if (imageRef != null) {
            NanoVG.nvgDeleteImage(context, imageRef);
        }
    }

    /**
     * Used to obtain image reference by image.
     *
     * @param image   image to get reference.
     * @param context nanovg context.
     * @return reference of provided image or 0 if not found.
     */
    public int getImageReference(LoadableImage image, long context) {
        Integer imageRef = 0;
        if (image != null) {

            String path = image.getPath();
            if (path != null) {
                try {
                    imageRef = imageCache.get(path, createNewImageReference(image, context));
                } catch (ExecutionException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } else {
                return 0;
            }
        }
        return imageRef;
    }

    private Callable<Integer> createNewImageReference(LoadableImage image, long context) {
        return () -> {
            Integer reference = 0;
            ByteBuffer imageData = image.getImageData();
            if (imageData != null) {
                int width = image.getWidth();
                int height = image.getHeight();
                if (image.getChannels() == ImageChannels.RGBA) {
                    reference = NanoVG.nvgCreateImageRGBA(context, width, height, 0, imageData);
                } else {
                    reference = NanoVG.nvgCreateImageMem(context, 0, imageData);
                }
            }
            imageAssociationMap.put(image.getPath(), reference);
            return reference;
        };
    }

    public int getImageReference(FBOImage image, long context) {
        Integer imageRef = 0;
        if (image != null) {
            int textureId = image.getTextureId();
            if (textureId != 0) {
                String path = getFboPath(textureId);
                try {
                    imageRef = imageCache.get(path, createNewImageReference(image, context));
                } catch (ExecutionException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } else {
                return 0;
            }
        }
        return imageRef;
    }

    public int getImageReference(TextureImageRGBA image, long context) {
        Integer imageRef = 0;
        if (image != null) {
            String path = getPath(image);
            try {
                imageRef = imageCache.get(path, () -> {
                    int reference = nvgCreateImageRGBA(context, image.getWidth(), image.getHeight(), 0, image.getImageData());
                    imageAssociationMap.put(getPath(image), reference);
                    return reference;
                });
            } catch (ExecutionException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return imageRef;
    }

    private String getPath(TextureImageRGBA image) {
        return "TI::RGBA::" + image;
    }

    private String getFboPath(int textureId) {
        return "::FBO::" + textureId;
    }

    private Callable<Integer> createNewImageReference(FBOImage image, long context) {
        return () -> {
            int handle = image.getTextureId();
            int width = image.getWidth();
            int height = image.getHeight();
            Integer reference = 0;
            boolean isVersionNew = (glGetInteger(GL_MAJOR_VERSION) > 3) || (glGetInteger(GL_MAJOR_VERSION) == 3 && glGetInteger(GL_MINOR_VERSION) >= 2);
            if (isVersionNew) {
                reference = NanoVGGL3.nvglCreateImageFromHandle(context, handle, width, height, 0);
            } else {
                reference = NanoVGGL2.nvglCreateImageFromHandle(context, handle, width, height, 0);
            }
            imageAssociationMap.put(getFboPath(handle), reference);
            return reference;
        };
    }

    /**
     * Used to destroy image reference manager.
     */
    public void destroy() {
        cleanup.shutdown();
    }
}
