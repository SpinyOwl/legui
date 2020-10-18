package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.style.util.StyleUtilities.getStyle;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
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
            renderBackground(component, context, nanovg);
        }
        resetScissor(nanovg);
    }

    protected void renderBackground(C component, Context context, long nanovg) {
        Icon bgIcon = getStyle(component, s -> s.getBackground().getIcon());
        Vector4f bgColor = getStyle(component, s -> s.getBackground().getColor());
        Vector4f cornerRadius = getBorderRadius(component);

        NvgRenderUtils.renderShadow(nanovg, component);

        nvgSave(nanovg);
        NvgShapes.drawRect(nanovg, component.getAbsolutePosition(), component.getSize(), bgColor, cornerRadius);
        if (bgIcon != null) {
            renderIcon(bgIcon, component, context);
        }
        nvgRestore(nanovg);
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
