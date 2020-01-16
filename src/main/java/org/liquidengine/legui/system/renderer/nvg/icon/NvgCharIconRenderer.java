package org.liquidengine.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgIconRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;
import org.liquidengine.legui.util.TextUtil;

/**
 * Created by ShchAlexander on 13.03.2017.
 */
public class NvgCharIconRenderer<I extends CharIcon> extends NvgIconRenderer<I> {

    @Override
    protected void renderIcon(I icon, Component component, Context context, long nanovg) {
        if (!component.isVisible() || icon == null || icon.getFont() == null) {
            return;
        }
        // render simple rectangle border
        Vector2f iconSize = icon.getSize();
        Vector2f p        = calculateIconPosition(icon, component, iconSize);
        float    w        = iconSize.x;
        float    h        = iconSize.y;

        drawIcon(nanovg, p.x, p.y, w, h, icon, component);
    }


    private void drawIcon(long context, float x, float y, float w, float h, CharIcon icon, Component component) {
        if (component.isFocused()) {
            Vector4f focusedStrokeColor = component.getStyle().getFocusedStrokeColor();
            if (focusedStrokeColor != null) {
                NvgText.drawTextLineToRect(context, new Vector4f(x - 0.5f, y + 1, w, h), false, icon.getHorizontalAlign(), icon.getVerticalAlign(),
                                           icon.getSize().y, icon.getFont(), TextUtil.cpToStr(icon.getCharCode()), focusedStrokeColor);
            }
        }

        NvgText.drawTextLineToRect(context, new Vector4f(x + 0.5f, y, w, h), false, icon.getHorizontalAlign(), icon.getVerticalAlign(),
                                   icon.getSize().y, icon.getFont(), TextUtil.cpToStr(icon.getCharCode()), icon.getColor());
    }
}
