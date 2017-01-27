package org.liquidengine.legui.font;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 5/16/2016.
 */
public class FontRegister {
    public static final  String            ENTYPO                 = "entypo";
    public static final  String            ROBOTO_BOLD            = "roboto italic";
    public static final  String            ROBOTO_LIGHT           = "roboto-light";
    public static final  String            ROBOTO_REGULAR         = "roboto-regular";
    public static final  String            MATERIAL_ICONS_REGULAR = "MaterialIcons-Regular";
    public static final  String            DEFAULT                = ROBOTO_BOLD;
    private static final Map<String, Font> fontRegister           = new ConcurrentHashMap<>();

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
        Font font = new Font(path);
        fontRegister.put(name, font);
    }

    public static Map<String, Font> getFontRegister() {
        return new HashMap<>(fontRegister);
    }

    public static Font getFont(String name) {
        return fontRegister.get(name);
    }
}
