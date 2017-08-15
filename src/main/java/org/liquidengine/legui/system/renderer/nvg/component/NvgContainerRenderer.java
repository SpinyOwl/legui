package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRect;

import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NVGUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.lwjgl.nanovg.NVGColor;

/**
 * Created by Aliaksandr_Shcherbin on 2/15/2017.
 */
public class NvgContainerRenderer extends NvgComponentRenderer<Container> {

    @Override
    protected void renderComponent(Container component, Context context, long nanovg) {
        NvgRenderUtils.createScissor(nanovg, component);
        {
            Vector2f p = component.getScreenPosition();
            Vector2f s = component.getSize();

            Vector4f color = component.getBackgroundColor();
            float x = p.x;
            float y = p.y;
            float w = s.x;
            float h = s.y;

            drawRectBackground(nanovg, color, x, y, w, h);
        }
        NvgRenderUtils.resetScissor(nanovg);

        List<Component> all = component.getChilds();
        for (Component child : all) {
            RendererProvider.getInstance().
                getComponentRenderer(child.getClass()).render(child, context);
        }

        renderBorderWScissor(component, context, nanovg);
    }

    private void drawRectBackground(long nanovg, Vector4f color, float x, float y, float w, float h) {
        NVGColor nvgColor = NVGColor.calloc();
        NVGUtils.rgba(color, nvgColor);
        nvgBeginPath(nanovg);
        nvgFillColor(nanovg, nvgColor);
        nvgRect(nanovg, x, y, w, h);
        nvgFill(nanovg);
        nvgColor.free();
    }

}
