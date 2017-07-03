package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Border renderer base.
 */
public abstract class LeguiBorderRenderer<B extends Border> {
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public void initialize() {
        // should be overrided
    }

    public void render(B border, Component component, LeguiContext context) {
        if (!initialized.getAndSet(true)) {
            initialize();
        } else {
            renderBorder(border, component, context);
        }
    }

    public abstract void renderBorder(B border, Component component, LeguiContext context);

    public void destroy() {
        // should be overrided
    }
}
