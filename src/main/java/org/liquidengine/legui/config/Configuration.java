package org.liquidengine.legui.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.Keyboard;
import org.liquidengine.legui.input.Shortcut;
import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Configuration {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Configuration instance;

    static {
        initialize();
    }

    /**
     * Defines keyboard layout configuration to use.
     */
    private String keyboardLayout;

    /**
     * Set of shortcuts used in application.
     */
    private Shortcuts shortcuts;

    /**
     * Map that contains key configurations per keyboard layout.
     */
    private Map<String, Map<KeyCode, Integer>> keyboardLayouts;

    private static void initialize() {
        Gson gson = new Gson();
        try {
            String defaultConfig = new String(IOUtil.resourceToString("defaultLegui.json").getBytes(), StandardCharsets.UTF_8);
            JsonObject initial = gson.fromJson(defaultConfig, JsonObject.class);
            try {
                String configBytes = IOUtil.resourceToString("legui.json");
                String config = new String(configBytes.getBytes(), StandardCharsets.UTF_8);
                JsonObject imported = gson.fromJson(config, JsonObject.class);
                merge(initial, imported);
            } catch (Exception e) {
                // skip
            }

            instance = gson.fromJson(initial, Configuration.class);

            Map<KeyCode, Integer> mapping = instance.getKeyboardLayouts().get(instance.getKeyboardLayout());
            if (mapping != null) {
                Keyboard.updateMapping(mapping);
            }
            Shortcut copy = instance.getShortcuts().getCopy();
            if (copy != null) {
                Keyboard.setCopyShortcut(copy);
            }

            Shortcut cut = instance.getShortcuts().getCut();
            if (cut != null) {
                Keyboard.setCutShortcut(cut);
            }

            Shortcut paste = instance.getShortcuts().getPaste();
            if (paste != null) {
                Keyboard.setPasteShortcut(paste);
            }

            Shortcut selectAll = instance.getShortcuts().getSelectAll();
            if (selectAll != null) {
                Keyboard.setSelectAllShortcut(selectAll);
            }


        } catch (IOException e) {
            LOGGER.error("LEGUI - Failed to load config.");
        }
    }

    private static void merge(JsonObject initial, JsonObject imported) {
        for (Map.Entry<String, JsonElement> entry : imported.entrySet()) {
            JsonElement jsonElement = initial.get(entry.getKey());
            if (jsonElement != null && jsonElement.isJsonObject() && entry.getValue().isJsonObject()) {
                merge(jsonElement.getAsJsonObject(), entry.getValue().getAsJsonObject());
            } else {
                initial.add(entry.getKey(), entry.getValue());
            }
        }
    }

    public static Configuration getInstance() {
        return instance;
    }

    public static void setInstance(Configuration instance) {
        Configuration.instance = instance;
    }

    public String getKeyboardLayout() {
        return keyboardLayout;
    }

    public void setKeyboardLayout(String keyboardLayout) {
        this.keyboardLayout = keyboardLayout;
    }

    public Map<String, Map<KeyCode, Integer>> getKeyboardLayouts() {
        return keyboardLayouts;
    }

    public void setKeyboardLayouts(Map<String, Map<KeyCode, Integer>> keyboardLayouts) {
        this.keyboardLayouts = keyboardLayouts;
    }

    public Shortcuts getShortcuts() {
        return shortcuts;
    }

    public void setShortcuts(Shortcuts shortcuts) {
        this.shortcuts = shortcuts;
    }


    public static class Shortcuts {
        private Shortcut copy;
        private Shortcut paste;
        private Shortcut cut;
        private Shortcut selectAll;

        public Shortcut getCopy() {
            return copy;
        }

        public void setCopy(Shortcut copy) {
            this.copy = copy;
        }

        public Shortcut getPaste() {
            return paste;
        }

        public void setPaste(Shortcut paste) {
            this.paste = paste;
        }

        public Shortcut getCut() {
            return cut;
        }

        public void setCut(Shortcut cut) {
            this.cut = cut;
        }

        public Shortcut getSelectAll() {
            return selectAll;
        }

        public void setSelectAll(Shortcut selectAll) {
            this.selectAll = selectAll;
        }
    }
}
