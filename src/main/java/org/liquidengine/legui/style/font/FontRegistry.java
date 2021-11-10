package org.liquidengine.legui.style.font;

import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.liquidengine.legui.exception.LeguiExceptionTemplate.FAILED_TO_LOAD_FONT;

/**
 * Created by ShchAlexander on 1/26/2017.
 */
public class FontRegistry {
    /**
     * Default entypo font.
     */
    public static final String ENTYPO = "entypo";
    /**
     * Default roboto-bold font.
     */
    public static final String ROBOTO_BOLD = "roboto-bold";
    /**
     * Default roboto-light font.
     */
    public static final String ROBOTO_LIGHT = "roboto-light";
    /**
     * Default roboto-regular font.
     */
    public static final String ROBOTO_REGULAR = "roboto-regular";
    /**
     * Default material-icons-regular font.
     */
    public static final String MATERIAL_ICONS_REGULAR = "MaterialIcons-Regular";
    /**
     * Default font-awesome-icons font.
     */
    public static final String FONT_AWESOME_ICONS = "FontAwesomeIcons";
    /**
     * Default material-design-icons font.
     */
    public static final String MATERIAL_DESIGN_ICONS = "materialdesignicons";
    /**
     * Font register.
     */
    private static final Map<String, Font> fontRegister = new ConcurrentHashMap<>();
    /**
     * Font used by default. {@link #ROBOTO_BOLD}.
     */
    private static String defaultFont = ROBOTO_LIGHT;

    static {
        registerFontSafe(ENTYPO, "org/liquidengine/legui/style/font/entypo.ttf");
        registerFontSafe(ROBOTO_BOLD, "org/liquidengine/legui/style/font/Roboto-Bold.ttf");
        registerFontSafe(ROBOTO_LIGHT, "org/liquidengine/legui/style/font/Roboto-Light.ttf");
        registerFontSafe(ROBOTO_REGULAR, "org/liquidengine/legui/style/font/Roboto-Regular.ttf");
        registerFontSafe(MATERIAL_ICONS_REGULAR, "org/liquidengine/legui/style/font/MaterialIcons-Regular.ttf");
        registerFontSafe(FONT_AWESOME_ICONS, "org/liquidengine/legui/style/font/FontAwesome.otf");
        registerFontSafe(MATERIAL_DESIGN_ICONS, "org/liquidengine/legui/style/font/materialdesignicons.ttf");
    }

    /**
     * Private constructor to avoid creation instances of utility class.
     */
    private FontRegistry() {
    }

    public static String getDefaultFont() {
        return defaultFont;
    }

    public static void setDefaultFont(String defaultFont) {
        FontRegistry.defaultFont = Objects.requireNonNull(defaultFont);
    }

    /**
     * Load and register font by provided name and paht..
     *
     * @param name font name.
     * @param path font path.
     * @throws LeguiException if failed to load font.
     */
    public static void registerFont(final String name, final String path) {
        try {
            fontRegister.put(name, new Font(path, IOUtil.resourceToByteBuffer(path)));
        } catch (IOException e) {
            throw FAILED_TO_LOAD_FONT.create(e, path);
        }
    }

    /**
     * Fail-safe load and register font by provided name and path.
     */
    public static void registerFontSafe(final String name, final String path) {
        try {
            registerFont(name, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns map of fonts where key is font name and value is font.
     *
     * @return map of fonts where key is font name and value is font.
     */
    public static Map<String, Font> getFontRegister() {
        return new HashMap<>(fontRegister);
    }

    /**
     * Used to retrieve font by name
     *
     * @param name font name.
     * @return font or null.
     */
    public static Font getFont(String name) {
        return fontRegister.get(name);
    }
}
