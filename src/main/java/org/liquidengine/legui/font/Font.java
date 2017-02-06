package org.liquidengine.legui.font;

import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.liquidengine.legui.exception.LeguiExceptions.FAILED_TO_LOAD_FONT;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class Font {
    private ByteBuffer data;
    private String     path;

    public Font() {

    }

    public Font(String path) {
        this.path = path;
        try {
            data = IOUtil.ioResourceToByteBuffer(path, 1024);
        } catch (IOException e) {
            throw FAILED_TO_LOAD_FONT.create(e, path);
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