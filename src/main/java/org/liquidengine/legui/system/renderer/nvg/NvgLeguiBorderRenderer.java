package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.LeguiBorderRenderer;

import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.NVG_CONTEXT;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public abstract class NvgLeguiBorderRenderer<B extends Border> extends LeguiBorderRenderer<B> {

    @Override
    public void renderBorder(B border, Component component, LeguiContext context) {
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        if (!border.isEnabled()) return;
        renderBorder(border, component, context, nanovgContext);
    }

    protected abstract void renderBorder(B border, Component component, LeguiContext context, long nanovg);

}
