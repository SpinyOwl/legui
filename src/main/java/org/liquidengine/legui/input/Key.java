package org.liquidengine.legui.input;

import java.util.Objects;

public final class Key {

    private String key;
    private int code;
    private boolean pressed;

    public Key() {
    }

    public Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key1 = (Key) o;
        return code == key1.code &&
                pressed == key1.pressed &&
                Objects.equals(key, key1.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, code, pressed);
    }
}
