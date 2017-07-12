package org.liquidengine.legui.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.exception.LeguiExceptionTemplate;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.BufferUtils.createByteBuffer;

/**
 * Input/output utility.
 */
public final class IOUtil {
    /**
     * Used to log errors.
     */
    private static final Logger LOGGER              = LogManager.getLogger(IOUtil.class);
    /**
     * Initial buffer size.
     */
    private static final int    INITIAL_BUFFER_SIZE = 1024;

    /**
     * Private constructor to avoid creation instances of utility class.
     */
    private IOUtil() {
    }

    /**
     * Used to read file content as String.
     *
     * @param file file path to read.
     *
     * @return content of file or null if error occurs.
     */
    public static String loadAsString(final String file) {
        LOGGER.debug("loading resource: " + file);
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append('\n');
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return result.toString();
    }

    /**
     * Used to read file content as String.
     *
     * @param file file to read.
     *
     * @return content of file or null if error occurs.
     */
    public static String loadAsString(final File file) {
        LOGGER.debug("loading resource: " + file);
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append('\n');
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return result.toString();
    }

    /**
     * Used to read stream content as String.
     *
     * @param stream stream to read.
     *
     * @return content of file or null if error occurs.
     */
    public static String loadAsString(final InputStream stream) {
        if (stream == null) {
            throw new IllegalArgumentException();
        }
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
            return null;
        }
        return result.toString();
    }

    /**
     * Used to create new buffer with specified capacity and fill it with existing buffer data.
     *
     * @param buffer      source buffer.
     * @param newCapacity new capacity. If new capacity is lower then used capacity of source buffer to avoid {@link
     *                    java.nio.BufferOverflowException}.
     *
     * @return new buffer filled with data form source buffer.
     */
    private static ByteBuffer resizeBuffer(final ByteBuffer buffer, final int newCapacity) {
        int capacity = newCapacity;
        if (buffer.capacity() > capacity) {
            capacity = buffer.capacity();
        }
        ByteBuffer newBuffer = createByteBuffer(capacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    /**
     * Used to read source stream to byte buffer.
     *
     * @param buffer initial buffer to fill with source data.
     * @param source source to read.
     *
     * @return filled in byte buffer with source data.
     *
     * @throws IOException if any exception occurs
     */
    private static ByteBuffer readToBuffer(final ByteBuffer buffer, final InputStream source) throws IOException {
        ByteBuffer bufferToWork = buffer;
        ReadableByteChannel rbc = Channels.newChannel(source);
        while (true) {
            int bytes = rbc.read(bufferToWork);
            if (bytes == -1) {
                break;
            }
            if (bufferToWork.remaining() == 0) {
                bufferToWork = resizeBuffer(bufferToWork, bufferToWork.capacity() * 2);
            }
        }
        return bufferToWork;
    }

    /**
     * Used to read resource to byte buffer.
     * <p>
     * (Resource could be file or java resource. Search starts from files.)
     *
     * @param resource   path to resource to initialize.
     * @param bufferSize initial buffer size.
     *
     * @return Created ByteBuffer.
     *
     * @throws RuntimeException if any exception occurs.
     * @see IOUtil#ioResourceToByteBuffer(String, int).
     */
    public static ByteBuffer loadResourceToByteBuffer(final String resource, final int bufferSize) throws RuntimeException {
        try {
            return ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw LeguiExceptionTemplate.UNHANDLED_EXCEPTION.create(e, e.getMessage());
        }
    }

    /**
     * Used to load resource as String.
     * <p>
     * (Resource could be file or java resource. Search starts from files.)
     *
     * @param resource path to resource to initialize
     *
     * @return Created String from resources.
     *
     * @throws RuntimeException if any exception occurs.
     */
    public static String loadResourceAsString(final String resource) throws RuntimeException {
        ByteBuffer byteBuffer = loadResourceToByteBuffer(resource, INITIAL_BUFFER_SIZE);
        byte[] b = new byte[byteBuffer.limit()];
        byteBuffer.get(b);
        return new String(b);
    }

    /**
     * Used to load resource to byte buffer from input stream.
     *
     * @param resource   Resource Stream.
     * @param bufferSize initial buffer size.
     *
     * @return Created ByteBuffer.
     *
     * @throws RuntimeException if an IO error occurs.
     * @see IOUtil#ioResourceToByteBuffer(String, int).
     */
    public static ByteBuffer loadResourceToByteBuffer(final InputStream resource, final int bufferSize) throws RuntimeException {
        try {
            return ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw LeguiExceptionTemplate.UNHANDLED_EXCEPTION.create(e, e.getMessage());
        }
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     *
     * @return the resource data
     *
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer ioResourceToByteBuffer(final InputStream resource, final int bufferSize) throws IOException {
        ByteBuffer buffer = createByteBuffer(bufferSize);
        if (resource == null) {
            throw new IOException("Input stream resource is null!");
        }
        buffer = readToBuffer(buffer, resource);
        buffer.flip();
        return buffer;
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read.
     * @param bufferSize the initial buffer size.
     *
     * @return the resource data.
     *
     * @throws IOException if an IO error occurs.
     */
    public static ByteBuffer ioResourceToByteBuffer(final String resource, final int bufferSize) throws IOException {
        ByteBuffer buffer;

        File file = new File(resource);
        if (file.exists() && file.isFile()) {
            FileInputStream source = new FileInputStream(file);
            buffer = ioResourceToByteBuffer(source, bufferSize);
            buffer.flip();
        } else {
            InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if (source == null) {
                throw new FileNotFoundException(resource);
            }
            buffer = ioResourceToByteBuffer(source, bufferSize);
        }

        return buffer;
    }

    /**
     * Used to find parent folder from path.
     *
     * @param path path to process.
     *
     * @return parent folder from specified path.
     */
    public static String getParentFolder(final String path) {
        int delimL = path.lastIndexOf('/');
        int delimR = path.lastIndexOf('\\');
        int delim = delimL > delimR ? delimL : delimR;
        return path.substring(0, delim) + "/";
    }

    /**
     * Used to get filename from path.
     *
     * @param path path to process.
     *
     * @return filename or null(if path ends with '/' or '\').
     */
    public static String getChildFile(final String path) {
        int delimL = path.lastIndexOf('/');
        int delimR = path.lastIndexOf('\\');
        int delim = (delimL > delimR ? delimL : delimR) + 1;
        String file = path.substring(delim);
        if (file.isEmpty()) {
            return null;
        }
        return file;
    }

}
