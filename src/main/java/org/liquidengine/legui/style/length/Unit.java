package org.liquidengine.legui.style.length;

public abstract class Unit {

    public boolean isLength() {
        return this instanceof Length;
    }

    public boolean isAuto() {
        return this instanceof Auto;
    }

    public Length asLength() {
        return (Length) this;
    }
}
