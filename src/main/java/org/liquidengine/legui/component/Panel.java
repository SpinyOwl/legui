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
public class Panel extends Component implements ContainerHolder {

    private static final Vector2f NULL = new Vector2f(0);
    protected ComponentContainer<Panel> container = new ComponentContainer(this);

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
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public ComponentContainer getContainer() {
        return container;
    }

    @Override
    public Vector2f getContainerPosition() {
        return NULL;
    }
}
