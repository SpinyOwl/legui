package org.liquidengine.legui.event.component;

import org.liquidengine.legui.component.Component;

/**
 * Created by Alexander on 13.10.2016.
 */
public class KeyboardKeyEvent extends AbstractComponentEvent {
    private final int key;
    private final int action;
    private final int mods;
    private final int scancode;

    public KeyboardKeyEvent(Component component, int key, int action, int mods, int scancode) {
        super(component);
        this.key = key;
        this.action = action;
        this.mods = mods;
        this.scancode = scancode;
    }

    public int getKey() {
        return key;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }

    public int getScancode() {
        return scancode;
    }
}
