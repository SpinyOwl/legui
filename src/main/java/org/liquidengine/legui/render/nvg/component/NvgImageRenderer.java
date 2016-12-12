package org.liquidengine.legui.render.nvg.component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.image.Image;
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
     * ImageView queue to remove
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
        ImageView imageView = (ImageView) component;

        Vector2f size = imageView.getSize();
        Vector2f position = Util.calculatePosition(component);

        int imageRef = getImageReference(imageView, context);

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
        Integer imageRef = imageAssociationMap.remove(path);
        if (imageRef != null) {
            NanoVG.nvgDeleteImage(context, imageRef);
        }
    }

    private int getImageRef(ImageView imageView, long context) {
        String path = imageView.getPath();
        Integer imageRef = imageCache.getIfPresent(path);
        if (imageRef == null) {
            if (path == null) {
                imageRef = 0;
            } else {
                try {
                    ByteBuffer data = IOUtil.ioResourceToByteBuffer(path, 32 * 1024);
                    imageRef = NanoVG.nvgCreateImageMem(context, 0, data);
                } catch (IOException e) {
                    imageRef = 0;
                }
                imageCache.put(path, imageRef);
                imageAssociationMap.put(path, imageRef);
            }
        }
        return imageRef;
    }

    private int getImageReference(ImageView imageView, long context) {
        Integer imageRef = 0;
        Image image = imageView.getImage();
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
}
