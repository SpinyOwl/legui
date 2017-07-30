package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.util.NVGUtils.rgba;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgLabelRenderer extends NvgComponentRenderer<Label> {
    private NVGColor colorA = NVGColor.create();

    @Override
    public void renderComponent(Label label, Context context, long nanovg) {
        createScissor(nanovg, label);
        {
            Vector2f pos = label.getScreenPosition();
            Vector2f size = label.getSize();
            Vector4f backgroundColor = new Vector4f(label.getBackgroundColor());

            /*Draw background rectangle*/
            {
                nvgBeginPath(nanovg);
                nvgRoundedRect(nanovg, pos.x, pos.y, size.x, size.y, 0);
                nvgFillColor(nanovg, rgba(backgroundColor, colorA));
                nvgFill(nanovg);
            }

            // draw text into box
            TextState textState = label.getTextState();
            renderTextStateLineToBounds(nanovg, pos, size, textState, false);
            renderBorder(label, context);
        }
        resetScissor(nanovg);
    }
}
