package org.liquidengine.legui.style.font;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
     * Font used by default. {@link #ROBOTO_BOLD}.
     */
    public static final String DEFAULT = ROBOTO_LIGHT;
    /**
     * Font register.
     */
    private static final Map<String, Font> fontRegister = new ConcurrentHashMap<>();

    static {
        registerFont(ENTYPO, "org/liquidengine/legui/style/font/entypo.ttf");
        registerFont(ROBOTO_BOLD, "org/liquidengine/legui/style/font/Roboto-Bold.ttf");
        registerFont(ROBOTO_LIGHT, "org/liquidengine/legui/style/font/Roboto-Light.ttf");
        registerFont(ROBOTO_REGULAR, "org/liquidengine/legui/style/font/Roboto-Regular.ttf");
        registerFont(MATERIAL_ICONS_REGULAR, "org/liquidengine/legui/style/font/MaterialIcons-Regular.ttf");
        registerFont(FONT_AWESOME_ICONS, "org/liquidengine/legui/style/font/FontAwesome.otf");
        registerFont(MATERIAL_DESIGN_ICONS, "org/liquidengine/legui/style/font/materialdesignicons.ttf");
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
        try {
            Font font = new Font(path);
            fontRegister.put(name, font);
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
     *
     * @return font or null.
     */
    public static Font getFont(String name) {
        return fontRegister.get(name);
    }
}
