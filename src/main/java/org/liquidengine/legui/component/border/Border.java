package org.liquidengine.legui.component.border;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.LeguiComponentRenderer;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class Border {
    private LeguiComponentRenderer renderer;

    public void render(Component gui, LeguiContext context) {
        renderer.render(gui, context);
    }

}
