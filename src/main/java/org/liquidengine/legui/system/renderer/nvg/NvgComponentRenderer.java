package org.liquidengine.legui.system.renderer.nvg;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;

import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.ComponentRenderer;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;

/**
 * The base NanoVG component renderer.
 *
 * @param <C> component type.
 */
public abstract class NvgComponentRenderer<C extends Component> extends ComponentRenderer<C> {

    /**
     * Used to render component.
     *
     * @param component component to render.
     * @param context legui context.
     */
    @Override
    public void renderComponent(C component, Context context) {
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        if (!component.isVisible() || !NvgRenderUtils.visibleInParents(component)) {
            return;
        }
        renderComponent(component, context, nanovgContext);
        for (Component child : (List<Component>) component.getChilds()) {
            RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
        }
        renderBorderWScissor(component, context, nanovgContext);
    }

    /**
     * Used to render component.
     *
     * @param component component to render.
     * @param context legui context.
     * @param nanovg nanovg context pointer.
     */
    protected abstract void renderComponent(C component, Context context, long nanovg);

}
