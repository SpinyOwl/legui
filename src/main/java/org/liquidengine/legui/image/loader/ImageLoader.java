package org.liquidengine.legui.image.loader;

import org.liquidengine.legui.image.Image;

/**
 * Created by Aliaksandr_Shcherbin on 3/2/2017.
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

    public static Image loadImage(String path) {
        initializeDefault();
        return loader.createImage(path);
    }

    protected abstract Image createImage(String path);

}
