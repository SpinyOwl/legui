package org.liquidengine.legui.render;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public interface LeguiComponentRenderer extends Serializable {

    /**
     * Initialize renderer
     */
    void initialize();

    /**
     * Render Gui and fit it to target width and height
     *
     * @param component gui frame to render
     */
    void render(Component component, LeguiContext context);

    /**
     * Destroy renderer
     */
    void destroy();

}
