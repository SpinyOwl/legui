package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

import static org.liquidengine.legui.style.util.StyleUtilities.getPadding;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgCheckBoxRenderer extends NvgDefaultComponentRenderer<CheckBox> {

    @Override
    public void renderSelf(CheckBox checkBox, Context context, long nanovg) {
        createScissor(nanovg, checkBox);
        {
            Style style = checkBox.getStyle();
            Vector2f pos = checkBox.getAbsolutePosition();
            Vector2f size = checkBox.getSize();

            /*Draw background rectangle*/
            renderBackground(checkBox, context, nanovg);

            TextState textState = checkBox.getTextState();
            Icon icon = checkBox.isChecked() ? checkBox.getIconChecked() : checkBox.getIconUnchecked();
            float iconWid = icon.getSize().x;

            Vector4f padding = getPadding(checkBox, style);

            float iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * iconWid;

            float h = size.y - (padding.y + padding.w);
            float y = pos.y + padding.y;
            float x = pos.x + iconWidthForUse + padding.x;
            float w = size.x - iconWidthForUse - padding.z - padding.x;

            Vector2fc size1 = new Vector2f(w, h);
            Vector4f rect = new Vector4f(new Vector2f(x, y), size1.x(), size1.y());
            NvgText.drawTextLineToRect(nanovg, (Vector4fc) rect, true, style.getHorizontalAlign(), style.getVerticalAlign(),
                    getFontSize(checkBox), getFont(checkBox), textState.getText(), style.getTextColor());

            renderIcon(icon, checkBox, context);
        }
        resetScissor(nanovg);
    }
}
