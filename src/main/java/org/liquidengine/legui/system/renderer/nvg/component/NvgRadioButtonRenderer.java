package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

import static org.liquidengine.legui.style.util.StyleUtilities.getPadding;
import static org.liquidengine.legui.style.util.StyleUtilities.getStyle;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgRadioButtonRenderer extends NvgDefaultComponentRenderer<RadioButton> {

    @Override
    public void renderSelf(RadioButton radioButton, Context context, long nanovg) {
        createScissor(nanovg, radioButton);
        {
            // default renderer used
            Style style = radioButton.getStyle();
            Vector2f pos = radioButton.getAbsolutePosition();
            Vector2f size = radioButton.getSize();

            // Draw background rectangle
            renderBackground(radioButton, context, nanovg);

            TextState textState = radioButton.getTextState();
            Icon icon = radioButton.isChecked() ? radioButton.getIconChecked() : radioButton.getIconUnchecked();
            Vector4f padding = getPadding(radioButton, style);
            Vector4f pad = new Vector4f(padding.w, padding.x, padding.y, padding.z);

            // renderNvg text
            float iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * icon.getSize().x;

            Vector2f textRectPos = new Vector2f(pos.x + iconWidthForUse, pos.y + pad.y);
            Vector2f textRectSize = new Vector2f(size.x - iconWidthForUse - pad.z, size.y - (pad.y + pad.w));

            Vector4f rect = new Vector4f(textRectPos, ((Vector2fc) textRectSize).x(), ((Vector2fc) textRectSize).y());
            NvgText.drawTextLineToRect(nanovg, rect, true,
                    getStyle(radioButton, Style::getHorizontalAlign, HorizontalAlign.LEFT),
                    getStyle(radioButton, Style::getVerticalAlign, VerticalAlign.MIDDLE),
                    getStyle(radioButton, Style::getFontSize, 16F),
                    getStyle(radioButton, Style::getFont, FontRegistry.DEFAULT),
                    textState.getText(),
                    getStyle(radioButton, Style::getTextColor));
            renderIcon(icon, radioButton, context);
        }
        resetScissor(nanovg);
    }
}
