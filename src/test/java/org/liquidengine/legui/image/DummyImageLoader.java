package org.liquidengine.legui.image;

import org.liquidengine.legui.image.loader.ImageLoader;

/**
 * Created by Aliaksandr_Shcherbin on 3/2/2017.
 */
public class DummyImageLoader extends ImageLoader {
    @Override
    protected LoadableImage createImage(String path) {
        return new DummyImage(path);
    }
}
