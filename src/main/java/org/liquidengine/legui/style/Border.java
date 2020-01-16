package org.liquidengine.legui.style;

/**
 * Abstract class defines hierarchy of borders.
 */
public abstract class Border {

    /**
     * Defines is border enabled or not.
     */
    private boolean enabled = true;

    /**
     * Returns true if border enabled and renderable. By default it enabled.
     *
     * @return true if border enabled and renderable.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Used to set border enabled or not. By default it enabled.
     *
     * @param enabled new 'enabled' state.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
