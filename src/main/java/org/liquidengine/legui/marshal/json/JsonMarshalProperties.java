package org.liquidengine.legui.marshal.json;

/**
 * Holds special json properties names.
 *
 * @author Aliaksandr_Shcherbin.
 */
public final class JsonMarshalProperties {

    /**
     * Type property used as name for class alias in json.
     */
    public static final String TYPE_PROPERTY = "@type";
    /**
     * Type property used as name for class name in json.
     */
    public static final String CLASS_PROPERTY = "@class";

    /**
     * Private constructor to avoid instantiations.
     */
    private JsonMarshalProperties() {
    }

}
