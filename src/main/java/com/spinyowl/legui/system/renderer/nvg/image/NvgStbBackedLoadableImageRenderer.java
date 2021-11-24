package com.spinyowl.legui.system.renderer.nvg.image;

import com.spinyowl.legui.image.StbBackedLoadableImage;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.NvgImageReferenceManager;
import com.spinyowl.legui.system.renderer.nvg.NvgImageRenderer;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.joml.Vector2fc;
import org.lwjgl.nanovg.NanoVG;


public class NvgStbBackedLoadableImageRenderer extends NvgImageRenderer<StbBackedLoadableImage> {

  @Override
  public void initialize() {
    NvgImageReferenceManager manager = NvgImageReferenceManager.getInstance();
    manager.putImageReferenceProvider(StbBackedLoadableImage.class, (image, context) -> {
      int imageRef = 0;
      String path = image.getPath();
      if (path != null) {
        try {
          imageRef = manager.getImageCache().get(path, createReference(manager, image, context));
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      } else {
        return 0;
      }
      return imageRef;
    });
  }

  private Callable<Integer> createReference(NvgImageReferenceManager manager,
      StbBackedLoadableImage image, Long context) {
    return () -> {
      int reference = 0;
      ByteBuffer imageData = image.getImageData();
      if (imageData != null) {
        reference = NanoVG.nvgCreateImageRGBA(context, image.getWidth(), image.getHeight(), 0,
            imageData);
      }
      manager.getImageAssociationMap().put(image.getPath(), reference);
      return reference;
    };
  }

  /**
   * Used to render specific Icon.
   *
   * @param image      image to render.
   * @param position   image position.
   * @param size       image size.
   * @param context    context.
   * @param nanovg     nanoVG context.
   * @param properties properties map.
   */
  @Override
  protected void renderImage(StbBackedLoadableImage image, Vector2fc position, Vector2fc size,
      Map<String, Object> properties, Context context, long nanovg) {

    NvgImageReferenceManager manager = NvgImageReferenceManager.getInstance();
    int imageRef = manager.getImageReference(image, nanovg);

    renderImage(imageRef, position, size, properties, nanovg);
  }
}
