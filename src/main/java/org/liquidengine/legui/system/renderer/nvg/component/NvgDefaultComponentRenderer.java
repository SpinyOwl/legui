package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Default component renderer.
 *
 * @param <C> component type.
 */
public class NvgDefaultComponentRenderer<C extends Component> extends NvgComponentRenderer<C> {

    /**
     * Used to render component.
     *
     * @param component component to render.
     * @param context legui context.
     * @param nanovg nanovg context pointer.
     */
    @Override
    protected void renderComponent(C component, Context context, long nanovg) {
        if (component.isVisible() && component.getSize().lengthSquared() > 0.01) {
            renderSelf(component, context, nanovg);
            renderChildComponents(component, context, nanovg);
            renderBorder(component, context, nanovg);
        }
    }

    /**
     * Used to render component without childComponents.
     *
     * @param component component to render.
     * @param context context.
     * @param nanovg nanovg context pointer.
     */
    protected void renderSelf(C component, Context context, long nanovg) {
        createScissor(nanovg, component);
        {
            Style style = component.getStyle();
            Vector4f radius = new Vector4f(style.getTopLeftCornerRadius(), style.getTopRightCornerRadius(), style.getBottomRightCornerRadius(),
                style.getBottomLeftCornerRadius());
            NvgShapes.drawRect(nanovg, component.getAbsolutePosition(), component.getSize(), style.getBackground().getColor(), radius);
        }
        resetScissor(nanovg);
    }

    /**
     * Used to render component childComponents.
     *
     * @param component component to render.
     * @param context context.
     * @param nanovg nanovg context pointer.
     */
    protected void renderChildComponents(C component, Context context, long nanovg) {
        for (Component child : component.getChildComponents()) {
            RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
        }
    }

    /**
     * Used to render component border.
     *
     * @param component component to render.
     * @param context context.
     * @param nanovg nanovg context pointer.
     */
    protected void renderBorder(C component, Context context, long nanovg) {
        renderBorderWScissor(component, context, nanovg);
    }
}
