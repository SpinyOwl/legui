package org.liquidengine.legui.context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;


/**
 * Created by Alexander on 07.09.2016.
 */
public final class ImageStorage {

    /**
     * Cache of loaded images. If image is reached only by soft reference it will be deleted.
     */
    private Cache<String, Image> imageCache = CacheBuilder.<String, Image>newBuilder().concurrencyLevel(4).
            weakKeys().initialCapacity(200).expireAfterAccess(10, TimeUnit.SECONDS).build();

    private ImageStorage() {
    }

    public static ImageStorage getInstance() {
        return ISH.I;
    }

    public Image loadImage(String path) {
        Image image = imageCache.getIfPresent(path);
        if (image == null) {
            try {
                ByteBuffer data = IOUtil.ioResourceToByteBuffer(path, 32 * 1024);
                image = new Image(path, data);
                imageCache.put(path, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    /**
     * Instance holder - singleton (on demand holder)
     */
    private static class ISH {
        private static final ImageStorage I = new ImageStorage();
    }

}
