package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.border.Border;
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

    private NVGColor colorA = NVGColor.calloc();

    @Override
    public void render(Component component, LeguiContext context, long nvgContext) {
        createScissor(nvgContext, component);
        {

            Vector2f pos = calculatePosition(component);
            Vector2f size = component.getSize();
            Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());

            /*Draw background rectangle*/
            {
                nvgBeginPath(nvgContext);
                nvgRoundedRect(nvgContext, pos.x, pos.y, size.x, size.y, 0);
                nvgFillColor(nvgContext, rgba(backgroundColor, colorA));
                nvgFill(nvgContext);
            }

            // draw text into box
            TextState textState = ((Label) component).getTextState();
            renderTextStateToBounds(nvgContext, pos, size, textState, false);

            Border border = component.getBorder();
            if (border != null) {
                border.render(context);
            }
        }
        resetScissor(nvgContext);
    }
}
