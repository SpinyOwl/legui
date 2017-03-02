package org.liquidengine.legui.image.loader;

import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.image.Image;

/**
 * Created by Aliaksandr_Shcherbin on 3/2/2017.
 */
public class DefaultImageLoader extends ImageLoader {

    @Override
    protected Image createImage(String path) {
        return new BufferedImage(path);
    }
}
