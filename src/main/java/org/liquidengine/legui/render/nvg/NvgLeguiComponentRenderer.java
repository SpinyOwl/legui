package org.liquidengine.legui.render.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.LeguiComponentRenderer;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public abstract class NvgLeguiComponentRenderer implements LeguiComponentRenderer {

    @Override
    public void initialize() {
    }

    @Override
    public final void render(Component component, LeguiContext context) {
        render(component, context, (Long) context.getContextData().get(NvgLeguiRenderer.NVG_CONTEXT));
    }

    public abstract void render(Component component, LeguiContext context, long nvgContext);

    @Override
    public void destroy() {
    }
}
