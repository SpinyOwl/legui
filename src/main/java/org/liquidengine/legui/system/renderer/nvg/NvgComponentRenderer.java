package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.ComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtil;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public abstract class NvgComponentRenderer<C extends Component> extends ComponentRenderer<C> {

    @Override
    public void initialize() {
    }

    @Override
    public void renderComponent(C component, Context context) {
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        renderComponent(component, context, nanovgContext);
    }

    protected abstract void renderComponent(C component, Context context, long nanovg);


    @Override
    public void destroy() {
    }
}
