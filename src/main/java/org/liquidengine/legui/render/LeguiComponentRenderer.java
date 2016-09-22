package org.liquidengine.legui.render;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public interface LeguiComponentRenderer {

    /**
     * Render Gui and fit it to target width and height
     *
     * @param component gui component to render
     */
    void render(Component component, LeguiContext context);

}
