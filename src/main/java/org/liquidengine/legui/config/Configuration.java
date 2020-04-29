package org.liquidengine.legui.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.leutil.io.IOUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Configuration {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Configuration instance;

    static {
        initialize();
    }

    /**
     * Defines keyboard layout configuration to use.
     */
    private String currentKeyboardLayout;

    /**
     * Set of shortcuts used in application.
     */
    private Shortcuts shortcuts;

    /**
     * Map that contains key configurations per keyboard layout.
     */
    private Map<String, Map<String, Integer>> keyboardLayouts;

    private static void initialize() {
        Gson gson = new Gson();
        try {
            String defaultConfig = new String(IOUtil.resourceToByteBuffer("defaultConfig.json").array(), StandardCharsets.UTF_8);
            String config = new String(IOUtil.resourceToByteBuffer("config.json").array(), StandardCharsets.UTF_8);

            JsonObject initial = gson.fromJson(defaultConfig, JsonObject.class);
            JsonObject imported = gson.fromJson(config, JsonObject.class);

            merge(initial, imported);

            instance = gson.fromJson(initial, Configuration.class);
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

    public String getCurrentKeyboardLayout() {
        return currentKeyboardLayout;
    }

    public void setCurrentKeyboardLayout(String currentKeyboardLayout) {
        this.currentKeyboardLayout = currentKeyboardLayout;
    }

    public Map<String, Map<String, Integer>> getKeyboardLayouts() {
        return keyboardLayouts;
    }

    public void setKeyboardLayouts(Map<String, Map<String, Integer>> keyboardLayouts) {
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

    public static class Shortcut {
        private String key;
        private Set<Mod> mods;

        public Shortcut(String key, Set<Mod> mods) {
            this.key = key;
            this.mods = mods;
        }

        public Shortcut(String key, Mod... mods) {
            this.key = key;
            this.mods = new HashSet<>(Arrays.asList(mods));
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Set<Mod> getMods() {
            return mods;
        }

        public void setMods(Set<Mod> mods) {
            this.mods = mods;
        }

    }

}
