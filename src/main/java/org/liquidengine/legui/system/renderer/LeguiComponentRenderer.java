package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public abstract class LeguiComponentRenderer<C extends Component> {
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public abstract void initialize();

    public void render(C component) {
        if (!initialized.getAndSet(true)) {
            initialize();
        } else {
            renderComponent(component);
        }
    }

    public abstract void renderComponent(C component);

    public abstract void destroy();
}