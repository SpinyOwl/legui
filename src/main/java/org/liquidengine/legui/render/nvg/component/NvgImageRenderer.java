package org.liquidengine.legui.render.nvg.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.render.nvg.NvgLeguiRenderer;
import org.liquidengine.legui.render.nvg.image.NvgImageReferenceManager;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGPaint;

import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Alexander on 07.09.2016.
 */
public class NvgImageRenderer extends NvgLeguiComponentRenderer {

    private static final Logger   LOGGER     = LogManager.getLogger();
    private              NVGPaint imagePaint = NVGPaint.malloc();

    @Override
    public void initialize() {
        super.initialize();
    }


    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        ImageView imageView = (ImageView) component;

        Vector2f size     = imageView.getSize();
        Vector2f position = Util.calculatePosition(component);

        NvgImageReferenceManager manager  = (NvgImageReferenceManager) leguiContext.getContextData().get(NvgLeguiRenderer.IMAGE_REFERENCE_MANAGER);
        int                      imageRef = manager.getImageReference(imageView.getImage(), context);

        createScissor(context, component);
        {
            nvgBeginPath(context);
            nvgImagePattern(context, position.x, position.y, size.x, size.y, 0, imageRef, 1, imagePaint);
            nvgRoundedRect(context, position.x, position.y, size.x, size.y, component.getCornerRadius());
            nvgFillPaint(context, imagePaint);
            nvgFill(context);

            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
