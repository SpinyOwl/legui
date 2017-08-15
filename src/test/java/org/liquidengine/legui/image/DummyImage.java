package org.liquidengine.legui.image;

import java.nio.ByteBuffer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Aliaksandr_Shcherbin on 3/2/2017.
 */
public class DummyImage extends LoadableImage {

    public DummyImage() {
    }

    public DummyImage(String path) {
        super(path);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public ImageChannels getChannels() {
        return null;
    }

    @Override
    public ByteBuffer getImageData() {
        return null;
    }

    /**
     * Should be used to load image data from source.
     */
    @Override
    public void load() {
        // nothing to do for dummy object
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
