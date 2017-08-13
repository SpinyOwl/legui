package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NVGUtils.rgba;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgRadioButtonRenderer extends NvgComponentRenderer<RadioButton> {

    @Override
    public void renderComponent(RadioButton radioButton, Context context, long nanovg) {
        createScissor(nanovg, radioButton);
        {
            // default renderer used
            Vector2f pos = radioButton.getScreenPosition();
            Vector2f size = radioButton.getSize();

            float px = pos.x;
            float py = pos.y;
            float sw = size.x;
            float sh = size.y;
                /*Draw background rectangle*/
            {
                NVGColor colorA = NVGColor.calloc();
                nvgBeginPath(nanovg);
                nvgRoundedRect(nanovg, px, py, sw, sh, 0);
                nvgFillColor(nanovg, rgba(radioButton.getBackgroundColor(), colorA));
                nvgFill(nanovg);
                colorA.free();
            }

            TextState textState = radioButton.getTextState();
            Icon icon = radioButton.isChecked() ? radioButton.getIconChecked() : radioButton.getIconUnchecked();
            float iconWid = icon.getSize().x;

            Vector4f pad = textState.getPadding();

            // renderNvg text
            float iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * iconWid;

            float h = sh - (pad.y + pad.w);
            float y = py + pad.y;
            float x = px + iconWidthForUse;
            float w = sw - iconWidthForUse - pad.z;


            renderTextStateLineToBounds(nanovg, new Vector2f(x, y), new Vector2f(w, h), textState);

            renderIcon(icon, radioButton, context);

            renderBorder(radioButton, context);
        }
        resetScissor(nanovg);

    }
}
