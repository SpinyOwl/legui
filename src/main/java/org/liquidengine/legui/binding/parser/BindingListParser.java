package org.liquidengine.legui.binding.parser;

import java.util.HashMap;
import java.util.Map;
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

    private Map<Class, ClassBinding> bindings = new HashMap<>();

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
            default: break;
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
                default: break;
            }
            if (path != null && className != null) {
                Class<?> aClass = null;
                try {
                    aClass = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (aClass == null) {
                    return;
                }
                ClassBinding binding = BindingParserService.getInstance().parseBinding(path);
                if (binding != null) {
                    Class bType = binding.getBindingForType();
                    if (bType != aClass && !bType.isAssignableFrom(aClass)) {
                        System.out.println("Binding skipped. "
                            + "Binding type '" + aClass.getCanonicalName() + "' is not instance of '"
                            + bType.getCanonicalName() + "' specified in '" + path + "'.");
                    } else {
                        bindings.put(aClass, binding);
                    }
                }
            }
        }
    }

    /**
     * Returns parsed bindings.
     *
     * @return bindings map.
     */
    public Map<Class, ClassBinding> getBindings() {
        return bindings;
    }
}
