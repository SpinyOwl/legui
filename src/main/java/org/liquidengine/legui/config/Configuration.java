package org.liquidengine.legui.config;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.Keyboard;
import org.liquidengine.legui.input.Shortcut;
import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

public class Configuration {
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
     * Map that contains key configurations per keyboard layout.=
     */
    private Map<String, Map<KeyCode, Integer>> keyboardLayouts;

    private static void initialize() {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Configuration> configurationAdapter = moshi.adapter(Configuration.class);
        ParameterizedType mapType = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> mapAdapter = moshi.adapter(mapType);
        try {
            Map<String, Object> initialJson = getJson("defaultLegui.json", mapAdapter, Map.of());
            Map<String, Object> importedJson = getJson("legui.json", mapAdapter, null);

            merge(initialJson, importedJson);
            String mergedJson = mapAdapter.toJson(initialJson);

            Configuration configuration = configurationAdapter.fromJson(mergedJson);
            if (configuration != null) {
                configuration.updateKeyboard();
                Configuration.instance = configuration;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> getJson(String path, JsonAdapter<Map<String, Object>> adapter, Map<String, Object> defaultJson) {
        try {
            String json = IOUtil.resourceToString(path);
            return adapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
            return defaultJson;
        }
    }

    @SuppressWarnings({"unchecked"})
    private static Map<String, Object> merge(Map<String, Object> left, Map<String, Object> right) {
        if (right == null) {
            return left;
        }
        for (Map.Entry<String, Object> importedEntry : right.entrySet()) {
            left.merge(importedEntry.getKey(), importedEntry.getValue(), (leftChild, rightChild) -> {
                if (leftChild instanceof Map && rightChild instanceof Map) {
                    return merge((Map<String, Object>) leftChild, (Map<String, Object>) rightChild);
                }
                return rightChild;
            });
        }
        return left;
    }

    public static Configuration getInstance() {
        return instance;
    }

    public static void setInstance(Configuration instance) {
        Configuration.instance = instance;
    }

    private void updateKeyboard() {
        Map<KeyCode, Integer> mapping = this.getKeyboardLayouts().get(this.getKeyboardLayout());
        if (mapping != null) {
            Keyboard.updateMapping(mapping);
        }
        Shortcut copy = this.getShortcuts().getCopy();
        if (copy != null) {
            Keyboard.setCopyShortcut(copy);
        }

        Shortcut cut = this.getShortcuts().getCut();
        if (cut != null) {
            Keyboard.setCutShortcut(cut);
        }

        Shortcut paste = this.getShortcuts().getPaste();
        if (paste != null) {
            Keyboard.setPasteShortcut(paste);
        }

        Shortcut selectAll = this.getShortcuts().getSelectAll();
        if (selectAll != null) {
            Keyboard.setSelectAllShortcut(selectAll);
        }
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
