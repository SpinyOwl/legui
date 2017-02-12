package org.liquidengine.legui.border;

/**
 * Abstract class defines hierarchy of borders.
 */
public abstract class Border {
    private boolean enabled = true;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
