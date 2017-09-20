package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.renderTextStateLineToBounds;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgRadioButtonRenderer extends NvgComponentRenderer<RadioButton> {

    @Override
    public void renderComponent(RadioButton radioButton, Context context, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, radioButton, () -> {
            // default renderer used
            Vector2f pos = radioButton.getScreenPosition();
            Vector2f size = radioButton.getSize();

            // Draw background rectangle
            NvgShapes.drawRect(nanovg, pos, size, radioButton.getBackgroundColor(), radioButton.getCornerRadius());

            TextState textState = radioButton.getTextState();
            Icon icon = radioButton.isChecked() ? radioButton.getIconChecked() : radioButton.getIconUnchecked();

            Vector4f pad = textState.getPadding();

            // renderNvg text
            float iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * icon.getSize().x;

            Vector2f textRectPos = new Vector2f(pos.x + iconWidthForUse, pos.y + pad.y);
            Vector2f textRectSize = new Vector2f(size.x - iconWidthForUse - pad.z, size.y - (pad.y + pad.w));

            renderTextStateLineToBounds(nanovg, textRectPos, textRectSize, textState);
            renderIcon(icon, radioButton, context);
            renderBorder(radioButton, context);
        });

    }
}
