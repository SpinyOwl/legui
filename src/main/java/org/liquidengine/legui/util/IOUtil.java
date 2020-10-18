package org.liquidengine.legui.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * IO utility. Used to read resource as {@link ByteBuffer} or as {@link String}.
 *
 * @author ShchAlexander.
 */
public class IOUtil {

    /**
     * Creates {@link ByteBuffer} from:
     * <ul>
     *     <li>file</li>
     *     <li>resource</li>
     *     <li>url (http/https) protocol</li>
     * </ul>
     *
     * @param path path to file or resource.
     * @return file or resource data or null.
     * @throws IOException in case if any IO exception occurs.
     */
    public static ByteBuffer resourceToByteBuffer(String path) throws IOException {
        ByteBuffer data = null;
        byte[] bytes;
        path = path.trim();
        if (path.startsWith("http")) {
            bytes = IOUtils.toByteArray(new URL(path));
        } else {
            InputStream stream;
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                stream = new FileInputStream(file);
            } else {
                stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            }
            if (stream == null) {
                throw new FileNotFoundException(path);
            }
            bytes = IOUtils.toByteArray(stream);
        }
        data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
        data.flip();
        return data;
    }

    /**
     * Creates {@link ByteBuffer} from stream.
     *
     * @param stream stream to read.
     * @return stream data or null.
     * @throws IOException in case if any IO exception occurs.
     */
    public static ByteBuffer resourceToByteBuffer(InputStream stream) throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("InputStream can not be null!");
        }
        ByteBuffer data = null;
        byte[] bytes = IOUtils.toByteArray(stream);
        data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
        data.flip();
        return data;
    }

    /**
     * Creates {@link ByteBuffer} from file.
     *
     * @param file file to read.
     * @return file or null.
     * @throws IOException in case if any IO exception occurs.
     */
    public static ByteBuffer resourceToByteBuffer(File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("File is not exist or is not a file.");
        }
        ByteBuffer data;
        InputStream stream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(stream);
        data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
        data.flip();
        return data;
    }

    /**
     * Creates {@link String} from file or resource.
     *
     * @param path path to file or resource.
     * @return String from file or resource data or null.
     * @throws IOException in case if any IO exception occurs.
     */
    public static String resourceToString(String path) throws IOException {
        return byteBufferToString(resourceToByteBuffer(path));
    }

    /**
     * Creates {@link String} from stream.
     *
     * @param stream stream to read.
     * @return String from stream data or null.
     * @throws IOException in case if any IO exception occurs.
     */
    public static String resourceToString(InputStream stream) throws IOException {
        return byteBufferToString(resourceToByteBuffer(stream));
    }

    /**
     * Creates {@link String} from file.
     *
     * @param file file to read.
     * @return String from file or null.
     * @throws IOException in case if any IO exception occurs.
     */
    public static String resourceToString(File file) throws IOException {
        return byteBufferToString(resourceToByteBuffer(file));
    }

    /**
     * Used to transfer buffer data to {@link String}.
     *
     * @param byteBuffer data to transfer
     * @return string or null.
     */
    public static String byteBufferToString(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        if (byteBuffer.limit() == 0) {
            return "";
        }
        byte[] buffer = new byte[byteBuffer.limit()];
        byteBuffer.get(buffer);
        return new String(buffer);
    }

}
