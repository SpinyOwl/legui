package org.liquidengine.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.LeguiImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgLeguiIconRenderer;
import org.lwjgl.nanovg.NVGPaint;

import java.util.HashMap;

import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.renderImage;

/**
 * Created by ShchAlexander on 13.03.2017.
 */
public class NvgImageLeguiIconRenderer<I extends ImageIcon> extends NvgLeguiIconRenderer<I> {
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
    protected void renderIcon(I icon, Component component, LeguiContext context, long nanovg) {
        if (!component.isVisible() || icon == null || icon.getImage() == null) return;
        // render simple rectangle border
        Vector2f position = component.getScreenPosition();
        Vector2f size     = component.getSize();
        Vector2f iconSize = icon.getSize();

        float x = position.x + icon.getHorizontalAlign().index * (size.x - iconSize.x) / 2f;
        float y = position.y + icon.getVerticalAlign().index * (size.y - iconSize.y) / 2f;

        HashMap<String, Object> p = new HashMap<>();
        p.put(LeguiImageRenderer.C_RADIUS, component.getCornerRadius());
        renderImage(icon.getImage(), new Vector2f(x, y), iconSize, p, context);
    }
}
