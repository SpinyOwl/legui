package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.ContainerHolder;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.ColorConstants;
import org.lwjgl.nanovg.NVGColor;

import java.util.List;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissorByParent;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.liquidengine.legui.util.Util.negativeColor;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public class NvgWidgetRenderer extends NvgLeguiComponentRenderer {
    private NVGColor colorA = NVGColor.calloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        Widget widget = (Widget) component;
        Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());
        float titleHeight = widget.getTitleHeight();
        Vector2f position = calculatePosition(component);
        float y = position.y + titleHeight - 0.5f;

        // draw title
        createScissor(context, component);
        {
            Vector2f size = new Vector2f(widget.getSize().x, titleHeight);
            nvgIntersectScissor(context, position.x, position.y, size.x, size.y);
            Vector4f titleBackgroundColor = widget.getTitleBackgroundColor();

            nvgBeginPath(context);
            nvgRoundedRect(context, position.x, position.y, size.x, size.y, component.getCornerRadius());
            nvgFillColor(context, rgba(titleBackgroundColor, colorA));
            nvgFill(context);

            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, 1);
            nvgStrokeColor(context, rgba(negativeColor(titleBackgroundColor), colorA));
            nvgBeginPath(context);
            nvgMoveTo(context, position.x, y);
            nvgLineTo(context, position.x + size.x, y);
            nvgStroke(context);
        }
        resetScissor(context);

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
        if (component instanceof ContainerHolder) {
            ComponentContainer container = ((ContainerHolder) component).getContainer();
            List<Component> components = container.getComponents();
            components.stream().filter(Component::isVisible).forEach(child -> child.render(leguiContext));
        }
        createScissor(context, component);
        {
            Border border = widget.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }
        }
        resetScissor(context);
        createScissorByParent(context, component);
        {
            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, 1);
            nvgStrokeColor(context, rgba(negativeColor(backgroundColor), colorA));
            nvgBeginPath(context);
            nvgMoveTo(context, position.x, y + 0.5f);
            nvgLineTo(context, position.x + component.getSize().x, y + 0.5f);
            nvgStroke(context);
        }
        resetScissor(context);

    }
}
