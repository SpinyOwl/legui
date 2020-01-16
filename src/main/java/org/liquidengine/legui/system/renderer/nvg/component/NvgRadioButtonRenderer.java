package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

import static org.liquidengine.legui.style.util.StyleUtilities.getPadding;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgRadioButtonRenderer extends NvgDefaultComponentRenderer<RadioButton> {

    @Override
    public void renderSelf(RadioButton radioButton, Context context, long nanovg) {
        createScissor(nanovg, radioButton);
        {
            // default renderer used
            Vector2f pos = radioButton.getAbsolutePosition();
            Vector2f size = radioButton.getSize();

            // Draw background rectangle
            renderBackground(radioButton, context, nanovg);

            TextState textState = radioButton.getTextState();
            Icon icon = radioButton.isChecked() ? radioButton.getIconChecked() : radioButton.getIconUnchecked();
            Vector4f padding = getPadding(radioButton, radioButton.getStyle());
            Vector4f pad = new Vector4f(padding.w, padding.x, padding.y, padding.z);

            // renderNvg text
            float iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * icon.getSize().x;

            Vector2f textRectPos = new Vector2f(pos.x + iconWidthForUse, pos.y + pad.y);
            Vector2f textRectSize = new Vector2f(size.x - iconWidthForUse - pad.z, size.y - (pad.y + pad.w));

            NvgText.drawTextLineToRect(nanovg, textState, textRectPos, textRectSize, true);
            renderIcon(icon, radioButton, context);
        }
        resetScissor(nanovg);
    }
}
