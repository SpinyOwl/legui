package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public abstract class ComponentRenderer<C extends Component> {
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public abstract void initialize();

    public void render(C component, Context context) {
        if (!initialized.getAndSet(true)) {
            initialize();
        } else {
            renderComponent(component, context);
        }
    }

    public abstract void renderComponent(C component, Context context);

    public abstract void destroy();
}