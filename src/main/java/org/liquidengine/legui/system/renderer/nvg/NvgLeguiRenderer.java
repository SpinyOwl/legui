package org.liquidengine.legui.system.renderer.nvg;

import org.joml.Vector2i;
import org.liquidengine.legui.font.Font;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.LeguiComponentRenderer;
import org.liquidengine.legui.system.renderer.LeguiRenderer;
import org.lwjgl.nanovg.NanoVGGL3;

import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgLeguiRenderer extends LeguiRenderer {
    public static final String NVG_CONTEXT             = "NVG_CONTEXT";
    public static final String IMAGE_REFERENCE_MANAGER = "IMAGE_REFERENCE_MANAGER";

    protected long                nvgContext;
    protected NvgRendererProvider provider;

    public NvgLeguiRenderer(LeguiContext context, NvgRendererProvider provider) {
        super(context);
        this.provider = provider;
    }

    @Override
    public void initialize() {
        int flags = NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_ANTIALIAS;
        nvgContext = NanoVGGL3.nvgCreate(flags);
        Map<String, Font> fontRegister = FontRegister.getFontRegister();

        for (Map.Entry<String, Font> fontDataEntry : fontRegister.entrySet()) {
            nvgCreateFontMem(nvgContext, fontDataEntry.getKey(), fontDataEntry.getValue().getData(), 0);
        }

        context.getContextData().put(NVG_CONTEXT, nvgContext);
        context.getContextData().put(IMAGE_REFERENCE_MANAGER, new NvgImageReferenceManager());

        provider.getComponentRenderers().forEach(LeguiComponentRenderer::initialize);
    }

    @Override
    public void preRender() {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

//        Vector2i windowSize = context.getWindowSize();
//        nvgBeginFrame(nvgContext, windowSize.x, windowSize.y, context.getPixelRatio());
    }

    @Override
    public void postRender() {

    }

    @Override
    public void destroy() {

    }
}
