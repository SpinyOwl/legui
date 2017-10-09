package org.liquidengine.legui.system.renderer.nvg.component;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgDefaultComponentRenderer extends NvgComponentRenderer {

    @Override
    protected void renderComponent(Component component, Context context, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, component, () ->
            NvgShapes.drawRect(nanovg, component.getAbsolutePosition(), component.getSize(), component.getBackgroundColor(), component.getCornerRadius())
        );
    }
}
