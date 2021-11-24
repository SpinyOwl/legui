package com.spinyowl.legui.image.loader;

import com.spinyowl.legui.image.LoadableImage;


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

  /**
   * Creates and loads image.
   *
   * @param path path to image.
   * @return loaded image.
   */
  protected abstract LoadableImage createImage(String path);

}
