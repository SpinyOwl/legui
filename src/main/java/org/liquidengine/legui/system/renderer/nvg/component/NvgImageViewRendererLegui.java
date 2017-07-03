package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.nvg.NvgLeguiComponentRenderer;

import java.util.HashMap;

import static org.liquidengine.legui.system.renderer.LeguiImageRenderer.C_RADIUS;
import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.renderImage;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgImageViewRendererLegui extends NvgLeguiComponentRenderer<ImageView> {
    @Override
    protected void renderComponent(ImageView imageView, LeguiContext context, long nanovg) {
        Vector2f size     = imageView.getSize();
        Vector2f position = imageView.getScreenPosition();

        createScissor(nanovg, imageView);
        {
            HashMap<String, Object> p = new HashMap<>();
            p.put(C_RADIUS, imageView.getCornerRadius());

            renderImage(imageView.getImage(), position, size, p, context);

            renderBorder(imageView, context);
        }
        resetScissor(nanovg);
    }
}
