package org.liquidengine.legui.render.nvg.component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.LineBorder;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Alexander on 07.09.2016.
 */
public class NvgImageRenderer extends NvgLeguiComponentRenderer {

    /**
     * Image queue to remove
     */
    private Queue<String> imagesToRemove = new ConcurrentLinkedQueue<>();

    /**
     * Removal listener
     */
    private RemovalListener<String, Image> removalListener = removal -> imagesToRemove.add(removal.getValue().getPath());

    /**
     * Cache of loaded images. If image is reached only by soft reference it will be deleted.
     */
    private Cache<String, Image> imageCache = CacheBuilder.<String, Image>newBuilder()/*.weakKeys()*/.initialCapacity(200)
            .expireAfterAccess(5, TimeUnit.SECONDS).removalListener(removalListener).build();

    private Map<String, Integer> imageAssociationMap = new ConcurrentHashMap<>();

    private NVGPaint imagePaint = NVGPaint.malloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        Image image = (Image) component;

        Vector2f size = image.getSize();
        Vector2f position = Util.calculatePosition(component);

        int imageRef = getImageRef(image, context);
        float borderRadius = 0;
        Border border = component.getBorder();
        if (border != null && border instanceof LineBorder) borderRadius = ((LineBorder) border).getBorderRadius();

        createScissor(context, component);
        {
            {
                nvgBeginPath(context);
                nvgImagePattern(context, position.x, position.y, size.x, size.y, 0, imageRef, 1, imagePaint);
                nvgRoundedRect(context, position.x, position.y, size.x, size.y, borderRadius);
                nvgFillPaint(context, imagePaint);
                nvgFill(context);
            }
            {
                if (border != null) {
                    border.render(leguiContext);
                }
            }

        }
        resetScissor(context);

        removeOldImages(context);
    }

    private void removeOldImages(long context) {
        String imageToRemove = imagesToRemove.poll();
        if (imageToRemove == null) return;
        System.out.println("removing " + imageToRemove);
        Integer imageRef = imageAssociationMap.remove(imageToRemove);
        NanoVG.nvgDeleteImage(context, imageRef);
    }

    private int getImageRef(Image image, long context) {
        String path = image.getPath();
        Integer imageRef = imageAssociationMap.get(path);
        if (imageRef == null || imageRef == 0) {
            imageRef = NanoVG.nvgCreateImageMem(context, 0, image.getImageData());
            imageCache.put(path, image);
            imageAssociationMap.put(path, imageRef);
        }

        return imageRef;
    }
}
