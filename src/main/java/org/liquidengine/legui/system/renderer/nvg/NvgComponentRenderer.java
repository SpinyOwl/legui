package org.liquidengine.legui.system.renderer.nvg;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.ComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.border.NvgSimpleLineBorderRenderer;
import org.liquidengine.legui.util.Utilites;

/**
 * The base NanoVG component renderer.
 *
 * @param <C> component type.
 */
public abstract class NvgComponentRenderer<C extends Component> extends ComponentRenderer<C> {

    private NvgSimpleLineBorderRenderer debugBorderRenderer = new NvgSimpleLineBorderRenderer();
    private SimpleLineBorder debugBorder = new SimpleLineBorder(ColorConstants.red(), 1);
    private SimpleLineBorder debugFocusBorder = new SimpleLineBorder(ColorConstants.blue(), 2);

    @Override
    public void initialize() {
        debugBorderRenderer.initialize();
    }

    /**
     * Used to render component.
     *
     * @param component component to render.
     * @param context legui context.
     */
    @Override
    public void renderComponent(C component, Context context) {
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        if (!component.isVisible() || !Utilites.visibleInParents(component)) {
            return;
        }
        renderComponent(component, context, nanovgContext);
        if (context.isDebugEnabled()) {
            if (component.isFocused()) {
                debugBorderRenderer.renderBorder(debugFocusBorder, component, context);
            } else {
                debugBorderRenderer.renderBorder(debugBorder, component, context);
            }
        }
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
