package com.spinyowl.legui.image.loader;

import com.spinyowl.legui.image.LoadableImage;
import com.spinyowl.legui.image.StbBackedLoadableImage;

public class DefaultImageLoader extends ImageLoader {

  @Override
  protected LoadableImage createImage(String path) {
    StbBackedLoadableImage loadableImage = new StbBackedLoadableImage(path);
    loadableImage.load();
    return loadableImage;
  }
}
