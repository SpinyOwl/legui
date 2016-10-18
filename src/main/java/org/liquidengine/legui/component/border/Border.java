package org.liquidengine.legui.component.border;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.LeguiBorderRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class Border implements Serializable {
    protected LeguiBorderRenderer renderer;
    protected boolean enabled = true;

    public Border() {
        renderer = LeguiRendererProvider.getProvider().getRenderer(this);
    }

    public void render(LeguiContext context, Component component) {
        renderer.render(this, context, component);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
