package org.liquidengine.legui.image.loader;

import org.liquidengine.legui.image.LoadableImage;

/**
 * Created by ShchAlexander on 3/2/2017.
 */
public abstract class ImageLoader {

    private static ImageLoader loader;

    public static void setLoader(ImageLoader loader) {
        ImageLoader.loader = loader;
    }

    private static void initializeDefault() {
        if (loader == null) {
            loader = new DefaultImageLoader();
        }
    }

    public static LoadableImage loadImage(String path) {
        initializeDefault();
        return loader.createImage(path);
    }

    protected abstract LoadableImage createImage(String path);

}
