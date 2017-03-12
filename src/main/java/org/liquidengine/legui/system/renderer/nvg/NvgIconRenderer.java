package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.IconRenderer;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public abstract class NvgIconRenderer<I extends Icon> extends IconRenderer<I> {

    @Override
    public void initialize() {
    }

    @Override
    public void renderIcon(I icon, Component component, Context context) {
        if (icon == null) return;
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        renderIcon(icon, component, context, nanovgContext);
    }

    protected abstract void renderIcon(I icon, Component component, Context context, long nanovg);


    @Override
    public void destroy() {
    }
}
