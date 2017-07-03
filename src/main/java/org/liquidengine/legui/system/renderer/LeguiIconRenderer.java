package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.LeguiContext;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Icon renderer base.
 */
public abstract class LeguiIconRenderer<I extends Icon> {
    private AtomicBoolean initialized = new AtomicBoolean(false);


    public void render(I icon, Component component, LeguiContext context) {
        if (!initialized.getAndSet(true)) {
            initialize();
        } else {
            renderIcon(icon, component, context);
        }
    }

    public abstract void renderIcon(I icon, Component component, LeguiContext context);

    public void initialize() {
        // this method should be reimplemented if need to initialize some data in renderer before it can be used
        // called only once
    }

    public void destroy() {
        // this method should be reimplemented if need to destroy some data in renderer before exit
    }
}
