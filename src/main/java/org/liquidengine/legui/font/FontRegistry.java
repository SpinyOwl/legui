package org.liquidengine.legui.font;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class FontRegistry {
    /**
     * Default entypo font.
     */
    public static final String ENTYPO = "entypo";
    /**
     * Default roboto-bold font.
     */
    public static final String ROBOTO_BOLD = "roboto italic";
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
     * Font used by default. {@link #ROBOTO_BOLD}.
     */
    public static final String DEFAULT = ROBOTO_BOLD;
    /**
     * Font register.
     */
    private static final Map<String, Font> fontRegister = new ConcurrentHashMap<>();

    static {
        registerFont(ENTYPO, "org/liquidengine/legui/font/entypo.ttf");
        registerFont(ROBOTO_BOLD, "org/liquidengine/legui/font/Roboto-Bold.ttf");
        registerFont(ROBOTO_LIGHT, "org/liquidengine/legui/font/Roboto-Light.ttf");
        registerFont(ROBOTO_REGULAR, "org/liquidengine/legui/font/Roboto-Regular.ttf");
        registerFont(MATERIAL_ICONS_REGULAR, "org/liquidengine/legui/font/MaterialIcons-Regular.ttf");
        registerFont(FONT_AWESOME_ICONS, "org/liquidengine/legui/font/FontAwesome.otf");
        registerFont(MATERIAL_DESIGN_ICONS, "org/liquidengine/legui/font/materialdesignicons.ttf");
    }

    /**
     * Private constructor to avoid creation instances of utility class.
     */
    private FontRegistry() {
    }

    /**
     * Used to register fonts.
     *
     * @param name font name.
     * @param path font path.
     */
    public static void registerFont(final String name, final String path) {
        Font font = new Font(path);
        fontRegister.put(name, font);
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
     *
     * @return font or null.
     */
    public static Font getFont(String name) {
        return fontRegister.get(name);
    }
}
