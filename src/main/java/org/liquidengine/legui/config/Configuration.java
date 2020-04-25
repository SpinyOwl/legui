package org.liquidengine.legui.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.leutil.io.IOUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
     * Map that contains key configurations per keyboard layout.
     */
    private Map<String, KeyConfig> keyConfigMap;

    private static void initialize() {
        Gson gson = new Gson();
        try {
            String config = new String(IOUtil.resourceToByteBuffer("config.json").array(), StandardCharsets.UTF_8);

            Configuration defaultConfig = new Configuration();
            HashMap<String, KeyConfig> map = new HashMap<>();
            map.put("qwerty", new KeyConfig());
            map.put("йцукен", new KeyConfig());
            map.put("azerty", new KeyConfig(67, 86, 81, 88));
            defaultConfig.keyConfigMap = map;
            defaultConfig.keyboardLayout = "qwerty";

            JsonObject initial = gson.toJsonTree(defaultConfig).getAsJsonObject();
            JsonObject imported = gson.fromJson(config, JsonElement.class).getAsJsonObject();

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

    public String getKeyboardLayout() {
        return keyboardLayout;
    }

    public void setKeyboardLayout(String keyboardLayout) {
        this.keyboardLayout = keyboardLayout;
    }

    public Map<String, KeyConfig> getKeyConfigMap() {
        return keyConfigMap;
    }

    public void setKeyConfigMap(Map<String, KeyConfig> keyConfigMap) {
        this.keyConfigMap = keyConfigMap;
    }
}
