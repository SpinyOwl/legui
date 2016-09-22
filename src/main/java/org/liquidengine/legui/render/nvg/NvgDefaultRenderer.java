package org.liquidengine.legui.render.nvg;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.util.ColorConstants;
import org.lwjgl.nanovg.NVGColor;

import java.util.List;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public class NvgDefaultRenderer extends NvgLeguiComponentRenderer {
    private static final Vector4f BLACK = ColorConstants.black();
    private NVGColor colorA = NVGColor.malloc();

    @Override
    public void render(Component gui, LeguiContext leguiContext, long context) {
        nvgSave(context);

        Vector2f pos = calculatePosition(gui);
        Vector2f size = gui.getSize();
        Vector4f backgroundColor = new Vector4f(gui.getBackgroundColor());

        createScissor(context, gui);
        // rectangle
        nvgBeginPath(context);
        nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, 0);
        nvgFillColor(context, rgba(backgroundColor, colorA));
        nvgFill(context);

        Border border = gui.getBorder();
        if (border != null) {
            border.render(leguiContext);
        }

        resetScissor(context);

        if (gui instanceof ComponentContainer) {
            ComponentContainer container = (ComponentContainer) gui;
            List<Component> all = container.getComponents();
            all.stream().filter(Component::isVisible).forEach(child -> child.render(leguiContext));
        }
    }

}
