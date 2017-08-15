package org.liquidengine.legui.marshal.json.gsonimpl;

import java.util.HashMap;
import java.util.Map;
import org.liquidengine.legui.component.RadioButtonGroup;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;

/**
 * Marshal context for GsonMarshaller.
 */
public final class GsonMarshalContext implements JsonMarshalContext {

    private Map<String, Object> contextMap = new HashMap<>();
    private Map<RadioButtonGroup, Integer> serializeRadioGroupMap = new HashMap<>();
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
