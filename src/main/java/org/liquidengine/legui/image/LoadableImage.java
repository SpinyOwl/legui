package org.liquidengine.legui.image;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.ByteBuffer;

/**
 * Represent image source.
 */
public abstract class LoadableImage extends Image {

    /**
     * Path to image source.
     */
    private String path;


    /**
     * This constructor should be used with {@link #setPath(String)} and {@link #load()} methods.
     */
    public LoadableImage() {
    }

    /**
     * Used to create image object but not load it.
     *
     * @param path path to image source.
     */
    public LoadableImage(String path) {
        this.path = path;
    }

    /**
     * Returns image width.
     *
     * @return image width.
     */
    public abstract int getWidth();

    /**
     * Returns image height.
     *
     * @return image height.
     */
    public abstract int getHeight();

    /**
     * Returns image channels.
     *
     * @return image channels.
     */
    public abstract ImageChannels getChannels();

    /**
     * Returns image data.
     *
     * @return image data.
     */
    public abstract ByteBuffer getImageData();

    /**
     * Returns image path.
     *
     * @return image path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Used to set path to image. Use this method with {@link #load()} method.
     *
     * @param path path to image.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Should be used to load image data from source.
     */
    public abstract void load();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("path", path)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoadableImage image = (LoadableImage) o;

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
