package org.liquidengine.legui.input;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Shortcut {
    private KeyCode key;
    private Set<KeyMod> mods;

    public Shortcut(KeyCode key, Set<KeyMod> mods) {
        this.key = key;
        this.mods = mods;
    }

    public Shortcut(KeyCode key, KeyMod... mods) {
        this.key = key;
        this.mods = new HashSet<>(Arrays.asList(mods));
    }

    public KeyCode getKey() {
        return key;
    }

    public void setKey(KeyCode key) {
        this.key = key;
    }

    public Set<KeyMod> getMods() {
        return mods;
    }

    public void setMods(Set<KeyMod> mods) {
        this.mods = mods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shortcut shortcut = (Shortcut) o;
        return key == shortcut.key &&
            Objects.equals(mods, shortcut.mods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, mods);
    }
}
