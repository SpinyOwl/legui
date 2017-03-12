package org.liquidengine.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgIconRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgImageReferenceManager;
import org.lwjgl.nanovg.NVGPaint;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;
import static org.lwjgl.nanovg.NanoVG.*;

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
        NvgImageReferenceManager manager = (NvgImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
        if (!component.isVisible() || icon == null || icon.getImage() == null) return;
        // render simple rectangle border
        Vector2f position = component.getScreenPosition();
        Vector2f size     = component.getSize();
        Vector2f iconSize = icon.getSize();

        float x = position.x + icon.getHorizontalAlign().index * (size.x - iconSize.x) / 2f;
        float y = position.y + icon.getVerticalAlign().index * (size.y - iconSize.y) / 2f;
        float w = iconSize.x;
        float h = iconSize.y;

        drawImage(nanovg, x, y, w, h, manager, icon.getImage(), component);
    }


    private void drawImage(long context, float x, float y, float w, float h, NvgImageReferenceManager manager, Image image, Component component) {
        int imageRef = manager.getImageReference(image, context);

        nvgBeginPath(context);
        nvgImagePattern(context, x, y, w, h, 0, imageRef, 1, imagePaint);
        nvgRoundedRect(context, x, y, w, h, component.getCornerRadius());
        nvgFillPaint(context, imagePaint);
        nvgFill(context);
    }
}
