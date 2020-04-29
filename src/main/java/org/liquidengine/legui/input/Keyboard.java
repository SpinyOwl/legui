package org.liquidengine.legui.input;

import org.liquidengine.legui.config.Configuration;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This class used to allow use last states of keyboard keys.
 */
public final class Keyboard {

    private static final Map<String, Keyboard> keyboards = new ConcurrentHashMap<>();

    private final Set<Key> keys = new CopyOnWriteArraySet<>();

    public static void addKeyboard(String layout, Keyboard keyboard) {
        keyboards.put(Objects.requireNonNull(layout), Objects.requireNonNull(keyboard));
    }

    public static void removeKeyboard(String layout) {
        keyboards.remove(layout);
    }

    public static Keyboard getKeyboard(String layout) {
        return keyboards.get(layout);
    }

    public static Keyboard getKeyboard() {
        return keyboards.get(Configuration.getInstance().getCurrentKeyboardLayout());
    }

    public Key getKey(int code) {
        return keys.stream().filter(k -> k.getCode() == code).findFirst().orElse(null);
    }
}
