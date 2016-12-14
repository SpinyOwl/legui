package org.liquidengine.legui.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.BufferUtils.createByteBuffer;

public final class IOUtil {
    private static final Logger LOGGER = LogManager.getLogger(IOUtil.class);

    private IOUtil() {
    }

    public static String loadAsString(String file) {
        LOGGER.debug("loading resource: " + file);
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append('\n');
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result.toString();
    }

    public static String loadAsString(File file) {
        LOGGER.debug("loading resource: " + file);
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append('\n');
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result.toString();
    }

    public static String loadAsString(InputStream stream) {
        if (stream == null) throw new NullPointerException();
        LOGGER.debug("loading resource: " + stream);
        StringBuilder result = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(stream);
        try (BufferedReader reader = new BufferedReader(streamReader)) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append('\n');
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result.toString();
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    private static ByteBuffer readToBuffer(ByteBuffer buffer, InputStream source) {
        try (ReadableByteChannel rbc = Channels.newChannel(source)) {
            while (true) {
                int bytes = rbc.read(buffer);
                if (bytes == -1) break;
                if (buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 2);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return buffer;
    }

    /**
     * @param resource   path to resource to initialize
     * @param bufferSize initial buffer size
     * @return Created ByteBuffer
     * @throws RuntimeException if any exception occurs
     * @see IOUtil#ioResourceToByteBuffer(String, int)
     */
    public static ByteBuffer loadResourceToByteBuffer(String resource, int bufferSize) throws RuntimeException {
        try {
            return ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadResourceAsString(String resource, int bufferSize) throws RuntimeException {
        ByteBuffer byteBuffer = loadResourceToByteBuffer(resource, bufferSize);
        byte b[] = new byte[byteBuffer.limit()];
        byteBuffer.get(b);
        return new String(b);
    }

    /**
     * @param resource   Resource Stream
     * @param bufferSize initial buffer size
     * @return Created ByteBuffer
     * @throws RuntimeException if an IO error occurs
     * @see IOUtil#ioResourceToByteBuffer(String, int)
     */
    public static ByteBuffer loadResourceToByteBuffer(InputStream resource, int bufferSize) throws RuntimeException {
        try {
            return ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     * @return the resource data
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer ioResourceToByteBuffer(InputStream resource, int bufferSize) throws IOException {
        ByteBuffer buffer = createByteBuffer(bufferSize);
        if (resource == null) throw new IOException("Input stream resource is null!");
        buffer = readToBuffer(buffer, resource);
        buffer.flip();
        return buffer;
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     * @return the resource data
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        File file = new File(resource);
        if (file.exists() && file.isFile()) {
            FileInputStream source = new FileInputStream(file);
            buffer = ioResourceToByteBuffer(source, bufferSize);
            buffer.flip();
        } else {
            InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if (source == null) throw new FileNotFoundException(resource);
            buffer = ioResourceToByteBuffer(source, bufferSize);
        }

        return buffer;
    }


    public static String getParentFolder(String path) {
        int delimL = path.lastIndexOf('/');
        int delimR = path.lastIndexOf('\\');
        int delim = delimL > delimR ? delimL : delimR;
        return path.substring(0, delim) + "/";
    }

    public static String getChildFile(String path) {
        int delimL = path.lastIndexOf('/');
        int delimR = path.lastIndexOf('\\');
        int delim = (delimL > delimR ? delimL : delimR) + 1;
        String file = path.substring(delim);
        if (file.isEmpty()) return null;
        return file;
    }

}