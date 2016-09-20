package org.liquidengine.legui.font;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.liquidengine.legui.util.IOUtil.ioResourceToByteBuffer;

/**
 * Created by Shcherbin Alexander on 5/16/2016.
 */
public class FontRegister {
    public static final String ENTYPO = "entypo";
    public static final String ROBOTO_BOLD = "roboto-bold";
    public static final String ROBOTO_LIGHT = "roboto-light";
    public static final String ROBOTO_REGULAR = "roboto-regular";
    public static final String MATERIAL_ICONS_REGULAR = "MaterialIcons-Regular";
    public static final String DEFAULT = ROBOTO_BOLD;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, FontData> fontRegister = new ConcurrentHashMap<>();

    static {
        registerFont(ENTYPO, "org/liquidengine/legui/font/entypo.ttf", true);
        registerFont(ROBOTO_BOLD, "org/liquidengine/legui/font/Roboto-Bold.ttf", true);
        registerFont(ROBOTO_LIGHT, "org/liquidengine/legui/font/Roboto-Light.ttf", true);
        registerFont(ROBOTO_REGULAR, "org/liquidengine/legui/font/Roboto-Regular.ttf", true);
        registerFont(MATERIAL_ICONS_REGULAR, "org/liquidengine/legui/font/MaterialIcons-Regular.ttf", true);

        registerFont(DEFAULT, "org/liquidengine/legui/font/Roboto-Bold.ttf", true);
    }

    private FontRegister() {
    }

    public static void registerFont(final String name, final String path, final boolean inner) {
        try {
            ByteBuffer data = ioResourceToByteBuffer(path, 1024);
            if (data == null) throw new IOException("Failed to load "+ name + ":'" + path + "' font");
            FontData value = new FontData(path, inner, data);
            fontRegister.put(name, value);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static Map<String, FontData> getFontRegister() {
        return new HashMap<>(fontRegister);
    }

    public static class FontData {
        private final String path;
        private final boolean inner;
        private final ByteBuffer data;

        public FontData(String path, boolean inner, ByteBuffer data) {
            this.path = path;
            this.inner = inner;
            this.data = data;
        }

        public String getPath() {
            return path;
        }

        public boolean isInner() {
            return inner;
        }

        public ByteBuffer getData() {
            return data;
        }
    }
}
