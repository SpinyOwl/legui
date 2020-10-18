package org.liquidengine.legui.image;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ShchAlexander on 30.05.2018.
 */
public class BufferedImageRGBA extends Image {
    // image data that would be passed to gpu
    private final ByteBuffer[] byteBuffers = new ByteBuffer[2];
    private final AtomicBoolean needToRead = new AtomicBoolean(false);

    private final int width;
    private final int height;

    private final int dataSize;

    private volatile int readPB = 1;
    private volatile int writePB = 0;


    public BufferedImageRGBA(int width, int height) {
        this.width = width;
        this.height = height;
        this.dataSize = width * height * 4;

        byteBuffers[0] = ByteBuffer.allocateDirect(dataSize);
        byteBuffers[1] = ByteBuffer.allocateDirect(dataSize);

        for (ByteBuffer byteBuffer : byteBuffers) {
            for (int i = 0; i < dataSize; i++) {
                byteBuffer.put((byte) (i % 255));
            }
        }

    }

    public void updateImageData(ByteBuffer data) {
        if (data.capacity() != dataSize) {
            throw new IllegalArgumentException("Wrong size of data");
        }

        ByteBuffer buffer = byteBuffers[writePB];

        buffer.clear();
        buffer.put(data);
        buffer.rewind();

        int tmp = writePB;
        writePB = readPB;
        readPB = tmp;

        needToRead.set(true);
    }

    public boolean isUpdated() {
        return needToRead.get();
    }

    public ByteBuffer getImageData() {
        return byteBuffers[readPB];
    }


    /**
     * Returns image width.
     *
     * @return image width.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Returns image height.
     *
     * @return image height.
     */
    @Override
    public int getHeight() {
        return height;
    }
}
