package org.liquidengine.legui.renderer;

import org.liquidengine.legui.component.Frame;

/**
 * Created by Aliaksandr_Shcherbin on 1/23/2017.
 */
public interface LeguiRenderer {
    /**
     * Used only once before rendering to initialize renderer.
     */
    void initialize();

    /**
     * Used every frame to render scene.
     *
     * @param frame frame to render.
     */
    void render(Frame frame);

    /**
     * Used only once after rendering to destroy renderer.
     */
    void destroy();
}
