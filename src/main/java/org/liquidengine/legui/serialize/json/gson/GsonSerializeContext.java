package org.liquidengine.legui.serialize.json.gson;

import org.liquidengine.legui.component.RadioButtonGroup;
import org.liquidengine.legui.serialize.json.JsonSerializeContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 28.11.2016.
 */
public final class GsonSerializeContext implements JsonSerializeContext {
    private Map<String, Object>            contextMap               = new HashMap<>();
    private Map<RadioButtonGroup, Integer> serializeRadioGroupMap   = new HashMap<>();
    private Map<Integer, RadioButtonGroup> deserializeRadioGroupMap = new HashMap<>();


    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public Map<RadioButtonGroup, Integer> getSerializeRadioGroupMap() {
        return serializeRadioGroupMap;
    }

    public Map<Integer, RadioButtonGroup> getDeserializeRadioGroupMap() {
        return deserializeRadioGroupMap;
    }
}
