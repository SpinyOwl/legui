package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.TextState;
import org.liquidengine.legui.component.align.HorizontalAlign;
import org.liquidengine.legui.component.align.VerticalAlign;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
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

            TextState textState = ((Label) component).getTextState();
            Vector4f pad = new Vector4f(textState.getPadding());

            // draw text into box

            float x = pos.x;
            float y = pos.y;
            float w = size.x;
            float h = size.y;
            float x1 = x + pad.x;
            float y1 = y + pad.y;
            float w1 = w - pad.x - pad.z;
            float h1 = h - pad.y - pad.w;

            float fontSize = textState.getFontSize();
            String font = textState.getFont() == null ? FontRegister.DEFAULT : textState.getFont();
            Vector4f textColor = textState.getTextColor();
            String text = textState.getText();
            HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
            VerticalAlign verticalAlign = textState.getVerticalAlign();
            boolean hide = false;
            NvgRenderUtils.renderTextLineToBounds(nvgContext, x1, y1, w1, h1, fontSize, font, textColor,
                    colorA, text, horizontalAlign, verticalAlign, hide);

            Border border = component.getBorder();
            if(border!=null){
                border.render(context);
            }
        }
        resetScissor(nvgContext);
    }
}
