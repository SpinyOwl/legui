package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class NvgLabelRenderer extends NvgLeguiComponentRenderer {

    private NVGColor colorA = NVGColor.create();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {

            Vector2f pos = calculatePosition(component);
            Vector2f size = component.getSize();
            Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());

            /*Draw background rectangle*/
            {
                nvgBeginPath(context);
                nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, 0);
                nvgFillColor(context, rgba(backgroundColor, colorA));
                nvgFill(context);
            }

            // draw text into box
            TextState textState = ((Label) component).getTextState();
            renderTextStateLineToBounds(context, pos, size, textState, false);

            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }
}
