package org.liquidengine.legui.image.loader;

import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.image.StbBackedLoadableImage;

/**
 * Created by ShchAlexander on 3/2/2017.
 */
public class DefaultImageLoader extends ImageLoader {

    @Override
    protected LoadableImage createImage(String path) {
        StbBackedLoadableImage loadableImage = new StbBackedLoadableImage(path);
        loadableImage.load();
        return loadableImage;
    }
}
