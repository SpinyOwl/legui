package org.liquidengine.legui.style.font;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.ByteBuffer;

/**
 * Representation of font. Used by text components to specify font to use by renderer.
 */
public class Font {

    /**
     * Font data.
     */
    private final ByteBuffer data;

    /**
     * Path to font.
     */
    private final String path;

    /**
     * Used to create font with specified path and data.
     *
     * @param path path to font.
     * @param data font data.
     */
    public Font(String path, ByteBuffer data) {
        this.path = path;
        this.data = data;
    }

    /**
     * Returns font data.
     *
     * @return font data.
     */
    public ByteBuffer getData() {
        return data;
    }

    /**
     * Returns font path.
     *
     * @return font path.
     */
    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Font font = (Font) o;

        return new EqualsBuilder()
                .append(getData(), font.getData())
                .append(getPath(), font.getPath())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getData())
                .append(getPath())
                .toHashCode();
    }
}