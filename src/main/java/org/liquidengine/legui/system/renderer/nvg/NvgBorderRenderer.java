package org.liquidengine.legui.system.renderer.nvg;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Border;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.BorderRenderer;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public abstract class NvgBorderRenderer<B extends Border> extends BorderRenderer<B> {

    @Override
    public void renderBorder(B border, Component component, Context context) {
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        if (!border.isEnabled()) {
            return;
        }
        renderBorder(border, component, context, nanovgContext);
    }

    protected abstract void renderBorder(B border, Component component, Context context, long nanovg);

}
