package org.liquidengine.legui.render.nvg;

import org.joml.Vector2i;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.Font;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRenderer;
import org.liquidengine.legui.render.nvg.image.NvgImageReferenceManager;
import org.lwjgl.nanovg.NanoVGGL3;

import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class NvgLeguiRenderer extends LeguiRenderer {

    public static final String IMAGE_REFERENCE_MANAGER = "ImageReferenceManager";
    public static final String NVG_CONTEXT = "nvgContext";

    private long nvgContext;

    public NvgLeguiRenderer(LeguiContext context) {
        super(context);
    }

    @Override
    public void initialize() {
        int flags = NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_ANTIALIAS | (context.isDebugEnabled() ? NanoVGGL3.NVG_DEBUG : 1);
        nvgContext = NanoVGGL3.nvgCreate(flags);
        Map<String, Font> fontRegister = FontRegister.getFontRegister();

        for (Map.Entry<String, Font> fontDataEntry : fontRegister.entrySet()) {
            nvgCreateFontMem(nvgContext, fontDataEntry.getKey(), fontDataEntry.getValue().getData(), 0);
        }

        context.getContextData().put(NVG_CONTEXT, nvgContext);
        context.getContextData().put(IMAGE_REFERENCE_MANAGER, new NvgImageReferenceManager());

        NvgRendererProvider.getProvider().getComponentRenderers().forEach(LeguiComponentRenderer::initialize);
    }

    @Override
    public void render(Component component) {
        super.render(component);
    }

    @Override
    public void preRender(Component component) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Vector2i windowSize = context.getWindowSize();
        nvgBeginFrame(nvgContext, windowSize.x, windowSize.y, context.getPixelRatio());
    }

    @Override
    public void postRender(Component component) {
        nvgEndFrame(nvgContext);

        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);

        NvgImageReferenceManager manager = (NvgImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
        manager.removeOldImages(nvgContext);
    }

    @Override
    public void destroy() {
        NanoVGGL3.nnvgDeleteGL3(nvgContext);
        NvgRendererProvider.getProvider().getComponentRenderers().forEach(LeguiComponentRenderer::destroy);
        ((NvgImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER)).destroy();
    }
}
