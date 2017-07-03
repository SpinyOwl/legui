package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.LeguiIconRenderer;

import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.NVG_CONTEXT;

/**
 * Abstract renderer for Icon implementations.
 */
public abstract class NvgLeguiIconRenderer<I extends Icon> extends LeguiIconRenderer<I> {

    /**
     * This method called by base abstract icon renderer.
     *
     * @param icon      icon to render.
     * @param component component - icon owner.
     * @param context   context.
     */
    @Override
    public void renderIcon(I icon, Component component, LeguiContext context) {
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
    protected abstract void renderIcon(I icon, Component component, LeguiContext context, long nanovg);

}
