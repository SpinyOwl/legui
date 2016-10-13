package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleLineBorder;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class Panel extends ComponentContainer {

    private static final Vector2f NULL = new Vector2f(0);

    public Panel() {
        initialize();
    }

    public Panel(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    public Panel(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    private void initialize() {
        border = new SimpleLineBorder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Panel panel = (Panel) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
