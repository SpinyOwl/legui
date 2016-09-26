package org.liquidengine.legui.component.border;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.LeguiBorderRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class Border {
    protected LeguiBorderRenderer renderer;
    protected Component component;
    protected boolean enabled = true;

    public Border(Component component) {
        this.component = component;
        renderer = LeguiRendererProvider.getProvider().getRenderer(this);
    }

    public void render(LeguiContext context) {
        renderer.render(this, context);
    }

    public Component getComponent() {
        return component;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
