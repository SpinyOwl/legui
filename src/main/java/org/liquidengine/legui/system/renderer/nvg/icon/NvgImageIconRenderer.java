package org.liquidengine.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.ImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgIconRenderer;
import org.lwjgl.nanovg.NVGPaint;

import java.util.HashMap;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderImage;

/**
 * Created by ShchAlexander on 13.03.2017.
 */
public class NvgImageIconRenderer<I extends ImageIcon> extends NvgIconRenderer<I> {
    private NVGPaint imagePaint;

    @Override
    public void initialize() {
        imagePaint = NVGPaint.calloc();
    }

    @Override
    public void destroy() {
        imagePaint.free();
    }

    @Override
    protected void renderIcon(I icon, Component component, Context context, long nanovg) {
        if (!component.isVisible() || icon == null || icon.getImage() == null) return;
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

        HashMap<String, Object> p = new HashMap<>();
        p.put(ImageRenderer.C_RADIUS, component.getCornerRadius());
        renderImage(icon.getImage(), new Vector2f(x, y), iconSize, p, context);
    }
}
