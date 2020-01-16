package org.liquidengine.legui.system.renderer.nvg;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.IconRenderer;

/**
 * Abstract renderer for Icon implementations.
 */
public abstract class NvgIconRenderer<I extends Icon> extends IconRenderer<I> {

    /**
     * This method called by base abstract icon renderer.
     *
     * @param icon icon to render.
     * @param component component - icon owner.
     * @param context context.
     */
    @Override
    public void renderIcon(I icon, Component component, Context context) {
        if (icon == null) {
            return;
        }
        long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
        renderIcon(icon, component, context, nanovgContext);
    }

    /**
     * Used to render specific Icon.
     *
     * @param icon icon to render.
     * @param component component - icon owner.
     * @param context context.
     * @param nanovg nanoVG context.
     */
    protected abstract void renderIcon(I icon, Component component, Context context, long nanovg);


    /**
     * Used to calculate on screen position of icon.
     * @param icon icon
     * @param component icon component
     * @param iconSize icon size
     * @return icon position
     */
    protected Vector2f calculateIconPosition(I icon, Component component, Vector2f iconSize) {
        Vector2f size = component.getSize();

        Vector2f p = new Vector2f(component.getAbsolutePosition());
        if (icon.getPosition() == null) {
            p.x += icon.getHorizontalAlign().index * (size.x - iconSize.x) / 2f;
            p.y += icon.getVerticalAlign().index * (size.y - iconSize.y) / 2f;
        } else {
            p.x += icon.getPosition().x;
            p.y += icon.getPosition().y;
        }
        return p;
    }
}
