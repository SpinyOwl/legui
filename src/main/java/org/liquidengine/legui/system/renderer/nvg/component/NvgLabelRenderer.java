package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgLabelRenderer extends NvgDefaultComponentRenderer<Label> {

    @Override
    public void renderSelf(Label label, Context context, long nanovg) {
        createScissor(nanovg, label);
        {
            Vector2f pos = label.getAbsolutePosition();
            Vector2f size = label.getSize();
            Vector4f backgroundColor = new Vector4f(label.getStyle().getBackground().getColor());

            /*Draw background rectangle*/
            NvgShapes.drawRect(nanovg, pos, size, backgroundColor, label.getStyle().getCornerRadius());

            // draw text into box
            TextState textState = label.getTextState();
            NvgText.drawTextLineToRect(nanovg, textState, pos, size, false);
        }
        resetScissor(nanovg);
    }
}
