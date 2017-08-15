package org.liquidengine.legui.image;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Channels of image.
 */
public enum ImageChannels {
    G(1),
    GA(2),
    RGB(3),
    RGBA(4);

    private int channels;

    ImageChannels(int channels) {
        this.channels = channels;
    }

    public static ImageChannels instance(int channels) {
        for (ImageChannels value : values()) {
            if (value.channels == channels) {
                return value;
            }
        }
        return null;
    }

    public int getChannels() {
        return channels;
    }


    /**
     * Returns the name of this enum constant, as contained in the declaration.  This method may be overridden, though it typically isn't necessary or
     * desirable.  An enum type should override this method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("channels", channels)
            .toString();
    }
}
