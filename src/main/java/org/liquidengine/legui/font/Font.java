package org.liquidengine.legui.font;

import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.liquidengine.legui.exception.LeguiExceptions.FAILED_TO_LOAD_FONT;

/**
 * Created by Alexander on 08.12.2016.
 */
public class Font {
    private ByteBuffer data;
    private String path;

    public Font() {

    }

    public Font(String path) {
        this.path = path;
        try {
            data = IOUtil.ioResourceToByteBuffer(path, 1024);
        } catch (IOException e) {
            throw new LeguiException(FAILED_TO_LOAD_FONT.message(path), e);
        }
    }

    public Font(String path, ByteBuffer data) {
        this.path = path;
        this.data = data;
    }

    public ByteBuffer getData() {
        return data;
    }

    public String getPath() {
        return path;
    }
}
