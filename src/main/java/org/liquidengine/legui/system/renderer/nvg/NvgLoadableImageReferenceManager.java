package org.liquidengine.legui.system.renderer.nvg;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.liquidengine.legui.image.LoadableImage;
import org.lwjgl.nanovg.NanoVG;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgLoadableImageReferenceManager {

    /**
     * BufferedImage queue to remove.
     */
    private Queue<String> imagesToRemove = new ConcurrentLinkedQueue<>();

    /**
     * Removal listener.
     */
    private RemovalListener<String, Integer> removalListener = removal -> imagesToRemove.add(removal.getKey());

    /**
     * Cache of loaded images. If image is reached only by soft reference it will be deleted.
     */
    private Cache<String, Integer> imageCache;

    /**
     * Cleanup scheduler.
     */
    private ScheduledExecutorService cleanup = Executors.newSingleThreadScheduledExecutor();
    private Map<String, Integer> imageAssociationMap = new ConcurrentHashMap<>();

    /**
     * Used to create image reference manager.
     */
    protected NvgLoadableImageReferenceManager() {
        imageCache = CacheBuilder.newBuilder().initialCapacity(200).softValues()
            .expireAfterAccess(300, TimeUnit.SECONDS).removalListener(removalListener).build();
        cleanup.scheduleAtFixedRate(() -> imageCache.cleanUp(), 1, 1, TimeUnit.SECONDS);
    }

    /**
     * Used to create image reference manager.
     *
     * @param expireCleanupEnabled the expire cleanup enabled.
     */
    protected NvgLoadableImageReferenceManager(boolean expireCleanupEnabled) {
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
    protected NvgLoadableImageReferenceManager(int duration) {
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
     * @param image image to get reference.
     * @param context nanovg context.
     * @return reference of provided image or 0 if not found.
     */
    public int getImageReference(LoadableImage image, long context) {
        Integer imageRef = 0;
        if (image != null) {

            String path = image.getPath();
            if (path != null) {
                try {
                    imageRef = imageCache.get(path, getIntegerCallable(image, context));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                return 0;
            }
        }
        return imageRef;
    }

    private Callable<Integer> getIntegerCallable(LoadableImage image, long context) {
        return () -> {
            Integer reference = 0;
            ByteBuffer imageData = image.getImageData();
            if (imageData != null) {
                int width = image.getWidth();
                int height = image.getHeight();
                reference = NanoVG.nvgCreateImageRGBA(context, width, height, 0, imageData);
            }
            imageAssociationMap.put(image.getPath(), reference);
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
