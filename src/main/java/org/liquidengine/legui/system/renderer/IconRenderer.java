package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Icon renderer base.
 */
public abstract class IconRenderer<I extends Icon> {
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public abstract void initialize();

    public void render(I icon, Component component, Context context) {
        if (!initialized.getAndSet(true)) {
            initialize();
        } else {
            renderIcon(icon, component, context);
        }
    }

    public abstract void renderIcon(I icon, Component component, Context context);

    public abstract void destroy();
}
