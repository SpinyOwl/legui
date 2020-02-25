package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class DropEvent<T extends Component> extends Event<T> {

    private List<String> files;

    public DropEvent(T component, Context context, Frame frame, List<String> files) {
        super(component, context, frame);
        this.files = files;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("files", files)
            .toString();
    }

    public List<String> getFiles() {
        return files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DropEvent<?> that = (DropEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(files, that.files)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(files)
            .toHashCode();
    }
}
