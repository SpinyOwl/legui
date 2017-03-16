package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.IconRenderer;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

/**
 * Abstract renderer for Icon implementations.
 */
public abstract class NvgIconRenderer<I extends Icon> extends IconRenderer<I> {

    /**
     * This method called by base abstract icon renderer.
     *
     * @param icon      icon to render.
     * @param component component - icon owner.
     * @param context   context.
     */
    @Override
    public void renderIcon(I icon, Component component, Context context) {
        if (icon == null) return;
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        renderIcon(icon, component, context, nanovgContext);
    }

    /**
     * Used to render specific Icon.
     *
     * @param icon      icon to render.
     * @param component component - icon owner.
     * @param context   context.
     * @param nanovg    nanoVG context.
     */
    protected abstract void renderIcon(I icon, Component component, Context context, long nanovg);

}
