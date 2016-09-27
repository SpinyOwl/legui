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
import org.lwjgl.nanovg.NVGColor;

import java.util.List;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public class NvgWidgetRenderer extends NvgLeguiComponentRenderer {
    private NVGColor colorA = NVGColor.calloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        Widget widget = (Widget) component;
        createScissor(context, component);
        {
            nvgSave(context);

            Vector2f pos = calculatePosition(component);
            Vector2f size = component.getSize();
            Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());

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

        if (component instanceof ContainerHolder) {
            ComponentContainer container = ((ContainerHolder) component).getContainer();
            List<Component> components = container.getComponents();
            components.stream().filter(Component::isVisible).forEach(child -> child.render(leguiContext));
        }
    }
}
