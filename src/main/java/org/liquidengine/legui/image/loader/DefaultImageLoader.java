package org.liquidengine.legui.image.loader;

import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.image.LoadableImage;

/**
 * Created by ShchAlexander on 3/2/2017.
 */
public class DefaultImageLoader extends ImageLoader {

    @Override
    protected LoadableImage createImage(String path) {
        return new BufferedImage(path);
    }
}
