package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgCheckBoxRenderer extends NvgDefaultComponentRenderer<CheckBox> {

    @Override
    public void renderSelf(CheckBox checkBox, Context context, long nanovg) {
        createScissor(nanovg, checkBox);
        {
            Vector2f pos = checkBox.getAbsolutePosition();
            Vector2f size = checkBox.getSize();

            float px = pos.x;
            float py = pos.y;
            float sw = size.x;
            float sh = size.y;
            /*Draw background rectangle*/
            renderBackground(checkBox, context, nanovg);

            TextState textState = checkBox.getTextState();
            Icon icon = checkBox.isChecked() ? checkBox.getIconChecked() : checkBox.getIconUnchecked();
            float iconWid = icon.getSize().x;

            Vector4f pad = textState.getPadding();

            float iconWidthForUse = (icon.getHorizontalAlign().index == 0 ? 1 : 0) * iconWid;

            float h = sh - (pad.y + pad.w);
            float y = py + pad.y;
            float x = px + iconWidthForUse;
            float w = sw - iconWidthForUse - pad.z;

            NvgText.drawTextLineToRect(nanovg, textState, new Vector2f(x, y), new Vector2f(w, h), true);

            renderIcon(icon, checkBox, context);
        }
        resetScissor(nanovg);
    }
}
