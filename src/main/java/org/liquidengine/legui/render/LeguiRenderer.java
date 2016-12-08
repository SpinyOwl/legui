package org.liquidengine.legui.render;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class LeguiRenderer {
    protected LeguiContext context;

    public LeguiRenderer(LeguiContext context) {
        this.context = context;
    }

    public abstract void preRender(Component component);

    public abstract void postRender(Component component);

    public void render(Component component) {
        preRender(component);
        try {
            component.render(context);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        postRender(component);
    }

    public abstract void initialize();

    public abstract void destroy();
}
