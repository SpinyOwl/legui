package org.liquidengine.legui.system.renderer.nvg;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.liquidengine.legui.image.Image;
import org.lwjgl.nanovg.NanoVG;

import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.BiFunction;

/**
 * Created by ShchAlexander on 1/26/2017.
 */
public final class NvgImageReferenceManager {
    private static final NvgImageReferenceManager INSTANCE = new NvgImageReferenceManager();

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
    private final Map<Class<? extends Image>, BiFunction<? extends Image, Long, Integer>> imageReferenceProviders = new ConcurrentHashMap<>();

    /**
     * Used to create image reference manager.
     */
    private NvgImageReferenceManager() {
        imageCache = CacheBuilder.newBuilder().initialCapacity(200)
            .expireAfterAccess(3000, TimeUnit.SECONDS).removalListener(removalListener).build();
        cleanup.scheduleAtFixedRate(imageCache::cleanUp, 1, 1, TimeUnit.SECONDS);
    }

    public static NvgImageReferenceManager getInstance() {
        return INSTANCE;
    }

    public <I extends Image> void putImageReferenceProvider(
        Class<I> imageClass, BiFunction<I, Long, Integer> imageReferenceProvider
    ) {
        imageReferenceProviders.put(Objects.requireNonNull(imageClass), Objects.requireNonNull(imageReferenceProvider));
    }

    public <I extends Image> void removeImageReferenceProvider(Class<I> imageClass) {
        imageReferenceProviders.remove(imageClass);
    }

    public <I extends Image> BiFunction<I, Long, Integer> getImageReferenceProvider(Class<I> imageClass) {
        return (BiFunction<I, Long, Integer>) imageReferenceProviders.get(imageClass);
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
    public <I extends Image> int getImageReference(I image, long context) {
        Integer imageRef = 0;
        if (image != null) {
            Class<I> aClass = (Class<I>) image.getClass();
            if (imageReferenceProviders.containsKey(aClass)) {
                imageRef = getImageReferenceProvider(aClass).apply(image, context);
            }
        }
        return imageRef;
    }

    /**
     * Used to destroy image reference manager.
     */
    public void destroy() {
        cleanup.shutdown();
    }

    public Cache<String, Integer> getImageCache() {
        return imageCache;
    }

    public Map<String, Integer> getImageAssociationMap() {
        return imageAssociationMap;
    }
}
