package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Component renderer base.
 */
public abstract class ComponentRenderer<C extends Component> {

    public void initialize() {
        // this method should be reimplemented if need to initialize some data in renderer before it can be used
        // called only once
    }

    public void render(C component, Context context) {
        renderComponent(component, context);
    }

    public abstract void renderComponent(C component, Context context);

    public void destroy() {
        // this method should be reimplemented if need to destroy some data in renderer before exit
    }
}