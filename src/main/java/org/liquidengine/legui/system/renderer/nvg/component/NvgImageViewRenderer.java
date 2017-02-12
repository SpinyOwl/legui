package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgImageReferenceManager;
import org.lwjgl.nanovg.NVGPaint;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgImageViewRenderer extends NvgComponentRenderer<ImageView> {
    private NVGPaint imagePaint = NVGPaint.malloc();

    @Override
    protected void renderComponent(ImageView imageView, Context context, long nanovg) {
        Vector2f size     = imageView.getSize();
        Vector2f position = imageView.getScreenPosition();

        NvgImageReferenceManager manager  = (NvgImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
        int                      imageRef = manager.getImageReference(imageView.getImage(), nanovg);

        createScissor(nanovg, imageView);
        {
            nvgBeginPath(nanovg);
            nvgImagePattern(nanovg, position.x, position.y, size.x, size.y, 0, imageRef, 1, imagePaint);
            nvgRoundedRect(nanovg, position.x, position.y, size.x, size.y, imageView.getCornerRadius());
            nvgFillPaint(nanovg, imagePaint);
            nvgFill(nanovg);

            renderBorder(imageView, context);
        }
        resetScissor(nanovg);
    }
}
