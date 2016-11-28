package org.liquidengine.legui.render.nvg.component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.IOUtil;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Alexander on 07.09.2016.
 */
public class NvgImageRenderer extends NvgLeguiComponentRenderer {

    private static final Logger LOGGER = LogManager.getLogger();

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
            .expireAfterAccess(20, TimeUnit.SECONDS).removalListener(removalListener).build();

    private ScheduledExecutorService cleanup = Executors.newSingleThreadScheduledExecutor();
    private Map<String, Integer> imageAssociationMap = new ConcurrentHashMap<>();
    private NVGPaint imagePaint = NVGPaint.malloc();

    @Override
    public void initialize() {
        super.initialize();
        cleanup.scheduleAtFixedRate(() -> imageCache.cleanUp(), 1, 1, TimeUnit.SECONDS);
    }


    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        Image image = (Image) component;

        Vector2f size = image.getSize();
        Vector2f position = Util.calculatePosition(component);

        int imageRef = getImageRef(image, context);

        createScissor(context, component);
        {
            nvgBeginPath(context);
            nvgImagePattern(context, position.x, position.y, size.x, size.y, 0, imageRef, 1, imagePaint);
            nvgRoundedRect(context, position.x, position.y, size.x, size.y, component.getCornerRadius());
            nvgFillPaint(context, imagePaint);
            nvgFill(context);

            renderBorder(component, leguiContext);
        }
        resetScissor(context);

        removeOldImages(context);
    }

    @Override
    public void destroy() {
        super.destroy();
        cleanup.shutdown();
    }

    private void removeOldImages(long context) {
        String path = imagesToRemove.poll();
        if (path == null) return;
        LOGGER.debug("Removing image data from memory: " + path);
        Integer imageRef = imageAssociationMap.remove(path);
        if (imageRef != null) {
            NanoVG.nvgDeleteImage(context, imageRef);
        }
    }

    private int getImageRef(Image image, long context) {
        String path = image.getPath();
        Integer imageRef = imageCache.getIfPresent(path);
        if (imageRef == null) {
            LOGGER.debug("Loading image data to memory: " + path);
            if (path == null) {
                imageRef = new Integer(0);
            } else {
                try {
                    ByteBuffer data = IOUtil.ioResourceToByteBuffer(path, 32 * 1024);
                    imageRef = NanoVG.nvgCreateImageMem(context, 0, data);
                } catch (IOException e) {
                    imageRef = new Integer(0);
                }
                imageCache.put(path, imageRef);
                imageAssociationMap.put(path, imageRef);
            }
        } else {
            LOGGER.debug("Obtaining image from cache: " + path);
        }
        return imageRef;
    }
}
