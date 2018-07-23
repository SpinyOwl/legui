package org.liquidengine.legui.system;

public class Window {

    private long pointer;

    protected Window(long pointer) {
        this.pointer = pointer;
    }

    public void setVisible(boolean visible) {
        LeguiSystem.setVisible(pointer);
    }

}
