package org.liquidengine.legui.render;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.context.LeguiContext;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class LeguiRenderer {
    protected LeguiContext context;

    public LeguiRenderer(LeguiContext context) {
        this.context = context;
    }

    public abstract void preRender(Frame component);

    public abstract void postRender(Frame component);

    public void render(Frame component) {
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
