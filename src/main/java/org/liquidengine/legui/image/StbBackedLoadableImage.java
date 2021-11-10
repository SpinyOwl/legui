package org.liquidengine.legui.image;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.exception.LeguiExceptionTemplate;
import org.liquidengine.legui.util.IOUtil;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by ShchAlexander on 2/6/2017.
 */
public class StbBackedLoadableImage extends LoadableImage {
    private int width;
    private int height;
    private ImageChannels channels;
    private ByteBuffer imageData;


    /**
     * Used to create buffered image object and load it.
     *
     * @param path path to image source.
     */
    public StbBackedLoadableImage(String path) {
        super(path);
    }

    /**
     * Should be used to load image data from source.
     */
    @Override
    public void load() {
        try {
            ByteBuffer byteBuffer = IOUtil.resourceToByteBuffer(getPath());
            int[] w = {0};
            int[] h = {0};
            int[] c = {0};
            ByteBuffer buffer = STBImage.stbi_load_from_memory(byteBuffer, w, h, c, 4);

            if (buffer != null) {
                this.width = w[0];
                this.height = h[0];
                this.channels = ImageChannels.instance(c[0]);
                this.imageData = buffer;
            } else { // if error occurs
                throw LeguiExceptionTemplate.FAILED_TO_LOAD_IMAGE.create(STBImage.stbi_failure_reason());
            }
        } catch (IOException e) {
            throw LeguiExceptionTemplate.FAILED_TO_LOAD_IMAGE.create(e, e.getMessage());
        }
    }

    /**
     * Returns image width.
     *
     * @return image width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns image height.
     *
     * @return image height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns image data.
     *
     * @return image data.
     */
    public ByteBuffer getImageData() {
        return imageData;
    }

    /**
     * Returns image channels.
     *
     * @return image channels.
     */
    public ImageChannels getChannels() {
        return channels;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("path", getPath())
                .append("width", width)
                .append("height", height)
                .append("channels", channels)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        StbBackedLoadableImage image = (StbBackedLoadableImage) obj;

        return new EqualsBuilder()
                .append(width, image.width)
                .append(height, image.height)
                .append(getPath(), image.getPath())
                .append(channels, image.channels)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPath())
                .append(width)
                .append(height)
                .append(channels)
                .toHashCode();
    }

}
