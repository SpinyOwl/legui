package org.liquidengine.legui.image;

import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.util.IOUtil;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Alexander on 12.12.2016.
 */
public class Image {
    private String path;
    private int width;
    private int height;
    private ImageChannels channels;
    private ByteBuffer imageData;

    public Image(String path) {
        this.path = path;
    }


    public Image() {
    }

    public void load() {
        try {
            ByteBuffer byteBuffer = IOUtil.ioResourceToByteBuffer(path, 1024);
            int[] width = {0};
            int[] height = {0};
            int[] channels = {0};
            ByteBuffer imageData = STBImage.stbi_load_from_memory(byteBuffer, width, height, channels, 4);

            if (imageData != null) {
                this.width = width[0];
                this.height = width[0];
                this.channels = ImageChannels.instance(channels[0]);
                this.imageData = imageData;
            } else { // if error occurs
                throw new LeguiException(LeguiExceptions.FAILED_TO_LOAD_IMAGE.message(STBImage.stbi_failure_reason()));
            }
        } catch (IOException e) {
            throw new LeguiException(LeguiExceptions.FAILED_TO_LOAD_IMAGE.message(e.getMessage()), e);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getImageData() {
        return imageData;
    }

    public ImageChannels getChannels() {
        return channels;
    }

    public String getPath() {
        return path;
    }

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
                if (value.channels == channels) return value;
            }
            return null;
        }

        public int getChannels() {
            return channels;
        }
    }
}
