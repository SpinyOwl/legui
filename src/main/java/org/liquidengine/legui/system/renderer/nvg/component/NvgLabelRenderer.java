package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.renderTextStateLineToBounds;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgLabelRenderer extends NvgComponentRenderer<Label> {

    @Override
    public void renderComponent(Label label, Context context, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, label, () -> {
            Vector2f pos = label.getAbsolutePosition();
            Vector2f size = label.getSize();
            Vector4f backgroundColor = new Vector4f(label.getBackgroundColor());

            /*Draw background rectangle*/
            NvgShapes.drawRect(nanovg, pos, size, backgroundColor, label.getCornerRadius());

            // draw text into box
            TextState textState = label.getTextState();
            renderTextStateLineToBounds(nanovg, pos, size, textState, false);
            renderBorder(label, context);
        });
    }
}
