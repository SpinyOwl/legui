package org.liquidengine.legui.border;

/**
 * Border base class
 * Created by Alexander on 14.01.2017.
 */
public abstract class Border {
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
