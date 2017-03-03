package org.liquidengine.legui.image;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.ByteBuffer;

/**
 * Represent image source
 */
public abstract class Image {
    private final String path;

    public Image(String path) {
        this.path = path;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract ImageChannels getChannels();

    public abstract ByteBuffer getImageData();

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("path", path)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return new EqualsBuilder()
                .append(getPath(), image.getPath())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPath())
                .toHashCode();
    }
}
