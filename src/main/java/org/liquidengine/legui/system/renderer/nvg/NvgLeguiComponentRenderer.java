package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.LeguiComponentRenderer;

import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.NVG_CONTEXT;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public abstract class NvgLeguiComponentRenderer<C extends Component> extends LeguiComponentRenderer<C> {

    @Override
    public void renderComponent(C component, LeguiContext context) {
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        if (!component.isVisible()) return;
        renderComponent(component, context, nanovgContext);
    }

    protected abstract void renderComponent(C component, LeguiContext context, long nanovg);

}
