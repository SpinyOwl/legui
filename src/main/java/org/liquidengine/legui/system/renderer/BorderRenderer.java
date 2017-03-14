package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Border renderer base.
 */
public abstract class BorderRenderer<B extends Border> {
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public abstract void initialize();

    public void render(B border, Component component, Context context) {
        if (!initialized.getAndSet(true)) {
            initialize();
        } else {
            renderBorder(border, component, context);
        }
    }

    public abstract void renderBorder(B border, Component component, Context context);

    public abstract void destroy();
}
