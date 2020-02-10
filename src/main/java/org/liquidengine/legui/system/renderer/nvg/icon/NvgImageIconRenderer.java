package org.liquidengine.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.ImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgIconRenderer;

import java.util.HashMap;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderImage;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;

/**
 * Created by ShchAlexander on 13.03.2017.
 */
public class NvgImageIconRenderer<I extends ImageIcon> extends NvgIconRenderer<I> {

    @Override
    protected void renderIcon(I icon, Component component, Context context, long nanovg) {
        if (!component.isVisible() || icon == null || icon.getImage() == null) {
            return;
        }
        // render simple rectangle border
        Vector2f iconSize = icon.getSize();
        Vector2f p        = calculateIconPosition(icon, component, iconSize);

        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ImageRenderer.C_RADIUS, getBorderRadius(component));
        renderImage(icon.getImage(), new Vector2f(p.x, p.y), iconSize, prop, context);
    }
}
