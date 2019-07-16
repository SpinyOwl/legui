package org.liquidengine.legui.style.length;

public final class LengthType<T> {
    public static final LengthType<Float> PIXEL = new LengthType<>("PIXEL", Float.class);
    public static final LengthType<Float> PERCENT = new LengthType<>("PERCENT", Float.class);

    private final String name;
    private final Class<T> type;

    public LengthType(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public Class<T> type() {
        return type;
    }

    public Length<T> length(T value) {
        return new Length<>(value, this);
    }
}
