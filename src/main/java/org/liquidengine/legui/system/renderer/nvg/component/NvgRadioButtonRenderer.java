package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.util.TextUtil;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgRadioButtonRenderer extends NvgComponentRenderer<RadioButton> {
    private NVGColor colorA = NVGColor.create();

    @Override
    public void renderComponent(RadioButton component, Context context, long nanovg) {
        createScissor(nanovg, component);
        {
            // default renderer used
            Vector2f pos  = component.getScreenPosition();
            Vector2f size = component.getSize();

            TextState textState = component.getTextState();
            float     fontSize  = textState.getFontSize();
            Vector4f  textColor = textState.getTextColor();
            Vector4f  pad       = textState.getPadding();

            // renderNvg text
            float y1 = pos.y + pad.y;
            float h1 = size.y - (pad.y + pad.w);
            float x1 = pos.x + fontSize;
            float w1 = size.x - fontSize - pad.z;
            renderTextStateLineToBounds(nanovg, new Vector2f(x1, y1), new Vector2f(w1, h1), textState);
            renderIcon(component, pos.x, fontSize, textColor, fontSize, y1, h1, nanovg);

            renderBorderWScissor(component, context, nanovg);
        }
        resetScissor(nanovg);
    }


    private void renderIcon(RadioButton agui, float x, float fontSize, Vector4f textColor, float iconWid, float y1, float h1, long context) {
        // renderNvg check symbol
        int    iconChar = agui.isChecked() ? agui.getIconChecked() : agui.getIconUnchecked();
        String icon     = TextUtil.cpToStr(iconChar);

        ImageView image = agui.isChecked() ? agui.getIconImageChecked() : agui.getIconImageUnchecked();

        if (image == null) {
            if (agui.isFocused()) {
                renderTextLineToBounds(context, x - 1, y1 + 1, iconWid, h1, fontSize, agui.getIconFont(),
                        agui.getFocusedStrokeColor(), colorA, icon, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
            }
            renderTextLineToBounds(context, x, y1, iconWid, h1, fontSize, FontRegister.MATERIAL_ICONS_REGULAR,
                    textColor, colorA, icon, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
        } else {

        }
    }
}
