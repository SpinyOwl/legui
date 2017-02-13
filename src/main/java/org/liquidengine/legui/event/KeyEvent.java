package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/13/2017.
 */
public class KeyEvent extends AbstractEvent {

    private final int action;
    private final int key;
    private final int mods;
    private final int scancode;

    public KeyEvent(Component component, Context context, int action, int key, int mods, int scancode) {
        super(component, context);
        this.action = action;
        this.key = key;
        this.mods = mods;
        this.scancode = scancode;
    }

    public int getAction() {
        return action;
    }

    public int getKey() {
        return key;
    }

    public int getMods() {
        return mods;
    }

    public int getScancode() {
        return scancode;
    }
}
