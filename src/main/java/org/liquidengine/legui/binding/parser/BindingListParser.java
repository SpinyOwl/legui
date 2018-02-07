package org.liquidengine.legui.binding.parser;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser for binding list.
 *
 * @author ShchAlexander.
 */
public class BindingListParser extends DefaultHandler {
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * Map of bindings.
     */
    private Map<Class, AbstractClassBinding> bindings = new LinkedHashMap<>();

    /**
     * Receive notification of the start of an element. <p> <p>By default, do nothing.  Application writers may override this method in a subclass to take
     * specific actions at the start of each element (such as allocating a new tree node or writing output to a file).</p>
     *
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If there are no attributes, it shall be an empty Attributes object.
     *
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see ContentHandler#startElement
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName == null) {
            return;
        }
        switch (localName) {
            case "binding":
                addBinding(attributes);
                break;
            case "custom-binding":
                addCustomBinding(attributes);
                break;
            default:
                break;
        }
    }

    /**
     * Used to create custom binding.
     *
     * @param attributes attributes.
     */
    private void addCustomBinding(Attributes attributes) {
        String className = null;
        String bindingClassName = null;

        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String aName = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            switch (aName) {
                case "for":
                    className = value;
                    break;
                case "class":
                    bindingClassName = value;
                    break;
                default:
                    break;
            }
        }
        if (className != null && bindingClassName != null) {

            Class<?> bForType = null;
            try {
                bForType = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (bForType == null) {
                return;
            }

            Class<AbstractClassBinding> classBindingClass = null;
            try {
                classBindingClass = (Class<AbstractClassBinding>) Class.forName(bindingClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (classBindingClass == null) {
                return;
            }

            try {
                AbstractClassBinding binding = classBindingClass.newInstance();
                Class bType = binding.getBindingForType();
                if (!bForType.equals(bType) && !bType.isAssignableFrom(bForType)) {
                    LOGGER.warn("Binding skipped. "
                        + "Binding type '" + bForType.getCanonicalName() + "' is not instance of '"
                        + bType.getCanonicalName() + "' specified in '" + bindingClassName + "'.");
                } else {
                    bindings.put(bForType, binding);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to add binding.
     *
     * @param attributes attributes.
     */
    private void addBinding(Attributes attributes) {
        String path = null;
        String className = null;

        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String aName = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            switch (aName) {
                case "for":
                    className = value;
                    break;
                case "is":
                    path = value;
                    break;
                default:
                    break;
            }
        }
        if (path != null && className != null) {
            Class<?> bForType = null;
            try {
                bForType = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (bForType == null) {
                return;
            }
            ClassBinding binding = BindingParserService.getInstance().parseBinding(path);
            if (binding != null) {
                Class bType = binding.getBindingForType();
                if (bType != bForType && !bType.isAssignableFrom(bForType)) {
                    LOGGER.warn("Binding skipped. "
                        + "Binding type '" + bForType.getCanonicalName() + "' is not instance of '"
                        + bType.getCanonicalName() + "' specified in '" + path + "'.");
                } else {
                    bindings.put(bForType, binding);
                }
            }
        }
    }

    /**
     * Returns parsed bindings.
     *
     * @return bindings map.
     */
    public Map<Class, AbstractClassBinding> getBindings() {
        return bindings;
    }
}
