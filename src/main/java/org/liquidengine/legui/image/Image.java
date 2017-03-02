package org.liquidengine.legui.image;

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
}
