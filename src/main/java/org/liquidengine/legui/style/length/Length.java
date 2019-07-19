package org.liquidengine.legui.style.length;

import java.util.Objects;

public class Length<T> extends Unit {
    private final T value;
    private final LengthType<T> type;

    public Length(T value, LengthType<T> type) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(type);
        this.value = value;
        this.type = type;
    }

    public T get() {
        return value;
    }

    public LengthType<T> type() {
        return this.type;
    }
}
