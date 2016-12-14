package org.liquidengine.legui.render.nvg.image;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.liquidengine.legui.image.Image;
import org.lwjgl.nanovg.NanoVG;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by Alexander on 14.12.2016.
 */
public class NvgImageReferenceManager {

    /**
     * Image queue to remove
     */
    private Queue<String> imagesToRemove = new ConcurrentLinkedQueue<>();

    /**
     * Removal listener
     */
    private RemovalListener<String, Integer> removalListener = removal -> imagesToRemove.add(removal.getKey());


    /**
     * Cache of loaded images. If image is reached only by soft reference it will be deleted.
     */
    private Cache<String, Integer> imageCache = CacheBuilder.newBuilder().initialCapacity(200)
            .expireAfterAccess(300, TimeUnit.SECONDS).removalListener(removalListener).build();
    /**
     * Cleanup scheduler
     */
    private ScheduledExecutorService cleanup = Executors.newSingleThreadScheduledExecutor();
    private Map<String, Integer> imageAssociationMap = new ConcurrentHashMap<>();

    public NvgImageReferenceManager(){
        cleanup.scheduleAtFixedRate(() -> imageCache.cleanUp(), 1, 1, TimeUnit.SECONDS);
    }


    public void removeOldImages(long context) {
        String path = imagesToRemove.poll();
        if (path == null) return;
        Integer imageRef = imageAssociationMap.remove(path);
        if (imageRef != null) {
            NanoVG.nvgDeleteImage(context, imageRef);
        }
    }

    public int getImageReference(Image image, long context) {
        Integer imageRef = 0;
        if (image != null) {

            String path = image.getPath();
            if (path != null) {
                imageRef = imageCache.getIfPresent(path);
                if (imageRef == null) {
                    ByteBuffer imageData = image.getImageData();
                    if (imageData != null) {
                        int width = image.getWidth();
                        int height = image.getHeight();
                        imageRef = NanoVG.nvgCreateImageRGBA(context, width, height, 0, imageData);
                    } else {
                        imageRef = 0;
                    }
                    imageCache.put(path, imageRef);
                    imageAssociationMap.put(path, imageRef);
                }
            } else {
                return 0;
            }
        }
        return imageRef;
    }

    public void destroy(){
        cleanup.shutdown();
    }
}
