package org.liquidengine.legui.system.renderer.nvg.icon;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.renderTextLineToBounds;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgIconRenderer;
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
        Vector2f position = component.getScreenPosition();
        Vector2f size = component.getSize();
        Vector2f iconSize = icon.getSize();

        float x = position.x;
        float y = position.y;
        if (icon.getPosition() == null) {
            x += icon.getHorizontalAlign().index * (size.x - iconSize.x) / 2f;
            y += icon.getVerticalAlign().index * (size.y - iconSize.y) / 2f;
        } else {
            x += icon.getPosition().x;
            y += icon.getPosition().y;
        }
        float w = iconSize.x;
        float h = iconSize.y;

        drawIcon(nanovg, x, y, w, h, icon, component);
    }


    private void drawIcon(long context, float x, float y, float w, float h, CharIcon icon, Component component) {
        if (component.isFocused()) {
            renderTextLineToBounds(context, x - 0.5f, y + 1, w, h, icon.getSize().y, icon.getFont(),
                component.getFocusedStrokeColor(), TextUtil.cpToStr(icon.getCharCode()), icon.getHorizontalAlign(), icon.getVerticalAlign(), false);
        }

        renderTextLineToBounds(context, x + 0.5f, y, w, h, icon.getSize().y, icon.getFont(),
            icon.getColor(), TextUtil.cpToStr(icon.getCharCode()), icon.getHorizontalAlign(), icon.getVerticalAlign(), false);
    }
}
