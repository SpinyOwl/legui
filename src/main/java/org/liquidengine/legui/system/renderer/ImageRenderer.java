package org.liquidengine.legui.system.renderer;

import org.joml.Vector2fc;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.system.context.Context;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Image renderer.
 */
public abstract class ImageRenderer<I extends Image> {

    public static final String C_RADIUS = "C_RADIUS";
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    /**
     * This method called by base abstract image renderer.
     *
     * @param image      image to render.
     * @param position   image position.
     * @param size       image size.
     * @param properties rendering properties.
     * @param context    context.
     */
    public void render(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context) {
        if (!initialized.getAndSet(true)) {
            initialize();
        }
        renderImage(image, position, size, properties, context);
    }

    /**
     * This method called by base abstract image renderer.
     *
     * @param image      image to render.
     * @param position   image position.
     * @param size       image size.
     * @param properties rendering properties.
     * @param context    context.
     */
    public abstract void renderImage(I image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context);

    public void initialize() {
        // this method should be reimplemented if need to initialize some data in renderer before it can be used
        // called only once
    }

    public void destroy() {
        // this method should be reimplemented if need to destroy some data in renderer before exit
    }
}
