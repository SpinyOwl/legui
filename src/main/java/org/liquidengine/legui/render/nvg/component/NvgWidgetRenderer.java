package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.liquidengine.legui.util.Util.*;
import static org.liquidengine.legui.util.ColorUtil.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public class NvgWidgetRenderer extends NvgLeguiComponentRenderer {
    private static final String CLOSE_ICON = cpToStr(0xE5CD);
    private NVGColor colorA = NVGColor.calloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        Widget widget = (Widget) component;
        Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());
        float titleHeight = widget.getTitleHeight();

        Vector2f position = calculatePosition(component);
        Vector2f widgetSize = widget.getSize();

        float y = position.y + titleHeight - 0.5f;
        float rightX = position.x + widgetSize.x;

        // draw title
        if (widget.isTitleEnabled()) {
            createScissor(context, component);
            {
                Vector2f size = new Vector2f(widgetSize.x - (widget.isCloseable() ? titleHeight : 0), titleHeight);
                Vector4f titleBackgroundColor = widget.getTitleBackgroundColor();

                nvgBeginPath(context);
                nvgRoundedRect(context, position.x, position.y, size.x, size.y, component.getCornerRadius());
                nvgFillColor(context, rgba(titleBackgroundColor, colorA));
                nvgFill(context);

                renderTextStateToBounds(context, position, size, widget.getTitleTextState(), true);

                if (widget.isCloseable()) {
                    Vector4f closeButtonColor = widget.getCloseButtonColor();

                    nvgBeginPath(context);
                    float xp = rightX - titleHeight;
                    nvgRoundedRect(context, xp, position.y, titleHeight, titleHeight, component.getCornerRadius());
                    nvgFillColor(context, rgba(closeButtonColor, colorA));
                    nvgFill(context);
                    Vector4f closeColor = blackOrWhite(closeButtonColor);

                    renderTextLineToBounds(context, xp + titleHeight * 0.2f, position.y, 0.8f * titleHeight, titleHeight, titleHeight, FontRegister
                                    .MATERIAL_ICONS_REGULAR,
                            closeColor,
                            CLOSE_ICON, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
                }

                //draw intersector
                nvgLineCap(context, NVG_ROUND);
                nvgLineJoin(context, NVG_ROUND);
                nvgStrokeWidth(context, 1);
                nvgStrokeColor(context, rgba(blackOrWhite(titleBackgroundColor), colorA));
                nvgBeginPath(context);
                nvgMoveTo(context, position.x, y);
                nvgLineTo(context, rightX, y);
                nvgStroke(context);
            }
            resetScissor(context);
        }

        //draw container
        createScissorByParent(context, component);
        {
            nvgSave(context);

            Vector2f pos = calculatePosition(component).add(widget.getContainerPosition());
            Vector2f size = widget.getContainerSize();

            // rectangle
            nvgBeginPath(context);
            nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, component.getCornerRadius());
            nvgFillColor(context, rgba(backgroundColor, colorA));
            nvgFill(context);

            Border border = component.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }

        }
        resetScissor(context);

        // draw child components
        ComponentContainer container = widget.getContainer();
        container.getComponents().stream().filter(Component::isVisible).forEach(child -> child.render(leguiContext));

        //draw intersector
        createScissorByParent(context, component);
        {
            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, 1);
            nvgStrokeColor(context, rgba(blackOrWhite(backgroundColor), colorA));
            nvgBeginPath(context);
            nvgMoveTo(context, position.x, y + 0.5f);
            nvgLineTo(context, rightX, y + 0.5f);
            nvgStroke(context);
        }
        resetScissor(context);
        //draw border
        createScissor(context, component);
        {
            Border border = widget.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }
        }
        resetScissor(context);

    }
}
