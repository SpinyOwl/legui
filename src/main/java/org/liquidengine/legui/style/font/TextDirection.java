package org.liquidengine.legui.style.font;

public enum TextDirection {
    HORIZONTAL(0),
    VERTICAL_TOP_DOWN(1),
    VERTICAL_DOWN_TOP(2);
    private final int direction;

    TextDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
