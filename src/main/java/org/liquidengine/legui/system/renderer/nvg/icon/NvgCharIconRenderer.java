package org.liquidengine.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgIconRenderer;
import org.liquidengine.legui.util.TextUtil;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;

/**
 * Created by ShchAlexander on 13.03.2017.
 */
public class NvgCharIconRenderer<I extends CharIcon> extends NvgIconRenderer<I> {
    private NVGColor colorA;

    @Override
    public void initialize() {
        colorA = NVGColor.calloc();
    }

    @Override
    public void destroy() {
        colorA.free();
    }

    @Override
    protected void renderIcon(I icon, Component component, Context context, long nanovg) {
        if (!component.isVisible() || icon == null || icon.getFont() == null) return;
        // render simple rectangle border
        Vector2f position = component.getScreenPosition();
        Vector2f size     = component.getSize();
        Vector2f iconSize = icon.getSize();

        float x = position.x + icon.getHorizontalAlign().index * (size.x - iconSize.x) / 2f;
        float y = position.y + icon.getVerticalAlign().index * (size.y - iconSize.y) / 2f;
        float w = iconSize.x;
        float h = iconSize.y;

        drawIcon(nanovg, x, y, w, h, icon, component);
    }


    private void drawIcon(long context, float x, float y, float w, float h, CharIcon icon, Component component) {
        if (component.isFocused()) {
            renderTextLineToBounds(context, x - 1, y + 1, w, h, icon.getSize().x, icon.getFont(),
                    component.getFocusedStrokeColor(), colorA, TextUtil.cpToStr(icon.getCharCode()), icon.getHorizontalAlign(), icon.getVerticalAlign(), false);
        }

//        renderTextStateLineToBounds(nanovg, pos, size, textState);
        renderTextLineToBounds(context,
            /* X position             */ x + 0.5f,
            /* Y position             */ y,
            /* Width                  */ w,
            /* Height                 */ h,
            /* font size              */ icon.getSize().y,
            /* font name              */ icon.getFont(),
            /* text color             */ icon.getColor(),
            /* text to render         */ TextUtil.cpToStr(icon.getCharCode()),
            /* horizontal alignment   */ HorizontalAlign.CENTER,
            /* vertical alignment     */ VerticalAlign.MIDDLE,
            /* hide out of bound text */ false);
        drawRectStroke(context, x, y, w, h, ColorConstants.red(), 0,1);
//        renderTextLineToBounds(context, x, y, w, h, icon.getSize().x, icon.getFont(),
//                icon.getColor(), colorA, TextUtil.cpToStr(icon.getCharCode()), icon.getHorizontalAlign(), icon.getVerticalAlign(), false);
    }
}
