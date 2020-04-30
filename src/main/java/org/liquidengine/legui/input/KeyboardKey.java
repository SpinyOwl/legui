package org.liquidengine.legui.input;

import java.util.Objects;

public final class KeyboardKey {

    private KeyCode keyCode;
    private int nativeKeyCode;

    public KeyboardKey(KeyCode keyCode, int nativeKeyCode) {
        this.keyCode = keyCode;
        this.nativeKeyCode = nativeKeyCode;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(KeyCode keyCode) {
        this.keyCode = keyCode;
    }


    public int getNativeKeyCode() {
        return nativeKeyCode;
    }

    public void setNativeKeyCode(int nativeKeyCode) {
        this.nativeKeyCode = nativeKeyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyboardKey that = (KeyboardKey) o;
        return nativeKeyCode == that.nativeKeyCode &&
                keyCode == that.keyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyCode, nativeKeyCode);
    }
}
