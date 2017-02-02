package org.liquidengine.legui.font;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class FontRegister {
    private static Map<String, Font> fontRegister = new ConcurrentHashMap<>();

    public static Map<String, Font> getFontRegister() {
        return fontRegister;
    }
}
