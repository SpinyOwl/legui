package org.liquidengine.legui.render.nvg;

import org.joml.Vector2i;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRenderer;
import org.lwjgl.nanovg.NanoVGGL3;

import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class NvgLeguiRenderer extends LeguiRenderer {

    private long nvgContext;

    public NvgLeguiRenderer(LeguiContext context) {
        super(context);
    }

    @Override
    public void initialize() {
        int flags = NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_ANTIALIAS | (context.isDebug() ? NanoVGGL3.NVG_DEBUG : 1);
        nvgContext = NanoVGGL3.nvgCreateGL3(flags);
        Map<String, FontRegister.FontData> fontRegister = FontRegister.getFontRegister();

        for (Map.Entry<String, FontRegister.FontData> fontDataEntry : fontRegister.entrySet()) {
            nvgCreateFontMem(nvgContext, fontDataEntry.getKey(), fontDataEntry.getValue().getData(), 0);
        }

        context.getContextData().put("nvgContext", nvgContext);

        NvgRendererProvider.getProvider().getComponentRenderers().forEach(LeguiComponentRenderer::initialize);

    }

    @Override
    public void render(Component component) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        {
            Vector2i windowSize = context.getTargetSize();
            nvgBeginFrame(nvgContext, windowSize.x, windowSize.y, context.getPixelRatio());
            component.render(context);
            nvgEndFrame(nvgContext);
        }
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void destroy() {
        NanoVGGL3.nnvgDeleteGL3(nvgContext);
        NvgRendererProvider.getProvider().getComponentRenderers().forEach(LeguiComponentRenderer::destroy);
    }
}
